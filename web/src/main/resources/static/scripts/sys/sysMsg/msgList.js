layui.use(['layer', 'table', 'laytpl'], function () {
    var layer = layui.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;

    //全部消息
    table.render({
        elem: '#LAY-equipment-message-all',
        data: [],
        cellMinWidth: 95,
        page: true,
        height: "full-100",
        toolbar: '#allList-toolbar',
        limits: [10, 13, 15, 20, 30, 50, 100, 200],
        limit: 10,
        id: "LAY-equipment-message-all",
        cols: [[
            {type: "checkbox", fixed: "left", width: 50},
            {field: 'titleContent', title: '标题内容', minWidth: 100, align: "center"},
            {field: 'tile', title: '时间', minWidth: 100, align: "center"}
        ]]
    });

    //个人消息
    table.render({
        elem: '#LAY-equipment-message-personal',
        data: [],
        cellMinWidth: 95,
        page: true,
        height: "full-100",
        toolbar: '#personalList-toolbar',
        limits: [10, 13, 15, 20, 30, 50, 100, 200],
        limit: 10,
        id: "LAY-equipment-message-personal",
        cols: [[
            {type: "checkbox", fixed: "left", width: 50},
            {field: 'titleContent', title: '标题内容', minWidth: 100, align: "center"},
            {field: 'tile', title: '时间', minWidth: 100, align: "center"}
        ]]
    });

    //系统公告
    table.render({
        elem: '#LAY-equipment-message-notice',
        data: [],
        cellMinWidth: 95,
        page: true,
        height: "full-100",
        toolbar: '#noticeList-toolbar',
        limits: [10, 13, 15, 20, 30, 50, 100, 200],
        limit: 10,
        id: "LAY-equipment-message-notice",
        cols: [[
            {type: "checkbox", fixed: "left", width: 50},
            {field: 'titleContent', title: '标题内容', minWidth: 100, align: "center"},
            {field: 'tile', title: '时间', minWidth: 100, align: "center"}
        ]]
    });
});
