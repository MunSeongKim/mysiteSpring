<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${ pageContext.servletContext.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board">
				<form id="search_form" action="${ pageContext.servletContext.contextPath }/board/list" method="post">
					<input type="text" id="kwd" name="k" value="" />
					<input type="submit" value="찾기" />
				</form>
				<c:if test='${ param.k != "" }'>
					<p>"${ param.k }"의 검색 결과 : ${ pager.startPostNumber }개가 검색됨.</p>
				</c:if>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					
					<c:forEach items="${ list }" var="result" varStatus="status">
					<tr>
						<td>${ pager.startPostNumber - status.index }</td>
						<td style="text-align:left; padding-left: ${ result.depth * 20 }px">
							<c:if test='${ result.depth gt 0 }'>
								<img src="${ pageContext.servletContext.contextPath }/assets/images/reply.png" />
							</c:if>
							<a href="${ pageContext.servletContext.contextPath }/board/view/${ pager.currentPageNumber }/${ result.no }?k=${ keyword }">${ result.title }</a>
						</td>
						<td>${ result.userName }</td>
						<td>${ result.hitCount }</td>
						<td>${ result.regDate }</td>
						<td>
						<c:if test="${ not empty authUser && result.userNo eq authUser.no }">
							<a href="${ pageContext.servletContext.contextPath }/board/delete/${ pager.currentPageNumber }/${ result.no }?k=${ keyword }" class="del">삭제</a>
						</c:if>
						</td>
					</tr>
					</c:forEach>
				</table>
				<div class="pager">
					<ul>
						<c:if test='${ pager.leftNavigator }'>
						<li><a href="${ pageContext.servletContext.contextPath }/board/list/${ pager.startPageNumber -1 }?k=${ keyword }">◀</a></li>
						</c:if>
						<c:forEach begin='0' end='${ pager.pageCount -1 }' var='i' step='1'>
						<c:choose>
							<c:when test='${ (pager.startPageNumber + i) gt pager.totalPageCount }'>
								<li>${ pager.startPageNumber + i }</li>
							</c:when>
							<c:when test='${ (pager.startPageNumber + i) eq pager.currentPageNumber }'>
								<li class="selected"><a href="${ pageContext.servletContext.contextPath }/board/list/${ pager.startPageNumber + i }?k=${ keyword }">${ pager.startPageNumber + i }</a></li>
							</c:when>
							<c:otherwise>
								<li><a href="${ pageContext.servletContext.contextPath }/board/list/${ pager.startPageNumber + i }?k=${ keyword }">${ pager.startPageNumber + i }</a></li>
							</c:otherwise>
						</c:choose>
						</c:forEach>
						<c:if test='${ pager.rightNavigator }'>
						<li><a href="${ pageContext.servletContext.contextPath }/board/list/${ pager.endPageNumber +1 }?k=${ keyword }">▶</a></li>
						</c:if>
					</ul>
				</div>
				<c:if test="${ not empty sessionScope.authUser }">
				<div class="bottom">
					<a href="${ pageContext.servletContext.contextPath }/board/write/${ pager.currentPageNumber }?k=${ keyword }" id="new-book">글쓰기</a>
				</div>
				</c:if>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" >
			<c:param name="menu" value="board" />
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>