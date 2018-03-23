<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% pageContext.setAttribute( "newLine", "\n" ); %>
<!doctype html>
<html>
<head>
	<title>mysite</title>
	<meta http-equiv="content-type" content="text/html; charset=utf-8">
	<link href="${ pageContext.servletContext.contextPath }/assets/css/guestbook.css" rel="stylesheet" type="text/css">
	<script src="${ pageContext.servletContext.contextPath }/assets/js/jquery/jquery-1.9.0.js" type="text/javascript"></script>
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
					var lastTable = $('#post-list table:last')
					for(var i = list.length-1; i >= 0 ; i--){
						lastTable.after("<br />"
									 + "<table border='1'>"
									 + "<tr>"
									 + "<td class='no'>[" + (startNumber - index - i) + "]</td>"
									 + "<td>" + list[i].name + "</td>"
									 + "<td>" + list[i].regDate + "</td>"
									 + "<td><a href='" + window.location.pathname.replace("/list", "") + "/delete/" + list[i].no + "'>삭제</a></td>"
									 + "</tr>"
									 + "<tr>"
									 + "<td colspan='4'>"
									 + list[i].content.replace("\n", "<br />")
									 + "</td>"
									 + "</tr>"
									 + "</table>"
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
				<form action="${ pageContext.servletContext.contextPath }/guestbook/write" method="post">
					<table border="1">
						<tr>
							<td>이름</td><td><input type="text" name="name"></td>
							<td>비밀번호</td><td><input type="password" name="password"></td>
						</tr>
						<tr>
							<td colspan=4><textarea name="content" cols=66 rows=5></textarea></td>
						</tr>
						<tr>
							<td colspan=4 align=right><input type="submit" VALUE=" 확인 "></td>
						</tr>
					</table>
				</form>
				<br />
				<div id='post-list'>
				<c:forEach items="${ list }" var="vo" varStatus="status">
					<table border="1">
						<tr>
							<td class="no">[${ startNumber - status.index }]</td>
							<td>${ vo.name }</td>
							<td>${ vo.regDate }</td>
							<td><a href="${ pageContext.servletContext.contextPath }/guestbook/delete/${ vo.no }">삭제</a></td>
						</tr>
						<tr>
							<td colspan="4">
								${ fn:replace(vo.content, newLine, "<br />") }
							</td>
						</tr>
					</table>
					<br />
				</c:forEach>
				</div>
				<div style="width: 100%;">
					<button id="btn-addlist" style="display: block; margin: 0 auto;">게시글 더 보기</button>
				</div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" >
			<c:param name="menu" value="guestbook" />
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>