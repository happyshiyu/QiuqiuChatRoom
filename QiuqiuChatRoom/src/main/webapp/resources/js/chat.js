$(document).ready(function() {
	//发送事件执行方法
    function send() {
        
        var val = $("#textarea").val();
        if(val != null && val != '') {
			websocket.send(val);
        }else {
        	alert("请输入内容后再发送");
        }
    }

    //创建socket
    var websocket;
    function createWebsocket() {
    	var token = $('#Token').val();
    	if ('WebSocket' in window) {
    		websocket = new WebSocket("ws://127.0.0.1:9090/ws?token="+token);
    	} else if ('MozWebSocket' in window) {
    		websocket = new MozWebSocket("ws://127.0.0.1:9090/ws?token="+token);
    	} else {
    		websocket = new SockJS("http://127.0.0.1:9090/ws?token="+token);
    	}
    	
        websocket.onopen = function(event) {
        	initUsers();
    		console.log("WebSocket:已连接");
    	};
    	
    	websocket.onmessage = function(ev) {
    		var obj = JSON.parse(ev.data);
    		if(!obj || obj == undefined) {
    			return false;
    		}
    		var users = obj.to;
    		var cur = obj.from;
    		if(!!obj.message) {// 如果空消息不予处理
    			var name = cur.id == token? '我':cur.name;
    			
    			var newmsg = "<div class='media'><div class='media-left'>"
                            + "<a href='#'>" 
                            + "<img height='50px' width='50px' class='media-object' src='" + cur.headImg + "' alt='...'></a></div>"
                            + "<div class='media-body'><h4 class='media-heading'>"  
                            +   "<h5 style='color:grey;'>" + name + "&nbsp;&nbsp;" +obj.createTime+ "</h5>"
                            +   "<p>" + obj.message + "</p></div></div><hr style='margin:0 0 0 0;'/>";
    			 $("#msgbox").append(newmsg);
    			 $("#msgbox").scrollTop($(".media").height()*$(".media").length)
				 $("#textarea").val("")
    		}
            refresh(users);
		};
		
		websocket.onerror = function(event) {
			console.log("WebSocket:发生错误 ");
		};
		
		websocket.onclose = function(event) {
			console.log("WebSocket:已关闭");
		}
    }
    
    //初始化WebSocket
    createWebsocket();

    //发送事件
    $("#send").click(function() {
    	send();
    });
    
    document.onkeydown = function(a) {
        var b = document.all ? window.event: a;
        return 13 == b.keyCode ? (send(), !1) : void 0
    };
    
    $.fn.setCursorPosition = function(a) {
        return 0 == this.lengh ? this: $(this).setSelection(a, a)
    };
    
    $.fn.setSelection = function(a, b) {
        if (0 == this.lengh) return this;
        if (input = this[0], input.createTextRange) {
            var c = input.createTextRange();
            c.collapse(!0),
            c.moveEnd("character", b),
            c.moveStart("character", a),
            c.select()
        } else input.setSelectionRange && (input.focus(), input.setSelectionRange(a, b));
        return this
    };
    $.fn.focusEnd = function() {
        this.setCursorPosition(this.val().length)
    }
});

//初始化用户列表
function initUsers() {
	var id = {
		id : $('#Token').val()	
	};
	
	$.ajax({
        url: "/chat/users",
        type: "POST",
        dataType: "JSON",
        contentType: "application/json",
        data: JSON.stringify(id),
        success: function(data) {
        	refresh(data.users);
        }
    });


}

function refresh(data) {
	var h = "";
	 $.each(data, function(key, obj) {
		 h += "<div class='media'><div class='media-left'>"
             + "<a href='#'>" 
             + "<img height='50px' width='50px' class='media-object' src='" + obj.headImg + "' alt='...'></a></div>"
             + "<div class='media-body'><h4 class='media-heading'>"  
             +   "<h5 style='color:grey;'>" + obj.name + "</h5></div></div>";
	 });
	 $('#onlineUser').html("").append(h);
}