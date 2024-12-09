package com.ict.edu3.domain.members.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ict.edu3.domain.auth.vo.MembersVO;

@Mapper
public interface MembersMapper {
    public int getMembersJoin(MembersVO mvo);

    public MembersVO getMembersById(String m_id);

    public MembersVO findUserByProvider(MembersVO mvo);

    public void insertUser(MembersVO mvo);
}
