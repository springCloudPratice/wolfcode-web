<form class="layui-form input-form" lay-filter="catalog-form" id="catalog-input-form">
    <div class="layui-form-item layui-col-space10">
        <div class="layui-col-lg6">
            <label class="layui-form-label text-label">目录名称</label>
            <div class="layui-input-block text-input">
                <input type="text" name="menuName" lay-verify="required" placeholder="目录名称,最多50字"
                       autocomplete="off" maxlength="50"
                       class="layui-input" value="${sysMenu.menuName}">
            </div>
        </div>
        <div class="layui-col-lg6">
            <label class="layui-form-label text-label">排序</label>
            <div class="layui-input-block text-input">
                <input type="number" name="sort" lay-verify="number" placeholder="请输入排序" autocomplete="off"
                       class="layui-input" value="value="${sysMenu.sort}"">
            </div>
        </div>
        <div class="layui-col-lg6">
            <label class="layui-form-label text-label">目录图标</label>
            <div class="layui-input-block">
                <input type="text" id="menuIcon" name="menuIcon"
                       placeholder="off" lay-filter="menuIcon"
                       autocomplete="off" class="layui-input">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block popup-btn-block">
            <button class="layui-btn layui-btn-normal" lay-submit lay-filter="catalog-filter">新增
            </button>
            <button class="layui-btn layui-btn-primary btn-close">关闭</button>
        </div>
    </div>
</form>