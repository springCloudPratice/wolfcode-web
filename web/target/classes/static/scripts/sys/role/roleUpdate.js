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

    var oPid = $("#oPid").val().toString();
    var roleId = $("#roleId").val().toString();


    //======= 权限合集菜单
    var xtree1;
    $.ajax({
        url: web.rootPath() + "role/userRoleMenus",
        type: "get",
        data: {roleId: roleId},
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

    //======= 所属角色树菜单
    var organizationTree;
    // 树形菜单
    var setting = {
        callback: {
            onAsyncSuccess: function (event, treeId, treeNode, msg) {
                console.log(msg.data)
                //重新渲染数据.并且初始化树对象
                organizationTree = $.fn.zTree.init($('#organization-tree'), setting, msg.data);
                var nodes = organizationTree.transformToArray(organizationTree.getNodes());
                console.log(nodes)
                for (var i = 0, l = nodes.length; i < l; i++) {
                    var obj = nodes[i];
                    //默认勾选自身的上级节点
                    if (obj.id == oPid) {
                        organizationTree.selectNode(nodes[i]);
                        organizationTree.checkNode(nodes[i], true);
                    }

                    //禁用自己以及下级id
                    if (obj.id == roleId) {
                        organizationTree.setChkDisabled(nodes[i], true, false, true);
                    }
                }
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
                pIdKey: "pId",
            }
        }
    };
    //页面加载完成后初始化
    $(function () {
        $.fn.zTree.init($('#organization-tree'), setting);
    })
    //======= 请求
    form.on('submit(roleAdd-filter)', function (data) {

        //节点
        var nodes = organizationTree.getCheckedNodes(true);

        //设置父机构
        if( nodes[0]){
            data.field.pid = nodes[0].id
        }else{
            data.field.pid = oPid
        }

        var arr = xtree1.GetChecked();
        var authMenuCodes = [];
        $.each(arr, function (index, value) {
            authMenuCodes.push(value.value);
        });
        //设置权限合集
        data.field.authMenuCodes = authMenuCodes
        $.ajax({
            url: web.rootPath() + "role/update",
            contentType: "application/json",
            type: "put",
            data: JSON.stringify(data.field),
            dataType: 'json',
            success: function (data) {
                layer.msg("操作成功", {
                    icon: 1,
                    success: function () {
                        reloadTb("roleUpdate-frame", "#roleSearchBtn");
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