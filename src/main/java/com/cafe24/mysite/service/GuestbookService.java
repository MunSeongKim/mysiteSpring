package com.cafe24.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.mysite.repository.GuestbookDAO;
import com.cafe24.mysite.vo.GuestbookVO;

@Service
public class GuestbookService {
    @Autowired
    private GuestbookDAO guestbookDao;
    
    public List<GuestbookVO> getList() {
	return guestbookDao.readAll();
    }
    
    public void write(GuestbookVO vo) {
	guestbookDao.create(vo);
    }
    
    public void delete(GuestbookVO vo) {
	guestbookDao.delete( vo );
    }
}
