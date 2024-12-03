package com.ict.edu3.domain.guestbook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.edu3.domain.guestbook.mapper.GuestBookMapper;
import com.ict.edu3.domain.guestbook.vo.GuestBookVO;

@Service
public class GuestBookServiceImpl implements GuestBookService {

    @Autowired
    private GuestBookMapper guestBookMapper;

    @Override
    public List<GuestBookVO> getGuestBookList() {
        return guestBookMapper.getGuestBookList();
    }

    @Override
    public GuestBookVO getGuestBookById(String gb_idx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getGuestBookById'");
    }

    @Override
    public int getGuestBookUpdate(GuestBookVO vo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getGuestBookUpdate'");
    }

    @Override
    public int getGuestBookDelete(String gb_idx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getGuestBookDelete'");
    }

}
