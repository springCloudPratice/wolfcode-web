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
                    <label class="layui-form-label">字典名称</label>
                    <div class="layui-input-block">
                        <input type="text" name="diceName" lay-verify="diceName" placeholder="请输入字典名称"
                               autocomplete="off" class="layui-input" maxlength="50">
                    </div>
                </div>
                <div class="layui-col-lg4">
                    <label class="layui-form-label">字典编码</label>
                    <div class="layui-input-block">
                        <input type="text" name="diceCode" lay-verify="diceCode" placeholder="请输入字典编码"
                               autocomplete="off" class="layui-input" maxlength="50">
                    </div>
                </div>
                <div class="layui-col-lg4">
                    <label class="layui-form-label">字典值</label>
                    <div class="layui-input-block">
                        <input type="text" name="diceValue" lay-verify="diceValue" placeholder="请输入字典值"
                               autocomplete="off" class="layui-input" maxlength="255">
                    </div>
                </div>
                <div class="layui-col-lg4">
                    <label class="layui-form-label">所属字典</label>
                    <div class="layui-input-block">
                        <select name="parentDiceCode">
                            <option value="">--请选择--</option>
                            <#list dictList as dict>
                                <option value="${dict.diceCode}">${dict.diceName}</option>
                            </#list>
                        </select>
                    </div>
                </div>
                <div class="layui-col-lg4">
                    <label class="layui-form-label">排序</label>
                    <div class="layui-input-block">
                        <input type="number" name="sort" lay-verify="number" placeholder="请输入排序" autocomplete="off"
                               class="layui-input">
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">描述</label>
                <div class="layui-input-block">
                    <textarea name="description" placeholder="请输入描述" class="layui-textarea" style="resize: none"
                              maxlength="255"></textarea>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button class="layui-btn layui-btn-normal" lay-submit lay-filter="dictAdd-filter">新增</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
        </form>
    </div>
</div>

<script src="${request.contextPath}/layuiadmin/layui/layui.js"></script>
<script src="${request.contextPath}/layui-extend.js"></script>
<script>
    layui.config({
        base: '${request.contextPath}/layuiadmin/' //静态资源所在路径
    }).extend({
        index: 'lib/index' //主入口模块
    }).use('index');
</script>
<script type="text/javascript" src="${request.contextPath}/static/scripts/sys/dataDict/dictAdd.js?_=${randomNum}"></script>
</body>