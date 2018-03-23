<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
				<form id="join-form" name="joinForm" method="POST" action="${ pageContext.servletContext.contextPath }/user/join">
					<label class="block-label" for="name">이름</label>
					<input id="name" name="name" type="text" value="" />

					<label class="block-label" for="email">이메일</label>
					<input id="email" name="email" type="text" value="" />
					<input id="btn-checkemail" type="button" value="id 중복체크" />
					<img id="img-check" style="display:none" src="${ pageContext.servletContext.contextPath }/assets/images/check.png" />
					<label class="block-label">패스워드</label>
					<input name="password" type="password" value="" />
					
					<fieldset>
						<legend>성별</legend>
						<label>여</label> <input type="radio" name="gender" value="female" checked="checked">
						<label>남</label> <input type="radio" name="gender" value="male">
					</fieldset>
					
					<fieldset>
						<legend>약관동의</legend>
						<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
						<label>서비스 약관에 동의합니다.</label>
					</fieldset>
					
					<input type="submit" value="가입하기">
					
				</form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
	
</body>
</html>