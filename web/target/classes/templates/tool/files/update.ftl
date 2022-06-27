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


                <div class="layui-col-lg6">
                        <label class="layui-form-label">id</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="id"
                               value="${obj.id}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">创建时间</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="createTime"
                               value="${obj.createTime}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">状态. 1代表正常. 0代表已删除</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="disable"
                               value="${obj.disable}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">更新时间</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="updateTime"
                               value="${obj.updateTime}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">文件key</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="fileKey"
                               value="${obj.fileKey}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">高度</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="height"
                               value="${obj.height}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">文件头类型</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="mediaType"
                               value="${obj.mediaType}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">名称</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="name"
                               value="${obj.name}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">路径</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="path"
                               value="${obj.path}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">大小</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="fileSize"
                               value="${obj.fileSize}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">后缀</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="suffix"
                               value="${obj.suffix}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">压缩路径</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="thumbPath"
                               value="${obj.thumbPath}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">类型</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="type"
                               value="${obj.fileType}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">宽度</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="width"
                               value="${obj.width}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>


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
<script type="text/javascript" src="${request.contextPath}/scripts/tool/files/update.js?_=${randomNum}"></script>
</body>
