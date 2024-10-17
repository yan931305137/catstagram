<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>catstagram : 会员注销</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>
<link rel="stylesheet" type="text/css" href="/css/memberInfo.css">
</head>
<body>
<%@ include file="header.jsp" %>
<div class="menu_title_div">
    <div class="menu_title">会员注销</div>
</div>
<form class="info_form2" action="/catstagram/account/quitOk" method="post" onsubmit="return check()">
    <div class="info_div">
        <div id="quitAgreement">
			<!-- import -->
		</div>
    </div>
    <div class="chk_div">
    <div class="form-check">
        <label class="form-check-label" for="chk">(必填)我已阅读并同意所有注意事项和指南。</label>&nbsp;
        <input type="checkbox" id="chk" class="form-check-input">
    </div>
    </div>
    <div class="info_btn2">
        <input type="submit" value="注销" class="btn btn-secondary info_cancel_btn">
        <input type="button" value="取消" onclick="javascript: location.href='/catstagram/account/infoUpdate'" class="btn btn-primary info_ok_btn">
    </div>
</form>
<%@ include file="footer.jsp" %>
</body>
<script>
    window.onload = function() {
		document.getElementById("quitAgreement").innerHTML = 
			'<object type="text/html" data="/etc/quit.html" style="width:840px; margin: 10px auto auto 40px; border: 1px solid #ededed; padding: 10px; height: 400px;"></object>';
	}

    // 注销同意检查后会员注销
    function check() {
        const chk = document.getElementById('chk').checked;
		if(chk == true) {
            return true;
		} else if(chk == false) {
			window.alert('请勾选必填项。');
			return false;
		}
	}
</script>
</html>