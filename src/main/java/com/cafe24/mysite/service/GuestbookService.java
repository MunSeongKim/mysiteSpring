package com.cafe24.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.mysite.repository.GuestbookDAO;
import com.cafe24.mysite.vo.GuestbookVO;

@Service
public class GuestbookService {
    @Autowired
    private GuestbookDAO guestbookDao;
    
    private static final Long count = 5L;
    
    public List<GuestbookVO> getList(Long start) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("start", start);
	map.put("count", count);
	return guestbookDao.readAll( map );
    }
    
    public void write(GuestbookVO vo) {
	guestbookDao.create(vo);
    }
    
    public void delete(GuestbookVO vo) {
	guestbookDao.delete( vo );
    }
    
    public Long getCount() {
	return guestbookDao.readCount();
    }
}
