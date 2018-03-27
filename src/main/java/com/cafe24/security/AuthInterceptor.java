package com.cafe24.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cafe24.mysite.vo.UserVO;

public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle( HttpServletRequest request, HttpServletResponse response, Object handler )
	    throws Exception {
	// 1. Handler 종류 확인
	if ( handler instanceof HandlerMethod == false ) {
	    return true;
	}
	// 2. Type Casting
	HandlerMethod handlerMethod = (HandlerMethod) handler;
	handlerMethod.getBeanType().getAnnotation( Auth.class ); //-> 클래스의 annotation 가져오기
	// 3. get @Auth
	Auth auth = handlerMethod.getMethodAnnotation( Auth.class );
	// 4. Method에 @Auth가 없는 경우
	if ( auth == null ) {
	    return true;
	}
	// 5. @Auth가 있는 경우, 인증여부 체크
	HttpSession session = request.getSession();
	if ( session == null ) {
	    response.sendRedirect( request.getContextPath() + "/user/login" );
	    return false;
	}

	UserVO authUser = (UserVO) session.getAttribute( "authUser" );
	if( authUser == null ){
	    response.sendRedirect( request.getContextPath() + "/user/login" );
	    return false;
	}
	// 6. 접근 허가
	return true;
    }

}
