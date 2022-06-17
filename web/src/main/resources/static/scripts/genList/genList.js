layui.use(['form', 'layer', 'table', 'laytpl', 'laydate'], function () {
    var form = layui.form,
        layer =  layui.layer ,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        laydate = layui.laydate,
        table = layui.table;
    var tableList;
    var dbTableList
    //用户列表
    var tableIns = table.render({
        elem: '#genList',
        url: 'getGenList',
        cellMinWidth: 95,
        page: true,
        height: "full-" + Math.round(Number($('.layui-card-header').height()) + 44),
        limits: [10, 13, 15, 20, 30, 50, 100, 200],
        limit: 10,
        toolbar: '#genList-toolbar',
        id: "genListTable",
        cols: [[
            {type: "checkbox", fixed: "left", width: 50},
            {field: 'tableName', title: '表名称', minWidth: 100, align: "center"},
            {field: 'tableComment', title: '表描述', minWidth: 100, align: "center"},
            {field: 'className', title: '实体类名称', minWidth: 100, align: "center"},
            {field: 'createTime', title: '创建时间', minWidth: 100, align: "center"},
            {field: 'updateTime', title: '更新时间', minWidth: 100, align: "center"},
            {title: '操作', width: 200, templet: '#genList-editBar', fixed: "right", align: "center"}
        ]],
    });

    var $ = layui.$, active = {
        reload: function () {
            //获取搜索条件值
            var tableName = $("#searchForm").find("input[name='tableName']").val().trim();
            var tableComment = $("#searchForm").find("input[name='tableComment']").val().trim();
            //表格重载
            tableIns.reload({
                where: { //设定异步数据接口的额外参数，任意设
                    tableName: tableName,
                    tableComment: tableComment
                }
            });
        },
        dbTableListReload:function () {
            var tableName = $("#dbTableName").val().trim();
            var tableComment = $("#dbTableComment").val().trim();
            //表格重载
            dbTableList.reload({
                where: { //设定异步数据接口的额外参数，任意设
                    tableName: tableName,
                    tableComment: tableComment
                }
            });
        }
    };

    $('#tableSearchBtn,#dbTableListSearchBtn').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });


    //头工具栏事件
    table.on('toolbar(genList-toolbar)', function (obj) {
        switch (obj.event){
            case 'add':
                layer.confirm('确认要生成选中的数据吗?', {
                    btn: ['确定', '取消'] //按钮
                }, function (index) {
                    var checkStatus = table.checkStatus("genListTable")
                    var tableNames = new Array();
                    for (var i = 0; i < checkStatus.data.length; i++) {
                        tableNames.push(checkStatus.data[i].tableName);
                    }
                    location.href = web.rootPath() + "gen/batchGenCode?tableNames=" + tableNames;
                    layer.close(index);
                }, function () {
                });
                break;
            case 'import':
                tableList = layer.open({
                    title: '导入表结构',
                    type: 1,
                    area: ['80%', '75%'],
                    btn: ['确定', '取消'],
                    content: $('#dbTable'),
                    yes: function (index, layero) {
                      var checkStatus = table.checkStatus("dbTableList")
                        if (checkStatus.data.length == 0){
                          layer.msg("请至少选择一条数据");
                        }else{
                            var tableNames = [];
                            for (var i = 0; i < checkStatus.data.length; i++) {
                                tableNames.push(checkStatus.data[i].tableName);
                            }
                            $.ajax({
                                type: "POST",
                                url: 'importTable',
                                data: JSON.stringify(tableNames),
                                async: false,
                                dataType: 'json',
                                contentType: "application/json;charset=UTF-8",
                                success: function (data) {
                                    layer.msg(data.message);
                                    $(".layui-laypage-btn").click();
                                },
                                error: function (e) {
                                    layer.msg(e.responseJSON.message, {icon: 2});
                                }
                            });
                            tableIns.reload("genListTable");
                            layer.close(index);
                        }
                    },
                    btn2: function (index, layero) {
                    }
                });
                 dbTableList =  table.render({
                    elem: '#dbTableList',
                    url: web.rootPath() + "gen/getDbTableList",
                    cellMinWidth: 95,
                    page: true,
                     limits: [10, 13, 15, 20, 30, 50, 100, 200],
                    limit: 10,
                    id: "dbTableList",
                    method: "post",
                    cols: [[
                        {type: "checkbox", fixed: "left", width: 50},
                        {field: 'tableName', title: '表名称', minWidth: 100, align: "center"},
                        {field: 'tableComment', title: '表描述', minWidth: 100, align: "center"},
                        {field: 'createTime', title: '创建时间', minWidth: 100, align: "center"},
                        {field: 'updateTime', title: '更新时间', minWidth: 100, align: "center"},
                    ]],
                });
                break;
            case 'deleteTable':
                layer.confirm('确定删除这条配置信息?', {
                    btn: ['确定', '取消'] //按钮
                }, function () {
                    var checkStatus = table.checkStatus("genListTable")
                    var ids = new Array();
                    for (var i = 0; i < checkStatus.data.length; i++) {
                        ids.push(checkStatus.data[i].tableId);
                    }
                    $.ajax({
                        type: "POST",
                        url: 'remove',
                        data: JSON.stringify(ids),
                        async: false,
                        dataType: 'json',
                        contentType: "application/json;charset=UTF-8",
                        success: function (data) {
                            layer.msg(data.message);
                            table.reload("genListTable");
                            $(".layui-laypage-btn").click();
                        },
                        error: function (e) {
                            layer.msg(e.responseJSON.message, {icon: 2});
                        }
                    });
                }, function () {
                });
                break;
        }
    });
    //    单元复选框单选事件
    table.on('checkbox(genList-toolbar)', function (obj) {
        var btnDel = $("#btn_del");
        var btnAdd = $("#btn_add");
        var checkStatus = table.checkStatus("genListTable");
        if (checkStatus.data.length > 0) {
            btnDel.removeClass("layui-btn-disabled");
            btnDel.removeAttr("disabled");
            btnAdd.removeClass("layui-btn-disabled");
            btnAdd.removeAttr("disabled");
        } else {
            btnDel.addClass("layui-btn-disabled");
            btnDel.attr("disabled","disabled");
            btnAdd.addClass("layui-btn-disabled");
            btnAdd.attr("disabled","disabled");
        }
    });
    //监听工具条
    table.on('tool(genList-toolbar)', function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case 'editTable':
                window.location=web.rootPath()+"gen/columnList?tableId=" + obj.data.tableId;
                break;
            case 'preview':
                $.ajax({
                    url: "preview/" + obj.data.tableId,
                    async: false,
                    type: 'get',
                    dataType: 'json',
                    success: function (result) {
                        var items = [];
                        $.each(result.data, function(index, value) {
                            value = value.replace(/</g, "&lt;");
                            value = value.replace(/>/g, "&gt;");
                            var templateName = index.substring(index.lastIndexOf("/") + 1, index.length).replace(/\.vm/g, "");
                            if ("sql" != templateName && "tree.html" != templateName){
                                items.push({
                                    title: templateName , content: "<pre class=\"layui-code\">" + value + "</pre>"
                                })
                            }
                        });
                        top.layer.tab({
                            area: ['90%', '90%'],
                            shadeClose: true,
                            tab: items
                        });
                    }
                });
                break;
        }
        ;
    });
    $(window).resize(function () {
        $('div[lay-id="genListTable"]').height(document.body.offsetHeight - Math.round(Number($('.layui-card-header').height()) + 47));
    });
});
