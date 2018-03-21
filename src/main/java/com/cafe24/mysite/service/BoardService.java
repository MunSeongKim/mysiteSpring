package com.cafe24.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.mysite.component.Pager;
import com.cafe24.mysite.dto.BoardDTO;
import com.cafe24.mysite.repository.BoardDAO;
import com.cafe24.mysite.vo.BoardVO;

@Service
public class BoardService {
    @Autowired
    private BoardDAO boardDao;

    public List<BoardDTO> getList( String keyword, Pager pager ) {
	return boardDao.readAll( keyword, pager );
    }

    public BoardVO getPost( Long boardNo ) {
	boardDao.update( boardNo );
	return boardDao.read( boardNo );
    }

    public BoardVO getPostAtLast() {
	return boardDao.readAtLast();
    }
    
    public void deletePost( Long boardNo ) {
	boardDao.delete( boardNo );
    }
    
    public void writePost( BoardVO vo, Long userNo ){
	boardDao.create( vo, userNo );
    }
    
    public void writeReply( BoardVO vo, Long userNo ){
	boardDao.create( vo, userNo, vo.getGroupNo(), vo.getOrderNo()+1, vo.getDepth()+1 );
    }
    
    public void modifyPost( BoardVO vo ){
	boardDao.update( vo );
    }
}