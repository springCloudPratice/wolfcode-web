layui.use(['form', 'layer', 'table', 'laytpl', 'laydate'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        laydate = layui.laydate,
        table = layui.table;


    //用户列表
    var tableIns = table.render({
        elem: '#roleList',
        url: web.rootPath() + 'role/roleList',
        cellMinWidth: 95,
        page: true,
        height: "full-" + Math.round(Number($('.layui-card-header').height()) + 44),
        limits: [10, 13, 15, 20, 30, 50, 100, 200],
        limit: 10,
        toolbar: '#roleList-toolbar',
        id: "roleListTable",
        cols: [[
            {type: "checkbox", fixed: "left", width: 50},
            {field: 'roleName', title: '角色名', width: 180, align: "center"},
            {field: 'roleCode', title: '角色编码', width: 180, align: "center"},
            {field: 'description', title: '角色描述', width: 250, align: "center"},
            {field: 'createTime', title: '创建时间', width: 180, align: "center"},
            {field: 'numberLevel', title: '业务层级', minWidth: 30,width: 100, align: "center"},
            {field: 'businessLevel', title: '业务层级', minWidth: 180, align: "left"},
            {
                field: 'disable', title: '状态', width: 100, align: "center", templet: function (d) {
                    if (d.disable == '0') {
                        return "<button class=\"layui-btn layui-btn-normal layui-btn-xs\">正常</button>";
                    } else if (d.disable == '1') {
                        return "<button class=\"layui-btn layui-btn-danger layui-btn-xs\">已删除</button>";
                    } else {
                        return "<button class=\"layui-btn layui-btn-disabled layui-btn-xs\">未知</button>";
                    }
                }
            },
            {title: '操作', width: 160, templet: '#roleList-editBar', fixed: "right", align: "center"}
        ]],
    });

    //头工具栏事件
    table.on('toolbar(roleList-toolbar)', function (obj) {
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
            var roleName = $("#searchForm").find("input[name='roleName']").val().trim();
            //表格重载
            tableIns.reload({
                where: { //设定异步数据接口的额外参数，任意设
                    roleName: roleName
                }
            });
        }
    };

    $('#roleSearchBtn').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });


    //头工具栏事件
    table.on('toolbar(roleList-toolbar)', function (obj) {
        if (obj.event == 'add') {
            layer.open({
                id: "roleSave-frame",
                type: 2,
                area: ['600px', '501px'],
                title: '新增角色',
                fixed: false,
                maxmin: true,
                content: web.rootPath() + 'role/add.html'
            });
        }
        ;
    });
    //监听工具条
    table.on('tool(roleList-toolbar)', function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case 'update':
                layer.open({
                    id: "roleUpdate-frame",
                    type: 2,
                    resize: false,
                    area: ['550px', '500px'],
                    title: '修改角色',
                    fixed: false,
                    maxmin: true,
                    content: web.rootPath() + "role/" + data.roleId + ".html?_"
                });
                break;
            case 'delete':
                var index = layer.confirm('确定要删除?', {
                    btn: ['确定', '取消'] //按钮
                }, function () {
                    $.ajax({
                        url: web.rootPath() + "role/delete/" + data.roleId,
                        type: "delete",
                        contentType: "application/json;charset=utf-8",
                        data: JSON.stringify(data.field),
                        dataType: 'json',
                        success: function (data) {
                            layer.msg("操作成功", {
                                icon: 1,
                                success: function () {
                                    $('#roleSearchBtn').trigger("click");
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
            case 'recovery':
                var index = layer.confirm('确定要恢复?', {
                    btn: ['确定', '取消'] //按钮
                }, function () {
                    $.ajax({
                        url: web.rootPath() + "role/recovery/" + data.roleId,
                        type: "get",
                        contentType: "application/json;charset=utf-8",
                        data: JSON.stringify(data.field),
                        dataType: 'json',
                        success: function (data) {
                            layer.msg("操作成功", {
                                icon: 1,
                                success: function () {
                                    $('#roleSearchBtn').trigger("click");
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
        }
        ;
    });

    $(window).resize(function () {
        $('div[lay-id="roleListTable"]').height(document.body.offsetHeight - Math.round(Number($('.layui-card-header').height()) + 47));
    });
});
