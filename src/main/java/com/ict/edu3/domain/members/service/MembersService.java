package com.ict.edu3.domain.members.service;

import com.ict.edu3.domain.auth.vo.MembersVO;

public interface MembersService {
    public int getMembersJoin(MembersVO mvo);

    public MembersVO getMembersById(String m_id);
}
