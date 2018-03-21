package com.cafe24.mysite.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler( Exception.class )
    public void handlerExcpetion( HttpServletRequest request, HttpServletResponse response, Exception e )
	    throws Exception {
	// 1. Must be to write log
	StringWriter errors = new StringWriter();
	e.printStackTrace( new PrintWriter( errors ) );
	request.setAttribute( "errors", errors );
	// 2. Apologize
	request.getRequestDispatcher( "/WEB-INF/views/error/error.jsp" ).forward( request, response );
    }
}
