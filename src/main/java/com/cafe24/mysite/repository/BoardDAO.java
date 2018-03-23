package com.cafe24.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.mysite.component.Pager;
import com.cafe24.mysite.dto.BoardListDTO;
import com.cafe24.mysite.vo.BoardVO;

@Repository
public class BoardDAO {
    @Autowired
    private SqlSession sqlSession;

    // --
    public List<BoardListDTO> readAll( String keyword, Pager pager ) {
	int start = (pager.getCurrentPageNumber() - 1) * pager.getPostCount();
	int count = pager.getPostCount();

	Map<String, Object> map = new HashMap<String, Object>();
	map.put( "keyword", keyword );
	map.put( "start", start );
	map.put( "count", count );
	List<BoardListDTO> list = sqlSession.selectList( "board.selectAll", map );

	return list;
    }

    // --
    public BoardVO read( Long no ) {
	return sqlSession.selectOne( "board.select", no );
    }
    
    public int readPostCount( Map<String, Object> map ){
	return sqlSession.selectOne("board.selectCount", map);
    }

    // --
    public BoardVO create( BoardVO vo ) {
	int count = sqlSession.insert( "board.insert", vo );
	if ( count == 0 ) {
	    return null;
	}
	return vo;
    }

    // --
    public boolean update( BoardVO vo ) {
	int count = sqlSession.update( "board.updateByPost", vo );
	return count == 1;
    }

    // --
    public boolean update( Long no ) {
	int count = sqlSession.update( "board.updateByHit", no );
	return count == 1;
    }

    // --
    public boolean update( Long groupNo, Long orderNo ) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put( "groupNo", groupNo );
	map.put( "orderNo", orderNo );

	int count = sqlSession.update( "updateByReply", map );

	return count == 1;
    }
    
    // --
    public boolean delete( Long no ) {
	int count = sqlSession.delete( "board.deleteByNo", no );
	return count == 1;
    }
}
