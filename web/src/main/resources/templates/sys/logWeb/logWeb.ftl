<#assign sec=JspTaglibs["http://http://www.ahsj.link/security/tags"]/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>通用后台管理模板系统</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, loginLog-scalable=0">
    <link rel="stylesheet" href="${request.contextPath}/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${request.contextPath}/layuiadmin/style/admin.css" media="all">
    <link rel="stylesheet" href="${request.contextPath}/layuiadmin/style/common.css" media="all">
    <script src="${request.contextPath}/webjars/jquery/jquery.min.js"></script>
    <script src="${request.contextPath}/webjars/sockjs-client/sockjs.min.js"></script>
    <script src="${request.contextPath}/webjars/stomp-websocket/stomp.min.js"></script>
</head>
<body>
    <div class="layui-fluid">
        <div class="layui-row layui-col-space15">
            <div class="layui-col-md12">
                <div class="layui-card">
                    <!-- 搜索条件start -->
                    <@sec.authenticate grants="loginLog:loginLogList">
                        <div class="layui-form layui-card-header layuiadmin-card-header-auto" id="searchForm">
                            <div class="layui-form-item">
                                <div class="layui-inline">
                                    <button class="layui-btn layui-btn-normal"   id="openSocket" data-type="reload">开启日志</button>
                                </div>
                                <div class="layui-inline">
                                    <button class="layui-btn layui-btn-normal"   id="closeSocket" data-type="reload">关闭日志</button>
                                </div>
                            </div>
                        </div>
                        <!-- 搜索条件end -->
                    </@sec.authenticate>

                    <!-- 数据表格start -->
                    <div class="layui-card-body">
                        <#--                    <button onclick="openSocket()">开启日志</button><button onclick="closeSocket()">关闭日志</button>-->
                        <div id="log-container" style="height: 700px; overflow-y: scroll; background: #333; color: #aaa; padding: 10px;">
                            <div></div>
                        </div>
                        <#--                    <table class="layui-hide" id="loginLogList" lay-filter="loginLogList-toolbar"></table>-->
                    </div>
                    <!-- 数据表格end -->
                </div>
            </div>
        </div>
    </div>
<body>


<script src="${request.contextPath}/layuiadmin/layui/layui.js"></script>
<script src="${request.contextPath}/layui-extend.js"></script>
<script type="text/javascript">
    layui.config({
        base: '${request.contextPath}/layuiadmin/'
    }).extend({
        formSelects: 'lib/formSelects-v4.min'
    });
</script>
<script type="text/javascript" src="${request.contextPath}/static/scripts/sys/logweb/logWebList.js?_=${randomNum}"></script>
</body>
</html>