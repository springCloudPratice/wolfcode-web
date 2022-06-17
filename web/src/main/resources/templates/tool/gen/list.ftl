<#assign sec=JspTaglibs["http://http://www.ahsj.link/security/tags"]/>
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
    <link rel="stylesheet" href="${request.contextPath}/MultipleTreeSelect/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <style>
        .ztree-select-body {
            position: absolute;
            left: 0;
            top: 42px;
            padding: 5px 0;
            z-index: 9999;
            min-width: 100%;
            border: 1px solid #d2d2d2;
            max-height: 300px;
            overflow-y: auto;
            background-color: #fff;
            border-radius: 2px;
            box-shadow: 0 2px 4px rgba(0,0,0,.12);
            box-sizing: border-box;
        }
        .title {
            padding: 10px 0px;
            text-align: center;
            font-size: 16px;
            font-weight: bold;
            color: #333;
        }

        .main-container {
            font-size: 0px;
        }

        .main-container .left-list {
            display: inline-block;
            width: 50%;
            font-size: 14px;
        }

        .main-container .left-list .layui-input-block {
            margin-left: 100px;
        }

        .main-container .left-list .layui-form-label {
            width: 100px;
        }

        .main-container .left-list .layui-inline .layui-form-label {
            width: 100px;
        }

        .main-container .right-img {
            display: inline-block;
            width: 50%;
            padding-left: 20px;
            vertical-align: top;
            box-sizing: border-box;
            font-size: 14px;
        }
    </style>
</head>
<body>

<div class="layui-card layui-content">
    <div class="layui-card-body">

        <form class="layui-form" action="" lay-filter="component-form-element">
            <div class="main-container">
                <div class="left-list">
                    <div class="layui-row layui-col-space10 layui-form-item">
                        <div class="title">------基本信息-----</div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">作者名称</label>
                            <div class="layui-input-block">
                                <input type="text" id="name" value="写代码没有出息的" name="author" lay-verify="name" placeholder="请输入"
                                       autocomplete="off"
                                       class="layui-input">
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <label class="layui-form-label">菜单名称</label>
                            <div class="layui-input-block">
                                <input type="text" id="name"  name="menuName" lay-verify="name" placeholder="请输入"
                                       autocomplete="off"
                                       class="layui-input">
                            </div>
                        </div>
                        <div class="layui-col-lg6">
                            <label class="layui-form-label text-label">上级菜单</label>
                            <div class="layui-input-block text-input">
                                <input type="text" id="menuParentCode" name="baseMenuId" lay-verify="required" placeholder="请选择上级菜单"
                                       autocomplete="off" class="layui-input layui-hide" readonly >
                            </div>
                        </div>


                        <div class="layui-form-item">
                            <label class="layui-form-label">数据库类型</label>
                            <div class="layui-input-block">
                                <input type="radio" name="dbType" value="mysql" title="mysql" checked="">
                                <input type="radio" name="dbType" value="oracle" title="oracle">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">代码类型</label>
                            <div class="layui-input-block">
                                <input type="radio" name="codeType" value="web" title="web" checked="">
                                <input type="radio" name="codeType" value="api" title="api">
                            </div>
                        </div>
                        <div class="title">------数据库信息-----</div>
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <label class="layui-form-label">用户名</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="username" value="root" lay-verify="required" placeholder="请输入"
                                           autocomplete="off"
                                           class="layui-input">
                                </div>
                            </div>
                            <div class="layui-inline">
                                <label class="layui-form-label">密码</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="password" value="123456" lay-verify="required" placeholder="请输入"
                                           autocomplete="off"
                                           class="layui-input">
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <label class="layui-form-label">数据库名称</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="database" value="nebula-web" lay-verify="required" placeholder="请输入"
                                           autocomplete="off"
                                           class="layui-input">
                                </div>
                            </div>
                            <div class="layui-inline">
                                <label class="layui-form-label">数据库端口</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="prot" value="3306" lay-verify="required" placeholder="请输入"
                                           autocomplete="off"
                                           class="layui-input">
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">数据库地址</label>
                            <div class="layui-input-block">
                                <input type="text" name="ip" value="127.0.0.1" lay-verify="required" placeholder="请输入"
                                       autocomplete="off"
                                       class="layui-input">
                            </div>
                        </div>
                        <div class="title">------包以及上下文信息-----</div>
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <label class="layui-form-label">包名</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="packageName" value="com.nebula.web.modules" lay-verify="info" placeholder="请输入info"
                                           autocomplete="off" class="layui-input">
                                </div>
                            </div>
                            <div class="layui-inline">
                                <label class="layui-form-label">模块名</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="moduleName" value="sys" lay-verify="info" placeholder="请输入info"
                                           autocomplete="off" class="layui-input">
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <label class="layui-form-label">页面上级模块</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="baseContext" value="test" lay-verify="info" placeholder="请输入info"
                                           autocomplete="off" class="layui-input">
                                </div>
                            </div>
                            <div class="layui-inline">
                                <label class="layui-form-label">上下文名称</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="context" value="demo" lay-verify="info" placeholder="请输入info"
                                           autocomplete="off" class="layui-input">
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">表名称(多个逗号隔开)</label>
                            <div class="layui-input-block">
                                <input type="text" name="tableNames" lay-verify="required" placeholder="请输入"
                                       autocomplete="off"
                                       class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">表ID(没有主键的自己填)</label>
                            <div class="layui-input-block">
                                <input type="text" name="tableId"  placeholder="请输入"
                                       autocomplete="off"
                                       class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">表切割前缀(SYS)那么SYS_USER表,实体就是USER</label>
                            <div class="layui-input-block">
                                <input type="text" name="tablePrefixs" value="SYS_,RP_,TB_" lay-verify="required" placeholder="请输入"
                                       autocomplete="off"
                                       class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">输出路径(你电脑磁盘)</label>
                            <div class="layui-input-block">
                                <input type="text" name="outputDir" value="/Users/shiyun/Desktop/code" lay-verify="info" placeholder="请输入info"
                                       autocomplete="off" class="layui-input">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="right-img">
                    <img width="100%" height="300px" style="margin-bottom: 15px" src="../static/images/packageInfo.png"/>
                    <img width="100%" height="300px" style="margin-bottom: 15px" src="../static/images/contextInfo.png"/>
                    <img width="100%" height="300px" src="../static/images/fileInfo.png"/>
                </div>
                <div class="layui-form-item">
                    <div class="layui-input-block" style="text-align: center;">
                        <button class="layui-btn layui-btn-normal" lay-submit lay-filter="Add-filter">新增</button>
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
<script type="text/javascript" src="${request.contextPath}/MultipleTreeSelect/js/jquery.ztree.all.js"></script>
<script type="text/javascript" src="${request.contextPath}/scripts/tool/gen/list.js?_=${randomNum}"></script>
</body>
</html>
