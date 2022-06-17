var use = layui.use(['upload'], function () {
    var $ = layui.jquery
        , upload = layui.upload;

    $("#templateHandle").on("click", function () {
        window.location.href = web.rootPath() + "user/template";
    });

    //拖拽上传
    upload.render({
        elem: '#importUser-upload-drag'
        , url: web.rootPath() + 'user/import'
        , accept: 'file'
        , auto: false
        , exts: 'xls|xlsx'
        , bindAction: '#uploadBtn'
        , done: function (res) {
            if (res.errCode == 0) {
                layer.msg("操作成功", {
                    icon: 1,
                    end: function () {
                        reloadTb("userImport-frame", "#userSearchBtn");
                    }
                });
            } else {
                parent.layer.close(parent.layer.getFrameIndex("userImport-frame"));
                //显示错误数据列表
                parent.layer.open({
                    type: 1,
                    resize: false,
                    area: ['550px', '430px'],
                    title: '错误信息',
                    fixed: false,
                    maxmin: true,
                    content: renderErrTb("error-table", res.data),
                    success: function (layero, index) {
                    }
                })
                ;
            }
        }
        , error: function (index, upload) {
            layer.msg("导入失败", {icon: 2});
        }
    });
});