package com.injin.jwtstudy.config.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//스프링 시큐리티에서 UsernamePasswordAuthenticationFilter 가 있음.
// login 요청 -> post 로 username, password 전송하면 UsernamePasswordAuthenticationFilter 필터가 동작함
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    // /login 요청을 하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("JwtAuthenticationFilter : 로그인 시도중");

        // 1.username, password 받는다

        // 2.정상인지 로그인 시도를 해본다.
        // -> authenticationManager 로 로그인 시도를 하면 PrincipalDetailsService 가 호출 하여 loadUserByUsername() 함수가 실행됨.


        // 3.principalDetails 를 세션에 담고 (권한 관리를 위해서 담는다)

        // 4.JWT 토큰읋 만들어서 응답해주면 됨.
        return super.attemptAuthentication(request, response);
    }
}
