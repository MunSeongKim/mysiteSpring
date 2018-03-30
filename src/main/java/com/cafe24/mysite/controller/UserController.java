package com.cafe24.mysite.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cafe24.mysite.service.UserService;
import com.cafe24.mysite.vo.UserVO;
import com.cafe24.security.Auth;
import com.cafe24.security.AuthUser;

@Controller
@RequestMapping( "/user" )
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping( value = "/join", method = RequestMethod.GET )
    public String join(@ModelAttribute UserVO vo) {
	return "user/join";
    }

    @RequestMapping( value = "/join", method = RequestMethod.POST )
    public String join( @ModelAttribute @Valid UserVO vo, BindingResult result ) {
	if( result.hasErrors() ) {
	    List<ObjectError> list = result.getAllErrors();
	    for(ObjectError error: list){
		System.out.println( "Object Error: " + error );
	    }
	    
	    return "user/join";
	}
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
   
    @Auth
    @RequestMapping( value = "/modify", method = RequestMethod.GET )
    public String modify( @AuthUser UserVO authUser, Model model ) {
//    public String modify( HttpSession session, Model model ) {
//	UserVO authUser = (UserVO) session.getAttribute( "authUser" );

	System.out.println( authUser );
	UserVO user = userService.getUserByNo( authUser.getNo() );
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