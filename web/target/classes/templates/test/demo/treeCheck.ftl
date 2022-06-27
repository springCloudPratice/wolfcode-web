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
</head>
<body>

<div class="layui-card layui-content">
    <div class="layui-card-body">
        <form class="layui-form" action="" lay-filter="component-form-element">
            <div class="layui-row layui-col-space10 layui-form-item">
                <div class="layui-col-lg4">
                    <label class="layui-form-label text-label">树形下拉框演示</label>
                    <div class="layui-input-block text-input">
                        <input type="text" id="dept-multiSelectTree" name="treeSelectId" lay-verify="required" placeholder="请选择上级机构"
                               autocomplete="off" class="layui-input layui-hide"
                               tree-value="${checkId}"
                               readonly >
                    </div>
                </div>
            </div>


            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button class="layui-btn layui-btn-normal" lay-submit lay-filter="Add-filter">新增</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
        </form>
    </div>
</div>

<script src="${request.contextPath}/layuiadmin/layui/layui.js"></script>
<script type="text/javascript" src="${request.contextPath}/webjars/jquery/jquery.min.js"></script>
<script src="${request.contextPath}/layui-extend.js"></script>
<script>
    layui.config({
        base: '${request.contextPath}/layuiadmin/' //静态资源所在路径
    }).extend({
        treeSelect: 'lib/extend/treeSelect'
    });
</script>
<script type="text/javascript">
    var use = layui.use(['form', 'layer', 'treeSelect'], function () {
        var form = layui.form,
            layer = layui.layer,
            $ = layui.jquery,
            treeSelect = layui.treeSelect;

        var setting = {
            el: '#dept-multiSelectTree',
            id: 'parentSelect',
            url: web.rootPath() + "test/listTree",
            enableAsync: true,
            enableSearch: true,
            nameKey: "name",
        }

        treeSelect.render(setting);

        form.on('submit(Add-filter)', function (data) {
            $.ajax({
                url: web.rootPath() + "test/save",
                type: "post",
                contentType: "application/json;charset=utf-8",
                data: JSON.stringify(data.field),
                dataType: 'json',
                success: function (data) {
                    layer.msg("操作成功", {
                        icon: 1,
                        success: function () {
                            reloadTb("Save-frame", "#SearchBtn");
                        }
                    });
                },
                error: function (e) {
                    layer.msg(e.responseJSON.message, {icon: 2});
                }
            })
            return false;
        });
    });

</script>
</body>