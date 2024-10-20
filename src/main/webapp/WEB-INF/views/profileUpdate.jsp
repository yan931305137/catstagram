<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>catstagram : 个人资料更新</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>
<link rel="stylesheet" type="text/css" href="/css/profileUpdate.css">
</head>
<body>
<%@ include file="header.jsp" %>
<div class="menu_title_div">
    <div class="menu_title">个人资料更新</div>
</div>

<form class="profile_update_div" action="/catstagram/account/profileUpdate" method="post">
    <div class="profile_update_img_div">
    	<c:if test="${empty sessionScope.simg}">
        	<img src="/img/default_photo2.png" class="profile_update_img" id="profileImgPreview">
        </c:if>
        <c:if test="${!empty sessionScope.simg}">
        	<img src="/upload/member/${sessionScope.simg}" class="profile_update_img" id="profileImgPreview">
        </c:if>
    </div>
    <div class="profile_update_content_div">
        <textarea class="profile_update_content_textarea" id="profileInto" name="member_intro" maxlength="100" oninput="textCheck()" placeholder="输入介绍...">${member_content}</textarea>
        <div class="textCount">(<span id="textLength">000</span> / 100)</div>
        <div class="profile_update_btn_div">
            <input type="button" value="取消" class="btn btn-secondary profile_update_cancel_btn" onclick="javascript: location.href='/catstagram/${sessionScope.sid}'">
            <input type="submit" value="更新" class="btn btn-primary profile_update_btn">
        </div>
    </div>
</form>
<div class="profile_img_div">
    <span class="profile_img_btn" onclick="profileImgPopup()">更换个人资料照片</span>
</div>
<%@ include file="footer.jsp" %>
</body>
<script>
	// 进入个人资料更新页面时加载个人介绍的字数
	window.onload = () => {
		if(document.getElementById('profileInto').value.length < 10) {
            document.getElementById('textLength').innerHTML = '00'+document.getElementById('profileInto').value.length;
        } else if(document.getElementById('profileInto').value.length >= 10 && document.getElementById('profileInto').value.length < 100) {
            document.getElementById('textLength').innerHTML = '0'+document.getElementById('profileInto').value.length;
        } else {
            document.getElementById('textLength').innerHTML = document.getElementById('profileInto').value.length;
        }
	}

    // 检查个人介绍字数
    function textCheck() {
        if(document.getElementById('profileInto').value.length < 10) {
            document.getElementById('textLength').innerHTML = '00'+document.getElementById('profileInto').value.length;
        } else if(document.getElementById('profileInto').value.length >= 10 && document.getElementById('profileInto').value.length < 100) {
            document.getElementById('textLength').innerHTML = '0'+document.getElementById('profileInto').value.length;
        } else {
            document.getElementById('textLength').innerHTML = document.getElementById('profileInto').value.length;
        }
    }
    
    // 个人资料图片修改弹窗
    function profileImgPopup() {
        window.open('/catstagram/account/profileImgPopup', 'popup', 'width=500, height=500');
    }
</script>
</html>