layui.use(['form', 'layer', 'table', 'laytpl', 'formSelects', 'treetable'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        formSelects = layui.formSelects,
        treetable = layui.treetable,
        table = layui.table
    // 渲染表格
    var renderTable = function (dictName, diceCodes) {
        layer.load(2);
        treetable.render({
            treeColIndex: 0,
            treeSpid: "",
            treeIdName: 'diceCode',
            treePidName: 'parentDiceCode',
            treeDefaultClose: true,
            treeLinkage: false,
            toolbar: '#dictList-toolbar',
            elem: '#dictList',
            url: web.rootPath() + 'dataDict/dictList?dictName=' + dictName + '&diceCodes=' + diceCodes,
            cols: [[
                {field: 'diceName', title: '字典名称', minWidth: 100, align: "left"},
                {field: 'diceCode', title: '字典编码', minWidth: 100, align: "center"},
                {field: 'diceValue', title: '字典值', minWidth: 100, align: "center"},
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
                {field: 'description', title: '描述', minWidth: 100, align: "center"},
                {field: 'parentDiceCode', title: '上级菜单', minWidth: 100, align: "center"},
                {title: '操作', width: 160, templet: "#dictList-editBar", fixed: "right", align: "center"}
            ]],
            done: function () {
                layer.closeAll('loading');
            }
        });
    };

    renderTable("", "");

    var $ = layui.$, active = {
        reload: function () {
            var diceName = $("#searchForm").find("input[name='diceName']").val().trim();
            var diceCodes = formSelects.value("diceCodes-select2", "val");
            renderTable(diceName, diceCodes);
        }
    };

    $('#dictSearchBtn').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });


    //头工具栏事件
    table.on('toolbar(dictList-toolbar)', function (obj) {
        switch (obj.event) {
            case 'add':
                layer.open({
                    id: "dictSave-frame",
                    type: 2,
                    resize: false,
                    area: ['550px', '500px'],
                    title: '新增字典',
                    fixed: false,
                    maxmin: true,
                    content: web.rootPath() + 'dataDict/add.html?_' + new Date().getTime()
                });
                break;
            case 'import':
                layer.open({
                    id: "dictImport-frame",
                    type: 2,
                    resize: false,
                    area: ['400px', '350px'],
                    title: '导入字典',
                    fixed: false,
                    maxmin: false,
                    content: web.rootPath() + 'dataDict/import.html?_' + new Date().getTime()
                });
                break;
            case 'export':
                var eix;
                var diceName = $("#searchForm").find("input[name='diceName']").val().trim();
                var diceCodes = formSelects.value("diceCodes-select2", "val");
                var url = web.rootPath() + 'dataDict/export?dictName=' + diceName + '&diceCodes=' + diceCodes
                $.fileDownload(url, {
                    httpMethod: 'POST',
                    prepareCallback: function (url) {
                        eix = layer.load(2);
                    },
                    successCallback: function (url) {
                        layer.close(eix)
                    },
                    failCallback: function (html, url) {
                        layer.close(eix)
                        layer.msg("导出失败", {icon: 2});
                    }
                });
                break;
            case 'expand':
                treetable.expandAll('#dictList');
                break;
            case 'fold':
                treetable.foldAll('#dictList');
                break;
        }
        ;
    });

    //监听工具条
    table.on('tool(dictList-toolbar)', function (obj) {
        var data = obj.data;

        switch (obj.event) {
            case 'update':
                layer.open({
                    id: "dictUpdate-frame",
                    type: 2,
                    resize: false,
                    area: ['550px', '500px'],
                    title: '修改字典',
                    fixed: false,
                    maxmin: true,
                    content: web.rootPath() + "dataDict/" + data.diceId + ".html?_" + new Date().getTime()
                });
                break;
            case 'delete':
                var index = layer.confirm('确定要删除?一旦删除,会连同子节点删除!', {
                    btn: ['确定', '取消'] //按钮
                }, function () {
                    var idx = layer.load(2);
                    $.ajax({
                        url: web.rootPath() + "dataDict/" + "delete/" + data.diceId,
                        type: "delete",
                        contentType: "application/json;charset=utf-8",
                        data: JSON.stringify(data.field),
                        dataType: 'json',
                        success: function (data) {
                            layer.msg("操作成功", {
                                icon: 1,
                                success: function () {
                                    $('#dictSearchBtn').trigger("click");
                                }
                            });
                        },
                        error: function (e) {
                            layer.msg(e.responseJSON.message, {icon: 2});
                        },
                        complete: function () {
                            layer.close(idx);
                        }
                    })
                }, function () {
                });
                break;
            case 'recovery':
                var index = layer.confirm('确定要恢复?一旦恢复,会连同子节点恢复!', {
                    btn: ['确定', '取消'] //按钮
                }, function () {
                    var idx = layer.load(2);
                    $.ajax({
                        url: web.rootPath() + "dataDict/" + "recovery/" + data.diceId,
                        type: "put",
                        contentType: "application/json;charset=utf-8",
                        data: JSON.stringify(data.field),
                        dataType: 'json',
                        success: function (data) {
                            layer.msg("操作成功", {
                                icon: 1,
                                success: function () {
                                    $('#dictSearchBtn').trigger("click");
                                }
                            });
                        },
                        error: function (e) {
                            layer.msg(e.responseJSON.message, {icon: 2});
                        },
                        complete: function () {
                            layer.close(idx);
                        }
                    })
                }, function () {
                });
                break;
        }
        ;
    });
});