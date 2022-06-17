layui.use(['form', 'layer',], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery;
    //开启websocket监控
    $('#openSocket').on('click', function () {
        openSocket()
    });

    //关闭websocket监控
    $('#closeSocket').on('click', function () {
        closeSocket()
    });

});

var stompClient = null;


function openSocket() {
    if (typeof WebSocket == 'undefined') {
        layer.msg("你的浏览器不支持这个功能,请更换谷歌,火狐,或者ie10以上浏览器");
        return;
    }
    if (!(window.WebSocket && window.WebSocket.prototype.send)) {
        layer.msg("你的浏览器不支持这个功能,请更换谷歌,火狐,或者ie10以上浏览器");
        return;
    }
    if (stompClient == null) {
        var socket = new SockJS(web.rootPath() + "websocket?token=kl");
        stompClient = Stomp.over(socket);
        stompClient.connect({token: "kl"}, function (frame) {
            stompClient.subscribe('/topic/pullLogger', function (event) {
                var content = JSON.parse(event.body);
                $("#log-container div").append(content.timestamp + " " + content.level + " --- [" + content.threadName + "] " + content.className + "   :" + content.body).append("<br/>");
                $("#log-container").scrollTop($("#log-container div").height() - $("#log-container").height());
            }, {
                token: "kltoen"
            });
        });
    }
}

function closeSocket() {
    if (stompClient != null) {
        stompClient.disconnect();
        stompClient = null;
    }
}