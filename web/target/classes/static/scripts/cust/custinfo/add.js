layui.use(['form', 'layer','laydate'], function () {
    var form = layui.form,
        layer = layui.layer,
        laydate=layui.laydate,
        $ = layui.jquery;
    laydate.render({
        elem: '#registerDate'
    })

    form.on('submit(Add-filter)', function (data) {
        $.ajax({
            url: web.rootPath() + "custinfo/save",
            type: "post",
            contentType: "application/json",
            data: JSON.stringify(data.field),
            dataType: 'json',
            traditional: true,
            success: function (data) {
                layer.msg("操作成功", {
                    icon: 1,
                    success: function () {
                        reloadTb("Save-frame", "#SearchBtn");
                    }
                });
            },
            error: function (e) {
                if(e.responseJSON.errCode === 1003){
                    layer.msg(e.responseJSON.data.toString(),{icon:2});
                }
                else{
                    layer.msg(e.responseJSON.message, {icon: 2});
                }
            }

        });
        return false;
    });

});
