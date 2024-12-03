package com.ict.edu3.domain.guestbook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ict.edu3.domain.auth.vo.DataVO;
import com.ict.edu3.domain.guestbook.service.GuestBookService;
import com.ict.edu3.domain.guestbook.vo.GuestBookVO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RestController
@RequestMapping("/api/guestbook")
public class GuestBookController {
    @Autowired
    private GuestBookService guestBookService;

    @GetMapping("/list")
    public DataVO getGuestBookList() {
        DataVO dataVO = new DataVO();
        try {
            List<GuestBookVO> list = guestBookService.getGuestBookList();
            dataVO.setSuccess(true);
            dataVO.setMessage("게스트북 조회 성공");
            dataVO.setData(list);
        } catch (Exception e) {
            dataVO.setSuccess(false);
            dataVO.setMessage("게스트북 조회 실패");
        }
        return dataVO;
    }

}
