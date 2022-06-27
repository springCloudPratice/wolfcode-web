String.prototype.contains = function (value) {
    return this.indexOf(value) != -1;
};

String.prototype.endsWith = function (value) {
    if (value == null || value == "" || this.length == 0 || value.length > this.length)
        return false;
    if (this.substring(this.length - value.length) == value)
        return true;
    else
        return false;
    return true;
};

String.prototype.startsWith = function (value) {
    if (value == null || value == "" || this.length == 0 || value.length > this.length)
        return false;
    if (this.substr(0, value.length) == value)
        return true;
    else
        return false;
    return true;
};

String.prototype.replaceAll = function (searchValue, replaceValue) {
    var regExp = new RegExp(searchValue, "g");
    return this.replace(regExp, replaceValue);
};

String.prototype.isBlank = function () {
    if (this == undefined || this == null || this.trim() == "") {
        return true;
    }
    return false;
};

//该方法不能去重对象类型的元素
Array.prototype.unique = function () {
    this.sort(); //先排序
    var res = [this[0]];
    for (var i = 1; i < this.length; i++) {
        if (this[i] !== res[res.length - 1]) {
            res.push(this[i]);
        }
    }
    return res;
};

Array.prototype.contains = function (element) {
    for (i in this) {
        if (this[i] == element) return true;
    }
    return false;
};

Array.prototype.exists = function (element) {
    for (i in this) {
        if (this[i] == element) return true;
    }
    return false;
};

Array.prototype.remove = function (element) {
    if (this.length == 0) {
        return false;
    } else {
        for (var i = 0; i < this.length; i++) {
            console.log("this=", JSON.stringify(this[i]), "element=", JSON.stringify(element))
            if (JSON.stringify(this[i]) == JSON.stringify(element)) {
                this.splice(i, 1);
            }
        }
    }
    return true;
};

Date.prototype.format = function (pattern) {
    var o = {
        "M+": this.getMonth() + 1,                 //月份
        "d+": this.getDate(),                    //日
        "H+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(pattern)) {
        pattern = pattern.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(pattern)) {
            pattern = pattern.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return pattern;
};


//获取每个月的第一天
Date.prototype.getMonthFirstDay = function () {
    var new_year = this.getFullYear();    //取当前的年份
    var new_month = parseInt(this.getMonth());//
    if (parseInt(this.getMonth()) > 12) {
        new_month -= 12;        //月份减
        new_year++;            //年份增
    }
    var new_date = new Date(new_year, new_month, 1);    //取当年当月中的第一天
    return new_date;
};

//获取每个月的最后一天
Date.prototype.getLastDay = function () {
    var new_year = this.getFullYear();    //取当前的年份
    var new_month = parseInt(this.getMonth()) + 1;//取下一个月的第一天，方便计算（最后一天不固定）
    if (parseInt(this.getMonth()) > 12) {
        new_month -= 12;        //月份减
        new_year++;            //年份增
    }
    var new_date = new Date(new_year, new_month, 1);    //取当年当月中的第一天
    return new Date(new_date.getTime() - 1000 * 60 * 60 * 24);//获取当月最后一天日期
};

/**
 获取每个季度最后一个月的日期
 例如: month : [1-3] => 返回3月
 month : [4-6] => 返回6月
 month : [7-9] => 返回9月
 month : [10-12] => 返回12月
 */
Date.prototype.getThisQuarterLastDay = function () {
    var m = this.getMonth();
    var quarter = 0;
    if (m <= 3) {
        quarter = 2;
    } else if (m >= 4 && m <= 6) {
        quarter = 5;
    } else if (m >= 7 && m <= 9) {
        quarter = 8;
    } else {
        quarter = 11;
    }
    return new Date(this.getFullYear(), quarter, this.getDate(), this.getHours(), this.getMinutes(), this.getSeconds());
};

//获取本周一的日期
Date.prototype.getMonDay = function () {
    var d = new Date(),
        day = d.getDay(),
        date = d.getDate();
    if (day == 1)
        return d;
    if (day == 0)
        d.setDate(date - 6);
    else
        d.setDate(date - day + 1);
    return d;
};

//获取本周日的日期
Date.prototype.getSunDay = function () {
    var mis = this.getMonDay().getTime() + 86400000 * 6;
    return new Date(mis);
};

var web = {
    rootPath: function () {
        //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
        var curWwwPath = window.document.location.href;
        //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
        var pathName = window.document.location.pathname;
        var pos = curWwwPath.indexOf(pathName);
        //获取主机地址，如： http://localhost:8083
        var localhostPaht = curWwwPath.substring(0, pos);
        //获取带"/"的项目名，如：/uimcardprj
        var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
        return (localhostPaht + projectName) + "/";
    },
    basePath: function () {
        //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
        var curWwwPath = window.document.location.href;
        //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
        var pathName = window.document.location.pathname;
        var pos = curWwwPath.indexOf(pathName);
        //获取主机地址，如： http://localhost:8083
        var localhostPaht = curWwwPath.substring(0, pos);
        return localhostPaht;
    }
};

/**
 *
 * @param iframeID 弹出层的ID值
 * @param selector 列表页面的查询按钮
 */
function reloadTb(iframeID, selector) {
    layui.use(['jquery'], function () {
        $ = layui.jquery;
        var box = $(window.top.document).find("#LAY_app #LAY_app_body").find(".layui-show");
        $(box).find("iframe:first").contents().find(selector).click();
        var index = parent.layer.getFrameIndex(iframeID);
        parent.layer.close(index);
    })
}

function renderErrTb(id, data) {
    var html = new String();
    html += "<table class='layui-table' id='" + id + "'>";

    //渲染表头
    html += "<thead>";
    html += "<tr>";
    html += "<th lay-data=\"{field:'msg', width:80}\">错误信息</th>";
    html += "</tr>";
    html += "</thead>";

    //渲染内容
    html += "<tbody>";
    for (var i = 0; i < data.length; i++) {
        html += "<tr>";
        html += "<td>" + data[i] + "</td>";
        html += "</tr>";
    }
    html += "</tbody>";

    html += "</table>";

    return html;
}


