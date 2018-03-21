package com.cafe24.mysite.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cafe24.mysite.service.UserService;
import com.cafe24.mysite.vo.UserVO;

@Controller
@RequestMapping( "/user" )
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping( value = "/join", method = RequestMethod.GET )
    public String join() {
	return "user/join";
    }

    @RequestMapping( value = "/join", method = RequestMethod.POST )
    public String join( @ModelAttribute UserVO vo ) {
	userService.join( vo );
	return "redirect:/user/joinsuccess";
    }

    @RequestMapping( value = "/joinsuccess", method = RequestMethod.GET )
    public String joinsuccess() {
	return "user/joinsuccess";
    }

    @RequestMapping( value = "/login", method = RequestMethod.GET )
    public String login() {
	return "user/login";
    }

    @RequestMapping( value = "/login", method = RequestMethod.POST )
    public String login( @ModelAttribute UserVO vo, Model model, HttpSession session ) {
	UserVO authUser = userService.getUser( vo );

	// 인증 처리
	if ( authUser == null ) {
	    model.addAttribute( "result", "fail" );
	    model.addAttribute( "email", vo.getEmail() );
	    return "/user/login";
	}

	session.setAttribute( "authUser", authUser );
	return "redirect:/main";
    }

    @RequestMapping( value = "/logout", method = RequestMethod.GET )
    public String logout( HttpSession session ) {
	session.removeAttribute( "authUser" );
	session.invalidate();
	return "redirect:/main";
    }

    @RequestMapping( value = "/modify", method = RequestMethod.GET )
    public String modify( HttpSession session, Model model ) {
	// Access Controll
	UserVO authUser = (UserVO) session.getAttribute( "authUser" );
	if ( authUser == null ) {
	    return "redirect:/main";
	}

	UserVO user = userService.getUserByNo( authUser );
	model.addAttribute( "vo", user );
	return "user/modify";
    }

    @RequestMapping( value = "/modify", method = RequestMethod.POST )
    public String modify( @ModelAttribute UserVO vo, HttpSession session, Model model ) {
	UserVO user = userService.setUser( vo );
	model.addAttribute( "vo", user );
	session.setAttribute( "authUser", user );
	return "user/modify";
    }

    // @ExceptionHandler( UserDAOException.class )
    // public String handleUserDAOException() {
    // /* Must be to write Log */
    // return "error/error";
    // }

}