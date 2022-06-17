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
        elem: '#loginLogList',
        url:  web.rootPath() + 'loginLog/loginLogList',
        cellMinWidth: 95,
        page: true,
        height: "full-" + Math.round(Number($('.layui-card-header').height()) + 44),
        limits: [10, 13, 15, 20, 30, 50, 100, 200],
        limit: 10,
        toolbar: '#roleList-toolbar',
        id: "loginLogListTable",
        cols: [[
            {type: "checkbox", fixed: "left", width: 50},
            {field: 'userName', title: '用户名', minWidth: 100, align: "center"},
            {field: 'loginTime', title: '登录时间', minWidth: 100, align: "center"},
            {
                field: 'success', title: '状态', width: 100, align: "center", templet: function (d) {
                    if (d.success == '1') {
                        return "<button class=\"layui-btn layui-btn-normal layui-btn-xs\">成功</button>";
                    } else if (d.success == '2') {
                        return "<button class=\"layui-btn layui-btn-danger layui-btn-xs\">失败</button>";
                    } else {
                        return "<button class=\"layui-btn layui-btn-disabled layui-btn-xs\">未知</button>";
                    }
                }
            },
            {field: 'errorLog', title: '错误日志', minWidth: 100, align: "center"},
            {field: 'ipAddress', title: 'IP地址', minWidth: 100, align: "center"},
            {field: 'duration', title: '执行时长', minWidth: 100, align: "center"},
            {field: 'operationTime', title: '操作时间', minWidth: 100, align: "center"},
        ]],
    });

    var $ = layui.$, active = {
        reload: function () {
            //获取搜索条件值
            var username = $("#searchForm").find("input[name='username']").val().trim();
            var createTime = $("#searchForm").find("#operationTime").val().trim();
            var startDate = "";
            var endDate = "";
            var success = $("#searchForm").find("select[name='success'] option:selected").val();
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
                    success:success
                }
            });
        }
    };

    $('#loginLogSearchBtn').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

    $(window).resize(function () {
        $('div[lay-id="loginLogListTable"]').height(document.body.offsetHeight - Math.round(Number($('.layui-card-header').height()) + 47));
    });
});
