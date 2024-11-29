package com.ict.edu3.domain.auth.vo;

import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataVO {
    // 클라이언트에 결과 정보를 보낼때 사용
    private boolean success;
    private Object data;
    private String token;
    private String message;
    private UserDetails userDetails;

    // 사용 방법
    // DataVO dataVO = new DataVO()
    // dataVO.setSuccess(true);
    // dataVO.setData(list) 또는 dataVO.setData(MemberVO)
    // dataVO.setToken(token)

    // 사용 방법2
    // Map<String,Object> resultMap = new HashMap<>();
    // resultMap.put("list", MemberList)
    // resultMap.put("rowTotal", rowTotal)
}
