<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>catstagram : 修改密码</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>
<link rel="stylesheet" type="text/css" href="/css/memberInfo.css">
<script type="text/javascript" src="/js/xmlHttpRequest.js"></script>
</head>
<body>
<%@ include file="header.jsp" %>
<div class="menu_title_div">
    <div class="menu_title">修改密码</div>
</div>
<form class="info_form" method="post" action="/catstagram/account/pwdUpdateOk" onsubmit="return validation()">
    <div class="info_div">
        <span class="menu_span">密码</span>
        <input type="password" name="member_pwd" id="userpwd" placeholder="密码(8~12个字符，字母、数字、特殊字符组合)" class="form-control form-control-sm input_textbox_deco" maxlength="12" oninput="pwdChk()" required> 
    </div>
    <div class="info_div">
        <span class="menu_span">确认密码</span>
        <input type="password" id="userpwdchk" placeholder="确认密码" class="form-control form-control-sm input_textbox_deco" maxlength="12" oninput="pwdChk()" required> 
    </div>
    <div class="info_btn">
        <input type="button" value="取消" onclick="javascript: location.href='/catstagram/account/infoUpdate'" class="btn btn-secondary info_cancel_btn">
        <input type="submit" value="修改" class="btn btn-primary info_ok_btn">
    </div>
</form>
<%@ include file="footer.jsp" %>
</body>
<script>
    // 密码有效性
    function pwdChk() {
		const reg = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,12}$/;
		const pwd = document.getElementById('userpwd');
		const pwdchk = document.getElementById('userpwdchk');

		if(pwd.value != '') {
			if(!reg.test(pwd.value)) {
				pwd.className = '';
				pwd.classList.add('form-control', 'form-control-sm', 'unavailable_it');
			} else {
				pwd.className = '';
				pwd.classList.add('form-control', 'form-control-sm', 'available_it');
			}

			if(pwdchk.value != '') {
				if(pwdchk.value == pwd.value) {
					if(reg.test(pwdchk.value)) {
						pwdchk.className = '';
						pwdchk.classList.add('form-control', 'form-control-sm', 'available_it');
					}
				} else {
					pwdchk.className = '';
					pwdchk.classList.add('form-control', 'form-control-sm', 'unavailable_it');
				}
			} else {
				pwdchk.className = '';
				pwdchk.classList.add('form-control', 'form-control-sm', 'input_textbox_deco');
			}
		} else {
			pwd.className = '';
			pwd.classList.add('form-control', 'form-control-sm', 'input_textbox_deco');
			pwdchk.className = '';
			pwdchk.classList.add('form-control', 'form-control-sm', 'input_textbox_deco');
		}
	}

    // 密码有效性检查
	function validation() {
		const pwd = document.getElementById('userpwd');
		const pwdchk = document.getElementById('userpwdchk');
		
		if(pwd.value != pwdchk.value || !pwd.classList.contains('available_it') || !pwdchk.classList.contains('available_it')) {
			window.alert('请确认密码。');
			return false;
			
		}
		return true;
	}
</script>
</html>