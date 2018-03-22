package com.cafe24.mysite.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.mysite.dto.CommentListDTO;
import com.cafe24.mysite.vo.CommentVO;

@Repository
public class CommentDAO {
    @Autowired
    private SqlSession sqlSession;

    // --
    public List<CommentListDTO> readAll( Long boardNo ) {
	return sqlSession.selectList( "comment.selectAll", boardNo );
    }
    // --
    public boolean create( CommentVO vo ) {
	int count = sqlSession.insert( "comment.insert", vo );

	return count == 1;
    }

    public boolean delete( Long no ) {
	int count = sqlSession.delete( "comment.delete", no );
	return count == 1;
    }
}
