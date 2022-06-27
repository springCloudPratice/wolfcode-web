layui.use(['form', 'layer', 'table', 'laytpl', 'laydate', 'formSelects'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        laydate = layui.laydate,
        formSelects = layui.formSelects,
        table = layui.table;
    var onClickData;
    // layer = parent.layer === undefined ? layui.layer : top.layer,
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

    //日期时间选择器
    laydate.render({
        elem: '#user_createTime'
        , type: 'datetime'
        , range: true
        , calendar: true
    });

    //用户列表
    var tableIns = table.render({
        elem: '#userList',
        url: web.rootPath() + 'user/userList',
        cellMinWidth: 95,
        page: true,
        height: "full-" + Math.round(Number($('.layui-card-header').height()) + 44),
        limits: [5, 13, 15, 20, 30, 50, 100, 200],
        limit: 10,
        toolbar: '#userList-toolbar',
        id: "userListTable",
        method: "post",
        cols: [[
            {type: "checkbox", fixed: "left", width: 50},
            {field: 'username', title: '账号', minWidth: 100, align: "center"},
            {field: 'nickName', title: '姓名', minWidth: 100, align: "center"},
            {field: 'roleNames', title: '角色', minWidth: 100, align: "center"},
            {field: 'deptPath', title: '机构', minWidth: 100, align: "center"},
            {field: 'phone', title: '手机号码', minWidth: 100, align: "center"},
            {field: 'email', title: '邮箱', minWidth: 100, align: "center"},
            {
                field: 'disable', title: '状态', width: 80, align: "center", templet: function (d) {
                    if (d.disable == '0') {
                        return "<button class=\"layui-btn layui-btn-normal layui-btn-xs\">正常</button>";
                    } else if (d.disable == '1') {
                        return "<button class=\"layui-btn layui-btn-danger layui-btn-xs\">已删除</button>";
                    } else {
                        return "<button class=\"layui-btn layui-btn-disabled layui-btn-xs\">未知</button>";
                    }
                }
            },
            {field: 'loginTime', title: '最近登录时间', minWidth: 100, align: "center"},
            {title: '操作', width: 200, templet: "#dictList-editBar", fixed: "right", align: "center"}
        ]],
    });

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

    var $ = layui.$, active = {
        reload: function () {
            //表格重载
            tableIns.reload({
                where: buildCondition()
            });
        }
    };

    $('#userSearchBtn').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });


    //头工具栏事件
    table.on('toolbar(userList-toolbar)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'add':
                var addLayer = layer.open({
                    id: "userSave-frame",
                    type: 2,
                    resize: false,
                    area: ['550px', '430px'],
                    title: '新增用户',
                    fixed: false,
                    maxmin: true,
                    content: web.rootPath() + 'user/add.html?_' + new Date().getTime()
                });
                // layer.full(addLayer);
                break;
            case 'delete':
                if (checkStatus.data.length < 1) {
                    layer.msg("请选择要删除的记录!", {icon: 2});
                    return false;
                }
                layer.confirm('确定要删除?', {
                    btn: ['确定', '取消'] //按钮
                }, function () {
                    var userIds = [];
                    for (var i = 0; i < checkStatus.data.length; i++) {
                        userIds.push(checkStatus.data[i].userId);
                    }
                    var index = layer.load(2);
                    $.ajax({
                        url: web.rootPath() + "user/delete",
                        type: "delete",
                        contentType: "application/json;charset=utf-8",
                        data: JSON.stringify(userIds),
                        dataType: 'json',
                        success: function (data) {
                            layer.msg("操作成功", {
                                icon: 1,
                                success: function () {
                                    $('#userSearchBtn').trigger("click");
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
            case 'import':
                layer.open({
                    id: "userImport-frame",
                    type: 2,
                    resize: false,
                    area: ['400px', '350px'],
                    title: '导入用户',
                    fixed: false,
                    maxmin: false,
                    content: web.rootPath() + 'user/import.html?_' + new Date().getTime()
                });
                break;
            case 'export':
                var eix;
                $.fileDownload(web.rootPath() + "user/export", {
                    httpMethod: 'POST',
                    data: buildCondition(),
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
        }
        ;
    });


    //监听工具条
    table.on('tool(userList-toolbar)', function (obj) {
        var data = obj.data;

        switch (obj.event) {
            case 'update':
                layer.open({
                    id: "userUpdate-frame",
                    type: 2,
                    resize: false,
                    area: ['550px', '430px'],
                    title: '修改用户',
                    fixed: false,
                    maxmin: true,
                    content: web.rootPath() + "user/" + data.userId + ".html?_" + new Date().getTime()
                });
                break;
            case 'resetPwd':
                layer.confirm('确定要重置密码?', {
                    btn: ['确定', '取消'] //按钮
                }, function () {
                    var index = layer.load(2);
                    $.ajax({
                        url: web.rootPath() + "user/resetPassword/" + data.userId,
                        type: "put",
                        contentType: "application/json;charset=utf-8",
                        dataType: 'json',
                        success: function (data) {
                            layer.msg("操作成功", {
                                icon: 1,
                                success: function () {
                                    $('#userSearchBtn').trigger("click");
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
            case 'resetUser':
                layer.confirm('确定要恢复?', {
                    btn: ['确定', '取消'] //按钮
                }, function () {
                    var index = layer.load(2);
                    $.ajax({
                        url: web.rootPath() + "user/reset/" + data.userId,
                        type: "put",
                        contentType: "application/json;charset=utf-8",
                        dataType: 'json',
                        success: function (data) {
                            layer.msg("操作成功", {
                                icon: 1,
                                success: function () {
                                    $('#userSearchBtn').trigger("click");
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
        }
        ;
    });

    $(window).resize(function () {
        $('div[lay-id="userListTable"]').height(document.body.offsetHeight - Math.round(Number($('.layui-card-header').height()) + 47));
    });
});

//构建搜索条件
function buildCondition() {
    var condition = new Object();
    layui.use(['jquery', 'formSelects'], function () {
        var $ = layui.jquery,
            formSelects = layui.formSelects;
        var roles = formSelects.value("roles-select2", "val");
        var username = $("#searchForm").find("input[name='username']").val().trim();
        var nickName = $("#searchForm").find("input[name='nickName']").val().trim();
        var phone = $("#searchForm").find("input[name='phone']").val().trim();
        var disable = $("#searchForm").find("select[name='disable']").val().trim();
        // var depts = $("#dept-multiSelectTree").drawMultipleTree("getChecks", "val").split(",");
        condition.username = username;
        condition.nickName = nickName;
        condition.phone = phone;
        condition.roles = roles && roles.length > 0 ? roles.toString() : null;
        condition.disable = disable;
    });
    return condition;
}