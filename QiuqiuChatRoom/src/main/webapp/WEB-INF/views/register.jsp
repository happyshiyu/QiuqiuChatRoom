<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>注册</title>
     <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
</head>
<body>
    <div class="container" style="margin-top: 100px;">
        <form class="center-block" style="width:50%;border: 1px solid grey; 
        padding: 20px 10px 50px 10px;" action="/register" method="post" enctype="multipart/form-data" >
            <h1 class="text-center" style="width: 100%">注册</h1>
            <div class="form-group">
                <label for="username">账号</label>
                <input type="text" class="form-control" id="username" placeholder="请输入用户名" name="username">
            </div>
            <div class="form-group">
                <label for="password">密码</label>
                <input type="password" class="form-control" id="password" placeholder="请输入密码" name="password">
            </div>
            <div class="form-group">
                <label for="name">昵称</label>
                <input type="text" class="form-control" id="name" placeholder="请输入昵称" name="name">
            </div>
            
            <div class="form-group">
                <label for="headshot">昵称</label>
                <input type="file" multiple="multiple" class="form-control" placeholder="请输入昵称" name="fileUpload">
            </div>
            <div>        
            	<p style="color: red;">${msg }</p>
                <button type="submit" class="btn btn-default center-block" style="width: 60%;margin-top: 40px;">注册</button>
                <button type="reset" class="btn btn-default center-block" style="width: 60%;margin-top: 20px;">重置</button>
                <a href="/index" style="margin-top: 20px;float: right;;">已有帐号，点我返回登录界面</a>
            </div>
        </form>
    </div>
</body>
</html>