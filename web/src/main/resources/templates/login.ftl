<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>通用后台管理模板系统</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link rel="shortcut icon" href="${request.contextPath}/static/favicon.ico"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="${request.contextPath}/static/layuiAdmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${request.contextPath}/static/layuiAdmin/style/admin.css" media="all">
    <link rel="stylesheet" href="${request.contextPath}/static/layuiAdmin/style/login.css" media="all">
    <style>
        .layadmin-user-login-header {
            padding: 20px 20px 0px 20px;
        }

        .layadmin-user-login-header h2 {
            margin-bottom: 0px;
        }
    </style>
    <script>
        if (window != top) {
            top.location.href = location.href;
        }
    </script>
</head>
<body style="background: url(${request.contextPath}/static/images/bgimage.png) repeat-x 0">

<div class="layadmin-user-login layadmin-user-display-show" id="LAY-user-login" style="display: none;">
    <input type="hidden" value="${publicKey}" id="publicKey">
    <div class="layadmin-user-login-main" style="background: #fff">
        <div class="layadmin-user-login-box layadmin-user-login-header">
            <h2>系统登录</h2>
        </div>
        <div class="layadmin-user-login-box layadmin-user-login-body layui-form">
            <div class="layui-form-item">
                <label class="layadmin-user-login-icon layui-icon layui-icon-username"></label>
                <input type="text" name="username" lay-verify="required" placeholder="用户名"
                       class="layui-input" value="admin">
            </div>
            <div class="layui-form-item">
                <label class="layadmin-user-login-icon layui-icon layui-icon-password"></label>
                <input type="password" name="password" lay-verify="required"
                       placeholder="密码" class="layui-input" value="123456@!Ab">
            </div>
            <div class="layui-form-item">
                <div class="layui-row">
                    <div class="layui-col-xs7">
                        <label class="layadmin-user-login-icon layui-icon layui-icon-vercode"></label>
                        <input type="text" name="code" lay-verify="required"
                               placeholder="图形验证码" class="layui-input" value="123">
                    </div>
                    <div class="layui-col-xs5">
                        <div style="margin-left: 10px;cursor: pointer">
                            <img src="${request.contextPath}/sign/captcha"
                                 class="layadmin-user-login-codeimg"
                                 id="LAY-captcha">
                        </div>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <button class="layui-btn layui-btn-fluid layui-btn-normal" lay-submit lay-filter="LAY-user-login-submit">登录</button>
            </div>
        </div>
    </div>
</div>

<script src="${request.contextPath}/static/layuiAdmin/layui/layui.js"></script>
<script src="${request.contextPath}/static/jsencrypt.min.js"></script>
<script src="${request.contextPath}/static/layui-extend.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/scripts/sys/sign/signIn.js?_=${randomNum}"></script>
</body>
</html>