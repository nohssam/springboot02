package com.ict.edu3.domain.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.edu3.common.util.JwtUtil;
import com.ict.edu3.domain.auth.vo.DataVO;
import com.ict.edu3.domain.auth.vo.MembersVO;
import com.ict.edu3.domain.auth.vo.UserVO;

@Service
public class AuthService {
    @Autowired
    private MyUserDetailService myUserDetailService;

    @Autowired
    private JwtUtil jwtUtil;

    public DataVO authenticate(MembersVO mvo) {
        DataVO dataVO = new DataVO();
        try {

            // 실제 DB
            UserVO uvo = myUserDetailService.getUserDetail(mvo.getM_id());
            String jwt = jwtUtil.generateToken(uvo.getM_id());

            dataVO.setSuccess(true);
            dataVO.setToken(jwt);
            dataVO.setUserDetails(uvo);
            return dataVO;
        } catch (Exception e) {
            dataVO.setSuccess(false);
            dataVO.setMessage(e.getMessage());
            return dataVO;
        }
    }

}
