package com.ict.edu3.domain.auth.mapper;

import org.apache.ibatis.annotations.Param;

import com.ict.edu3.domain.auth.vo.UserVO;

public interface AuthMapper {
    UserVO selectMember(@Param("m_id") String m_id);
}
