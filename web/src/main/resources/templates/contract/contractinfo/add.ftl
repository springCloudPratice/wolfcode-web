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
                        <label class="layui-form-label">所属客户</label>
                    <div class="layui-input-block">
<#--                        <input type="text"  name="custId"  placeholder="请输入"  autocomplete="off" class="layui-input">-->
                        <select name="custId" lay-filter="">
                            <option value="">--请选择--</option>
                            <#list custs as cust>
                                <option value="${cust.id}">${cust.customerName}</option>
                            </#list>
                        </select>
                    </div>
                </div>
                <div class="layui-col-lg6">
                        <label class="layui-form-label">合同名称</label>
                    <div class="layui-input-block">
                        <input type="text"  name="contractName"  placeholder="请输入"  autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-col-lg6">
                        <label class="layui-form-label">合同编码</label>
                    <div class="layui-input-block">
                        <input type="text"  name="contractCode"  placeholder="请输入"  autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-col-lg6">
                        <label class="layui-form-label">合同金额</label>
                    <div class="layui-input-block">
                        <input type="number"  name="amounts"  placeholder="请输入"  autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-col-lg6">
                        <label class="layui-form-label">合同生效开始时间</label>
                    <div class="layui-input-block">
                        <input type="text" id="startDate"  name="startDate"  placeholder="请输入"  autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-col-lg6">
                        <label class="layui-form-label">合同生效结束时间</label>
                    <div class="layui-input-block">
                        <input type="text" id="endDate" name="endDate"  placeholder="请输入"  autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-col-lg6">
                        <label class="layui-form-label">合同内容</label>
                    <div class="layui-input-block">
                        <input type="text"  name="content"  placeholder="请输入"  autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-col-lg6">
                        <label class="layui-form-label">是否盖章确认</label>
                    <div class="layui-input-block">
<#--                        <input type="text"  name="affixSealStatus"  placeholder="请输入"  autocomplete="off" class="layui-input">-->
                        <select name="affixSealStatus">
                            <option value="0">否</option>
                            <option value="1">是</option>
                        </select>
                    </div>
                </div>
                <div class="layui-col-lg6">
                        <label class="layui-form-label">审核状态</label>
                    <div class="layui-input-block">
<#--                        <input type="text"  name="auditStatus"  placeholder="请输入"  autocomplete="off" class="layui-input">-->
                        <select name="auditStatus">
                            <option value="0">未审核</option>
                            <option value="1">审核通过</option>
                            <option value="-1">审核不通过</option>
                        </select>
                    </div>
                </div>
                <div class="layui-col-lg6">
                        <label class="layui-form-label">是否作废</label>
                    <div class="layui-input-block">
<#--                        <input type="text"  name="nullifyStatus"  placeholder="请输入"  autocomplete="off" class="layui-input">-->
                        <select name="nullifyStatus">
                            <option value="0">在用</option>
                            <option value="1">作废</option>
                        </select>
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
<#--                <div class="layui-col-lg6">-->
<#--                        <label class="layui-form-label">修改时间</label>-->
<#--                    <div class="layui-input-block">-->
<#--                        <input type="text"  name="updateTime"  placeholder="请输入"  autocomplete="off" class="layui-input">-->
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
<script type="text/javascript" src="${request.contextPath}/scripts/contract/contractinfo/add.js?_=${randomNum}"></script>
</body>
