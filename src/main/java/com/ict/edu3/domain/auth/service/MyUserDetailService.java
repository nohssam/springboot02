package com.ict.edu3.domain.auth.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ict.edu3.domain.auth.mapper.AuthMapper;
import com.ict.edu3.domain.auth.vo.UserVO;

public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private AuthMapper authMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserVO member = authMapper.selectMember(username);
        if (member == null) {
            throw new UsernameNotFoundException("없은 아이디 입니다.");
        }
        return new User(member.getM_id(), member.getM_pw(), new ArrayList<>());
    }

    // DB에서 개인 정보 추출,
    public UserVO getUserDetail(String m_id) {
        return authMapper.selectMember(m_id);
    }

}
