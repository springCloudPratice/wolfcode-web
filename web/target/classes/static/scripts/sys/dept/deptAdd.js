var use = layui.use(['form', 'layer', 'treeSelect'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        treeSelect = layui.treeSelect;

    var setting = {
        el: '#dept-multiSelectTree',
        id: 'deptParentSelect',
        url: web.rootPath() + "dept/tree",
        enableAsync: true,
        enableSearch: true,
        nameKey: "name",
    }
    treeSelect.render(setting);

    form.verify({
        deptName: function (value) {
            if (value == null || value.trim() == '' || value.length <= 0) {
                return '机构名称不能为空';
            } else if (value.length > 50) {
                return '最多是50个字符';
            }
        }
    });

    form.on('submit(deptAdd-filter)', function (data) {
        $.ajax({
            url: web.rootPath() + "dept/save",
            type: "post",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(data.field),
            dataType: 'json',
            success: function (data) {
                layer.msg("操作成功", {
                    icon: 1,
                    success: function () {
                        reloadTb("Save-frame", "#SearchBtn");
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