package com.cafe24.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	return userDao.read( vo.getEmail(), vo.getPassword() );
    }
    
    public UserVO getUserByNo( UserVO vo ) {
	return userDao.read( vo.getNo() );
    }
    
    public UserVO setUser(UserVO vo ) {
	userDao.update( vo );
	return userDao.read( vo.getNo() );
    }
}