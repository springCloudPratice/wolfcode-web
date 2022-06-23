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
        url: web.rootPath() + 'contractinfo/list',
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
                    {field: 'customerName', title: '企业客户名称', minWidth: 100, align: "center"},
                    {field: 'contractName', title: '合同名称', minWidth: 100, align: "center"},
                    {field: 'contractCode', title: '合同编码', minWidth: 100, align: "center"},
                    {field: 'amounts', title: '合同金额', minWidth: 100, align: "center"},
                    {field: 'startDate', title: '合同生效开始时间', minWidth: 100, align: "center"},
                    {field: 'endDate', title: '合同生效结束时间', minWidth: 100, align: "center"},
                    {field: 'content', title: '合同内容', minWidth: 100, align: "center"},
                    {field: 'affixSealStatus', title: '是否盖章确认', minWidth: 100, align: "center", templet: function (d) {
                            if (d.affixSealStatus == '0') {
                                return "<button class=\"layui-btn layui-btn-danger layui-btn-xs sz-btn-sm\" style='cursor: default'>否</button>";
                            } else if (d.affixSealStatus == '1') {
                                return "<button class=\"layui-btn layui-btn-normal layui-btn-xs sz-btn-sm\" style='cursor: default'>是</button>";
                            }
                        }},
                    {field: 'auditStatus', title: '审核状态', minWidth: 100, align: "center", templet: function (d) {
                            if (d.auditStatus == '0') {
                                return "<button class=\"layui-btn layui-btn-normal layui-btn-xs sz-btn-sm\" style='cursor: default'>未审核</button>";
                            } else if (d.auditStatus == '1') {
                                return "<button class=\"layui-btn layui-btn-danger layui-btn-xs sz-btn-sm\" style='cursor: default'>审核通过</button>";
                            }
                            else if (d.auditStatus == '-1') {
                                return "<button class=\"layui-btn layui-btn-danger layui-btn-xs sz-btn-sm\" style='cursor: default'>审核不通过</button>";
                            }
                        }},
                    {field: 'nullifyStatus', title: '是否作废', minWidth: 100, align: "center", templet: function (d) {
                            if (d.nullifyStatus == '0') {
                                return "<button class=\"layui-btn layui-btn-normal layui-btn-xs sz-btn-sm\" style='cursor: default'>在用</button>";
                            } else if (d.nullifyStatus == '1') {
                                return "<button class=\"layui-btn layui-btn-danger layui-btn-xs sz-btn-sm\" style='cursor: default'>作废</button>";
                            }
                        }},
                    // {field: 'inputUser', title: '录入人', minWidth: 100, align: "center"},
                    // {field: 'inputTime', title: '录入时间', minWidth: 100, align: "center"},
                    // {field: 'updateTime', title: '修改时间', minWidth: 100, align: "center"},

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
            var affixSealStatus = $("#searchForm").find("select[name='affixSealStatus']").val().trim();
            var auditStatus =$("#searchForm").find("select[name='auditStatus']").val().trim();
            var nullifyStatus =$("#searchForm").find("select[name='nullifyStatus']").val().trim();
            //表格重载
            tableIns.reload({
                where: { //设定异步数据接口的额外参数，任意设
                    parameterName: parameterName,
                    nullifyStatus:nullifyStatus,
                    auditStatus:auditStatus,
                    affixSealStatus:affixSealStatus
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
                content: web.rootPath() + 'contractinfo/add.html'
            });
        }
        ;
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
                    content: web.rootPath() + "contractinfo/" + data.id + ".html?_"
                });
                break;
            case 'delete':
                var index = layer.confirm('确定要删除?', {
                    btn: ['确定', '取消'] //按钮
                }, function () {
                    $.ajax({
                        url: web.rootPath() + "contractinfo/delete/" + data.id,
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
