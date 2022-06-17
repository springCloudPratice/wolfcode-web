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
    <link rel="stylesheet" href="${request.contextPath}/layuiadmin/style/admin.css" media="all">
    <link rel="stylesheet" href="${request.contextPath}/layuiadmin/style/common.css" media="all">
    <link rel="stylesheet" href="${request.contextPath}/layuiadmin/style/popup.css" media="all">
</head>
<body>

<div class="layui-card layui-content">
    <div class="layui-card-body">
        <form class="layui-form" action="" lay-filter="component-form-element">
            <div class="layui-row layui-col-space10 layui-form-item">
                <div class="layui-col-lg6">
                    <label class="layui-form-label">配置标识</label>
                    <div class="layui-input-block">
                        <input type="text" id="configKey" name="configKey" lay-verify="configKey" placeholder="输入配置代码" autocomplete="off"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-col-lg6">
                    <label class="layui-form-label">所属配置</label>
                    <div class="layui-input-block">
                        <input type="text" id="parentKey" name="parentKey" lay-verify="parentKey" placeholder="顶级配置可不填,默认-"
                               autocomplete="off" class="layui-input" value="-">
                    </div>
                </div>
                <div class="layui-col-lg6">
                    <label class="layui-form-label">配置描述</label>
                    <div class="layui-input-block">
                        <input type="text" id="info" name="info" lay-verify="info" placeholder="配置描述,描述这个配置做什么的"
                               autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-col-lg6">
                    <label class="layui-form-label">配置的属性</label>
                    <div class="layui-input-block">
                        <input type="text" id="configValue" name="configValue" lay-verify="configValue" placeholder="配置的属性值"
                               autocomplete="off" class="layui-input">
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button class="layui-btn layui-btn-normal" lay-submit lay-filter="configAdd-filter">新增</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
        </form>
    </div>
</div>

<script src="${request.contextPath}/layuiadmin/layui/layui.js"></script>
<script src="${request.contextPath}/layui-extend.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/scripts/sys/config/configAdd.js?_=${randomNum}"></script>
</body>