<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/guestbook.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<style type="text/css">
* 											{-webkit-box-sizing: border-box; -moz-box-sizing: border-box; -o-box-sizing: border-box; -ms-box-sizing: border-box; box-sizing: border-box; }
hr											{ margin: 20px 0;}
div#input-form,
div#list									{ width: 100%; margin: 10px 0; }

div#guestbook h1							{ background: url('../assets/images/guestbook.png') 0 0 no-repeat; background-size: contain; padding-left: 40px; }

form#add-form input[type='text'],
form#add-form input[type='password'],
form#add-form textarea						{ width: 100%; display: block; padding: 5px; margin-bottom: 10px; border: 1px solid #999; }
form#add-form textarea						{ height:150px; resize:none; overflow-y: scorll;  }
form#add-form input[type='submit']			{ width: 100%; display:block; margin-bottom: 10px; border: 1px solid #999; background-color: #FFF; height: 30px; font-weight: bold; cursor: pointer; }	
form#add-form input[type='submit']:hover	{ background-color: #93C15E; }

ul#list-guestbook							{ width: 100%; }
ul#list-guestbook li						{ padding-left: 45px; background: url('../assets/images/user.png') 0 20px no-repeat; position: relative; }
ul#list-guestbook li strong					{ display: block; margin-bottom: 5px; color: #666;}
ul#list-guestbook li p						{ padding: 15px; background-color: #FAFAFA; border: 1px solid #EEEFEF; border-radius: 5px; }
ul#list-guestbook li a						{ width:20px; height: 20px; font-size: 0; cursor: pointer; display: block; position: absolute; left: 20px; top: 40px; background: url('../assets/images/delete.png') 0 0 no-repeat; background-size: contain;  }
</style>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<h1>방명록</h1>
				<div id="input-form">
					<form id="add-form" action="#">
						<input type="text" id="input-name" placeholder="이름">
						<input type="password" id="input-password" placeholder="비밀번호">
						<textarea id="tx-content" placeholder="내용을 입력해 주세요."></textarea>
						<input type="submit" value="보내기" />
					</form>
				</div>
				<hr />
				<div id="list">
				<ul id="list-guestbook">
					<li data-no=''>
						<strong>지나가다가</strong>
						<p>
							별루입니다.<br>
							비번:1234 -,.-
						</p>
						<strong></strong>
						<a href='' data-no=''>삭제</a> 
					</li>
					
					<li data-no=''>
						<strong>둘리</strong>
						<p>
							안녕하세요<br>
							홈페이지가 개 굿 입니다.
						</p>
						<strong></strong>
						<a href='' data-no=''>삭제</a> 
					</li>

					<li data-no=''>
						<strong>주인</strong>
						<p>
							아작스 방명록 입니다.<br>
							테스트~
						</p>
						<strong></strong>
						<a href='' data-no=''>삭제</a> 
					</li>
				</ul>
				</div>
			</div>
			<div id="dialog-delete-form" title="메세지 삭제" style="display:none">
  				<p class="validateTips normal">작성시 입력했던 비밀번호를 입력하세요.</p>
  				<p class="validateTips error" style="display:none">비밀번호가 틀립니다.</p>
  				<form>
 					<input type="password" id="password-delete" value="" class="text ui-widget-content ui-corner-all">
					<input type="hidden" id="hidden-no" value="">
					<input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
  				</form>
			</div>
			<div id="dialog-message" title="" style="display:none">
  				<p></p>
			</div>						
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="guestbook-ajax"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>