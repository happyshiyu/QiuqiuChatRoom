<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>修改密码</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-1.7.2.min.js"></script>
</head>
<script>
	function change() {
		$.ajax({
	        url: "/change",
	        type: "POST",
	        contentType : "application/x-www-form-urlencoded; charset=utf-8",
	        data: $("#mainform").serialize(),
	        success: function(data) {
	        	if(data.code == 200) {
	        		alert("修改成功");
	        		window.location.href="/index";
	        	} else {
	        		alert("用户名或密码错误");
	        	}
	        }
	    });
	}
</script>
<body>
    <div class="container" style="margin-top: 100px;">
        <form id="mainform" class="center-block" style="width:50%;border: 1px solid grey;
        padding: 20px 10px 50px 10px;" >
            <h1 class="text-center" style="width: 100%">修改密码</h1>
            <div class="form-group">
                <label for="username">账号</label>
                <input type="text" class="form-control" id="username" placeholder="请输入用户名" name="username">
            </div>
            <div class="form-group">
                <label for="oldpass">原密码</label>
                <input type="password" class="form-control" id="oldpass" placeholder="请输入原密码" name="oldpass">
            </div>
            <div class="form-group">
                <label for="newpass">新密码</label>
                <input type="password" class="form-control" id="newpass" placeholder="请输入新密码" name="newpass">
            </div>
            <div>
                <button type="button" class="btn btn-default center-block" 
                style="width: 60%;margin-top: 40px;" onclick="change()">修改密码</button>
                <button type="reset" class="btn btn-default center-block" style="width: 60%;margin-top: 20px;">重置</button>
                <a href="/index" style="margin-top: 20px;float: right;;">点我返回登录界面</a>
            </div>
        </form>
    </div>
</body>
</html>