layui.use(['form', 'layer'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery;

    form.verify({
        diceName: function (value) {
            if (value == null || value.trim() == '' || value.length <= 0) {
                return '字典名称不能为空';
            } else if (value.length > 50) {
                return '字典名称最多是50个字符';
            }
        }
        , diceCode: function (value) {
            if (value == null || value.trim() == '' || value.length <= 0) {
                return '字典编码不能为空';
            } else if (value.length > 50) {
                return '字典编码最多是50个字符';
            }
        }
        , diceValue: function (value) {
            if (value == null || value.trim() == '' || value.length <= 0) {
                return '字典值不能为空';
            } else if (value.length > 255) {
                return '字典值最多是255个字符';
            }
        }
    });

    form.on('submit(dictAdd-filter)', function (data) {
        var index = layer.load(2);
        $.ajax({
            url: web.rootPath() + "dataDict/save",
            type: "post",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(data.field),
            dataType: 'json',
            success: function (data) {
                layer.msg("操作成功", {
                    icon: 1,
                    success: function () {
                        reloadTb("dictSave-frame", "#dictSearchBtn");
                    }
                });
            },
            error: function (e) {
                layer.msg(e.responseJSON.message, {icon: 2});
            },
            complete: function () {
                layer.close(index);
            }

        })
        return false;
    });
});