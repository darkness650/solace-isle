package com.solaceisle.aspact;

import com.solaceisle.annotation.AutoFill;
import com.solaceisle.constant.AchievementConstants;
import com.solaceisle.constant.RemindConstant;
import com.solaceisle.context.BaseContext;
import com.solaceisle.mapper.AchievementMapper;
import com.solaceisle.mapper.CBTMapper;
import com.solaceisle.mapper.DiaryMapper;
import com.solaceisle.mapper.SafeSpaceMapper;
import com.solaceisle.pojo.enumeration.OperatorType;
import com.solaceisle.socketserver.WebSocketServer;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@RequiredArgsConstructor
// TODO
//@Component
//@Aspect
@Slf4j
public class AutoFillAspact {

    private final DiaryMapper diaryMapper;
    private final SafeSpaceMapper safeSpaceMapper;
    private final AchievementMapper achievementMapper;
    private final CBTMapper cbtMapper;
    private final WebSocketServer webSocketServer;
    @Pointcut("execution(* com.solaceisle.mapper.*.*(..)) && @annotation(com.solaceisle.annotation.AutoFill)")
    public void autoFIllPointCut(){}
    @Pointcut("execution(* com.solaceisle.mapper.*.*(..)) && @annotation(com.solaceisle.annotation.Achieve)")
    public void sendMessage(){}
    @After("autoFIllPointCut()")
    public void autoFill(JoinPoint joinPoint){
        MethodSignature signature =(MethodSignature) joinPoint.getSignature();
        AutoFill autoFill=signature.getMethod().getAnnotation(AutoFill.class);
        OperatorType operationType=autoFill.value();

        if(operationType==OperatorType.DIARY) {
            String studentId = BaseContext.getCurrentId();
            Integer sumDiary= diaryMapper.getSumDiary(studentId);
            Integer maxConsecutiveDays=achievementMapper.getMaxProcess(studentId);
            if(sumDiary==1){
                achievementMapper.achieve(studentId, AchievementConstants.FIRST_DIARY, LocalDateTime.now());
                if(maxConsecutiveDays==null)
                {
                    achievementMapper.recordConsecutiveDays(studentId,1);
                }
            }
            else if(sumDiary==7){
                achievementMapper.achieve(studentId,AchievementConstants.DIARY_7, LocalDateTime.now());
            }
            else if(sumDiary==30){
                achievementMapper.achieve(studentId,AchievementConstants.DIARY_30, LocalDateTime.now());
            }
            int consecutiveDays=diaryMapper.getCurrentMood(studentId).getConsecutiveDays();
            if(consecutiveDays>maxConsecutiveDays)
            {
                achievementMapper.recordConsecutiveDays(studentId,consecutiveDays);
                if(consecutiveDays==3)
                {
                    achievementMapper.achieve(studentId,AchievementConstants.STREAK_3, LocalDateTime.now());
                }
                else if(consecutiveDays==7)
                {
                    achievementMapper.achieve(studentId,AchievementConstants.DIARY_7, LocalDateTime.now());
                }
                else if(consecutiveDays==10)
                {
                    achievementMapper.achieve(studentId,AchievementConstants.GIVE_LIKE_10, LocalDateTime.now());
                }
                else if(consecutiveDays==14)
                {
                    achievementMapper.achieve(studentId,AchievementConstants.STREAK_14, LocalDateTime.now());
                }
            }
        }
        else if(operationType==OperatorType.SAFE)
        {
            int safeCount=safeSpaceMapper.getSafeCount(BaseContext.getCurrentId());
            if(safeCount==1){
                achievementMapper.achieve(BaseContext.getCurrentId(),AchievementConstants.FIRST_SAFE_POST, LocalDateTime.now());
            }
            else if(safeCount==10){
                achievementMapper.achieve(BaseContext.getCurrentId(),AchievementConstants.SAFE_POST_10, LocalDateTime.now());
            }
        }
        else if(operationType==OperatorType.LIKE)
        {
            Object[] args = joinPoint.getArgs();
            Integer likeSafeSpaceid=(Integer) args[1];
            Integer sumLikes=safeSpaceMapper.getSumLikes(BaseContext.getCurrentId());
            if(sumLikes==10)
            {
                try{
                    achievementMapper.achieve(BaseContext.getCurrentId(),AchievementConstants.GIVE_LIKE_10, LocalDateTime.now());
                }
                catch(Exception e){
                    log.info("用户{}已获得点赞成就",BaseContext.getCurrentId());
                }
            }
            String likedId=safeSpaceMapper.getLikedId(likeSafeSpaceid);
            sumLikes=safeSpaceMapper.getSumLikes(likedId);
            if(sumLikes==10)
            {
                try{
                    achievementMapper.achieve(likedId,AchievementConstants.SAFE_LIKED_10, LocalDateTime.now());
                }
                catch(Exception e){
                    log.info("用户{}已获得点赞成就",likedId);
                }
            }
        }
        else if(operationType==OperatorType.CBT)
        {
            try{
                if(cbtMapper.getDoneCBTIds(BaseContext.getCurrentId()).size()==1) {
                    achievementMapper.achieve(BaseContext.getCurrentId(), AchievementConstants.FIRST_CBT, LocalDateTime.now());
                }
            }catch(Exception e){
                log.info("用户{}已获得CBT成就",BaseContext.getCurrentId());
            }
        }
    }
    @After("sendMessage()")
    public void sendMessageAfter(JoinPoint joinPoint){
        webSocketServer.sendToClient(BaseContext.getCurrentId(), RemindConstant.CONGRATULATIONS);
    }
}
