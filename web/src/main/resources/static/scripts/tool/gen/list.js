layui.use(['form', 'layer', 'table', 'laytpl', 'laydate'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        laydate = layui.laydate,
        table = layui.table;

    var ztreeSetting = {
        async: {
            url: web.rootPath() + "select/noButton/menuTree",
            enable: true,
            type: "get",
            dataType: 'json'
        },
        view: {
            showLine: true,
            selectedMulti: false,
            showIcon: false,
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "menuCode",
                pIdKey: "parentMenuCode"
            }
        },
        callback: {

        }
    };
    ztreeRender($("#menuParentCode"), ztreeSetting);


    form.on('submit(Add-filter)', function (data) {
        var index = layer.load(2);
        $.ajax({
            url: web.rootPath() + "gen/save",
            type: "post",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(data.field),
            dataType: 'json',
            success: function (data) {
                layer.msg("操作成功", {
                    icon: 1,
                    success: function () {
                        //reloadTb("userSave-frame", "#userSearchBtn");
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
        return false;
    });

    $(window).resize(function () {
        $('div[lay-id="ListTable"]').height(document.body.offsetHeight - Math.round(Number($('.layui-card-header').height()) + 47));
    });
});

function ztreeRender(dom, setting) {
    layui.use(['jquery'], function () {
        var $ = layui.jquery,
            tem = new Date().getTime(),
            ZTREE_SELECT_INPUT = "ztree_select_input" + tem,
            ZTREE_SELECT_ID = "ztree_select_" + tem,
            ZTREE_SELECT_BODY = "ztree_select_body" + tem,
            placeholder = dom.attr("placeholder"),
            ZTREE_OBJ = undefined;
        // 组装结构
        dom.addClass("layui-hide");
        dom.after('<div id="' + ZTREE_SELECT_BODY + '" class="layui-card-body ztree-select-body layui-hide"><div class="ztree" id="' + ZTREE_SELECT_ID + '"></div></div>');
        dom.after('<input type="text" id="' + ZTREE_SELECT_INPUT + '" placeholder="' + placeholder + '" autocomplete="off" class="layui-input" readonly >');

        var ZTREE_SELECT_INPUT_DOM = $("#" + ZTREE_SELECT_INPUT),
            ZTREE_SELECT_ID_DOM = $("#" + ZTREE_SELECT_ID),
            ZTREE_SELECT_BODY_DOM = $("#" + ZTREE_SELECT_BODY);
        var click = setting.callback.onClick
        setting.callback.onClick = function (event, treeId, treeNode, clickFlag) {
            var treeObj = $.fn.zTree.getZTreeObj(treeId);
            var data = treeObj.setting.data
            var idKeyName = data.simpleData.idKey ? data.simpleData.idKey : "id";
            var nameKey = data.key.name ? data.key.name : "name";
            ZTREE_SELECT_INPUT_DOM.val(treeNode[nameKey])
            ZTREE_SELECT_INPUT_DOM.prev().val(treeNode[idKeyName])
            ZTREE_SELECT_BODY_DOM.addClass("layui-hide")
            if (click) click();
        }

        // 初始化树形
        ZTREE_OBJ = $.fn.zTree.init(ZTREE_SELECT_ID_DOM, setting);

        // 点击事件 点击任一地方收起下拉
        $(document).on("click", function () {
            ZTREE_SELECT_BODY_DOM.addClass("layui-hide")
        });
        // 点击输入框展开下拉
        ZTREE_SELECT_INPUT_DOM.on('click', function (e) {
            e.stopPropagation()
            ZTREE_SELECT_BODY_DOM.removeClass("layui-hide")
        })
        // 阻止一些默认事件
        ZTREE_SELECT_ID_DOM.on("click", function (e) {
            e.stopPropagation()
        })

    })
}