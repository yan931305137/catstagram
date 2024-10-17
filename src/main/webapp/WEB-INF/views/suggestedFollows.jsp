<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>catstagram : 推荐关注</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>
<link rel="stylesheet" type="text/css" href="/css/search.css">
<script type="text/javascript" src="/js/xmlHttpRequest.js"></script>
</head>
<body>
<%@ include file="header.jsp" %>
<div class="search_result_div">
    <div class="search_msg_div">
        <h4>💙 为你推荐 💙</h4>
    </div>
    
    <c:forEach var="suggestedFollows" items="${suggestedFollows}" varStatus="loop">
	    <div class="search_list_div">
	        <div class="img_and_id_and_name_div">
	            <c:if test="${empty suggestedFollows.following_img_of_my_following}">
	            	<img src="/img/default_photo2.png" class="search_profile_img" onclick="javacript: location.href='/catstagram/${suggestedFollows.following_id_of_my_following}'">&nbsp;
	            </c:if>
	            <c:if test="${!empty suggestedFollows.following_img_of_my_following}">
	            	<img src="/upload/member/${suggestedFollows.following_img_of_my_following}" class="search_profile_img" onclick="javacript: location.href='/catstagram/${suggestedFollows.following_id_of_my_following}'">&nbsp;
	            </c:if>
	            <div class="id_and_name_div">
	                <a href="/catstagram/${suggestedFollows.following_id_of_my_following}" class="search_list_id">${suggestedFollows.following_id_of_my_following}</a><br>
	                <c:if test="${suggestedFollows.num_of_followers eq 0}">
	                	<span class="search_list_name"><a class="first_following" href="/catstagram/${suggestedFollows.my_following_list_arr[0]}">${suggestedFollows.my_following_list_arr[0]}</a>关注了。</span>
	                </c:if>
	                <c:if test="${suggestedFollows.num_of_followers ne 0}">
	                	<span class="search_list_name"><a href="/catstagram/${suggestedFollows.my_following_list_arr[0]}" class="first_following">${suggestedFollows.my_following_list_arr[0]}</a>和
	                	<span id="suggestedFollowListId${loop.index}" data-index="${loop.index}"
		                	class="suggestedFollowList" data-bs-toggle="dropdown" aria-expanded="false"> 
	                		${suggestedFollows.num_of_followers}人</span>关注了。
	                		
	                		<div class="dropdown">
								<ul class="dropdown-menu" id="follow_list_ul${loop.index}">
									<c:forEach var="suggestedFollowList" items="${suggestedFollows.my_following_list_arr}">
										<li><a class="dropdown-item menu_font" href="/catstagram/${suggestedFollowList}">${suggestedFollowList}</a></li>
									</c:forEach>
								</ul>
		                	</div>
		                </span>
	                </c:if>
	            </div>
	        </div>
	        <div class="search_list_follow_btn_div">
				<input type="button" value="关注" id="follow${suggestedFollows.following_idx_of_my_following}" onclick="addFollowing(${suggestedFollows.following_idx_of_my_following})" class="btn btn-primary search_list_follow_btn">
	        </div>
	    </div>
	</c:forEach>
</div>
<img src="/img/upArrow.png" id="scrollToTopBtn" class="scroll_to_top_btn" onclick="scrollToTop()">
<%@ include file="footer.jsp" %>
</body>
<script>
    // 按钮点击后滚动到顶部
    function scrollToTop() {
        document.body.scrollTop = 0; // 适用于 Safari
        document.documentElement.scrollTop = 0; // 适用于 Chrome, Firefox, IE, 和 Opera
    }
    
    // 关注 - 不使用XMLHttpRequest模块化
    // 虽然服务器没有请求，但为了明确了解服务器是否处理成功，使用回调函数
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
    					followingBtn = document.createElement('input');
    					followingBtn.type = 'button';
    					followingBtn.value = '已关注';
    					followingBtn.id = 'following'+result;
    					followingBtn.className = 'btn btn-secondary follow_list_del_btn';
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
    					followBtn = document.createElement('input');
    					followBtn.type = 'button';
    					followBtn.value = '关注';
    					followBtn.id = 'follow'+result;
    					followBtn.className = 'btn btn-primary search_list_follow_btn';
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