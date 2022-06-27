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
    <link rel="stylesheet" href="${request.contextPath}/MultipleTreeSelect/css/metroStyle/metroStyle.css" media="all">
    <link rel="stylesheet" href="${request.contextPath}/layuiadmin/style/common.css" media="all">
    <link rel="stylesheet" href="${request.contextPath}/layuiadmin/style/popup.css" media="all">
</head>
<body>

<div class="layui-card layui-content">
    <div class="layui-card-body">
        <form class="layui-form" action="" lay-filter="component-form-element">
            <div class="layui-row layui-col-space10 layui-form-item">
                <div class="layui-col-lg4">
                    <label class="layui-form-label">账号</label>
                    <div class="layui-input-block">
                        <input type="text" name="username" lay-verify="username" placeholder="请输入账号" autocomplete="off"
                               class="layui-input" maxlength="30">
                    </div>
                </div>
                <div class="layui-col-lg4">
                    <label class="layui-form-label">姓名</label>
                    <div class="layui-input-block">
                        <input type="text" name="nickName" lay-verify="nickName" placeholder="请输入姓名" autocomplete="off"
                               class="layui-input" maxlength="50">
                    </div>
                </div>
                <div class="layui-col-lg4">
                    <label class="layui-form-label">角色</label>
                    <div class="layui-input-block">
                        <select name="roleIds" lay-verify="required" xm-select="roles-select2" xm-select-show-count="2"
                                xm-select-search xm-select-skin="default" xm-select-height="36px">
                            <option value="">--请选择--</option>
                            <#list roles as role>
                                <option value="${role.roleId}">${role.roleName}</option>
                            </#list>
                        </select>
                    </div>
                </div>
                <div class="layui-col-lg4">
                    <label class="layui-form-label">机构</label>
                    <div class="layui-input-block">
                        <input class="layui-input" lay-verify="required" name="deptId" id="dept-multiSelectTree"
                                  style="overflow:hidden;resize: none;"
                                  textLabel="jasontext" placeholder="请选择机构" type="text" readonly>
                    </div>
                </div>
                <div class="layui-col-lg4">
                    <label class="layui-form-label">手机号码</label>
                    <div class="layui-input-block">
                        <input type="number" name="phone" lay-verify="required" placeholder="请输入手机号码" autocomplete="off"
                               class="layui-input" maxlength="11">
                    </div>
                </div>
                <div class="layui-col-lg4">
                    <label class="layui-form-label">邮箱</label>
                    <div class="layui-input-block">
                        <input type="text" name="email" lay-verify="email" placeholder="请输入邮箱" autocomplete="off"
                               class="layui-input" maxlength="50">
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button class="layui-btn layui-btn-normal" lay-submit lay-filter="userAdd-filter">新增</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
        </form>
    </div>
</div>

<script src="${request.contextPath}/layuiadmin/layui/layui.js"></script>
<script type="text/javascript" src="${request.contextPath}/webjars/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/MultipleTreeSelect/js/jquery.ztree.all.js"></script>
<script type="text/javascript" src="${request.contextPath}/MultipleTreeSelect/MultipleTreeSelect.js"></script>
<script src="${request.contextPath}/layui-extend.js"></script>
<script>
    layui.config({
        base: '${request.contextPath}/layuiadmin/'
    }).extend({
        formSelects: 'lib/formSelects-v4.min',
    });
</script>
<script type="text/javascript" src="${request.contextPath}/static/scripts/sys/user/userAdd.js?_=${randomNum}"></script>
</body>