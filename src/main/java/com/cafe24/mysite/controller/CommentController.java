package com.cafe24.mysite.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cafe24.mysite.service.CommentService;
import com.cafe24.mysite.vo.CommentVO;
import com.cafe24.mysite.vo.UserVO;

@Controller
@RequestMapping( "/comment" )
public class CommentController {
    @Autowired
    private CommentService commentService;

    @RequestMapping( value = "/write", method = RequestMethod.POST )
    public String write( @ModelAttribute CommentVO commentVo, @RequestParam( "p" ) int pageNo,
	    @RequestParam( "k" ) String keyword, HttpSession session ) {
	commentVo.setUserNo( ((UserVO) session.getAttribute( "authUser" )).getNo() );
	commentService.write( commentVo );
	return "redirect:/board/view/" + pageNo + "/" + commentVo.getBoardNo() + "?k=" + keyword;
    }

    @RequestMapping( value = "/delete/{pno}/{bno}/{cno}", method = RequestMethod.GET )
    public String delete( @PathVariable( "pno" ) int pageNo, @PathVariable( "bno" ) Long boardNo,
	    @PathVariable( "cno" ) Long commentNo, @RequestParam( "k" ) String keyword, Model model ) {
	commentService.delete( commentNo );
	return "redirect:/board/view/" + pageNo + "/" + boardNo + "?k=" + keyword;
    }
}
