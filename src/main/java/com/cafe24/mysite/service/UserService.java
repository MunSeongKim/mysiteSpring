package com.cafe24.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cafe24.mysite.repository.UserDAO;
import com.cafe24.mysite.vo.UserVO;

@Service
public class UserService {
    @Autowired
    private UserDAO userDao;

    public void join( UserVO vo ) {
	userDao.create( vo );
    }
    
    public UserVO getUser( UserVO vo ) {
	return userDao.read( vo );
    }
    
    public UserVO getUserByNo( Long no ) {
	return userDao.read( no );
    }
    
    @Transactional
    public UserVO setUser(UserVO vo ) {
	userDao.update( vo );
	return userDao.read( vo.getNo() );
    }
}