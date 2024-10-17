<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>catstagram</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>
</head>
<body>
<div class="modal fade" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true" id="myModal">
	<div class="modal-dialog">
		<div class="modal-content">
		<div class="modal-header">
			<h1 class="modal-title fs-5" id="exampleModalLabel">Catstagram 🐱</h1>
			<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
		</div>
		<div class="modal-body">
			${msg}
		</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-primary">确认</button>
		</div>
		</div>
	</div>
</div>
</body>
<script>
	window.onload = () => {
		const modal = new bootstrap.Modal(document.getElementById('myModal'), {
			backdrop: 'static', // 点击模态框外部区域时，模态框不会关闭
			keyboard: false // 按下ESC键时，模态框不会关闭
		});

		modal.show();

		const okButton = document.querySelector('#myModal .modal-footer button');
		okButton.onclick = function() {
			modal.hide();
			window.self.close();
			window.opener.location.reload();
		}
		
		// 按下Enter键时也触发click事件
		document.addEventListener('keydown', function(event) {
			if(event.key === 'Enter') {
				okButton.click();
			}
		});
	}
</script>
</html>
