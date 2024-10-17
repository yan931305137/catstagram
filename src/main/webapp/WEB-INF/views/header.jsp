<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>插入标题</title>
<link rel="stylesheet" type="text/css" href="/css/header.css">
</head>
<body class="header_body">
	<div class="header_div">
		<a href="/catstagram">
			<div class="logo_div">
				<img src="/img/logo.png" class="logo_img">
				<span class="logo_title">Catstagram</span>
			</div>
		</a>
		<form action="/catstagram/account/search" method="get" class="search_div">
			<input type="text" class="search_input" name="id" placeholder="搜索" maxlength="30" required>
			<input type="submit" value="" class="search-btn">
		</form>
		<div class="menu_div">
			<img src="/img/home.png" class="menu_img" onclick="javascript: location.href='/catstagram'">
			
			<img src="/img/feed.png" class="menu_img" onclick="javascript: location.href='/catstagram/account/feedWrite'">
			<div class="btn-group">
				<span class="position-relative" data-bs-toggle="dropdown" aria-expanded="false" id="alarms">
					<img src="/img/heart.png" class="menu_img" onclick="redDotRemove(); lastAlarmTime();">
	  			</span>
	  			<ul class="dropdown-menu dropdown-menu-end alarm_size">
	  				<c:forEach var="alarm" items="${alarmList}">
	  					<c:if test="${alarm.activity_type eq 'follow'}">
	  						<li class="alarm-prop-size dropdown-item menu_font">
								<div class="alarm_msg_span2">${alarm.member_id}开始关注您。</div>
								<div class="alarm_time">${alarm.alarm_date_string}</div>
							</li>
	  					</c:if>
	  					<c:if test="${alarm.activity_type eq 'feedLike'}">
	  						<li class="alarm-prop-size dropdown-item menu_font">
								<img src="/upload/feed/${alarm.feed_img}" class="alarm_img">
								<div class="alarm_msg_span">${alarm.member_id}喜欢您的帖子。</div>
								<div class="alarm_time">${alarm.alarm_date_string}</div>
							</li>
	  					</c:if>
	  					<c:if test="${alarm.activity_type eq 'comment'}">
	  						<li class="alarm-prop-size dropdown-item menu_font">
								<img src="/upload/feed/${alarm.feed_img}" class="alarm_img">
								<div class="alarm_msg_span">${alarm.member_id}在您的帖子上评论了。 "${alarm.comment_content}"</div>
								<div class="alarm_time">${alarm.alarm_date_string}</div>
							</li>
	  					</c:if>
	  					<c:if test="${alarm.activity_type eq 'commentLike'}">
	  						<li class="alarm-prop-size dropdown-item menu_font">
								<img src="/upload/feed/${alarm.feed_img}" class="alarm_img">
								<div class="alarm_msg_span">${alarm.member_id}喜欢您的评论。 "${alarm.comment_content}"</div>
								<div class="alarm_time">${alarm.alarm_date_string}</div>
							</li>
	  					</c:if>
	  				</c:forEach>
				</ul>
  			</div>
			<div class="btn-group">
				<c:if test="${empty sessionScope.simg}">
					<img src="/img/default_photo2.png" class="header_profile_img" data-bs-toggle="dropdown" aria-expanded="false">
				</c:if>
				<c:if test="${!empty sessionScope.simg}">
					<img src="/upload/member/${sessionScope.simg}" class="header_profile_img" data-bs-toggle="dropdown" aria-expanded="false">
				</c:if>
				<ul class="dropdown-menu dropdown-menu-end">
					<li><a class="dropdown-item menu_font" href="/catstagram/${sessionScope.sid}">我的Catstagram</a></li>
					<li><hr class="dropdown-divider"></li>
				    <li><a class="dropdown-item menu_font" href="/catstagram/account/infoUpdate">修改会员信息</a></li>
				    <li><hr class="dropdown-divider"></li>
				    <li><a class="dropdown-item menu_font" href="/catstagram/account/logout">登出</a></li>
				</ul>
			</div>
		</div>
	</div>
</body>
<script>
	// 点击通知时移除红点
	function redDotRemove() {
		if(document.getElementById('alarmRedDot')) {
			document.getElementById('alarmRedDot').remove();
		}
	}
	
	// 将最后的通知时间保存到session和数据库
	function lastAlarmTime() {
		const XHR = new XMLHttpRequest();
		
		XHR.open('POST', '/catstagram/account/lastAlarmTimeSave', true);
		XHR.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
		XHR.send('lastAlarmTime='+'${alarmList[0].alarm_date}');
	}
	
	// 页面加载时检查是否有新通知并显示红点
	window.onload = () => {
		<c:if test='${!empty alarmList[0].alarm_date}'>
			const XHR = new XMLHttpRequest();
			
			XHR.onreadystatechange = () => {
			if(XHR.readyState == 4 && XHR.status == 200) {
					const result = JSON.parse(XHR.responseText);
					if(result == 1) {
						const alarmsSpan = document.getElementById('alarms');
						let alarmRedDot = document.getElementById('alarmRedDot');
						if(!alarmRedDot) {
							alarmRedDot = document.createElement('span');
							alarmRedDot.id = 'alarmRedDot';
							alarmRedDot.classList.add('position-absolute', 'top-0', 'start-50', 'translate-middle', 'p-1', 'bg-danger', 'border', 'border-light', 'rounded-circle');
							alarmsSpan.appendChild(alarmRedDot);
						}
					}
				}
			}
			
			XHR.open('POST', '/catstagram/account/lastAlarmTime', true);
			XHR.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
			XHR.send('lastAlarmTime='+'${alarmList[0].alarm_date}');
		</c:if>
	}
</script>
</html>