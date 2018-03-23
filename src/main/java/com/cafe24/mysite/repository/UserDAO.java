package com.cafe24.mysite.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.mysite.exception.UserDAOException;
import com.cafe24.mysite.vo.UserVO;

@Repository
public class UserDAO {
    @Autowired
    private SqlSession sqlSession;

    public boolean create( UserVO vo ) {
	int count = sqlSession.insert( "user.insert", vo );
	return count == 1;
    }

    public UserVO read( UserVO vo ) throws UserDAOException {
	return sqlSession.selectOne( "user.selectByEmailAndPassword", vo );
    }

    public UserVO read( String email ) {
	return sqlSession.selectOne( "user.selectByEmail", email );
    }

    public UserVO read( Long no ) {
	return sqlSession.selectOne( "user.selectByNo", no );
    }

    public boolean update( UserVO vo ) {
	int count = sqlSession.update( "user.update", vo );
	return count == 1;
    }
}
