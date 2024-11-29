package com.ict.edu3.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ict.edu3.common.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// 토큰을 추출하고, 유효성 검사하여 , 유효한 경우만 사용자 정보를 로드,
// 즉, 보호된 리소스에 대한 접근이 가능한 사용자인지 확인 
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;

    // Spring Security가 UserDetailsService를 호출하여 사용자 정보를 데이터베이스에서 가져오는 역할
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("JwtRequestFilter");
        // 요청 헤더에서 Authorization 값 확인
        final String requestTokenHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;

        // 토큰이 있으며 Authorization 안에 'Bearer '로 시작
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            // 토큰 추출
            jwtToken = requestTokenHeader.substring(7);

            try {
                // 이름추출 (id)
                username = jwtUtil.extractuserName(jwtToken);
            } catch (Exception e) {
                System.out.println("JWT 오류");
                logger.warn("JWT Token error");
            }
        } else {
            System.out.println("JWT 없음");
            logger.warn("JWT Token empty");
        }

        // 사용자이름(아이디)추출, 현재SecurityContext에 인증정보가 설정되었는지 확인 없으면 다음 단계진행
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // 사용자이름을 가지고 현재 DB에 있는지 검사
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwtToken, userDetails)) {
                // Spring Security 인증객체 생성
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                // Spring Security 인증객체 추가 세부 정보를 설정
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Spring Security 컨텍스트에 저장
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);

    }

}
