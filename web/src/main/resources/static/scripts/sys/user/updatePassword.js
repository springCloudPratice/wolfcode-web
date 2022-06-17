var use = layui.use(['form', 'layer','jquery'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery;

    form.verify({
        newPassword: function (value) {
            if (!/^([a-z0-9\.\@\!\#\$\%\^\&\*\(\)]){6,20}$/i.exec(value)) {
                return '只能输入6-20个字母、数字、下划线';
            }
        }
        , comfirmPassword: function (value) {
            if (value != $("#LAY_password").val().trim()) {
                return '两次输入的密码不一致';
            }
        }
    });

    form.on('submit(setmypass)', function (data) {
        var index = layer.load(2);
        var jsEncrypt = new JSEncrypt();

        jsEncrypt.setPublicKey($("#publicKey").val());
        data.field.oldPassword = jsEncrypt.encrypt(data.field.oldPassword);
        data.field.newPassword = jsEncrypt.encrypt(data.field.newPassword);

        $.ajax({
            url: web.rootPath() + "user/updatePassword",
            type: "put",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(data.field),
            dataType: 'json',
            success: function (data) {
                top.location.href = web.rootPath();
            },
            error: function (e) {
                if (e.responseJSON.errCode == 1003) {
                    layer.msg(JSON.stringify(e.responseJSON.data), {icon: 2});
                } else {
                    layer.msg(e.responseJSON.message, {icon: 2});
                }

            },
            complete: function () {
                layer.close(index);
            }
        });
        return false;
    });
});