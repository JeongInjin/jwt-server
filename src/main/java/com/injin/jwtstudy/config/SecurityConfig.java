package com.injin.jwtstudy.config;

import com.injin.jwtstudy.config.jwt.JwtAuthenticationFilter;
import com.injin.jwtstudy.filter.MyFilter3;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;

    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * 시큐리티 필터는 내가 만든 필터보다 우선적으로 실행된다.
         * 내가 먼저 실행되는 필터를 작동시키려면 시큐리티필터체인을 찾아 그 앞에 걸어준다.
         */
        http.addFilterBefore(new MyFilter3(), SecurityContextPersistenceFilter.class);
//        http.addFilterAfter(new MyFilter1(), BasicAuthenticationFilter.class);
//        http.addFilterBefore(new MyFilter2(), BasicAuthenticationFilter.class);
        System.out.println("=============================================================");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)// 세션방식을 사용하지 않겠다.
                .and()
                .addFilter(corsFilter) // @Crossorigin(인증x), 시큐리티 필터에 등록 인증(O)
//                .formLogin().disable() // form 태그를 이용한 로그인을 쓰지 않겠다.
                .addFilter(new JwtAuthenticationFilter(authenticationManager())) // AuthenticationManager
                .httpBasic().disable() // 기본적인 http 방식의 로그인은 쓰지 않겠다.
                .authorizeRequests()
                .antMatchers("/api/v1/user/**").access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/api/v1/manager/**").access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/api/v1/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll();
    }
}
