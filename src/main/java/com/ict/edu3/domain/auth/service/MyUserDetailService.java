package com.ict.edu3.domain.auth.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.ict.edu3.domain.auth.mapper.AuthMapper;
import com.ict.edu3.domain.auth.vo.MembersVO;
import com.ict.edu3.domain.auth.vo.UserVO;
import com.ict.edu3.domain.members.mapper.MembersMapper;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private AuthMapper authMapper;
    @Autowired
    private MembersMapper membersMapper;

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

    // 정보를 받아서 DB에 저장 하기
    public UserDetails loadUserByOAuth2User(OAuth2User oAuth2User, String provider) {
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        String id = "";
        MembersVO mvo = new MembersVO();
        if (provider.equals("kakao")) {
            Long kakaoId = oAuth2User.getAttribute("id");
            id = String.valueOf(kakaoId);
            mvo.setSns_email_kakao(email);
            mvo.setM_name(name);
            mvo.setM_id(id);
            mvo.setSns_provider("kakao");

        } else if (provider.equals("naver")) {
            id = oAuth2User.getAttribute("id");
            mvo.setSns_email_naver(email);
            mvo.setM_name(name);
            mvo.setM_id(id);
            mvo.setSns_provider("naver");
        }
        // 아이디가 존재하면 DB에 있는 것, 아니면 DB에 없는 것
        MembersVO mvo2 = membersMapper.findUserByProvider(mvo);
        if (mvo2 == null) {
            membersMapper.insertUser(mvo);
        }
        return new User(mvo.getM_id(), "", new ArrayList<>());
    }
}
