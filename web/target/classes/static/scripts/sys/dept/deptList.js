layui.use(['form', 'layer', 'table', 'laytpl', 'laydate', 'formSelects'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        laydate = layui.laydate,
        treetable = layui.treetable,
        formSelects = layui.formSelects,
        table = layui.table;

    var onClickData;

    //查询表单
    var settingAsync = {
        async: {
            enable: true,
            url: web.rootPath() + "dept/tree",
            type: "GET"
        },
        chkStyle: "checkbox",
        radioType: "all",
        height: 300,
        callback: {
            onCheck: chooseNode
        }
    };

    function chooseNode(event, treeId, treeNode) {
        if (onClickData !== treeNode.id) {
            //表格重载
            tableIns.reload({
                where: {deptId: treeNode.id}
            });
        }
        onClickData = treeNode.id
        return false;
    }

    $("#dept-multiSelectTree").drawMultipleTree(settingAsync);

    var setting = {
        async: {
            enable: true,
            url: web.rootPath() + "dept/tree",
            type: "GET"
        },
        view: {
            showIcon: true,
            dblClickExpand: false,
            showLine: true,
            selectedMulti: true
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "pId",
                rootPId: 1
            }
        },
        callback: {
            onClick: chooseNode
        }
    };

    var organizationTree = $.fn.zTree.init($('#organization-tree'), setting);

    $('.retract').on('click', function() {
        organizationTree.expandAll(false);
    });

    $('.refresh').on('click', function () {
        organizationTree = $.fn.zTree.init($('#organization-tree'), setting);
    });

    $('.show-hide').on('click', function () {
        if ($(this).find('.show').css('display') === 'none') {
            $(this).find('.show').show();
            $(this).find('.hide').hide();
            $('.left-page').animate({width:'0px'});
            $('.right-page').animate({marginLeft: '17px'});
            $('.left-page .header-page').hide();
        } else {
            $(this).find('.show').hide();
            $(this).find('.hide').show();
            $('.left-page').animate({width:'250px'});
            $('.right-page').animate({marginLeft: '270px'});
            $('.left-page .header-page').show();
        }
        setTimeout(function() {
            tableIns.reload({
                height: "full-" + Math.round(Number($('.layui-card-header').height()) + 44)
            });
        }, 500);

    });

    var tableIns = table.render({
        elem: '#deptList',
        url: web.rootPath() + 'dept/deptList',
        cellMinWidth: 95,
        page: true,
        height: "full-" + Math.round(Number($('.layui-card-header').height()) + 44),
        limits: [10, 13, 15, 20, 30, 50, 100, 200],
        limit: 10,
        toolbar: '#deptList-toolbar',
        id: "ListTable",
        cols: [[
            {type: "checkbox", fixed: "left", width: 50},
            {field: 'deptName', title: '机构名称', width: 200, align: "left"},
            {field: 'deptId', title: '机构id标识', width: 180, align: "center"},
            {field: 'parentDeptId', title: '上级机构id', width: 180, align: "center"},
            //电信机构才有的玩意
            {field: 'deptPath', title: '机构URL', minWidth: 150, align: "center"},
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
            {title: '操作', width: 160, templet: '#deptList-editBar', fixed: "right", align: "center"}
        ]],
    });

    var $ = layui.$, active = {
        reload: function () {
            //表格重载
            tableIns.reload({
                where: buildCondition()
            });
        }
    };

    $('#SearchBtn').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

    //头工具栏事件
    table.on('toolbar(deptList-toolbar)', function (obj) {
        if (obj.event == 'add') {
            layer.open({
                id: "Save-frame",
                type: 2,
                area: ['600px', '501px'],
                title: '新增',
                fixed: false,
                maxmin: true,
                content: web.rootPath() + 'dept/add.html'
            });
        };
    });

    //监听工具条
    table.on('tool(deptList-toolbar)', function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case 'update':
                layer.open({
                    id: "Update-frame",
                    type: 2,
                    resize: false,
                    area: ['550px', '430px'],
                    title: '修改机构',
                    fixed: false,
                    maxmin: true,
                    content: web.rootPath() + "dept/"+data.deptId + ".html?_"
                });
                break;
            case 'delete':
                var index = layer.confirm('确定要删除?', {
                    btn: ['确定', '取消'] //按钮
                }, function () {
                    $.ajax({
                        url: web.rootPath() + "dept/delete/" + data.deptId,
                        type: "delete",
                        contentType: "application/json;charset=utf-8",
                        data: JSON.stringify(data.field),
                        dataType: 'json',
                        success: function (data) {
                            layer.msg("操作成功", {
                                icon: 1,
                                success: function () {
                                    $('#SearchBtn').trigger("click");
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
                        url: web.rootPath() + "dept/recovery/" + data.deptId,
                        type: "get",
                        contentType: "application/json;charset=utf-8",
                        data: JSON.stringify(data.field),
                        dataType: 'json',
                        success: function (data) {
                            layer.msg("操作成功", {
                                icon: 1,
                                success: function () {
                                    $('#SearchBtn').trigger("click");
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
});

//构建搜索条件
function buildCondition() {
    var condition = new Object();
    layui.use(['jquery', 'formSelects'], function () {
        var $ = layui.jquery,
            formSelects = layui.formSelects;
        var deptName = $("#searchForm").find("input[name='deptName']").val().trim();
        var disable = $("#searchForm").find("select[name='disable']").val().trim();
        // var depts = $("#dept-multiSelectTree").drawMultipleTree("getChecks", "val").split(",");
        condition.deptName = deptName;
        condition.disable = disable;
    });
    return condition;
}