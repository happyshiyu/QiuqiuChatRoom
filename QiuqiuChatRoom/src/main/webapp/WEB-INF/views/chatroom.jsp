<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Qiuqiu聊天室</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-1.7.2.min.js"></script>
    <script src="/resources/js/chat.js"></script>
</head>
<body>
    <div class="container" style="margin-top: 100px;">
        <div class="col-md-9" >
            <div class="panel panel-default">
                <div class="panel-heading">${name }</div>
                <div id="msgbox" class="panel-body" style="height: 380px;overflow-x: none; overflow-y: auto; resize: vertical;">
                </div>
            </div>
            <div class="panel panel-default">
                    <div class="panel-heading">消息内容</div>
                    <div class="panel-body" style="height: 160px;">
                        <textarea name="textarea" id="textarea" cols="110" rows="4"></textarea>
                        <button type="button" id="send" class="btn btn-default" style="float: right;">发送</button>
                    </div>
                </div>
        </div>
        <div class="col-md-3" >
                <div class="panel panel-default">
                    <div class="panel-heading" >在线用户</div>
                    <div class="panel-body" id="onlineUser" style="height: 600px; overflow-x: none; overflow-y: auto; resize: vertical;">
                        
                    </div>
                </div>
            </div>
    </div>
    <input type="hidden" id="Token" value="${token}">
</body>
</html>