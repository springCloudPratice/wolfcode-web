package cn.wolfcode.web.modules.sys.conctroller;

import cn.wolfcode.web.modules.sys.service.SysMenuService;
import cn.wolfcode.web.modules.sys.service.SysRoleInfoService;
import cn.wolfcode.web.commons.config.SysConstant;
import cn.wolfcode.web.commons.enums.DeleteType;
import cn.wolfcode.web.modules.sys.entity.SysMenu;
import cn.wolfcode.web.modules.sys.entity.SysRoleInfo;
import cn.wolfcode.web.commons.builder.MenuTreeSelectBuilder;
import cn.wolfcode.web.commons.builder.SysMenuTreeSelect;
import link.ahsj.core.components.layui.treeselect.Hierarchy;
import link.ahsj.core.components.layui.treeselect.MultipleTreeSelect;
import link.ahsj.core.components.layui.treeselect.MultipleTreeSelectBuilder;
import link.ahsj.core.components.layui.xtree.Xtree3;
import link.ahsj.core.components.layui.xtree.Xtree3Builder;
import link.ahsj.core.components.layui.xtree.Xtree3Hierarchy;
import link.ahsj.core.entitys.ApiModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 下拉框,选择框,专用控制器
 *
 * @author Eastern unbeaten
 * @email chenshiyun2011@163.com
 * @date 2019-08-04 00:50
 */
@RestController
@RequestMapping("/select")
public class SelectController {

    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysRoleInfoService sysRoleInfoService;

    /**
     * 菜单下拉框--除掉已删除的,提供给选项使用
     *
     * @return
     */
    @GetMapping("/menuTree")
    @PreAuthorize("hasAuthority('select:menuTree')")
    public ResponseEntity<ApiModel> selectMenus() {
        List<SysMenu> list = sysMenuService.list(sysMenuService.lambdaQuery().eq(SysMenu::getDisable, DeleteType.NORMAL).orderByDesc(SysMenu::getSort).getWrapper());
        //转变为xtree3所需的json格式
        List<Xtree3> result = new Xtree3Builder<SysMenu>().parse(list,
                t -> new Xtree3Hierarchy(
                        t.getMenuCode(),
                        t.getParentMenuCode(),
                        t.getMenuName(),
                        false,
                        false,
                        t.getMenuCode()
                ), hierarchy -> StringUtils.isBlank(hierarchy.getPid()));

        return ResponseEntity.ok(ApiModel.data(result));
    }

    /**
     * 菜单下拉框--除掉已删除的,提供给选项使用
     *
     * @return
     */
    @GetMapping(value = "/noButton/menuTree")
    @PreAuthorize("hasAuthority('select:noButton:menuTree')")
    @ResponseBody
    public ResponseEntity selectNoButtonMenus() {
        List list = sysMenuService.list(sysMenuService.lambdaQuery().eq(SysMenu::getDisable, DeleteType.NORMAL).notIn(SysMenu::getMenuType, 4).getWrapper());
        List<SysMenuTreeSelect> sysMenuTreeSelects = new MenuTreeSelectBuilder(list).build();
        return ResponseEntity.ok(sysMenuTreeSelects);

    }

    @GetMapping("/roleTree")
//    @PreAuthorize("hasAuthority('select:menuTree')")
    public ResponseEntity<ApiModel> selectRoles() {
        List<SysRoleInfo> list = sysRoleInfoService.list(sysRoleInfoService.lambdaQuery().eq(SysRoleInfo::getDisable, DeleteType.NORMAL).orderByDesc(SysRoleInfo::getBusinessLevel).getWrapper());
        List<MultipleTreeSelect> result = new MultipleTreeSelectBuilder<SysRoleInfo>().parse(list,
                t -> new Hierarchy(t.getRoleId(), t.getPid(), t.getRoleName(), t.getRoleId()),
                hierarchy -> SysConstant.ADMIN_RILE_PID.equals(hierarchy.getPid()));
        return ResponseEntity.ok(ApiModel.data(result));

    }

}
