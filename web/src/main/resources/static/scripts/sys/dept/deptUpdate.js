layui.use(['form', 'layer', 'treeSelect'], function () {
    var form = layui.form,
        layer = layui.layer,
        treeSelect = layui.treeSelect,
        $ = layui.jquery;


    var setting = {
        el: '#dept-multiSelectTree',
        id: 'deptParentSelect',
        url: web.rootPath() + "dept/tree",
        enableAsync: true,
        enableSearch: true,
        search: "remote",
        nameKey: "name",
    };
    treeSelect.render(setting);

    form.on('submit(deptUpdate-filter)', function (data) {
        var index = layer.load(2);
        $.ajax({
            url: web.rootPath() + "dept/update",
            type: "put",
            contentType: "application/json;charset=utf-8",
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
            },
            complete: function () {
                layer.close(index);
            }
        })
        return false;
    });
});