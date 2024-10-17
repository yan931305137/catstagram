<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>catstagram : 上传头像</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>
<link rel="stylesheet" type="text/css" href="/css/profileImgPopup.css">
</head>
<body>
    <form class="profile_img_div" action="/catstagram/account/profileImgUpdate" method="post" onsubmit="return validation()" enctype="multipart/form-data">
        <div class="profile_img_update_div">
        	<c:if test="${empty sessionScope.simg}">
        		<img src="/img/default_photo2.png" class="profile_update_img" id="profileImgPreview">
        	</c:if>
        	<c:if test="${!empty sessionScope.simg}">
        		<img src="/upload/member/${sessionScope.simg}" class="profile_update_img" id="profileImgPreview">
        	</c:if>
        </div>
        <div class="profile_img_upload_btn_div">
            <input type="file" id="profileImgUpload" name="member_img" accept=".jpg, .jpeg, .png" onchange="extensionCheck(this); setProfileImg(event);" style="display: none">
            <input type="button" value="上传图片" class="btn btn-primary profile_img_upload_btn" onclick="document.getElementById('profileImgUpload').click();">
            <input type="button" value="删除" class="btn btn-secondary profile_img_del_btn" onclick="profileImgDel()">
        </div>
        <div class="profile_img_btn_div">
            <input type="button" value="取消" class="btn btn-secondary profile_img_cancel_btn" onclick="popupClose()">
            <input type="submit" value="修改" class="btn btn-primary profile_img_update_btn">
        </div>
    </form>
</body>
<script>
    // 图片上传时检查扩展名
    function extensionCheck(rp) {
        const filename = rp.value;
        const len = filename.length;
        const filetype = filename.substring(len-4, len);

        if(filetype!='.jpg' && filetype!='.png' && filetype!='jpeg') {
            window.alert('只能上传图片文件。');
            rp.value = '';
        }
    }

    // 上传图片预览 & 限制图片大小
    function setProfileImg(event) {
    	const profileImgUpload = document.getElementById('profileImgUpload');
    	if(profileImgUpload.files.length > 0) {
	        const fileSizeInBytes = profileImgUpload.files[0].size;
    		const maxSizeInBytes = 1024 * 1024 * 5; // 5MB
    		
    		if(fileSizeInBytes <= maxSizeInBytes) {
    	        const reader = new FileReader();
		        reader.onload = function(event) {
		            const img = document.getElementById('profileImgPreview');
		            img.setAttribute('src', event.target.result);
		        }
		        reader.readAsDataURL(event.target.files[0]);
    		} else {
    			window.alert('图片大小不能超过5MB。');
    			profileImgUpload.value = '';
    		}
    	}
    }
    
    // 从预览中删除头像
    function profileImgDel() {
    	document.getElementById('profileImgPreview').src = '/img/default_photo2.png';
    }
    
    // 验证
    function validation() {
        const fullProfileImgSrc = document.getElementById('profileImgPreview').src;
        const url = new URL(fullProfileImgSrc);
        const profileImgSrc = url.pathname; // /img/default_photo2.png

        // input file没有上传任何内容时
        if(document.getElementById('profileImgUpload').value == '') {
            // 头像被删除的情况（默认图片的情况）
            if(profileImgSrc == '/img/default_photo2.png') { // 没有进行任何操作就点击修改（没有头像的状态）
            	if(${sessionScope.simg eq ''}) {
            		window.alert('没有修改的头像。');
                    return false;
            	}
                return true;
            // 没有进行任何操作就点击修改（有头像的状态）
            } else if(profileImgSrc != '/img/default_photo2.png') {
                window.alert('没有修改的头像。');
                return false;
            }
        }
        return true;
    }
    
    // 关闭弹窗
    function popupClose() {
    	window.self.close();
    }
</script>
</html>