<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${ pageContext.servletContext.contextPath }/assets/css/user.css" rel="stylesheet" type="text/css">
<script src="${ pageContext.servletContext.contextPath }/assets/js/jquery/jquery-1.9.0.js" type="text/javascript"></script>
<script>
$(function() {
	$("#btn-checkemail").click(function() {
		var email = $("#email").val();
		if( email == "") return ;
		
		
		$.ajax({
			url: '${ pageContext.servletContext.contextPath }/api/user/checkemail?email=' + email,
			type: 'GET',
			data: "",
			dataType: 'json',
			success: function( response, status, xhr ) {
				console.log( response );
				if( response.result != "success" ) {
					console.log(response.message);
					return ;
				}
				
				if( response.data == "exist" ){
					alert(email + "(은)는 이미 사용중입니다.")
					$('#email').val("").focus();
					return ;
				}
				$('#btn-checkemail').hide();
				$('#img-check').show();
				
			},
			error: function( e, status, xhr ) {
				console.error("[" + status + "] " + e);
			}
			
		});
	});
});
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="user">
				<form:form modelAttribute="userVO" id="join-form" name="joinForm" method="POST" action="${ pageContext.servletContext.contextPath }/user/join">
					<label class="block-label" for="name">이름</label>
					<!-- <input id="name" name="name" type="text" value="" /> -->
					<form:input path="name" />
					<p style="padding: 0; text-align: left; color: red">
						<form:errors path="name" />
					</p>
					
					<label class="block-label" for="email">이메일</label>
					<!-- <input id="email" name="email" type="text" value="" /> -->
					<form:input path="email" />
					<input id="btn-checkemail" type="button" value="id 중복체크" />
					<p style="padding: 0; text-align: left; color: red">
						<form:errors path="email" />
					</p>
					<img id="img-check" style="display:none" src="${ pageContext.servletContext.contextPath }/assets/images/check.png" />
					<label class="block-label">패스워드</label>
					<!-- <input name="password" type="password" value="" /> -->
					<form:password path="password"/>
					<!-- <spring:hasBindErrors name="userVO">
					   <c:if test="${errors.hasFieldErrors('password') }">
					        <p style="padding: 0; text-align: left; color: red">
					        <strong>
					        	<spring:message code="${errors.getFieldError('password').codes[0] }" 
					        					text="${errors.getFieldError('password').defaultMessage }" />
					        	<!-- ${errors.getFieldError( 'password' ).defaultMessage } 
					        </strong>
					        </p>
					   </c:if>
					</spring:hasBindErrors> -->
					<p style="padding: 0; text-align: left; color: red">
						<form:errors path="password" />
					</p>
										
					
					<fieldset>
						<legend>성별</legend>
						<label>여</label> <form:radiobutton path="gender" value="female" />
						<label>남</label> <form:radiobutton path="gender" value="male" />
					</fieldset>
					
					<fieldset>
						<legend>약관동의</legend>
						<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
						<label>서비스 약관에 동의합니다.</label>
					</fieldset>
					
					<input type="submit" value="가입하기">
					
				</form:form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
	
</body>
</html>