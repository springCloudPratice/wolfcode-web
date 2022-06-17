<#assign sec=JspTaglibs["http://http://www.ahsj.link/security/tags"]/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>高度最大适应 - 数据表格</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, menu-scalable=0">
    <link rel="stylesheet" href="${request.contextPath}/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${request.contextPath}/layuiadmin/style/admin.css" media="all">
    <link rel="stylesheet" href="${request.contextPath}/layuiadmin/style/common.css" media="all">
</head>
<body>

<div class="layui-fluid">

    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <!-- 数据表格start -->
                <div class="layui-card-body">
                    <table class="layui-hide" id="menuList" lay-filter="menuList-toolbar"></table>

                    <script type="text/html" id="menuList-toolbar">
                        <div class="layui-btn-container">
                            <@sec.authenticate grants="menu:add">
                                <button class="layui-btn layui-btn-sm layui-btn-primary" lay-event="add"><i class="layui-icon">&#xe654;</i>新增</button>
                            </@sec.authenticate>
                            <button class="layui-btn layui-btn-sm layui-btn-primary" lay-event="expand">展开</button>
                            <button class="layui-btn layui-btn-sm layui-btn-primary" lay-event="fold">折叠</button>
                            <button class="layui-btn layui-btn-sm layui-btn-primary layui-hide" lay-event="menuSearchBtn" id="menuSearchBtn">刷新</button>
                        </div>
                    </script>

                    <script type="text/html" id="menuList-editBar">
                        {{#  if(d.disable == '0'){
                        }}
                        <@sec.authenticate grants="menu:update">
                            <a class="layui-btn layui-btn-xs layui-btn-primary" lay-event="update">修改<i class="layui-icon">&#xe642;</i></a>
                        </@sec.authenticate>
                        <@sec.authenticate grants="menu:delete">
                            <a class="layui-btn layui-btn-xs layui-btn-primary" lay-event="delete">删除<i class="layui-icon">&#xe640;</i></a>
                        </@sec.authenticate>
                        {{#  } else { }}
                            <a class="layui-btn layui-btn-xs layui-btn-primary" lay-event="recovery">恢复<i class="layui-icon">&#xe640;</i></a>
                        {{#  } }}
                    </script>
                </div>
                <!-- 数据表格end -->
            </div>
        </div>
    </div>
</div>
<script src="${request.contextPath}/layuiadmin/layui/layui.js"></script>
<script src="${request.contextPath}/webjars/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/scripts/jquery.placeholder.js?_=${randomNum}"></script>
<script src="${request.contextPath}/layui-extend.js"></script>
<script type="application/javascript">
    layui.config({
        base: '${request.contextPath}/layuiadmin/'
    }).extend({
        treetable: 'treetable-lay/treetable'
    }).use(['treetable'], function () {
        var treetable = layui.treetable;

    });
</script>
<script type="text/javascript" src="${request.contextPath}/static/scripts/sys/menu/menuList.js?_=${randomNum}"></script>
</body>
</html>