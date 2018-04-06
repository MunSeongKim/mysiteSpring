package com.cafe24.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cafe24.mysite.service.UserService;
import com.cafe24.mysite.vo.UserVO;

public class AuthLoginInterceptor extends HandlerInterceptorAdapter {
    // @Autowired
    // private UserService userService;

    @Override
    public boolean preHandle( HttpServletRequest request, HttpServletResponse response, Object handler )
	    throws Exception {
	String email = request.getParameter( "email" );
	String password = request.getParameter( "password" );

	UserVO vo = new UserVO();
	vo.setEmail( email );
	vo.setPassword( password );

	ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext( request.getServletContext() );
	UserService userService = ac.getBean( UserService.class );

	UserVO authUser = userService.getUser( vo );

	if ( authUser == null ) {
	    request.setAttribute( "email", email );
	    request.setAttribute( "result", "fail" );
	    request.getRequestDispatcher( "/WEB-INF/views/user/login.jsp" ).forward( request, response );
	    // response.sendRedirect( request.getContextPath() + "/user/login" );
	    return false;
	}

	HttpSession session = request.getSession( true );
	session.setAttribute( "authUser", authUser );
	response.sendRedirect( request.getHeader("referer") );
	return false;
    }
}
