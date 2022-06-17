<form class="layui-form input-form layui-hide" lay-filter="button-form" id="button-input-form">
    <div class="layui-form-item layui-col-space10">
        <div class="layui-col-lg6">
            <label class="layui-form-label text-label">按钮名称</label>
            <div class="layui-input-block text-input">
                <input type="text" name="menuName" lay-verify="required" placeholder="按钮名称,最多50字"
                       autocomplete="off" maxlength="50"
                       class="layui-input" value="${sysMenu.menuName}">
            </div>
        </div>
        <div class="layui-col-lg6">
            <label class="layui-form-label text-label">上级菜单</label>
            <div class="layui-input-block text-input">
                <input type="text" id="buttonParentCode" name="parentMenuCode" lay-verify="required" placeholder="请选择上级菜单"
                       autocomplete="off" class="layui-input" readonly value="${sysMenu.parentMenuCode}">
            </div>
        </div>
        <div class="layui-col-lg6">
            <label class="layui-form-label text-label">授权标识</label>
            <div class="layui-input-block text-input">
                <input type="text" name="authorization" lay-verify="required"
                       placeholder="多个用逗号分隔,如userlist,usercrate" maxlength="200"
                       autocomplete="off" class="layui-input" value="${sysMenu.authorization}">
            </div>
        </div>
        <div class="layui-col-lg6">
            <label class="layui-form-label text-lable">排序</label>
            <div class="layui-input-block text-input">
                <input type="number" name="sort" lay-verify="number" placeholder="请输入排序" autocomplete="off"
                       class="layui-input" value="${sysMenu.sort}">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block popup-btn-block">
            <button class="layui-btn layui-btn-normal" lay-submit lay-filter="button-filter">新增
            </button>
            <button class="layui-btn layui-btn-primary btn-close">关闭</button>
        </div>
    </div>
</form>