package com.ict.edu3.domain.members.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.edu3.domain.auth.vo.MembersVO;
import com.ict.edu3.domain.members.mapper.MembersMapper;

@Service
public class MembersServiceImpl implements MembersService {
    @Autowired
    private MembersMapper membersMapper;

    @Override
    public int getMembersJoin(MembersVO mvo) {
        return membersMapper.getMembersJoin(mvo);
    }

    @Override
    public MembersVO getMembersById(String m_id) {
        return membersMapper.getMembersById(m_id);
    }

}
