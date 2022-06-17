layui.use(['form', 'layer'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery;

    $("#LAY-captcha").on("click", function () {
        var src = $(this).attr("src");
        $(this).attr("src", src + "?_=" + new Date().getTime());
    });

    form.on('submit(LAY-user-login-submit)', function (data) {
        var index = layer.load(2);
        var jsEncrypt = new JSEncrypt();

        jsEncrypt.setPublicKey($("#publicKey").val());
        data.field.username = jsEncrypt.encrypt(data.field.username);
        data.field.password = jsEncrypt.encrypt(data.field.password);
        $.ajax({
            url: web.rootPath() + "sign/login",
            type: "post",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(data.field),
            dataType: 'json',
            success: function (data) {
                layer.msg("操作成功", {
                    icon: 1,
                    success: function () {
                        window.location.href = web.rootPath();
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