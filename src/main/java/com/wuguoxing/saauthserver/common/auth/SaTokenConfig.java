package com.wuguoxing.saauthserver.common.auth;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SaTokenConfig implements WebMvcConfigurer {

    private final String[] excludePathPatterns = {
            "/auth/login",
            "/auth/refresh",
            "/api-docs/**",
            "/swagger-ui/**"
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaInterceptor(handler -> {
            // 基础接口均需要登录后才可访问
            SaRouter
                    .match("/**")
                    .notMatch(excludePathPatterns)
                    .check(r -> StpUtil.checkLogin());
        })).addPathPatterns("/**");
    }
}
