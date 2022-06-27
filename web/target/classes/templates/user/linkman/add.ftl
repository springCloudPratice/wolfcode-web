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

<#--                <div class="layui-col-lg6">-->
<#--                        <label class="layui-form-label">id</label>-->
<#--                    <div class="layui-input-block">-->
<#--                        <input type="text"  name="id"  placeholder="请输入"  autocomplete="off" class="layui-input">-->
<#--                    </div>-->
<#--                </div>-->
                <div class="layui-col-lg6">
                        <label class="layui-form-label">所属企业</label>
                    <div class="layui-input-block">
                        <select name="custId">
                            <option value="">--请选择--</option>
                            <#list custs  as cust>
                                <option value=${cust.id}>${cust.customerName}</option>
                            </#list>
                        </select>
<#--                        <input type="text"  name="custId"  placeholder="请输入"  autocomplete="off" class="layui-input">-->
                    </div>
                </div>
                <div class="layui-col-lg6">
                        <label class="layui-form-label">联系人名字</label>
                    <div class="layui-input-block">
                        <input type="text"  name="linkman"  placeholder="请输入"  autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-col-lg6">
                        <label class="layui-form-label">性别</label>
                    <div class="layui-input-block">
                        <option value="">--请选择--</option>
                        <select name="sex">
                            <option value="1">男</option>
                            <option value="0">女</option>
                        </select>
                    </div>
                </div>
                <div class="layui-col-lg6">
                        <label class="layui-form-label">年龄</label>
                    <div class="layui-input-block">
                        <input type="text"  name="age"  placeholder="请输入"  autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-col-lg6">
                        <label class="layui-form-label">联系人电话</label>
                    <div class="layui-input-block">
                        <input type="text"  name="phone"  placeholder="请输入"  autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-col-lg6">
                        <label class="layui-form-label">职位</label>
                    <div class="layui-input-block">
                        <input type="text"  name="position"  placeholder="请输入"  autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-col-lg6">
                        <label class="layui-form-label">部门</label>
                    <div class="layui-input-block">
                        <input type="text"  name="department"  placeholder="请输入"  autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-col-lg6">
                        <label class="layui-form-label">备注信息</label>
                    <div class="layui-input-block">
                        <input type="text"  name="remark"  placeholder="请输入"  autocomplete="off" class="layui-input">
                    </div>
                </div>
<#--                <div class="layui-col-lg6">-->
<#--                        <label class="layui-form-label">录入人</label>-->
<#--                    <div class="layui-input-block">-->
<#--                        <input type="text"  name="inputUser"  placeholder="请输入"  autocomplete="off" class="layui-input">-->
<#--                    </div>-->
<#--                </div>-->
<#--                <div class="layui-col-lg6">-->
<#--                        <label class="layui-form-label">录入时间</label>-->
<#--                    <div class="layui-input-block">-->
<#--                        <input type="text"  name="inputTime"  placeholder="请输入"  autocomplete="off" class="layui-input">-->
<#--                    </div>-->
<#--                </div>-->

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
<script src="${request.contextPath}/layui-extend.js"></script>
<script type="text/javascript" src="${request.contextPath}/scripts/user/linkman/add.js?_=${randomNum}"></script>
</body>
