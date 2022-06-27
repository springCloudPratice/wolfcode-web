layui.use(['form', 'layer','laydate'], function () {
    var form = layui.form,
        layer = layui.layer,
        laydate = layui.laydate,
        $ = layui.jquery;
    laydate.render({
        elem: '#startDate' //指定元素
    });
    laydate.render({
        elem: '#endDate' //指定元素
    });


    form.on('submit(Add-filter)', function (data) {
        $.ajax({
            url: web.rootPath() + "contractinfo/update",
            contentType: "application/json",
            type: "put",
            data: JSON.stringify(data.field),
            dataType: 'json',
            success: function (data) {
                layer.msg("操作成功", {
                    icon: 1,
                    success: function () {
                        reloadTb("Update-frame", "#SearchBtn");
                    }
                });
            },
            error: function (e) {
                layer.msg(e.responseJSON.message, {icon: 2});
            }

        })
        return false;
    });

});
