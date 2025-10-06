package com.solaceisle.task;

import com.alibaba.fastjson2.JSON;
import com.solaceisle.constant.RemindConstant;
import com.solaceisle.context.BaseContext;
import com.solaceisle.mapper.UserMapper;
import com.solaceisle.pojo.vo.WebsocketVO;
import com.solaceisle.socketserver.WebSocketServer;

import jakarta.websocket.Session;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * 定时提醒任务：通过 WebSocket 群发提醒消息。
 */
@RequiredArgsConstructor
@Component
public class RemindTask {

    private static final Logger log = LoggerFactory.getLogger(RemindTask.class);

    private final WebSocketServer webSocketServer;

    private final RedisTemplate<String, Object> redisTemplate;

    private final UserMapper userMapper;
    /**
     * 每天 0 点：发送前三条提醒（树洞 / 日记 / CBT）
     */
    @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Shanghai")
    public void sendMidnightReminders() {
        log.info("[RemindTask] 发送 0 点通用提醒");
        List<String> userIds = userMapper.listAllUserId();
        for (Map.Entry<String, String> e : RemindConstant.FIRST_THREE_REMINDERS.entrySet()) {
            sendReminder(e.getValue(), e.getKey());
        }
        ConcurrentMap<String, Session> sessionMap = webSocketServer.getSessionMap();
        for(String id:userIds){
            if(!sessionMap.containsKey(String.valueOf(id))){
                for (var entity:RemindConstant.FIRST_THREE_REMINDERS.entrySet())
                {
                    redisTemplate.opsForHash().put(String.valueOf(id), entity.getKey(), entity.getValue());
                }
            }
        }
    }

    /**
     * 每天 07:00 发送 早安 + 早餐 提醒
     */
    @Scheduled(cron = "0 0 7 * * ?", zone = "Asia/Shanghai")
    public void sendMorningReminders() {
        log.info("[RemindTask] 发送 07:00 早安/早餐提醒");
        sendReminder(RemindConstant.MORNING_GREETING_VALUE, RemindConstant.MORNING_GREETING_KEY);
        sendReminder(RemindConstant.BREAKFAST_REMIND_VALUE, RemindConstant.BREAKFAST_REMIND_KEY);
        cacheGreeting(RemindConstant.MORNING_GREETING_VALUE);
    }
    private void cacheGreeting(String value){
        List<String> userIds = userMapper.listAllUserId();
        ConcurrentMap<String, Session> sessionMap = webSocketServer.getSessionMap();
        for(String id:userIds){
            if(!sessionMap.containsKey(String.valueOf(id))){
                redisTemplate.opsForHash().put(String.valueOf(id), RemindConstant.GREETINGS, value);
            }
        }

    }
    /**
     * 每天 11:00 发送 午安 + 午餐 提醒
     */
    @Scheduled(cron = "0 0 11 * * ?", zone = "Asia/Shanghai")
    public void sendNoonReminders() {
        log.info("[RemindTask] 发送 11:00 午安/午餐提醒");
        sendReminder(RemindConstant.NOON_GREETING_VALUE, RemindConstant.NOON_GREETING_KEY);
        sendReminder(RemindConstant.LUNCH_REMIND_VALUE, RemindConstant.LUNCH_REMIND_KEY);
        cacheGreeting(RemindConstant.NOON_GREETING_VALUE);
    }

    /**
     * 每天 17:00 发送 晚间问候 + 晚餐 提醒
     */
    @Scheduled(cron = "0 0 17 * * ?", zone = "Asia/Shanghai")
    public void sendEveningReminders() {
        log.info("[RemindTask] 发送 17:00 晚间/晚餐提醒");
        sendReminder(RemindConstant.EVENING_GREETING_VALUE, RemindConstant.EVENING_GREETING_KEY);
        sendReminder(RemindConstant.DINNER_REMIND_VALUE, RemindConstant.DINNER_REMIND_KEY);
        cacheGreeting(RemindConstant.EVENING_GREETING_VALUE);
    }

    /**
     * 每天 21:00 发送 深夜休息 提醒
     */
    @Scheduled(cron = "0 0 21 * * ?", zone = "Asia/Shanghai")
    public void sendLateNightReminder() {
        log.info("[RemindTask] 发送 21:00 深夜提醒");
        sendReminder(RemindConstant.LATE_NIGHT_VALUE, RemindConstant.LATE_NIGHT_KEY);
        cacheGreeting(RemindConstant.LATE_NIGHT_VALUE);
    }

    /**
     * 发送提醒消息
     * @param value
     * @param key
     */
    private void sendReminder(String value, String key) {
        WebsocketVO remind = WebsocketVO.remind(value);
        String payload = JSON.toJSONString(remind);
        sendToAllClient(payload, key);
    }

    /**
     * 发送成就解锁消息
     * @param websocketVO
     */
    private void sendAchievement(WebsocketVO websocketVO){
        String jsonString = JSON.toJSONString(websocketVO);
        // TODO 怎么拿到当前用户ID
        sendToClient(jsonString, "achievement" , BaseContext.getCurrentId());
    }

    /**
     * 通过 WebSocket 群发消息
     * @param value
     * @param key
     */
    private void sendToAllClient(String value, String key){
        try {
            webSocketServer.sendToAllClient(value);
        } catch (Exception ex) {
            log.warn("[RemindTask] 发送提醒失败 key={}, value={}, error={}", key, value, ex.getMessage(), ex);
        }
    }

    /**
     * 通过 WebSocket 给指定用户发送消息
     * @param userId
     * @param value
     * @param key
     */
    private void sendToClient(String userId, String value, String key){
        try {
            webSocketServer.sendToClient(userId, value);
        } catch (Exception ex) {
            log.warn("[RemindTask] 发送提醒失败 key={}, value={}, error={}", key, value, ex.getMessage(), ex);
        }
    }
}
