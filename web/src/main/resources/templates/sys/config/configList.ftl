<#assign sec=JspTaglibs["http://http://www.ahsj.link/security/tags"]/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>通用后台管理模板系统</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, config-scalable=0">
    <link rel="stylesheet" href="${request.contextPath}/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${request.contextPath}/layuiadmin/style/admin.css" media="all">
    <link rel="stylesheet" href="${request.contextPath}/layuiadmin/style/common.css" media="all">
</head>
<body>

<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
        <div class="layui-card">
            <@sec.authenticate grants="config:configList">
                <!-- 搜索条件start -->
                <form class="layui-form layui-card-header layuiadmin-card-header-auto"
                     id="searchForm">
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label">配置说明</label>
                            <div class="layui-input-block input-box">
                                <input type="text" name="info" placeholder="请输入"
                                       autocomplete="off"
                                       class="layui-input">
                            </div>
                        </div>

                        <div class="layui-inline">
                            <button type="button" class="layui-btn layui-btn-normal" id="configSearchBtn"
                                    data-type="reload">搜索
                            </button>
                            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                        </div>
                    </div>
                </form>
                <!-- 搜索条件end -->
            </@sec.authenticate>


                <!-- 数据表格start -->
                <div class="layui-card-body">
                    <table class="layui-hide" id="configList" lay-filter="configList-toolbar"></table>

                    <script type="text/html" id="configList-toolbar">
                        <div class="layui-btn-container">
                            <@sec.authenticate grants="config:add">
                                <button class="layui-btn layui-btn-sm layui-btn-primary" lay-event="add"><i
                                            class="layui-icon">&#xe654;</i>新增
                                </button>
                            </@sec.authenticate>
                        </div>
                    </script>


                    <script type="text/html" id="configList-editBar">
                        {{#  if(d.disable == '0'){
                        }}
                            <@sec.authenticate grants="config:update">
                                <a class="layui-btn layui-btn-xs layui-btn-primary" lay-event="update"><i
                                        class="layui-icon">&#xe642;</i>修改</a>
                            </@sec.authenticate>
                            <@sec.authenticate grants="config:delete">
                                <a class="layui-btn layui-btn-xs layui-btn-primary" lay-event="delete"><i
                                        class="layui-icon">&#xe640;</i>禁用</a>
                            </@sec.authenticate>
                        {{#  } else { }}
                        <@sec.authenticate grants="config:recovery">
                            <a class="layui-btn layui-btn-xs layui-btn-primary" lay-event="recovery"><i
                                        class="layui-icon">&#xe642;</i>恢复</a>
                        </@sec.authenticate>
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
<script src="${request.contextPath}/layui-extend.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/scripts/sys/config/configList.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/scripts/newForm.js?_=${randomNum}"></script>
</body>
</html>