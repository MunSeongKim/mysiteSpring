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
	return sqlSession.selectList( "guestbook.selectAll" );
    }
    
    public List<GuestbookVO> readAll( Long no) {
	return sqlSession.selectList( "guestbook.selectAll", no );
    }

    public int create( GuestbookVO vo ) {
	int count = sqlSession.insert( "guestbook.insert", vo );
	return count;
    }

    public boolean delete( GuestbookVO vo ) {
	int count = sqlSession.delete( "guestbook.delete", vo );
	return count == 1;
    }

    public Long readCount() {
	return sqlSession.selectOne( "guestbook.selectCount" );
    }

    public GuestbookVO read( Long no ) {
	return sqlSession.selectOne( "guestbook.select", no );
    }
}
