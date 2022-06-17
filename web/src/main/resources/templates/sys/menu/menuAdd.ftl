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
    <link rel="stylesheet" href="${request.contextPath}/layuiadmin/style/admin.css?_=${randomNum}" media="all">
    <link rel="stylesheet" href="${request.contextPath}/layuiadmin/style/common.css?_=${randomNum}" media="all">
    <link rel="stylesheet" href="${request.contextPath}/layuiadmin/style/popup.css?_=${randomNum}" media="all">
</head>
<body>

<div class="layui-card layui-content">
    <div class="layui-card-body">
        <form class="layui-form" lay-filter="component-form-element">
            <div class="layui-col-lg6 form-item">
                <label class="layui-form-label text-lable" style="line-height: 30px">类型</label>
                <div class="layui-input-block text-input">
                    <input type="radio" name="menu" checked="checked" lay-filter="menuType-radio" value="1" title="目录"/>
                    <input type="radio" name="menu" lay-filter="menuType-radio" value="2" title="菜单"/>
                    <input type="radio" name="menu" lay-filter="menuType-radio" value="4" title="按钮"/>
                </div>
            </div>
        </form>
        <#include '/sys/menu/catalogForm.ftl'>
        <#include '/sys/menu/menuForm.ftl'>
        <#include '/sys/menu/buttonForm.ftl'>
    </div>
</div>

<script src="${request.contextPath}/layuiadmin/layui/layui.js"></script>
<script src="${request.contextPath}/webjars/jquery/jquery.min.js"></script>
<script src="${request.contextPath}/layui-extend.js"></script>
<script>
    layui.config({
        base: '${request.contextPath}/layuiadmin/' //静态资源所在路径
    }).extend({
        // treeSelect: 'treeSelect/treeSelect',
        iconPicker: 'iconPicker/iconPicker',
        treeSelect: 'lib/extend/treeSelect'
    }).use(['iconPicker',]);

</script>
<script type="text/javascript" src="${request.contextPath}/static/scripts/sys/menu/menuAdd.js?_=${randomNum}"></script>
</body>