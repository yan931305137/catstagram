<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>catstagram : 关注</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>
<link rel="stylesheet" type="text/css" href="/css/follow.css">
<script type="text/javascript" src="/js/xmlHttpRequest.js"></script>
</head>
<body>
<%@ include file="header.jsp" %>
<div class="follow_nav_div">
    <ul class="nav nav-underline">
        <li class="nav-item nav_bottom_border">
            <a class="nav-link" href="/catstagram/account/follower">粉丝</a>
        </li>
        <li class="nav-item">
            <a class="nav-link active" aria-current="page" href="/catstagram/account/following">关注</a>
        </li>
    </ul>
</div>
<div class="follow_div">
	<c:forEach var="dto" items="${list}">    
	    <div class="follow_list_div">
	        <div class="img_and_id_and_name_div">
	            <c:if test="${empty dto.member_img}">
					<a href="/catstagram/${dto.member_id}"><img src="/img/default_photo2.png" class="follow_profile_img"></a>&nbsp;
            	</c:if>
            	<c:if test="${!empty dto.member_img}">
            		<a href="/catstagram/${dto.member_id}"><img src="/upload/member/${dto.member_img}" class="follow_profile_img"></a>&nbsp;
            	</c:if>
	            <div class="id_and_name_div">
	                <a href="/catstagram/${dto.member_id}" class="follow_list_id">${dto.member_id}</a><br>
	                <a class="follow_list_name">${dto.member_name}</a>
	            </div>
	        </div>
	        <div class="follow_list_btn_div">
	            <input type="button" value="已关注" id="following${dto.member_idx}" onclick="cancelFollowing(${dto.member_idx})" class="btn btn-secondary follow_list_del_btn">
	        </div>
	    </div>
	</c:forEach>
</div>
<img src="/img/upArrow.png" id="scrollToTopBtn" class="scroll_to_top_btn" onclick="scrollToTop()">
<%@ include file="footer.jsp" %>
</body>
<script>
    // 点击按钮滚动到顶部
    function scrollToTop() {
        document.body.scrollTop = 0; // 适用于 Safari
        document.documentElement.scrollTop = 0; // 适用于 Chrome, Firefox, IE 和 Opera
    }
    
 	// 添加关注 - 不使用XMLHttpRequest模块化
    // 为了明确知道服务器是否处理成功，使用回调
    function addFollowing(member_idx) {	
    	const XHR = new XMLHttpRequest();
    	XHR.onreadystatechange = function() {
    		if(XHR.readyState == 4 && XHR.status == 200) {
    			const result = JSON.parse(XHR.responseText);
    			const followBtn = document.getElementById('follow'+result);
    			let followingBtn = document.getElementById('following'+result);
    			const parentDiv = followBtn.parentNode;
    			
    			if(parentDiv) {
    				parentDiv.removeChild(followBtn); // 移除关注按钮
    				
    				if(!followingBtn) {
    					followingBtn = document.createElement("input");
    					followingBtn.type = "button";
    					followingBtn.value = "已关注";
    					followingBtn.id = "following"+result;
    					followingBtn.className = "btn btn-secondary follow_list_del_btn";
    					followingBtn.onclick = () => {
    						cancelFollowing(result);
    					}
    					
    					if(parentDiv) {
    						parentDiv.appendChild(followingBtn);
    					}
    				}
    			}
    		}
    	}
    	XHR.open('POST', '/catstagram/account/following', true);
    	XHR.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    	XHR.send('to='+member_idx);
    }
    
    // 取消关注
    function cancelFollowing(member_idx) {
    	const param = 'to='+member_idx;
    	sendRequest('/catstagram/account/cancelFollowing', param, cancelFollowingCallBack, 'POST');
    }
    
    function cancelFollowingCallBack() {
    	if(XHR.readyState == 4) {
    		if(XHR.status == 200) {
    			const result = JSON.parse(XHR.responseText);
    			const followingBtn = document.getElementById('following'+result);
    			let followBtn = document.getElementById('follow'+result);
    			const parentDiv = followingBtn.parentNode;
    			
    			if(parentDiv) {
    				parentDiv.removeChild(followingBtn); // 移除已关注按钮
    				
    				if(!followBtn) {
    					followBtn = document.createElement("input");
    					followBtn.type = "button";
    					followBtn.value = "关注";
    					followBtn.id = "follow"+result;
    					followBtn.className = "btn btn-primary follow_list_follow_btn";
    					followBtn.onclick = () => {
    						addFollowing(result);
    					}
    					
    					if(parentDiv) {
    						parentDiv.appendChild(followBtn);
    					}
    				}
    			}
    		}
    	}
    }
</script>
</html>