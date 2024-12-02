package com.ict.edu3.domain.members.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    // private final PasswordEncoder passwordEncoder;
    // public MembersController(PasswordEncoder passwordEncoder) {
    // this.passwordEncoder = passwordEncoder;
    // }

    @PostMapping("/join")
    public DataVO membersJoin(@RequestBody MembersVO mvo) {
        DataVO dataVO = new DataVO();

        // String rawPassword = mvo.getM_pw();
        // String encodePassword = passwordEncoder.encode(rawPassword);
        // mvo.setM_pw(encodePassword);
        // log.info("m_pw : " + mvo.getM_pw());

        mvo.setM_pw(passwordEncoder.encode(mvo.getM_pw()));

        int result = membersService.getMembersJoin(mvo);
        if (result > 0) {
            dataVO.setSuccess(true);
            dataVO.setMessage("회원가입 성공");
        } else {
            dataVO.setSuccess(false);
            dataVO.setMessage("회원가입 실패");
        }
        return dataVO;
    }
}
