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
    <link rel="stylesheet" href="${request.contextPath}/MultipleTreeSelect/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <style>
        .ztree-select-body {
            position: absolute;
            left: 0;
            top: 42px;
            padding: 5px 0;
            z-index: 9999;
            min-width: 100%;
            border: 1px solid #d2d2d2;
            max-height: 300px;
            overflow-y: auto;
            background-color: #fff;
            border-radius: 2px;
            box-shadow: 0 2px 4px rgba(0,0,0,.12);
            box-sizing: border-box;
        }
    </style>
</head>
<body>

<div class="layui-card layui-content">
    <div class="layui-card-body">
        <form class="layui-form" lay-filter="component-form-element">
            <input type="hidden" id="menuId" name="menuId" value="${id}">
            <input type="hidden" id="parentId" name="parentId" value="${parentId}">
            <div class="layui-row layui-form-item">
                <div class="layui-col-lg6">
                    <label class="layui-form-label text-lable" style="line-height: 30px">类型</label>
                    <div class="layui-input-block text-input">
                        <#if sysMenu.menuType == 1>
                            <input type="radio" name="menu" checked="checked" lay-filter="menuType-radio" value="1" title="目录"/>
                        <#elseif sysMenu.menuType == 2 || sysMenu.menuType == 3>
                            <input type="radio" name="menu" checked="checked" lay-filter="menuType-radio" value="2" title="菜单"/>
                        <#elseif sysMenu.menuType ==4>
                            <input type="radio" name="menu" checked="checked" lay-filter="menuType-radio" value="4" title="按钮"/>
                        </#if>
                    </div>
                </div>
            </div>
        </form>
        <#if sysMenu.menuType == 1>
            <#include '/sys/menu/catalogForm.ftl'>
        <#elseif sysMenu.menuType == 2 || sysMenu.menuType == 3>
            <#include '/sys/menu/menuForm.ftl'>
        <#elseif sysMenu.menuType == 4>
            <#include '/sys/menu/buttonForm.ftl'>
        </#if>
    </div>
</div>

<script src="${request.contextPath}/layuiadmin/layui/layui.js"></script>
<script src="${request.contextPath}/webjars/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/MultipleTreeSelect/js/jquery.ztree.all.js"></script>
<script src="${request.contextPath}/layui-extend.js"></script>
<script>
    layui.config({
        base: '${request.contextPath}/layuiadmin/' //静态资源所在路径
    }).extend({
        // index: 'lib/index' //主入口模块
        // ,treeSelect: 'treeSelect/treeSelect'
        iconPicker: 'iconPicker/iconPicker'
    }).use('iconPicker');
</script>
<script type="text/javascript" src="${request.contextPath}/static/scripts/sys/menu/menuUpdate.js?_=${randomNum}"></script>
</body>