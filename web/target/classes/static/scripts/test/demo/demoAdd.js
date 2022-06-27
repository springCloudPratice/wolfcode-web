layui.use(['form', 'layer'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery;

    form.on('submit(demoAdd-filter)', function (data) {
        $.ajax({
            url: web.rootPath() + "demo/save",
            type: "post",
            contentType: "application/json",
            data: JSON.stringify(data.field),
            dataType: 'json',
            traditional: true,
            success: function (data) {
                layer.msg("操作成功", {
                    icon: 1,
                    success: function () {
                        reloadTb("demoSave-frame", "#demoSearchBtn");
                    }
                });
            },
            error: function (e) {
                layer.msg(e.responseJSON.message, {icon: 2});
            }

        });
        return false;
    });

});