<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${ pageContext.servletContext.contextPath }/assets/css/board.css" rel="stylesheet"
	type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board" class="board-form">
				<table class="tbl-ex">
					<tr>
						<th colspan="2">글보기</th>
					</tr>
					<tr>
						<td class="label">제목</td>
						<td>${ board.title }</td>
					</tr>
					<tr>
						<td class="label">내용</td>
						<td>
							<div class="view-content">
								${ board.content }
							</div>
						</td>
					</tr>
				</table>
				
				<c:import url="/WEB-INF/views/comment/list.jsp" />
				
				<div class="bottom">
					<a href="${ pageContext.servletContext.contextPath }/board/list/1?k=">글목록</a>
					<c:if test="${ not empty authUser }">
					<a href="${ pageContext.servletContext.contextPath }/board/reply/${ pageNo }/${ board.no }?k=${ keyword }">답글달기</a>
					</c:if>
					<c:if test="${ not empty authUser && board.userNo eq authUser.no }">
					<a href="${ pageContext.servletContext.contextPath }/board/modify/${ pageNo }/${ board.no }?k=${ keyword }">글수정</a>
					</c:if>
				</div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board" />
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>