layui.use(['form', 'layer', 'table', 'laytpl', 'laydate'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        laydate = layui.laydate,
        table = layui.table;

    //日期时间选择器
    laydate.render({
        elem: '#operationTime'
        , type: 'datetime'
        , range: true
        , calendar: true
    });
    //用户列表
    var tableIns = table.render({
        elem: '#operationLogList',
        url: web.rootPath() + 'log/operationLogList',
        cellMinWidth: 95,
        page: true,
        height: "full-" + Math.round(Number($('.layui-card-header').height()) + 44),
        limits: [10, 13, 15, 20, 30, 50, 100, 200],
        limit: 10,
        toolbar: '#roleList-toolbar',
        id: "operationLogListTable",
        cols: [[
            {type: "checkbox", fixed: "left", width: 50},
            {field: 'userName', title: '用户名', minWidth: 100, align: "center"},
            {field: 'operationType', title: '用户操作', minWidth: 100, align: "center"},
            {field: 'visitMethod', title: '请求方法', minWidth: 100, align: "center"},
            {field: 'parameters', title: '请求参数', minWidth: 100, align: "center"},
            {field: 'duration', title: '执行时长(毫秒)', minWidth: 100, align: "center"},
            {field: 'ipAddress', title: 'ip地址', minWidth: 100, align: "center"},
            {field: 'operationTime', title: '操作时间', minWidth: 100, align: "center"},
        ]]
    });

    var $ = layui.$, active = {
        reload: function () {
            //获取搜索条件值
            var username = $("#searchForm").find("input[name='username']").val().trim();
            var createTime = $("#searchForm").find("#operationTime").val().trim();
            var startDate = "";
            var endDate = "";
            var operationType = $("#searchForm").find("select[name='operationType'] option:selected").val();
            if (createTime != "" && createTime != undefined) {
                var arr = createTime.split(" - ");
                startDate = arr[0];
                endDate = arr[1];
            }
            //表格重载
            tableIns.reload({
                where: { //设定异步数据接口的额外参数，任意设
                    userName: username,
                    startDate:startDate,
                    endDate:endDate,
                    operationType:operationType
                }
            });
        }
    };

    $('#operationLogSearchBtn').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

    $(window).resize(function () {
        $('div[lay-id="operationLogListTable"]').height(document.body.offsetHeight - Math.round(Number($('.layui-card-header').height()) + 47));
    });
});
