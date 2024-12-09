package com.ict.edu3.config;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.ict.edu3.common.util.JwtUtil;
import com.ict.edu3.domain.auth.service.MyUserDetailService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
            log.info("OAth2AuthenticationSuccessHandler");
            // authentication.getPrincipal가 OAuth2User(사용자정보를 가지고 있는객체)타입인지 확인한다.
            // authentication 객체는 현재 사용자의 인증상태
            // getPrincipal() 가지고 있는 사용자 정보를 반환
            if (authentication.getPrincipal() instanceof OAuth2User) {
                OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
                String uri = request.getRequestURI();
                String provider = "";
                if (uri.contains("kakao")) {
                    provider = "kakao";
                } else if (uri.contains("naver")) {
                    provider = "naver";
                } else {
                    provider = "unknown";
                }

                // 성공 후 토큰을 만들어서 클라이언트에게 리다이렉트 한다.
                String id = oAuth2User.getAttribute("id").toString();
                String name = oAuth2User.getAttribute("name");
                String email = oAuth2User.getAttribute("email");
                String token = jwtUtil.generateToken(id);

                // 클라이언트에 토큰, 이름, email 등 정보를 가지고 간다.
                String redirectUrl = String.format(
                        "http://localhost:3000/login?token=%s&username=%s&name=%s&email=%s",
                        URLEncoder.encode(token, StandardCharsets.UTF_8),
                        URLEncoder.encode(id, StandardCharsets.UTF_8),
                        URLEncoder.encode(name, StandardCharsets.UTF_8),
                        URLEncoder.encode(email, StandardCharsets.UTF_8));

                response.sendRedirect(redirectUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("/login?error");
        }
    }

}
