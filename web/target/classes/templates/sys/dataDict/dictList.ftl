<#assign sec=JspTaglibs["http://http://www.ahsj.link/security/tags"]/>
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
    <link rel="stylesheet" href="${request.contextPath}/layuiadmin/style/common.css" media="all">
</head>
<body>

<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">

                <!-- 搜索条件start -->
                <@sec.authenticate grants="dataDict:dictList">
                <form class="layui-form layui-card-header layuiadmin-card-header-auto" id="searchForm">
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label">字典名称</label>
                            <div class="layui-input-block input-box">
                                <input type="text" name="diceName" placeholder="请输入" autocomplete="off"
                                       class="layui-input">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">字典编码</label>
                            <div class="layui-input-block input-box">
                                <select name="diceCodes" xm-select="diceCodes-select2" xm-select-show-count="2"
                                        xm-select-search xm-select-skin="default" xm-select-height="36px">
                                    <option value="">请选择</option>
                                    <#list dictList as dict>
                                        <option value="${dict.diceCode}">${dict.diceCode}</option>
                                    </#list>
                                </select>
                            </div>
                        </div>
                        <div class="layui-inline">
                            <button type="button" class="layui-btn layui-btn-normal" id="dictSearchBtn" data-type="reload">查询</button>
                            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                        </div>
                    </div>
                </form>
                <!-- 搜索条件end -->
                </@sec.authenticate>

                <!-- 数据表格start -->
                <div class="layui-card-body">
                    <table class="layui-table" id="dictList" lay-filter="dictList-toolbar"></table>

                    <table id="table1" class="layui-table" lay-filter="table1"></table>

                    <script type="text/html" id="dictList-toolbar">
                        <div class="layui-btn-container">
                            <@sec.authenticate grants="dataDict:save">
                            <button class="layui-btn layui-btn-sm layui-btn-primary" lay-event="add"><i
                                    class="layui-icon">&#xe654;</i>新增</button>
                            </@sec.authenticate>

                            <@sec.authenticate grants="dataDict:import">
                            <button class="layui-btn layui-btn-sm layui-btn-primary" lay-event="import"><i
                                    class="layui-icon">&#xe67c;</i>导入
                            </button>
                            </@sec.authenticate>

                            <@sec.authenticate grants="dataDict:export">
                            <button class="layui-btn layui-btn-sm layui-btn-primary" lay-tips="导出" lay-event="export">
                                <i class="layui-icon layui-icon-export"></i>导出
                            </button>
                            </@sec.authenticate>

                            <button class="layui-btn layui-btn-sm layui-btn-primary" lay-event="expand">
                                展开
                            </button>
                            <button class="layui-btn layui-btn-sm layui-btn-primary" lay-event="fold">
                                折叠
                            </button>
                        </div>
                    </script>

                    <script type="text/html" id="dictList-editBar">
                        {{#  if(d.disable == '0'){
                        }}
                        <@sec.authenticate grants="dataDict:update">
                        <a class="layui-btn layui-btn-xs layui-btn-primary" lay-event="update"><i class="layui-icon">&#xe642;</i>修改</a>
                        </@sec.authenticate>

                        <@sec.authenticate grants="dataDict:delete">
                        <a class="layui-btn layui-btn-xs layui-btn-primary" lay-event="delete"><i class="layui-icon">&#xe640;</i>删除</a>
                        </@sec.authenticate>
                        {{#  } else { }}
                        <a class="layui-btn layui-btn-xs layui-btn-primary" lay-event="recovery"><i class="layui-icon">&#xe640;</i>恢复</a>
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
<script src="${request.contextPath}/fileDownload/jquery.fileDownload.min.js"></script>
<script src="${request.contextPath}/layui-extend.js"></script>
<script type="text/javascript">
    layui.config({
        base: '${request.contextPath}/layuiadmin/'
    }).extend({
        formSelects: 'lib/formSelects-v4.min',
    });
    layui.config({
        base: '${request.contextPath}/layuiadmin/'
    }).extend({
        treetable: 'treetable-lay/treetable'
    }).use(['treetable'], function () {
        var treetable = layui.treetable;

    });
</script>
<script type="text/javascript" src="${request.contextPath}/static/scripts/sys/dataDict/dictList.js?_=${randomNum}"></script>
<script type="text/javascript" src="${request.contextPath}/static/scripts/newForm.js?_=${randomNum}"></script>
</body>
</html>