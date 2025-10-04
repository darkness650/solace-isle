package com.solaceisle.task;

import com.solaceisle.constant.RemindConstant;
import com.solaceisle.mapper.UserMapper;
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
import java.util.concurrent.ConcurrentHashMap;
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
            send(e.getValue(), e.getKey());
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
        send(RemindConstant.MORNING_GREETING_VALUE, RemindConstant.MORNING_GREETING_KEY);
        send(RemindConstant.BREAKFAST_REMIND_VALUE, RemindConstant.BREAKFAST_REMIND_KEY);
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
        send(RemindConstant.NOON_GREETING_VALUE, RemindConstant.NOON_GREETING_KEY);
        send(RemindConstant.LUNCH_REMIND_VALUE, RemindConstant.LUNCH_REMIND_KEY);
        cacheGreeting(RemindConstant.NOON_GREETING_VALUE);
    }

    /**
     * 每天 17:00 发送 晚间问候 + 晚餐 提醒
     */
    @Scheduled(cron = "0 0 17 * * ?", zone = "Asia/Shanghai")
    public void sendEveningReminders() {
        log.info("[RemindTask] 发送 17:00 晚间/晚餐提醒");
        send(RemindConstant.EVENING_GREETING_VALUE, RemindConstant.EVENING_GREETING_KEY);
        send(RemindConstant.DINNER_REMIND_VALUE, RemindConstant.DINNER_REMIND_KEY);
        cacheGreeting(RemindConstant.EVENING_GREETING_VALUE);
    }

    /**
     * 每天 21:00 发送 深夜休息 提醒
     */
    @Scheduled(cron = "0 0 21 * * ?", zone = "Asia/Shanghai")
    public void sendLateNightReminder() {
        log.info("[RemindTask] 发送 21:00 深夜提醒");
        send(RemindConstant.LATE_NIGHT_VALUE, RemindConstant.LATE_NIGHT_KEY);
        cacheGreeting(RemindConstant.LATE_NIGHT_VALUE);
    }

    /**
     * 实际发送逻辑：带上描述方便前端区分。
     * 这里简单拼接成: [描述] 提示内容
     */
    private void send(String value, String key) {
        String payload =  value;
        try {
            webSocketServer.sendToAllClient(payload);
        } catch (Exception ex) {
            log.warn("[RemindTask] 发送提醒失败 key={}, value={}, error={}", key, value, ex.getMessage(), ex);
        }
    }
}
