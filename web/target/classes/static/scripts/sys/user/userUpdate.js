layui.use(['form', 'layer', 'jquery', 'formSelects'], function () {
    var form = layui.form,
        layer = layui.layer,
        formSelects = layui.formSelects,
        $ = layui.jquery;

    formSelects.render('roles-select2');
    /**/
    layer.load(1, {shade: [0.3, '#EEEEEE'], time: 0.5 * 1000});
    var settingAsync = {
        zNodes: JSON.parse($("#orgDiv").text()),
        chkStyle: "radio",
        radioType: "all",
        height: 300
    };

    $(function () {
        var tree = $("#depts").drawMultipleTree(settingAsync);
    });
    /**/
    form.on('submit(userUpdate-filter)', function (data) {
        data.field.roleIds = formSelects.value("roles-select2", "val");
        var index = layer.load(2);
        $.ajax({
            url: web.rootPath() + "user/update",
            type: "put",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(data.field),
            dataType: 'json',
            success: function (data) {
                layer.msg("操作成功", {
                    icon: 1,
                    success: function () {
                        reloadTb("userUpdate-frame", "#userSearchBtn");
                    }
                });
            },
            error: function (e) {
                layer.msg(e.responseJSON.message, {icon: 2});
            },
            complete: function () {
                layer.close(index);
            }
        });
        return false;
    });
});