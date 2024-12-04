package com.ict.edu3.domain.guestbook.service;

import java.util.List;

import com.ict.edu3.domain.guestbook.vo.GuestBookVO;

public interface GuestBookService {
    List<GuestBookVO> getGuestBookList();

    GuestBookVO getGuestBookById(String gb_idx);

    int getGuestBookUpdate(GuestBookVO gvo);

    int getGuestBookDelete(String gb_idx);

    int getGuestBookWrite(GuestBookVO gvo);
}
