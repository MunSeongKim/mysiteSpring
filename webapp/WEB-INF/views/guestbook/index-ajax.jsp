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
ul#list-guestbook li						{ padding-left: 45px; background: url('../assets/images/user.png') 0 20px no-repeat; position: relative; margin-bottom: 15px; }
ul#list-guestbook li strong					{ display: block; margin-left: 5px; margin-bottom: 5px; color: #666; float: left; }
ul#list-guestbook li p.date					{ float: right; font-size: 0.95em; }
ul#list-guestbook li p.content				{ padding: 15px; background-color: #FAFAFA; border: 1px solid #EEEFEF; border-radius: 5px; clear: both;}
ul#list-guestbook li a						{ width:20px; height: 20px; font-size: 0; cursor: pointer; display: block; position: absolute; left: 20px; top: 40px; background: url('../assets/images/delete.png') 0 0 no-repeat; background-size: contain;  }


</style>
<script>
	$(function() {
		var index = ${ postCount };
		var count = ${ postCount };
		var startNumber = ${ startNumber };
		
		$("#btn-addlist").click(function() {
			$.ajax({
				url: '${ pageContext.servletContext.contextPath }/api/guestbook/list?idx=' + index,
				type: 'GET',
				data: "",
				dataType: 'json',
				success: function( response, status, xhr ) {
					var list = response.data;
					var lastList = $('#list-guestbook li:last')
					for(var i = list.length-1; i >= 0 ; i--){
						lastList.after("<li data-no='" + list[i].no + "'>"
									 + "<strong>" + list[i].name + "</strong>"
									 + "<p class='date'>" + list[i].regDate + "</p>"
									 + "<p class='content'>"
									 + list[i].content.replace("\n", "<br />")
									 + "</p>"
									 + "<a href='${ pageContext.servletContext.contextPath }/guestbook/delete/"+list[i].no+"' data-no='"+list[i].no+"'>삭제</a>"
									 + "</li>"
								 );
					}
					
					index = index + count;
					if( index > startNumber ) {
						$('#btn-addlist').hide();
					}
				},
				error: function( xhr, status, e ) {
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
						<c:forEach items="${ list }" var="vo" varStatus="status">
							<li data-no='${ vo.no }'>
								<strong>${ vo.name }</strong> <p class="date">${ vo.regDate }<p>
								<p class="content">
									${ fn:replace(vo.content, newLine, "<br />") }
								</p>
								<a href='${ pageContext.servletContext.contextPath }/guestbook/delete/${ vo.no }' data-no='${ vo.no }'>삭제</a> 
							</li>
						</c:forEach>
					</ul>
					<div style="width: 100%;">
						<button id="btn-addlist" style="display: block; margin: 0 auto;">게시글 더 보기</button>
					</div>
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