package com.cafe24.mysite.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.mysite.vo.GuestbookVO;

@Repository
public class GuestbookDAO {
    @Autowired
    private SqlSession sqlSession;
    
    public List<GuestbookVO> readAll() {
	return sqlSession.selectList( "guestbook.readAll" );
    }

    public int create( GuestbookVO vo ) {
//	System.out.println( vo );
	int count = sqlSession.insert( "guestbook.insert", vo );
//	System.out.println( vo );
	return count;
    }

    public boolean delete( GuestbookVO vo ) {
//	Map<String, Object> map = new HashMap<String, Object>();
//	map.put( "no", vo.getNo() );
//	map.put( "pwd", vo.getPassword() );
//	int count = sqlSession.delete( "guestbook.delete", map );
	int count = sqlSession.delete( "guestbook.delete", vo );
	return count == 1;
    }
}
