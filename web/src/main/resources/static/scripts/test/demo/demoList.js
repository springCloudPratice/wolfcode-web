layui.use(['form', 'layer', 'table', 'laytpl', 'laydate'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        laydate = layui.laydate,
        table = layui.table;


    //用户列表
    var tableIns = table.render({
        elem: '#demoList',
        url: web.rootPath() + 'demo/demoList',
        cellMinWidth: 95,
        page: true,
        height: "full-" + Math.round(Number($('.layui-card-header').height()) + 44),
        limits: [10, 13, 15, 20, 30, 50, 100, 200],
        limit: 10,
        toolbar: '#demoList-toolbar',
        id: "demoListTable",
        cols: [[
            {type: "checkbox", fixed: "left", width: 50},
            {field: 'name', title: '名称', minWidth: 100, align: "center"},
            {field: 'age', title: '年龄', minWidth: 100, align: "center"},
            {field: 'info', title: '描述', minWidth: 100, align: "center"},
            {field: 'createTime', title: '创建时间', minWidth: 100, align: "center"},
            {title: '操作', width: 160, templet: '#demoList-editBar', fixed: "right", align: "center"}
        ]],
    });

    //头工具栏事件
    table.on('toolbar(demoList-toolbar)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'add':
                layer.msg("add");
                break;
            case 'update':
                layer.msg("update");
                break;
            case 'delete':
                layer.msg("delete");
                break;
            case 'export':
                layer.msg("export");
                break;
        }
        ;
    });

    var $ = layui.$, active = {
        reload: function () {
            //获取搜索条件值
            var parameterName = $("#searchForm").find("input[name='parameterName']").val().trim();
            //表格重载
            tableIns.reload({
                where: { //设定异步数据接口的额外参数，任意设
                    parameterName: parameterName
                }
            });
        }
    };

    $('#demoSearchBtn').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });


    //头工具栏事件
    table.on('toolbar(demoList-toolbar)', function (obj) {
        if (obj.event == 'add') {
            layer.open({
                id: "demoSave-frame",
                type: 2,
                area: ['600px', '501px'],
                title: '新增角色',
                fixed: false,
                maxmin: true,
                content: web.rootPath() + 'demo/add.html'
            });
        }
        ;
    });
    //监听工具条
    table.on('tool(demoList-toolbar)', function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case 'update':
                layer.open({
                    id: "demoUpdate-frame",
                    type: 2,
                    resize: false,
                    area: ['550px', '500px'],
                    title: '修改角色',
                    fixed: false,
                    maxmin: true,
                    content: web.rootPath() + "demo/" + data.id + ".html?_"
                });
                break;
            case 'delete':
                var index = layer.confirm('确定要删除?', {
                    btn: ['确定', '取消'] //按钮
                }, function () {
                    $.ajax({
                        url: web.rootPath() + "demo/delete/" + data.id,
                        type: "delete",
                        contentType: "application/json;charset=utf-8",
                        data: JSON.stringify(data.field),
                        dataType: 'json',
                        success: function (data) {
                            layer.msg("操作成功", {
                                icon: 1,
                                success: function () {
                                    $('#demoSearchBtn').trigger("click");
                                }
                            });
                        },
                        error: function (e) {
                            layer.msg(e.responseJSON.message, {icon: 2});
                        }
                    })
                }, function () {
                });
                break;
        };
    });

    $(window).resize(function () {
        $('div[lay-id="demoListTable"]').height(document.body.offsetHeight - Math.round(Number($('.layui-card-header').height()) + 47));
    });
});
