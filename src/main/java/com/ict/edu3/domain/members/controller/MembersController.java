package com.ict.edu3.domain.members.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ict.edu3.common.util.JwtUtil;
import com.ict.edu3.domain.auth.vo.DataVO;
import com.ict.edu3.domain.auth.vo.MembersVO;
import com.ict.edu3.domain.members.service.MembersService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/members")
public class MembersController {
    @Autowired
    private MembersService membersService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // private final PasswordEncoder passwordEncoder;
    // public MembersController(PasswordEncoder passwordEncoder) {
    // this.passwordEncoder = passwordEncoder;
    // }

    @PostMapping("/join")
    public DataVO membersJoin(@RequestBody MembersVO mvo) {
        DataVO dataVO = new DataVO();
        try {
            // String rawPassword = mvo.getM_pw();
            // String encodePassword = passwordEncoder.encode(rawPassword);
            // mvo.setM_pw(encodePassword);
            // log.info("m_pw : " + mvo.getM_pw());

            mvo.setM_pw(passwordEncoder.encode(mvo.getM_pw()));

            int result = membersService.getMembersJoin(mvo);
            if (result > 0) {
                dataVO.setSuccess(true);
                dataVO.setMessage("회원가입 성공");
            }
            return dataVO;
        } catch (Exception e) {
            log.error("회원가입 중 오류 발생: {}", e.getMessage());
            dataVO.setMessage("회원가입 실패: ");
            dataVO.setSuccess(false);
            return dataVO;
        }
    }

    @PostMapping("/login")
    public DataVO membersLogin(@RequestBody MembersVO mvo) {
        DataVO dataVO = new DataVO();
        try {
            // 사용자 정보 조회
            MembersVO membersVO = membersService.getMembersById(mvo.getM_id());

            if (membersVO == null) {
                dataVO.setSuccess(false);
                dataVO.setMessage("존재하지 않는 ID 입니다.");
                return dataVO;
            }

            // 비밀번호 검증 받기
            if (!passwordEncoder.matches(mvo.getM_pw(), membersVO.getM_pw())) {
                dataVO.setSuccess(false);
                dataVO.setMessage("비밀번호가 일치하지 않습니다.");
                return dataVO;
            }

            // JWT 토큰 생성 및 전송
            String token = jwtUtil.generateToken(mvo.getM_id());

            // SecurityContext에 인증 객체 설정
            // 다른 컨트롤러, 서비스, 또는 보안 필터에서 인증 정보를 쉽게 가져올 수 있습니다.
            // 인증된 사용자 이름이나 권한을 사용해 요청 처리.
            // 인증 객체가 설정되면, Spring Security의 유틸리티 메서드를 사용해 현재 사용자 정보를 쉽게 가져올 수 있습니다.
            // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            // String username = auth.getName(); // 인증된 사용자 이름

            // UsernamePasswordAuthenticationToken authenticationToken = new
            // UsernamePasswordAuthenticationToken(
            // membersVO.getM_id(), null, null); // 권한 정보 추가 가능
            // SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            // log.info("로그인 성공, SecurityContext에 인증 객체 설정 완료");

            dataVO.setData(membersVO);
            dataVO.setSuccess(true);
            dataVO.setMessage("로그인 성공");
            dataVO.setToken(token);
            return dataVO;

        } catch (Exception e) {
            dataVO.setSuccess(false);
            dataVO.setMessage("네트워크 오류");
            return dataVO;
        }

    }
}
