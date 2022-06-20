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
<script>
</script>
<div class="layui-card layui-content">
    <div class="layui-card-body">
        <form class="layui-form"  action="" lay-filter="component-form-element">
            <input type="hidden" id="id" name="id" value="${id}">
            <div class="layui-row layui-col-space10 layui-form-item">


<#--                <div class="layui-col-lg6">-->
<#--                        <label class="layui-form-label">id</label>-->
<#--                    <div class="layui-input-block">-->
<#--                        <input type="text"-->
<#--                               name="id"-->
<#--                               value="${obj.id}"-->
<#--                               autocomplete="off"-->
<#--                               class="layui-input">-->
<#--                    </div>-->
<#--                </div>-->


                <div class="layui-col-lg6">
                        <label class="layui-form-label">企业名称</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="customerName"
                               value="${obj.customerName}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">法定代表人</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="legalLeader"
                               value="${obj.legalLeader}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">成立时间</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="registerDate"
                               id="registerDate"
                               value="${obj.registerDate}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">经营状态</label>
                    <div class="layui-input-block">
                        <select name="openStatus">
                            <option <#if obj.openStatus=0 >selected</#if> value="0">开业</option>
                            <option <#if obj.openStatus=1 >selected</#if> value="1">注销</option>
                            <option <#if obj.openStatus=2 >selected</#if> value="2">破产</option>
                        </select>
<#--                        <input type="text"-->
<#--                               name="openStatus"-->
<#--                               value="${obj.openStatus}"-->
<#--                               autocomplete="off"-->
<#--                               class="layui-input">-->
                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">所属地区省份</label>
                    <div class="layui-input-block">
                        <select name="province">
                            <option value="">--请选择--</option>
                            <#list citys as city>
                                <#if city.key=obj.province>
                                    <option selected value="${city.key}">${city.value}</option>
                                <#else>
                                    <option value="${city.key}">${city.value}</option>
                                </#if>
                            </#list>
                        </select>
<#--                        <input type="text"-->
<#--                               name="province"-->
<#--                               value="${obj.province}"-->
<#--                               autocomplete="off"-->
<#--                               class="layui-input">-->
                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">注册资本,(万元)</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="regCapital"
                               value="${obj.regCapital}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">所属行业</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="industry"
                               value="${obj.industry}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">经营范围</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="scope"
                               value="${obj.scope}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">注册地址</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="regAddr"
                               value="${obj.regAddr}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>


<#--                <div class="layui-col-lg6">-->
<#--                        <label class="layui-form-label">录入时间</label>-->
<#--                    <div class="layui-input-block">-->
<#--                        <input type="text"-->
<#--                               name="inputTime"-->
<#--                               value="${obj.inputTime}"-->
<#--                               autocomplete="off"-->
<#--                               class="layui-input">-->
<#--                    </div>-->
<#--                </div>-->


<#--                <div class="layui-col-lg6">-->
<#--                        <label class="layui-form-label">修改时间</label>-->
<#--                    <div class="layui-input-block">-->
<#--                        <input type="text"-->
<#--                               name="updateTime"-->
<#--                               value="${obj.updateTime}"-->
<#--                               autocomplete="off"-->
<#--                               class="layui-input">-->
<#--                    </div>-->
<#--                </div>-->


<#--                <div class="layui-col-lg6">-->
<#--                        <label class="layui-form-label">录入人</label>-->
<#--                    <div class="layui-input-block">-->
<#--                        <input type="text"-->
<#--                               name="inputUserId"-->
<#--                               value="${obj.inputUserId}"-->
<#--                               autocomplete="off"-->
<#--                               class="layui-input">-->
<#--                    </div>-->
<#--                </div>-->


                <div class="layui-form-item">
                    <div class="layui-input-block">
                        <button class="layui-btn layui-btn-normal" lay-submit lay-filter="Add-filter">修改</button>
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

</script>
<script type="text/javascript" src="${request.contextPath}/scripts/cust/custinfo/update.js?_=${randomNum}"></script>
</body>
