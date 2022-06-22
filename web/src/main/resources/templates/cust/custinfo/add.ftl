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
                        <label class="layui-form-label">企业名称</label>
                    <div class="layui-input-block">
                        <input type="text"  name="customerName"  placeholder="请输入"  autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-col-lg6">
                        <label class="layui-form-label">法定代表人</label>
                    <div class="layui-input-block">
                        <input type="text"  name="legalLeader"  placeholder="请输入"  autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-col-lg6">
                        <label class="layui-form-label">成立时间</label>
                    <div class="layui-input-block">
                        <input type="text"  id="registerDate" name="registerDate"  placeholder="请输入"  autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-col-lg6">
                        <label class="layui-form-label">经营状态 0 开业、1 注销、2 破产。</label>
                    <div class="layui-input-block">
                        <option value="">--请选择--</option>
                        <select name="openStatus">
                            <option value="0">开业</option>
                            <option value="1">注销</option>
                            <option value="2">破产</option>
                        </select>
<#--                        <input type="text"  name="openStatus"  placeholder="请输入"  autocomplete="off" class="layui-input">-->
                    </div>
                </div>
                <div class="layui-col-lg6">
                        <label class="layui-form-label">所属地区省份</label>
                    <div class="layui-input-block">
                        <select name="province">
                            <option value="">--请选择--</option>
                            <#list citys  as city>
                                <option value=${city.key}>${city.value}</option>
                            </#list>
                        </select>
<#--                        <input type="text"  name="province"  placeholder="请输入"  autocomplete="off" class="layui-input">-->
                    </div>
                </div>
                <div class="layui-col-lg6">
                        <label class="layui-form-label">注册资本,(万元)</label>
                    <div class="layui-input-block">
                        <input type="text"  name="regCapital"  placeholder="请输入"  autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-col-lg6">
                        <label class="layui-form-label">所属行业</label>
                    <div class="layui-input-block">
                        <input type="text"  name="industry"  placeholder="请输入"  autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-col-lg6">
                        <label class="layui-form-label">经营范围</label>
                    <div class="layui-input-block">
                        <input type="text"  name="scope"  placeholder="请输入"  autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-col-lg6">
                        <label class="layui-form-label">注册地址</label>
                    <div class="layui-input-block">
                        <input type="text"  name="regAddr"  placeholder="请输入"  autocomplete="off" class="layui-input">
                    </div>
                </div>
<#--                <div class="layui-col-lg6">-->
<#--                        <label class="layui-form-label">录入时间</label>-->
<#--                    <div class="layui-input-block" >-->
<#--                        <input type="text"  name="inputTime"  placeholder="请输入"  autocomplete="off" class="layui-input">-->
<#--                    </div>-->
<#--                </div>-->
<#--                <div class="layui-col-lg6">-->
<#--                        <label class="layui-form-label">修改时间</label>-->
<#--                    <div class="layui-input-block">-->
<#--                        <input type="text"  name="updateTime"  placeholder="请输入"  autocomplete="off" class="layui-input">-->
<#--                    </div>-->
<#--                </div>-->
<#--                <div class="layui-col-lg6">-->
<#--                        <label class="layui-form-label">录入人</label>-->
<#--                    <div class="layui-input-block">-->
<#--                        <input type="text"  name="inputUserId"  placeholder="请输入"  autocomplete="off" class="layui-input">-->
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
<script type="text/javascript" src="${request.contextPath}/scripts/cust/custinfo/add.js?_=${randomNum}"></script>
</body>
