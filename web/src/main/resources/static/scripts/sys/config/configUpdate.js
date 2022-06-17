layui.use(['form', 'layer'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery;

    form.verify({
        configKey: function (value) {
            if (value == null || value.trim() == '' || value.length <= 0) {
                return '必填项';
            } else if (value.length > 50) {
                return '不能超过50个字符';
            }
        },
        parentKey: function (value) {
            if (value == null || value.trim() == '' || value.length <= 0) {
                return '必填项';
            } else if (value.length > 50) {
                return '不能超过50个字符';
            }
        }
        , info: function (value) {
            if (value == null || value.trim() == '' || value.length <= 0) {
                return '必填项';
            } else if (value.length > 100) {
                return '不能超过100个字符';
            }
        }
        , configValue: function (value) {
            if (value == null || value.trim() == '' || value.length <= 0) {
                return '必填项';
            } else if (value.length > 255) {
                return '不能超过255个字符';
            }
        }
    });
    form.on('submit(configUpdate-filter)', function (data) {
        var index = layer.load(2);
        $.ajax({
            url: web.rootPath() + "config/update",
            type: "put",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(data.field),
            dataType: 'json',
            success: function (data) {
                layer.msg("操作成功", {
                    icon: 1,
                    success: function () {
                        reloadTb("configUpdate-frame", "#configSearchBtn");
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