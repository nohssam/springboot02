package com.ict.edu3.domain.auth.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ict.edu3.common.util.JwtUtil;
import com.ict.edu3.domain.auth.vo.DataVO;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthAPIController {
    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/test")
    public String getMethodName() {
        return "Hello, Spring Boot";
    }

    @PostMapping("/generate-token")
    public String generateToken(@RequestBody Map<String, String> request) {

        // 클라이어트가 uesrname라는 key에 정보를 보냈다고 가정
        String username = request.get("username");

        // jwt를 생성할때 더 많은 정보를 추가 할 수있다.
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "USER");

        return jwtUtil.generateToken(username, claims);
    }

    @PostMapping("/validate-token")
    public DataVO validateToken(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        log.info("token : ", token);
        return null;
    }

}
