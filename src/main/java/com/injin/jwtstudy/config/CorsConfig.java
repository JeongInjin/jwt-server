package com.injin.jwtstudy.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        /* config.setAllowCredentials(true);
            내 서버가 응답 할때 json 을 자바스크립트에서 처리할 수 있게 할지 설정
            view(ajax, fetch, axios 등의 요청 -> 응답을 자바스크립트에서 받을 수 있는지에 대한 할것인지에 대한 설정
            false = 응답이 오지 않는다.
         */
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*"); // 모든 IP 에 대한 응답을 허용하겠다.
        config.addAllowedHeader("*"); // 모든 header 에 응답을 허용하겠다.
        config.addAllowedMethod("*"); // 모든 method 의 요청을 허용허겠다.

        source.registerCorsConfiguration("/api/**", config);

        return new CorsFilter(source);
    }
}




