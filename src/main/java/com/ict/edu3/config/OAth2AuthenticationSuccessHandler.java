package com.ict.edu3.config;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.ict.edu3.common.util.JwtUtil;
import com.ict.edu3.domain.auth.service.MyUserDetailService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class OAth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;
    private final MyUserDetailService userDetailService;

    public OAth2AuthenticationSuccessHandler(JwtUtil jwtUtil, MyUserDetailService userDetailService) {
        this.jwtUtil = jwtUtil;
        this.userDetailService = userDetailService;
    }

    // 실제 성공한 다음에 클라이언트로 리다이렉트 해주는 메서드
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        try {

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("/login?error");
        }
    }
}
