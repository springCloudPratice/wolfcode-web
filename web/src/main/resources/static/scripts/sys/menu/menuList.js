layui.use(['form', 'layer', 'table', 'laytpl', 'laydate', 'treetable'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        laydate = layui.laydate,
        treetable = layui.treetable,
        table = layui.table;

    $(function () {
        renderTable();
    });

    // 渲染表格
    function renderTable() {
        layer.load(2);
        treetable.render({
            treeColIndex: 0,
            treeSpid: "",
            treeIdName: 'menuCode',
            treePidName: 'parentMenuCode',
            treeDefaultClose: true,
            treeLinkage: false,
            even: true,
            toolbar: '#menuList-toolbar',
            // defaultToolbar: ['filter','print'],
            elem: '#menuList',
            url: web.rootPath() + 'menu/menuList',
            cols: [[
                {field: 'menuName', title: '菜单名称', minWidth: 100},
                {
                    field: 'menuIcon', title: '菜单图标', width: 90, align: "center", templet: function (d) {
                        if (d.menuIcon) {
                            return "<i class='layui-icon " + d.menuIcon + "'>";
                        } else {
                            return "";
                        }
                    }
                },
                {
                    field: 'menuType', title: '菜单类型', width: 90, align: "center", templet: function (d) {
                        if (d.menuType == 1) {
                            return "<button class=\"layui-btn layui-btn-normal layui-btn-xs sz-btn-sm\" style='cursor: default'>目录</button>";
                        } else if (d.menuType == 2 || d.menuType == 3) {
                            return "<button class=\"layui-btn layui-btn-primary layui-btn-xs sz-btn-sm\" style='cursor: default'>菜单</button>";
                        } else if (d.menuType == 4) {
                            return "<button class=\"layui-btn layui-badge layui-bg-gray layui-btn-xs sz-btn-sm\" style='cursor: default'>按钮</button>";
                        } else {
                            return "";
                        }
                    }
                },
                {field: 'menuCode', title: '菜单标识', minWidth: 100},
                {field: 'menuUrl', title: '菜单URL', minWidth: 100},
                {field: 'authorization', title: '权限标识', minWidth: 100},
                {
                    field: 'disable', title: '状态', width: 100, align: "center", templet: function (d) {
                        if (d.disable == '0') {
                            return "<button class=\"layui-btn layui-btn-normal layui-btn-xs sz-btn-sm\" style='cursor: default'>正常</button>";
                        } else if (d.disable == '1') {
                            return "<button class=\"layui-btn layui-btn-danger layui-btn-xs sz-btn-sm\" style='cursor: default'>已删除</button>";
                        } else {
                            return "<button class=\"layui-btn layui-btn-disabled layui-btn-xs sz-btn-sm\" style='cursor: default'>未知</button>";
                        }
                    }
                },
                {title: '操作', width: 160, templet: "#menuList-editBar", align: "center"}
            ]],
            done: function () {
                layer.closeAll('loading');
            }
        });
    }


    //头工具栏事件
    table.on('toolbar(menuList-toolbar)', function (obj) {
        switch (obj.event) {
            case 'add':
                layer.open({
                    id: "menuSave-frame",
                    type: 2,
                    area: ['550px', '500px'],
                    offset: '100px',
                    move: false,
                    title: '新增',
                    resize: false,
                    fixed: false,
                    maxmin: true,
                    content: web.rootPath() + 'menu/add.html?_=' + new Date().getTime(),
                    success: function (layero, index) {
                        // layer.full(index)
                    }
                });
                break;
            case 'expand':
                treetable.expandAll('#menuList');
                break;
            case 'fold':
                treetable.foldAll('#menuList');
                break;
            case 'menuSearchBtn':
                renderTable();
                break;
        }
    });

    //监听工具条
    table.on('tool(menuList-toolbar)', function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case 'update':
                layer.open({
                    id: "MenuUpdate-frame",
                    type: 2,
                    resize: false,
                    area: ['550px', '500px'],
                    title: '修改菜单',
                    fixed: false,
                    maxmin: true,
                    content: web.rootPath() + "menu/" + data.menuId + ".html"
                });
                break;
            case 'delete':
                var index = layer.confirm('确定要删除?', {
                    btn: ['确定', '取消'] //按钮
                }, function () {
                    $.ajax({
                        url: web.rootPath() + "menu/delete/" + data.menuId,
                        type: "delete",
                        contentType: "application/json;charset=utf-8",
                        data: JSON.stringify(data.field),
                        dataType: 'json',
                        success: function (data) {
                            layer.msg("操作成功", {
                                icon: 1,
                                success: function () {
                                    renderTable()
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
                        url: web.rootPath() + "menu/recovery/" + data.menuId,
                        type: "get",
                        contentType: "application/json;charset=utf-8",
                        data: JSON.stringify(data.field),
                        dataType: 'json',
                        success: function (data) {
                            layer.msg("操作成功", {
                                icon: 1,
                                success: function () {
                                    renderTable()
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
    });
});
