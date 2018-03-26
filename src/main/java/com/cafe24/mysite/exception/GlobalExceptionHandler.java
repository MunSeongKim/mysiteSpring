package com.cafe24.mysite.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cafe24.mysite.dto.JSONResult;
import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Logger 생성
     */
    private static final Log LOG = LogFactory.getLog( GlobalExceptionHandler.class );

    @ExceptionHandler( Exception.class )
    public void handlerExcpetion( HttpServletRequest request, HttpServletResponse response, Exception e )
	    throws Exception {
	// 1. Must be to write log
	StringWriter errors = new StringWriter();
	e.printStackTrace( new PrintWriter( errors ) );
	LOG.error(e.getMessage());
//	LOG.warn(errors.toString());

	String accept = request.getHeader( "accept" );
	if ( accept.matches( ".*application/json.*" ) ) {
	    // 실패 JSON 응답
	    JSONResult jsonResult = JSONResult.fail( e.getMessage() );
	    String json = new ObjectMapper().writeValueAsString( jsonResult );
	    System.out.println( json );
	    response.setContentType( "application/json; charset=utf-8" );
	    response.getWriter().print( json );
	    return;
	}
	// 2. Apologize
	request.getRequestDispatcher( "/WEB-INF/views/error/error.jsp" ).forward( request, response );
    }
}
