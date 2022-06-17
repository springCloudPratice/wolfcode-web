layui.use(['form', 'layer', 'table', 'laytpl', 'laydate'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        laydate = layui.laydate,
        table = layui.table;


    //用户列表
    var tableIns = table.render({
        elem: '#configList',
        url: web.rootPath() + 'config/configList',
        // cellMinWidth: 95,
        page: true,
        height: "full-" + Math.round(Number($('.layui-card-header').height()) + 44),
        limits: [10, 13, 15, 20, 30, 50, 100, 200],
        limit: 10,
        toolbar: '#configList-toolbar',
        id: "configListTable",
        cols: [[
            {type: "checkbox", fixed: "left", width: 50},
            {field: 'configKey', title: '配置标识', minWidth: 100, align: "center"},
            {field: 'parentKey', title: '所属配置', minWidth: 100, align: "center"},
            {field: 'configValue', title: '配置属性值', minWidth: 100, align: "center"},
            {field: 'info', title: '说明', minWidth: 100, align: "center"},
            {
                field: 'disable', title: '状态', width: 100, align: "center", templet: function (d) {
                    if (d.disable == '0') {
                        return "<button class=\"layui-btn layui-btn-normal layui-btn-xs\">正常</button>";
                    } else if (d.disable == '1') {
                        return "<button class=\"layui-btn layui-btn-danger layui-btn-xs\">已禁用</button>";
                    } else {
                        return "<button class=\"layui-btn layui-btn-disabled layui-btn-xs\">未知</button>";
                    }
                }
            },
            {title: '操作', width: 160, templet: '#configList-editBar', fixed: "right", align: "center"}
        ]],
    });

    var $ = layui.$, active = {
        reload: function () {
            //获取搜索条件值
            var info = $("#searchForm").find("input[name='info']").val().trim();
            //表格重载
            tableIns.reload({
                where: { //设定异步数据接口的额外参数，任意设
                    info: info
                }
            });
        }
    };

    $('#configSearchBtn').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

    //头工具栏事件
    table.on('toolbar(configList-toolbar)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'add':
                layer.open({
                    id: "configSave-frame",
                    type: 2,
                    area: ['600px', '501px'],
                    title: '新增配置',
                    fixed: false,
                    maxmin: true,
                    content: web.rootPath() + 'config/add.html'
                });
                break;
            case 'update':
                layer.msg("export");
                break;
            case 'delete':
                layer.msg("export");
                break;
            case 'export':
                layer.msg("export");
                break;
        }
        ;
    });

    //监听工具条
    table.on('tool(configList-toolbar)', function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case 'add':
                layer.open({
                    id: "configSave-frame",
                    type: 2,
                    area: ['600px', '501px'],
                    title: '新增配置',
                    fixed: false,
                    maxmin: true,
                    content: web.rootPath() + 'config/add.html'
                });
                break;
            case 'update':
                layer.open({
                    id: "configUpdate-frame",
                    type: 2,
                    resize: false,
                    area: ['550px', '500px'],
                    title: '修改角色',
                    fixed: false,
                    maxmin: true,
                    content: web.rootPath() + "config/" + data.configKey + ".html?_" + new Date().getTime()
                });
                break;
            case 'delete':
                var index = layer.confirm('确定要禁用?', {
                    btn: ['确定', '取消'] //按钮
                }, function () {
                    $.ajax({
                        url: web.rootPath() + "config/delete/" + data.configKey,
                        type: "delete",
                        contentType: "application/json;charset=utf-8",
                        data: JSON.stringify(data.field),
                        dataType: 'json',
                        success: function (data) {
                            layer.msg("操作成功", {
                                icon: 1,
                                success: function () {
                                    $('#configSearchBtn').trigger("click");
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
                }, function () {
                });
                break;
            case 'recovery':
                var index = layer.confirm('确定要恢复?', {
                    btn: ['确定', '取消'] //按钮
                }, function () {
                    $.ajax({
                        url: web.rootPath() + "config/recovery/" + data.configKey,
                        type: "delete",
                        contentType: "application/json;charset=utf-8",
                        data: JSON.stringify(data.field),
                        dataType: 'json',
                        success: function (data) {
                            layer.msg("操作成功", {
                                icon: 1,
                                success: function () {
                                    $('#configSearchBtn').trigger("click");
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
                }, function () {
                });
                break;
        };
    });

    $(window).resize(function () {
        $('div[lay-id="configListTable"]').height(document.body.offsetHeight - Math.round(Number($('.layui-card-header').height()) + 47));
    });
});
