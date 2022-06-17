var use = layui.use(['form', 'layer', 'formSelects'], function () {
    var form = layui.form,
        layer = layui.layer,
        formSelects = layui.formSelects,
        $ = layui.jquery;

    var settingAsync = {
        async: {
            enable: true,
            url: web.rootPath() + "dept/tree",
            type: "GET"
        },
        chkStyle: "radio",
        radioType: "all",
        height: 300,
        callback: {
            onCheck: function () {
            }
        }
    };

    $(function () {
        $("#dept-multiSelectTree").drawMultipleTree(settingAsync);
    });

    form.verify({
        username: function (value) {
            if (value == null || value.trim() == '' || value.length <= 0) {
                return '账号不能为空';
            } else if (value.length > 30) {
                return '账号最多是30个字符';
            }
        }
        , nickName: function (value) {
            if (value == null || value.trim() == '' || value.length <= 0) {
                return '姓名不能为空';
            } else if (value.length > 50) {
                return '姓名最多是50个字符';
            }
        }
    });

    form.on('submit(userAdd-filter)', function (data) {
        data.field.roleIds = formSelects.value("roles-select2", "val");
        data.field.deptId = $("#dept-multiSelectTree").drawMultipleTree("getChecks", "val");
        var index = layer.load(2);
        $.ajax({
            url: web.rootPath() + "user/save",
            type: "post",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(data.field),
            dataType: 'json',
            success: function (data) {
                layer.msg("操作成功", {
                    icon: 1,
                    success: function () {
                        reloadTb("userSave-frame", "#userSearchBtn");
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