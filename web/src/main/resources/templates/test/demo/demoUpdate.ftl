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
            <input type="hidden" id="id" name="id" value="${id}">
            <div class="layui-row layui-col-space10 layui-form-item">
                <div class="layui-col-lg6">
                    <label class="layui-form-label">名称</label>
                    <div class="layui-input-block">
                        <input type="text" id="name" name="name" value="${obj.name}" lay-verify="name" autocomplete="off"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-col-lg6">
                    <label class="layui-form-label">描述</label>
                    <div class="layui-input-block">
                        <input type="text" id="info" name="info" lay-verify="info" value="${obj.info}"
                               autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-col-lg6">
                    <label class="layui-form-label">创建时间</label>
                    <div class="layui-input-block">
                        <input type="text" id="createTime" name="createTime" lay-verify="createTime"   value="${obj.createTime}"
                               autocomplete="off" class="layui-input">
                    </div>
                </div>

                <div class="layui-form-item">
                    <div class="layui-input-block">
                        <button class="layui-btn layui-btn-normal" lay-submit lay-filter="demoAdd-filter">修改</button>
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
<script>
    layui.config({
        base: '${request.contextPath}/layuiadmin/'
    }).extend({
        formSelects: 'lib/formSelects-v4.min',
    });
</script>
<script>

</script>
<script src="${request.contextPath}/layuiadmin/layui-xtree/layui-xtree.js"></script>
<script type="text/javascript" src="${request.contextPath}/scripts/test/demo/demoUpdate.js?_=${randomNum}"></script>
</body>