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
    <link rel="stylesheet" href="${request.contextPath}/MultipleTreeSelect/css/zTreeStyle/zTreeStyle.css" media="all">
</head>
<body>

<div class="layui-card layui-content">
    <div class="layui-card-body">
        <form class="layui-form" action="" lay-filter="component-form-element">
            <div class="layui-row layui-col-space10 layui-form-item">
                <div class="layui-col-lg6">
                    <label class="layui-form-label">角色名称</label>
                    <div class="layui-input-block">
                        <input type="text" id="roleName" name="roleName" lay-verify="roleName" placeholder="请输入角色名" autocomplete="off"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-col-lg6">
                    <label class="layui-form-label">角色编码</label>
                    <div class="layui-input-block">
                        <input type="text" id="roleCode" name="roleCode" lay-verify="roleCode" placeholder="请输入角色编码"
                               autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-col-lg6">
                    <label class="layui-form-label">描述</label>
                    <div class="layui-input-block">
                        <input type="text" id="description" name="description" lay-verify="description" placeholder="角色描述"
                               autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-col-lg6 role-tree-box">
                    <label class="layui-form-label">授权</label>
                    <div id="xtree1"></div>
                </div>
                <div class="layui-col-lg6 role-tree-box">
                    <label class="layui-form-label">上级角色</label>
                    <div class="content-page">
                        <div id="organization-tree" class="ztree"></div>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button class="layui-btn layui-btn-normal" lay-submit lay-filter="roleAdd-filter">新增</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
        </form>
    </div>
</div>

<script src="${request.contextPath}/layuiadmin/layui/layui.js"></script>
<script src="${request.contextPath}/webjars/jquery/jquery.min.js"></script>
<script src="${request.contextPath}/layuiadmin/layui-xtree/layui-xtree.js"></script>
<script src="${request.contextPath}/MultipleTreeSelect/js/jquery.ztree.all.js"></script>
<script src="${request.contextPath}/layui-extend.js"></script>
<script type="text/javascript" src="${request.contextPath}/scripts/sys/role/roleAdd.js?_=${randomNum}"></script>
</body>