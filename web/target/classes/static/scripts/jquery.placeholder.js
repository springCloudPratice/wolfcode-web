layui.use(['jquery'], function () {
    var $ = layui.jquery;
    $(function () {
        setTimeout(function () {
            function placeholderSupport() {
                return 'placeholder' in document.createElement('input');
            }

            if (!placeholderSupport()) {   // 判断浏览器是否支持 placeholder
                $("[placeholder]").each(function () {
                    var _this = $(this);
                    var left = _this.css("padding-left");
                    _this.parent().append('<span class="placeholder" data-type="placeholder" style="position:absolute;color:#999;width:90%;overflow:hidden;white-space: nowrap;top:0px;left: ' + left + '">' + _this.attr("placeholder") + '</span>');
                    if (_this.attr('id') == "replyContent") {
                        _this.parent().find("span.placeholder").hide();
                    } else {
                        if (_this.val() != "") {
                            _this.parent().find("span.placeholder").hide();
                        } else {
                            _this.parent().find("span.placeholder").show();
                        }
                    }
                }).on("focus", function () {
                    $(this).parent().find("span.placeholder").hide();
                }).on("blur", function () {
                    var _this = $(this);
                    setTimeout(function () {
                        if (_this.val() != "") {
                            _this.parent().find("span.placeholder").hide();
                        } else {
                            _this.parent().find("span.placeholder").show();
                        }
                    }, 300);
                });
                // 点击表示placeholder的标签相当于触发input
                $("span.placeholder").on("click", function () {
                    if (!$(this).siblings("[placeholder]").hasClass('layui-disabled')) {
                        if (typeof ($(this).siblings("[placeholder]").attr('disabled')) == "undefined") {
                            $(this).hide();
                            $(this).siblings("[placeholder]").trigger("click");
                            $(this).siblings("[placeholder]").trigger("focus");
                        }
                    }
                });
            }
        }, 200);
    });
});