layui.use(['form', 'layer'], function () {
    var form = layui.form,
        layer = layui.layer,
        // laydate=layui.laydate,
        $ = layui.jquery;
    // var start = laydate.render({
    //     elem: '#visitDate',
    //     max:0,//最大值为当前日期
    //     trigger: 'click',
    //     value: getRecentDay(0),//默认值30天前
    //     done:function(value,date){
    //         if(value && (value>$("#end_date").val())){
    //             /*开始时间大于结束时间时，清空结束时间*/
    //             $("#end_date").val("");
    //         }
    //
    //         end.config.min ={
    //             year:date.year,
    //             month:date.month-1,
    //             date: date.date,
    //         };
    //     }
    // });
    // function getRecentDay(day){
    //     var today = new Date();
    //     var targetday_milliseconds=today.getTime() + 1000*60*60*24*day;
    //     today.setTime(targetday_milliseconds);
    //     var tYear = today.getFullYear();
    //     var tMonth = today.getMonth();
    //     var tDate = today.getDate();
    //     tMonth = doHandleMonth(tMonth + 1);
    //     tDate = doHandleMonth(tDate);
    //     return tYear+"-"+tMonth+"-"+tDate;
    // }
    // function doHandleMonth(month){
    //     var m = month;
    //     if(month.toString().length == 1){
    //         m = "0" + month;
    //     }
    //     return m;
    // }

    form.on('submit(Add-filter)', function (data) {
        $.ajax({
            url: web.rootPath() + "visit/update",
            contentType: "application/json",
            type: "put",
            data: JSON.stringify(data.field),
            dataType: 'json',
            success: function (data) {
                layer.msg("操作成功", {
                    icon: 1,
                    success: function () {
                        reloadTb("Update-frame", "#SearchBtn");
                    }
                });
            },
            error: function (e) {
                layer.msg(e.responseJSON.message, {icon: 2});
            }

        })
        return false;
    });

});
