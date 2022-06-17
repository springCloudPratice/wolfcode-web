<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>通用后台管理模板系统</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="${request.contextPath}/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${request.contextPath}/layuiadmin/layui/css/modules/formSelects/formSelects-v4.css"
          media="all">
    <link rel="stylesheet" href="${request.contextPath}/layuiadmin/style/admin.css" media="all">
</head>
<body>

<div class="layui-col-md12">
    <div class="layui-card">
        <div class="layui-card-header" style="color: #555;">1.如无模板,请先下载模板到本地磁盘
            <a href="javascript:void(0)"
               style="text-decoration: underline;text-decoration-color: #1E9FFF;color: #1E9FFF"
               id="templateHandle">下载模板</a>
        </div>
        <div class="layui-card-body" style="text-align: center">
            <div class="layui-upload-drag" id="importUser-upload-drag">
                <i class="layui-icon"></i>
                <p>点击上传，或将文件拖拽到此处</p>
            </div>
            <p style="color: orange;text-align: left"><i class="layui-icon">&#xe702;</i>必填信息:账号,姓名,手机号码</p>
        </div>
    </div>
</div>
<div class="layui-col-md12">
    <div class="layui-card">
        <div class="layui-card-body" style="text-align: center">
            <a id='uploadBtn' class="layui-btn layui-btn-normal"><i class="layui-icon"></i>上传</a>
        </div>
    </div>
</div>

<script src="${request.contextPath}/layuiadmin/layui/layui.js"></script>
<script src="${request.contextPath}/layui-extend.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/scripts/sys/user/importUser.js?_=${randomNum}"></script>
</body>