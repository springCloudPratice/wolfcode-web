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
                        <label class="layui-form-label">所属客户</label>
                    <div class="layui-input-block">
<#--                        <input type="text"-->
<#--                               name="custId"-->
<#--                               value="${obj.custId}"-->
<#--                               autocomplete="off"-->
<#--                               class="layui-input">-->
                        <select name="custId">
                            <#list custs as cust>
                                <#if cust.id=obj.custId>
                                    <option selected value="${cust.id}">${cust.customerName}</option>
                                <#else >
                                    <option value="${cust.id}">${cust.customerName}</option>
                                </#if>
                            </#list>
                        </select>
                    </div>
                </div>
                <div class="layui-col-lg6">
                        <label class="layui-form-label">合同名称</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="contractName"
                               value="${obj.contractName}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-col-lg6">
                        <label class="layui-form-label">合同编码</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="contractCode"
                               value="${obj.contractCode}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-col-lg6">
                        <label class="layui-form-label">合同金额</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="amounts"
                               value="${obj.amounts}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">合同生效开始时间</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="startDate"
                               id="startDate"
                               value="${obj.startDate}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">合同生效结束时间</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="endDate"
                               id="endDate"
                               value="${obj.endDate}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>

                <div class="layui-col-lg6">
                        <label class="layui-form-label">合同内容</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="content"
                               value="${obj.content}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">是否盖章确认</label>
                    <div class="layui-input-block">
<#--                        <input type="text"-->
<#--                               name="affixSealStatus"-->
<#--                               value="${obj.affixSealStatus}"-->
<#--                               autocomplete="off"-->
<#--                               class="layui-input">-->
                        <select name="affixSealStatus">
                            <option <#if obj.affixSealStatus=0 >selected</#if> value="0">否</option>
                            <option <#if obj.affixSealStatus=1 >selected</#if> value="1">是</option>
                        </select>
                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">审核状态</label>
                    <div class="layui-input-block">
<#--                        <input type="text"-->
<#--                               name="auditStatus"-->
<#--                               value="${obj.auditStatus}"-->
<#--                               autocomplete="off"-->
<#--                               class="layui-input">-->
                        <select name="auditStatus">
                            <option <#if obj.auditStatus=0 >selected</#if> value="0">未审核</option>
                            <option <#if obj.auditStatus=1 >selected</#if> value="1">审核通过</option>
                            <option <#if obj.auditStatus=-1 >selected</#if> value="-1">审核不通过</option>
                        </select>
                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">是否作废</label>
                    <div class="layui-input-block">
<#--                        <input type="text"-->
<#--                               name="nullifyStatus"-->
<#--                               value="${obj.nullifyStatus}"-->
<#--                               autocomplete="off"-->
<#--                               class="layui-input">-->
                        <select name="nullifyStatus">
                            <option <#if obj.nullifyStatus=0 >selected</#if> value="0">作废</option>
                            <option <#if obj.nullifyStatus=1 >selected</#if> value="1">在用</option>
                        </select>
                    </div>
                </div>


<#--                <div class="layui-col-lg6">-->
<#--                        <label class="layui-form-label">录入人</label>-->
<#--                    <div class="layui-input-block">-->
<#--                        <input type="text"-->
<#--                               name="inputUser"-->
<#--                               value="${obj.inputUser}"-->
<#--                               autocomplete="off"-->
<#--                               class="layui-input">-->
<#--                    </div>-->
<#--                </div>-->


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
<script type="text/javascript" src="${request.contextPath}/scripts/contract/contractinfo/update.js?_=${randomNum}"></script>
</body>
