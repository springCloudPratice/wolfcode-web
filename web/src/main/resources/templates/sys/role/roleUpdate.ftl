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
    <link rel="stylesheet" href="${request.contextPath}/layuiadmin/layui/css/modules/formSelects/formSelects-v4.css" media="all">
    <link rel="stylesheet" href="${request.contextPath}/MultipleTreeSelect/css/zTreeStyle/zTreeStyle.css" media="all">
    <link rel="stylesheet" href="${request.contextPath}/layuiadmin/style/admin.css" media="all">
    <link rel="stylesheet" href="${request.contextPath}/layuiadmin/style/common.css" media="all">
    <link rel="stylesheet" href="${request.contextPath}/layuiadmin/style/popup.css" media="all">
</head>
<body>
<script>
</script>
<div class="layui-card layui-content">
    <div class="layui-card-body">
        <form class="layui-form"  action="" lay-filter="component-form-element">
            <input type="hidden" id="roleId" name="roleId" value="${id}">
            <input type="hidden" id="oPid" name="oPid" value="${sysRoleAndMenu.pid}">
            <div class="layui-row layui-col-space10 layui-form-item">
                <div class="layui-col-lg6">
                    <label class="layui-form-label">角色名称</label>
                    <div class="layui-input-block">
                        <input type="text" id="roleName" name="roleName" value="${sysRoleAndMenu.roleName}" lay-verify="roleName" autocomplete="off"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-col-lg6">
                    <label class="layui-form-label">角色编码</label>
                    <div class="layui-input-block">
                        <input type="text" id="roleCode" name="roleCode" lay-verify="roleCode" value="${sysRoleAndMenu.roleCode}"
                               autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-col-lg6">
                    <label class="layui-form-label">描述</label>
                    <div class="layui-input-block">
                        <input type="text" id="description" name="description" lay-verify="description" placeholder="角色描述" value="${sysRoleAndMenu.description}"
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
                <div class="layui-form-item">
                    <div class="layui-input-block">
                        <button class="layui-btn layui-btn-normal" lay-submit lay-filter="roleAdd-filter">修改</button>
                        <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>

<script src="${request.contextPath}/layuiadmin/layui/layui.js"></script>
<script src="${request.contextPath}/layui-extend.js"></script>
<script src="${request.contextPath}/webjars/jquery/jquery.min.js"></script>
<script src="${request.contextPath}/MultipleTreeSelect/js/jquery.ztree.all.js"></script>
<script>
    layui.config({
        base: '${request.contextPath}/layuiadmin/'
    }).extend({
        formSelects: 'lib/formSelects-v4.min',
    });
    var pid = '${sysRoleAndMenu.pid}'
</script>
<script src="${request.contextPath}/layuiadmin/layui-xtree/layui-xtree.js"></script>
<script type="text/javascript" src="${request.contextPath}/scripts/sys/role/roleUpdate.js?_=${randomNum}"></script>
</body>