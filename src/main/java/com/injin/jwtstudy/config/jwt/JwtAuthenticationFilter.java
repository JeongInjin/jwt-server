package com.injin.jwtstudy.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.injin.jwtstudy.config.auth.PrincipalDetails;
import com.injin.jwtstudy.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//스프링 시큐리티에서 UsernamePasswordAuthenticationFilter 가 있음.
// login 요청 -> post 로 username, password 전송하면 UsernamePasswordAuthenticationFilter 필터가 동작함
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    // /login 요청을 하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("JwtAuthenticationFilter : 로그인 시도중");
        System.out.println("request.userEmail = " + request.getParameter("userEmail"));


        try {
//            BufferedReader br = request.getReader();
//            String input = null;
//            while ((input = br.readLine()) != null) {
//                System.out.println("input = " + input);
//            }

            // 1.username, password 받는다 (Json 으로 던짐)
            ObjectMapper om = new ObjectMapper();
            User user = om.readValue(request.getInputStream(), User.class);
            System.out.println("user = " + user);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

            // 2.정상인지 로그인 시도를 해본다.
            // -> authenticationManager 로 로그인 시도를 하면
            // PrincipalDetailsService 가 호출 하여 loadUserByUsername() 함수가 실행된 후 정상이면 authentication 이 리턴됨.
            // DB 에 있는 username과 password 가 일치한다.
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            System.out.println("로그인 완료됨  principalDetails.getUser().getUsername() = " + principalDetails.getUser().getUsername()); // 정상 출력 -> 로그인 정상적으로 되었다는 뜻.
            // authentication 객체가 session 영역에 저장을 해야하고 return 해주면 됨.
            // 리턴의 이유는 권한 관리를 security 가 대신 해주기 때문에 편하려고 하는거임.
            // 굳이 JWT 토큰을 사용하면서 세션을 만들 이유가 없음. 단지 권한 처리때문에 session 에 넣어 줍니다.

            return authentication;
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("=====================================================================");

        return null;
    }

    // attemptAuthentication 실행 후 인증이 정상적으로 되었으면 successfulAuthentication 함수가 실행됨
    // JWT 토큰을 만들어서 request 요청한 사용자에게 JWT 토큰을 response 해주면 됨.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("successfulAuthentication 실행됨 : 인증이 완료되었음을 의미");
        super.successfulAuthentication(request, response, chain, authResult);
    }
}
