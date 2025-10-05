package com.solaceisle.intercept;

import com.solaceisle.context.BaseContext;
import com.solaceisle.properties.JwtProperties;
import com.solaceisle.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtIntercept implements HandlerInterceptor {
    private final RedisTemplate<String, String> redisTemplate;
    private final JwtProperties jwtProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String header = request.getHeader(jwtProperties.getUserTokenName());
        log.info("拦截到请求: {}", request.getRequestURI());
        if (!(handler instanceof HandlerMethod)) {
            //当前拦截到的不是动态方法，直接放行
            log.info("当前拦截到的不是动态方法，直接放行");
            return true;
        }
        if (header == null || !header.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            log.info("未授权");
            return false;
        }
        log.info("前端发来的原始请求头：{}", header);
        String jwt = header.split(" ")[1];
        try {
            Map<String, Object> claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), jwt);
            String id = claims.get("id").toString();
            String sessionId = claims.get("sessionId").toString();
            String redisValue = redisTemplate.opsForHash().get(id, "sessionId").toString();
            log.info("redisValue:{}", redisValue);
            if (!sessionId.equals(redisValue)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                log.info("未授权,seesionid为：{}", sessionId);
                return false;
            }
            BaseContext.setCurrentId(id);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            log.error(e.getMessage(), e);
            return false;
        }
        log.info("放行");
        return true;
    }

}
