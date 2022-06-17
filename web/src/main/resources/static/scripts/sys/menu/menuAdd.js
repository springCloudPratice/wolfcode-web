layui.use(['form', 'iconPicker', 'layer', 'treeSelect'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        iconPicker = layui.iconPicker,
        treeSelect = layui.treeSelect;

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
        $.ajax({
            url: web.rootPath() + "menu/save",
            type: "post",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(data),
            dataType: 'json',
            success: function (data) {
                layer.msg("操作成功", {
                    icon: 1,
                    success: function () {
                        reloadTb("menuSave-frame", "#menuSearchBtn");
                    }
                });
            },
            error: function (e) {
                layer.msg(e.responseJSON.message, {icon: 2});
            }

        });
    }

    form.on('radio(menuType-radio)', function (data) {
        var type = data.value;
        $(".input-form").addClass("layui-hide")
        if (type === '1') {
            $("#catalog-input-form").removeClass("layui-hide")
        } else if (type === '2') {
            $("#menu-input-form").removeClass("layui-hide")
        } else if (type === '4') {
            $("#button-input-form").removeClass("layui-hide")
        }
    });

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

    $(".btn-close").on("click", function () {
        parent.layer.close(parent.layer.getFrameIndex('menuSave-frame'));
    });

    var menuParentSelectSetting = {
            el: '#menuParentCode',
            id: 'menuParentSelect',
            url: web.rootPath() + "select/noButton/menuTree",
            enableAsync: true,
            enableSearch: true,
            idKey: "menuCode",
            pIdKey: "parentMenuCode",
            nameKey: "name",
        },
        buttonParentSelectSetting = {
            el: '#buttonParentCode',
            id: 'buttonParentSelect',
            url: web.rootPath() + "select/noButton/menuTree",
            enableAsync: true,
            enableSearch: true,
            idKey: "menuCode",
            pIdKey: "parentMenuCode",
            nameKey: "name",
        };

    treeSelect.render(menuParentSelectSetting);
    treeSelect.render(buttonParentSelectSetting);

});

