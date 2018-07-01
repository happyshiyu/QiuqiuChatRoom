<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Qiuqiu聊天室：登录</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
</head>
<body>
    <div class="container" style="margin-top: 100px;">
        <form class="center-block" style="width:50%;border: 1px solid grey;
        padding: 20px 10px 50px 10px;" action="/login" method="post">
            <h1 class="text-center" style="width: 100%">Qiuqiu聊天室</h1>
            <div class="form-group">
                <label for="username">账号</label>
                <input type="text" class="form-control" id="username" placeholder="请输入用户名" name="username" required ="true">
            </div>
            <div class="form-group">
                <label for="password">密码</label>
                <input type="password" class="form-control" id="password" placeholder="请输入密码" name="password" required ="true">
            </div>
            <div>
            	<p style="color: red;">${msg }</p>
                <button type="submit" class="btn btn-default center-block" style="width: 60%;margin-top: 40px;">登录</button>
                <a href="/toRegister">
                    <button type="button" class="btn btn-default center-block" style="width: 60%;margin-top: 20px;">注册</button>
                </a>       
                <a href="/toChange" style="margin-top: 20px;float: right;;">修改密码</a>
            </div>       
        </form>
    </div>
</body>
</html>