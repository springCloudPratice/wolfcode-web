<#assign sec=JspTaglibs["http://http://www.ahsj.link/security/tags"]/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>通用后台管理模板系统</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, operationLog-scalable=0">
    <link rel="stylesheet" href="${request.contextPath}/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${request.contextPath}/layuiadmin/style/admin.css" media="all">
    <link rel="stylesheet" href="${request.contextPath}/layuiadmin/style/common.css" media="all">
</head>
<body>

<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">

                <!-- 搜索条件start -->
                 <@sec.authenticate grants="log:operationLogList">
                <form class="layui-form layui-card-header layuiadmin-card-header-auto" id="searchForm">
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label">用户名</label>
                            <div class="layui-input-block input-box">
                                <input type="text" name="username" placeholder="请输入用户名" autocomplete="off"
                                       class="layui-input">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">用户操作</label>
                            <div class="layui-input-block input-box">
                                <select name="operationType" xm-select="operationLogs-select2" xm-select-show-count="2" xm-select-search
                                        xm-select-skin="default"
                                        xm-select-height="36px">
                                    <option value="">请选择</option>
                                    <#list list as operationLog>
                                        <option value="${operationLog.operationType}">${operationLog.operationType}</option>
                                    </#list>
                                </select>
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">操作时间</label>
                            <div class="layui-input-inline input-box">
                                <input type="text" class="layui-input" id="operationTime"
                                       placeholder="操作时间">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <button type="button" class="layui-btn layui-btn-normal" id="operationLogSearchBtn" data-type="reload">查询</button>
                            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                        </div>
                    </div>
                </form>
                <!-- 搜索条件end -->
                 </@sec.authenticate>

                <!-- 数据表格start -->
                <div class="layui-card-body">
                    <table class="layui-hide" id="operationLogList" lay-filter="operationLogList-toolbar"></table>
                </div>
                <!-- 数据表格end -->
            </div>
        </div>
    </div>
</div>

<script src="${request.contextPath}/layuiadmin/layui/layui.js"></script>
<script src="${request.contextPath}/webjars/jquery/jquery.min.js"></script>
<script src="${request.contextPath}/layui-extend.js"></script>
<script type="text/javascript">
    layui.config({
        base: '${request.contextPath}/layuiadmin/'
    }).extend({
        formSelects: 'lib/formSelects-v4.min'
    });
</script>
<script type="text/javascript" src="${request.contextPath}/static/scripts/sys/operationLog/operationLogList.js?_=${randomNum}"></script>
<script type="text/javascript" src="${request.contextPath}/static/scripts/newForm.js?_=${randomNum}"></script>
</body>
</html>