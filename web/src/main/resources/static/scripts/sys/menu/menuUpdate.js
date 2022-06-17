layui.use(['form', 'iconPicker', 'layer', 'jquery'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        iconPicker = layui.iconPicker;

    $(".input-form").removeClass("layui-hide")

    // 保存目录
    form.on('submit(catalog-filter)', function (data) {
        data.field.menuType = 1;
        saveMenu(data.field);
        return false;
    });

    // 保存菜单
    form.on('submit(menu-filter)', function (data) {
        data.field.menuType = 2;
        saveMenu(data.field);
        return false;
    });

    // 保存按钮
    form.on('submit(button-filter)', function (data) {
        data.field.menuType = 4;
        saveMenu(data.field);
        return false;
    });

    function saveMenu(data) {
        data.menuId = $("#menuId").val()
        $.ajax({
            url: web.rootPath() + "menu/update",
            type: "put",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(data),
            dataType: 'json',
            success: function (data) {
                layer.msg("操作成功", {
                    icon: 1,
                    success: function () {
                        reloadTb("MenuUpdate-frame", "#menuSearchBtn");
                    }
                });
            },
            error: function (e) {
                layer.msg(e.responseJSON.message, {icon: 2});
            }
        });
    }

    // 图标选择器
    iconPicker.render({
        // 选择器，推荐使用input
        elem: '#menuIcon',
        // 数据类型：fontClass/unicode，推荐使用fontClass
        type: 'fontClass',
        // 是否开启搜索：true/false
        search: true,
        // 点击回调
        click: function (data) {
        }
    });

    // 选中图标 （常用于更新时默认选中图标）
    iconPicker.checkIcon('menuIcon', 'layui-icon-circle-dot');

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
            onAsyncSuccess: function (event, treeId, treeNode, msg) {
                var ztreeObj =  $.fn.zTree.getZTreeObj(treeId),
                    arr = ztreeObj.transformToArray(ztreeObj.getNodes()),
                    parentId = $("#parentId").val(),
                    idKey = ztreeObj.setting.data.simpleData.idKey,
                    nameKey = ztreeObj.setting.data.key.name;
                for (var i = 0; i < arr.length; i++) {
                    if (arr[i].id === parentId) {
                        ztreeObj.selectNode(arr[i]);
                        $("#menuParentCode").next("input").val(arr[i][nameKey])
                        $("#menuParentCode").val(arr[i][idKey])
                        break;
                    }
                }
            }
        }
    };
    ztreeRender($("#menuParentCode"), ztreeSetting);
    ztreeSetting.callback.onAsyncSuccess = function (event, treeId, treeNode, msg) {
        var ztreeObj =  $.fn.zTree.getZTreeObj(treeId),
            arr = ztreeObj.transformToArray(ztreeObj.getNodes()),
            parentId = $("#parentId").val(),
            idKey = ztreeObj.setting.data.simpleData.idKey,
            nameKey = ztreeObj.setting.data.key.name;
        for (var i = 0; i < arr.length; i++) {
            if (arr[i].id === parentId) {
                ztreeObj.selectNode(arr[i]);
                $("#buttonParentCode").next("input").val(arr[i][nameKey])
                $("#buttonParentCode").val(arr[i][idKey])
                break;
            }
        }
    }
    ztreeRender($("#buttonParentCode"), ztreeSetting);

    $(".btn-close").on("click", function () {
        parent.layer.close(parent.layer.getFrameIndex('MenuUpdate-frame'));
    })
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

        setting.callback.onClick = function (event, treeId, treeNode, clickFlag) {
            var treeObj = $.fn.zTree.getZTreeObj(treeId);
            var data = treeObj.setting.data
            var idKeyName = data.simpleData.idKey ? data.simpleData.idKey : "id";
            var nameKey = data.key.name ? data.key.name : "name";
            ZTREE_SELECT_INPUT_DOM.val(treeNode[nameKey])
            ZTREE_SELECT_INPUT_DOM.prev().val(treeNode[idKeyName])
            ZTREE_SELECT_BODY_DOM.addClass("layui-hide")
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
        ZTREE_SELECT_ID_DOM.on("click",function (e) {
            e.stopPropagation()
        })
    })
}