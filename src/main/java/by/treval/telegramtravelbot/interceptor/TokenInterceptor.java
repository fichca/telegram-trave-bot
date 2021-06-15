package by.treval.telegramtravelbot.interceptor;

import by.treval.telegramtravelbot.config.BotConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    private BotConfig config;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String header = request.getHeader("X-Api-Token");
        if (validToken(header)){
            return true;
        }
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalidate token");
        return false;
    }

    private boolean validToken(String token) {
        return config.getToken().equals(token);
    }
}
