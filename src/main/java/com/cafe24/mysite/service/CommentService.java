package com.cafe24.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.mysite.dto.CommentDTO;
import com.cafe24.mysite.repository.CommentDAO;
import com.cafe24.mysite.vo.CommentVO;

@Service
public class CommentService {
    @Autowired
    private CommentDAO commentDao;
    
    public List<CommentDTO> getList(Long boardNo){
	return commentDao.readAll( boardNo );
    }
    
    public void write(CommentVO vo){
	commentDao.create(vo);
    }
    
    public void delete( Long commentNo ){
	commentDao.delete( commentNo );
    }
}
