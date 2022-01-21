package com.injin.jwtstudy.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MyFilter3 implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("필터 3");
        /**
         * 토큰을 만들어줘야함
         * 언제? - id, pw 를 정상입력하여 로그인 되었을시에 토큰을 만들어주고 응답해준다.
         * 요청할 때 마다 header 에 Authorization 에 value 값으로 토큰을 가지고 온다.
         * 토큰이 넘어오면 해당 토큰이 서버에서 만든 토큰이 맞는지 검증하면 된다. (RSA, HS256)
         */

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if (req.getMethod().equals("POST")) {
            System.out.println("POST 요청 들어옴.");
            String headerAuth = req.getHeader("Authorization");
            System.out.println("headerAuth = " + headerAuth);

            if (headerAuth != null && headerAuth.equals("jeong")) {
                chain.doFilter(req, res);
            } else {
                PrintWriter out = res.getWriter();
                out.println("인증안됨");
            }
        }

//        chain.doFilter(request, response);
    }
}
