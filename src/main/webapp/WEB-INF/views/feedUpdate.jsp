<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>catstagram : 编辑帖子</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>
<link rel="stylesheet" type="text/css" href="/css/feedUpdate.css">
</head>
<body>
<%@ include file="header.jsp" %>
<div class="menu_title_div">
    <div class="menu_title">编辑帖子</div>
</div>

<form class="feed_write_div" action="/catstagram/account/feedUpdate" method="post">
    <div class="feed_write_img_div">
        <img src="/upload/feed/${feedInfo.feed_img}" class="feed_write_img" id="feedImgPreview">
    </div>
    <div class="feed_write_content_div">
        <textarea class="feed_write_content_textarea" id="feedWriteContent" name="feed_content" maxlength="500" oninput="textCheck()" required>${feedInfo.feed_content}</textarea>
        <div class="textCount">(<span id="textLength">000</span> / 500)</div>
        <div class="feed_write_btn_div">
            <input type="button" value="取消" class="btn btn-secondary feed_write_cancel_btn" onclick="javascript: location.href='/catstagram'">
            <input type="submit" value="修改" class="btn btn-primary feed_write_btn">
        </div>
    </div>
    <input type="hidden" name="feed_idx" value="${feedInfo.feed_idx}">
</form>
<%@ include file="footer.jsp" %>
</body>
<script>
	// 页面加载时显示帖子内容的字符数
    window.onload = () => {
    	if(document.getElementById('feedWriteContent').value.length < 10) {
            document.getElementById('textLength').innerHTML = '00'+document.getElementById('feedWriteContent').value.length;
        } else if(document.getElementById('feedWriteContent').value.length >= 10 && document.getElementById('feedWriteContent').value.length < 100) {
            document.getElementById('textLength').innerHTML = '0'+document.getElementById('feedWriteContent').value.length;
        } else {
            document.getElementById('textLength').innerHTML = document.getElementById('feedWriteContent').value.length;
        }
    }
    
    // 检查帖子内容的字符数
    function textCheck() {
        if(document.getElementById('feedWriteContent').value.length < 10) {
            document.getElementById('textLength').innerHTML = '00'+document.getElementById('feedWriteContent').value.length;
        } else if(document.getElementById('feedWriteContent').value.length >= 10 && document.getElementById('feedWriteContent').value.length < 100) {
            document.getElementById('textLength').innerHTML = '0'+document.getElementById('feedWriteContent').value.length;
        } else {
            document.getElementById('textLength').innerHTML = document.getElementById('feedWriteContent').value.length;
        }
    }
</script>
</html>