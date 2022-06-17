layui.use(['form', 'layer'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery;

    form.verify({
        roleName: function (value) {
            if (value == null || value.trim() == '' || value.length <= 0) {
                return '角色名称不能为空';
            } else if (value.length > 50) {
                return '角色名称最多是50个字符';
            }
        }
        , description: function (value) {
            if (value == null || value.trim() == '' || value.length <= 0) {
                return '角色编码不能为空';
            } else if (value.length > 50) {
                return '角色编码最多是50个字符';
            }
        }
        , roleCode: function (value) {
            if (value == null || value.trim() == '' || value.length <= 0) {
                return '角色描述不能为空';
            } else if (value.length > 255) {
                return '角色最多是255个字符';
            }
        }
    });

    // 权限标识选择
    var xtree1;
    $.ajax({
        url: web.rootPath() + "select/menuTree",
        type: "get",
        success: function (data) {
            if (data.errCode == 0) {
                var sysMenus = data.data;
                xtree1 = new layuiXtree({
                    elem: 'xtree1'   //(必填) 放置xtree的容器id，不要带#号
                    , form: form     //(必填) layui 的 from
                    , data: sysMenus     //(必填) json数组（数据格式在下面）
                    , isopen: false
                });
            } else {
                layer.msg(data.message, {icon: 2});
            }
        }
    });

    //定义树对象
    var organizationTree;
    // 树形菜单
    var setting = {
        callback: {
            onAsyncSuccess: function (event, treeId, treeNode, msg) {
                console.log(msg.data)
                //重新渲染数据.并且初始化树对象
                organizationTree = $.fn.zTree.init($('#organization-tree'), setting, msg.data);
            }
        },
        check: {
            enable: true,
            chkStyle: "radio",
            radioType: "all"
        },
        async: {
            enable: true,
            url: web.rootPath() + "select/roleTree",
            type: "GET",
            dataType: "json",
        },
        view: {
            showIcon: true,
            dblClickExpand: false,
            showLine: true,
            selectedMulti: true
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "pid",
            }
        }
    };
    //页面加载完成后初始化
    $(function () {
        $.fn.zTree.init($('#organization-tree'), setting);
    })

    form.on('submit(roleAdd-filter)', function (data) {

        //节点
        var nodes = organizationTree.getCheckedNodes(true);

        //设置父机构
        data.field.pid = nodes[0].id

        var arr = xtree1.GetChecked();

        var authMenuCodes = [];
        $.each(arr, function (index, value) {
            authMenuCodes.push(value.value);
        });
        //设置权限合集
        data.field.authMenuCodes = authMenuCodes

        $.ajax({
            url: web.rootPath() + "role/save",
            type: "post",
            contentType: "application/json",
            data: JSON.stringify(data.field),
            dataType: 'json',
            traditional: true,
            success: function (data) {
                layer.msg("操作成功", {
                    icon: 1,
                    success: function () {
                        reloadTb("roleSave-frame", "#roleSearchBtn");
                    }
                });
            },
            error: function (e) {
                layer.msg(e.responseJSON.message, {icon: 2});
            }
        });
        return false;
    });
});