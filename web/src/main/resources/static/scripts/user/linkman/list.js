layui.use(['form', 'layer', 'table', 'laytpl', 'laydate'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        laydate = layui.laydate,
        table = layui.table;


    //用户列表
    var tableIns = table.render({
        elem: '#List',
        url: web.rootPath() + 'linkman/list',
        cellMinWidth: 95,
        page: true,
        height: "full-" + Math.round(Number($('.layui-card-header').height()) + 44),
        limits: [10, 13, 15, 20, 30, 50, 100, 200],
        limit: 10,
        toolbar: '#List-toolbar',
        id: "ListTable",
        cols: [[
            {type: "checkbox", fixed: "left", width: 50},
                    // {field: 'id', title:  'id', minWidth: 100, align: "center"},
                    // {field: 'custId', title: '客户id', minWidth: 100, align: "center"},
                    {field: 'linkman', title: '联系人名字', minWidth: 100, align: "center"},
                    {field: 'sex', title: '性别', minWidth: 100, align: "center",
                        templet: function (d) {
                            if (d.sex == '0') {
                                return "<button class=\"layui-btn layui-btn-normal layui-btn-xs sz-btn-sm\" style='cursor: default'>女</button>";
                            } else if (d.sex == '1') {
                                return "<button class=\"layui-btn layui-btn-danger layui-btn-xs sz-btn-sm\" style='cursor: default'>男</button>";
                            } else
                                return "<button class=\"layui-btn layui-btn-danger layui-btn-xs sz-btn-sm\" style='cursor: default'>未设置性别</button>";
                        }},
                    {field: 'age', title: '年龄', minWidth: 100, align: "center"},
                    {field: 'phone', title: '联系人电话', minWidth: 100, align: "center"},
                    {field: 'position', title: '职位', minWidth: 100, align: "center"},
                    {field: 'department', title: '部门', minWidth: 100, align: "center"},
                    {field: 'remark', title: '备注信息', minWidth: 100, align: "center"},
                    {field: 'inputUser', title: '录入人', minWidth: 100, align: "center"},
                    {field: 'inputTime', title: '录入时间', minWidth: 100, align: "center"},

            {title: '操作', width: 160, templet: '#List-editBar', fixed: "right", align: "center"}
        ]],

    });

    //头工具栏事件
    table.on('toolbar(List-toolbar)', function (obj) {
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
            var custId = $("#searchForm").find("select[name='custId']").val().trim();
            //表格重载
            tableIns.reload({
                where: { //设定异步数据接口的额外参数，任意设
                    parameterName: parameterName,
                    custId: custId
                }
            });
        }
    };

    $('#SearchBtn').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });


    //头工具栏事件
    table.on('toolbar(List-toolbar)', function (obj) {
        if (obj.event == 'add') {
            layer.open({
                id: "Save-frame",
                type: 2,
                area: ['600px', '501px'],
                title: '新增',
                fixed: false,
                maxmin: true,
                content: web.rootPath() + 'linkman/add.html'
            });
        }

        //判断当前执行的是不是导出操作
        if(obj.event==='export'){
            var eix;
            var url=web.rootPath()+ 'linkman/export';
            $.fileDownload(url,{
                httpMethod: 'POST',
                prepareCallback: function (url){
                    eix=layer.load(2);
                },
                successCallback: function (url){
                    layer.close(eix);
                },
                failCallback: function (html,url){
                    layer.close(eix);
                    layer.msg("导出失败",{icon:2});
                }
            });
        }
    });
    //监听工具条
    table.on('tool(List-toolbar)', function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case 'update':
                layer.open({
                    id: "Update-frame",
                    type: 2,
                    resize: false,
                    area: ['550px', '500px'],
                    title: '修改',
                    fixed: false,
                    maxmin: true,
                    content: web.rootPath() + "linkman/" + data.id + ".html?_"
                });
                break;
            case 'delete':
                var index = layer.confirm('确定要删除?', {
                    btn: ['确定', '取消'] //按钮
                }, function () {
                    $.ajax({
                        url: web.rootPath() + "linkman/delete/" + data.id,
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
        };
    });

    $(window).resize(function () {
        $('div[lay-id="ListTable"]').height(document.body.offsetHeight - Math.round(Number($('.layui-card-header').height()) + 47));
    });
});
