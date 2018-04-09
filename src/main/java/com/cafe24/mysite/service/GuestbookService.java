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
    
    
    public List<GuestbookVO> getList(Long no) {
	return guestbookDao.readAll( no );
    }
    
    public List<GuestbookVO> getList() {
	return guestbookDao.readAll();
    }
    
    public void write(GuestbookVO vo) {
	guestbookDao.create(vo);
    }
    
    public GuestbookVO writeAPI(GuestbookVO guestbook){
	GuestbookVO vo = null;
	int count = guestbookDao.create(guestbook);
	if( count == 1 ){
	    vo = guestbookDao.read( guestbook.getNo() );
	}
	return vo;
    }
    
    public boolean delete(GuestbookVO vo) {
	return guestbookDao.delete( vo );
    }
    
    public Long getCount() {
	return guestbookDao.readCount();
    }
}
