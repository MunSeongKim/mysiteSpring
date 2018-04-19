<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/guestbook-ajax.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/ejs/ejs.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	// jQuery plugin - custom
	(function($){
		$.fn.hello = function() {
			var $element = $(this);
			console.log( $element.attr("id") + ": hello~" );
		};
	})(jQuery);

	// use to Dialog
	var messageBox = function( title, message, callback ) {
		$('#dialog-message').attr("title", title);
		$('#dialog-message p').text(message);
		$('#dialog-message').dialog({
			modal: true,
			draggable: false,
			resizable: false,
			position: { my: "center", at: "center", of: "#add-form" },
			buttons: {
				"확인": function() {
					$(this).dialog("close");
					
				}
			},
			close: callback || function() {}
		});
	};

	// use to Ajax
	var index = 0;
	var isEnd = false;
	// Apply to EJS engine 
	var ejsListItem = new EJS({
		url: "${pageContext.request.contextPath }/assets/js/ejs/template/listitem.ejs"
	});
	var render = function( mode, vo ) {
		 var html = ejsListItem.render(vo);
			 
			 /*"<li data-no='"+vo.no+"'>"
				 + "<strong>" + vo.name + "</strong>"
				 + "<p class='date'>" + vo.regDate + "</p>"
				 + "<p class='content'>" + vo.content.replace(/\n/gi, "<br />") + "</p>"
				 + "<a class='btn-delete' data-no='"+vo.no+"'>삭제</a>"
				 + "</li>";*/
				 
		if( mode == true ){
			$('#list-guestbook').prepend( html );
		} else {
			$('#list-guestbook').append( html );	
		}
		
		// $('#list-guestbook')[mode?"prepend":"append"](html);
	};

	var fetchList = function() {
		if( isEnd == true ){
			return;
		}
		// 조건 판단 우선순위에 의한 값의 대입
		// A || B 일때 A가 false이면 B의 값을 대입.
		var index = $('#list-guestbook li').last().data('no') || 0;
		$.ajax({
			url: '${ pageContext.servletContext.contextPath }/api/guestbook/list?idx=' + index,
			type: 'GET',
			data: "",
			dataType: 'json',
			success: function( response, status, xhr ) {
				// 성공 유무
				if( response.result != "success" ){
					console.warn( response.message );
					return ;
				}
				// 끝 감지
				if( response.data.length < 5 ) { 
					$('#btn-fetch').prop("disabled", true);
					isEnd = true;
				}
				// Render
				$.each(response.data, function(index, vo) {
					render(false, vo);
				});
			},
			error: function( xhr, status, e ) {
				console.error("[" + status + "] " + e);
			}
		});
	};
	
	// --- Main Execute code ---
	$(function() {
		// 최초 리스트 가져오기
		fetchList();
		
		// Ajax - Get a list 
		$("#btn-fetch").click(function() {
			fetchList();
		});
		
		$(window).scroll(function() {
			var $window = $(this);
			var scrollTop = $window.scrollTop();
			var windowHeight = $window.height();
			var documentHeight = $(document).height();
			// console.log(scrollTop + ":" + windowHeight + ":" + documentHeight);
			
			// scrollbar의 thumb가 바닥 전 30px까지 도달
			if( scrollTop + windowHeight + 30 > documentHeight ) {
				fetchList();
			}
		});
		
		// Live Event Listener - dynamic regist a event on target element
		$(document).on( "click", '#list-guestbook li a', function(e) {
			e.preventDefault();
			var no = $(this).data("no");
			console.log(no);
			$('#hidden-no').val(no);
			
			// Dialog positioning each a delete button
			deleteDialog.dialog('option', {position: { my: "left bottom", at: "left bottom", of: $(this) }});
			deleteDialog.dialog('open');
		});
		
		// Ajax - regist a new post
		$('#add-form').submit( function(event) {
			event.preventDefault();
			
			var data = {};
			$.each($(this).serializeArray(), function(index, obj) {
				data[obj.name] = obj.value;
			});
			// Form validation
			if( data["name"] == "" ){
				messageBox("메세지 등록", "이름이 비어 있습니다.", function(){
					$("#input-name").focus();	
				});
				return ;
			}
			if( data["password"] == "" ){
				messageBox("메세지 등록", "비밀번호가 비어 있습니다.", function(){
					$('#input-password').focus();
				});
				return ;
			}
			if( data["content"] == "" ){
				messageBox("메세지 등록", "내용이 비어 있습니다.", function(){
					$('#tx-content').focus();	
				});
				return ;
			}
			
			$.ajax({
				url: "${ pageContext.servletContext.contextPath }/api/guestbook/insert",
				type: "POST",
				dataType: "json",
				contentType: "application/json",
				data : JSON.stringify(data),
				success: function( response ) {
					render(true, response.data);
					$('#add-form')[0].reset();
				}
			});
		});
		
		// Dialog setting - 삭제시 비밀번호 입력 모달 다이얼로그
		var deleteDialog = $( "#dialog-delete-form" ).dialog({
			autoOpen: false,
			width: 350,
			modal: true,
			draggable: false,
			resizable: false,
			buttons: {
			  "삭제": function() {
				var password = $('#password-delete').val();
				var no = $('#hidden-no').val();
				  
				// Ajax - request to delete
				$.ajax({
					url: '${ pageContext.servletContext.contextPath }/api/guestbook/delete',
					type: 'POST',
					dataType: 'json',
					data: "no=" + no + "&password=" + password,
					success: function(response) {
						if( response.result == "fail"){
							console.warn(response);
							return ;
						}
						
						if( response.data == -1 ){
							$('.validateTips.normal').hide();
							$('.validateTips.error').show();
							$('#password-delete').val("");
							$('#password-delete').focus();
							return ;
						}
						
						$('#list-guestbook li[data-no='+response.data+']').remove();
						deleteDialog.dialog("close");
					}
				});
			  },
			  "취소": function() {
			  	deleteDialog.dialog("close");
			  }
			},
			close: function() {
			  $('#password-delete').val("");
			  $('#hidden-no').val("");
			  $('.validateTips.normal').show();
			  $('.validateTips.error').hide();
			}
		});
		
		// plugin 테스트
		$('#container').hello();
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
					<form id="add-form" action="">
						<input type="text" name="name" id="input-name" placeholder="이름">
						<input type="password" name="password" id="input-password" placeholder="비밀번호">
						<textarea id="tx-content" name="content" placeholder="내용을 입력해 주세요."></textarea>
						<input type="submit" value="보내기" />
					</form>
				</div>
				<hr />
				<div id="list">
					<ul id="list-guestbook">
						<!-- <c:forEach items="${ list }" var="vo" varStatus="status">
							<li data-no='${ vo.no }'>
								<strong>${ vo.name }</strong> <p class="date">${ vo.regDate }<p>
								<p class="content">
									${ fn:replace(vo.content, newLine, "<br />") }
								</p>
								<a class="btn-delete" data-no='${ vo.no }'>삭제</a> 
							</li>
						</c:forEach> -->
					</ul>
					<div style="width: 100%;">
						<button id="btn-fetch" class="ui-button ui-widget ui-corner-all" style="display: block; margin: 0 auto;">가져오기</button>
					</div>
				</div>
			</div>
			<div id="dialog-delete-form" title="메세지 삭제" style="display:none;">
  				<p class="validateTips normal">작성시 입력했던 비밀번호를 입력하세요.</p>
  				<p class="validateTips error" style="display:none">비밀번호가 틀립니다.</p>
  				<form>
 					<input type="password" id="password-delete" value="" class="text ui-widget-content ui-corner-all">
					<input type="hidden" id="hidden-no" value="">
					<input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
  				</form>
			</div>
			<div id="dialog-message" title="">
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