package com.ict.edu3.domain.guestbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ict.edu3.domain.guestbook.vo.GuestBookVO;

@Mapper
public interface GuestBookMapper {

    List<GuestBookVO> getGuestBookList();

    GuestBookVO getGuestBookById(String gb_idx);

    int getGuestBookUpdate(GuestBookVO gvo);

    int getGuestBookDelete(String gb_idx);

    int getGuestBookWrite(GuestBookVO gvo);
}
