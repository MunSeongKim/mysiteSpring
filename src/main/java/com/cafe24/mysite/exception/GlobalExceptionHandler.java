package com.cafe24.mysite.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cafe24.mysite.dto.JSONResult;
import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler( Exception.class )
    public void handlerExcpetion( HttpServletRequest request, HttpServletResponse response, Exception e )
	    throws Exception {
	// 1. Must be to write log
//	StringWriter errors = new StringWriter();
//	e.printStackTrace( new PrintWriter( errors ) );
//	request.setAttribute( "errors", errors.toString() );
	request.setAttribute( "errors", e.getMessage() );
	
	e.printStackTrace();

	String accept = request.getHeader("accept");
	if( accept.matches( ".*application/json.*" ) ) {
	    // 실패 JSON 응답
	    JSONResult jsonResult = JSONResult.fail( e.getMessage() );
	    String json = new ObjectMapper().writeValueAsString( jsonResult );
	    System.out.println( json );
	    response.setContentType( "application/json; charset=utf-8" );
	    response.getWriter().print(json);
	}
	// 2. Apologize
	request.getRequestDispatcher( "/WEB-INF/views/error/error.jsp" ).forward( request, response );
    }
}
