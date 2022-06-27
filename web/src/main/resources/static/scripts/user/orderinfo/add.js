layui.use(['form', 'layer','laydate'], function () {
    var form = layui.form,
        layer = layui.layer,
        laydate = layui.laydate,
        $ = layui.jquery;
    //执行一个laydate实例
    laydate.render({
        elem: '#contractTime'//指定元素
        ,type: 'datetime'
    });
laydate.render({
    elem: '#contractTime1'//指定元素
    ,type: 'datetime'
});

    form.on('submit(Add-filter)', function (data) {
        $.ajax({
            url: web.rootPath() + "orderinfo/save",
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
                if (e.responseJSON.errCode == 1003) {
                    layer.msg(e.responseJSON.data.toString(), {icon: 2});
                } else {
                    layer.msg(e.responseJSON.message, {icon: 2});
                }
            }

        });
        return false;
    });
    form.on('select(customerSelect)',function (data) {
        $.ajax({
            url: web.rootPath() + "linkman/listByCustomerId?custId="+data.value,
            type: "post",
            contentType: "application/json",
            dataType: 'json',
            traditional: true,
            success: function (data) {
                $("#linkman").empty();
                var optionHtml= '<option value="">请选择</option>'
                if(data.data.length>0) {
                    data.data.forEach(
                        item => {
                        optionHtml += `<option value="${item.linkman}">${item.linkman}</option>`
                    }
                )
                }

                $("#linkman").html(optionHtml)
                form.render("select",'component-form-element')

            },
            error: function (e) {
                if(e.responseJSON.errCode === 1003){
                    layer.msg(e.responseJSON.data.toString(),{icon:2});
                }
                else{
                    layer.msg(e.responseJSON.message, {icon: 2});
                }
            }
            // error: function (e) {
            //     layer.msg(e.responseJSON.message, {icon: 2});
            // }

        });
        return false;
    });
});
