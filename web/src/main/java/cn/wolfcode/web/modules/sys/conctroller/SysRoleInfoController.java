package cn.wolfcode.web.modules.sys.conctroller;


import cn.wolfcode.web.modules.sys.service.SysMenuService;
import cn.wolfcode.web.modules.sys.service.SysRoleInfoService;
import cn.wolfcode.web.modules.sys.service.UserRoleMenuService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.wolfcode.web.commons.enums.DeleteType;
import cn.wolfcode.web.modules.log.LogModules;
import cn.wolfcode.web.modules.sys.entity.SysMenu;
import cn.wolfcode.web.modules.sys.entity.SysRoleInfo;
import cn.wolfcode.web.commons.entity.CacheKeyConstant;
import cn.wolfcode.web.commons.entity.LayuiPage;
import cn.wolfcode.web.commons.utils.LayuiTools;
import cn.wolfcode.web.commons.utils.SystemCheckUtils;
import cn.wolfcode.web.modules.sys.form.RoleMenuForm;
import link.ahsj.core.annotations.AddGroup;
import link.ahsj.core.annotations.SysLog;
import link.ahsj.core.annotations.UpdateGroup;
import link.ahsj.core.components.layui.xtree.Xtree3;
import link.ahsj.core.components.layui.xtree.Xtree3Builder;
import link.ahsj.core.components.layui.xtree.Xtree3Hierarchy;
import link.ahsj.core.entitys.ApiModel;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 系统角色表 前端控制器
 * </p>
 *
 * @author Aimer
 * @since 2019-04-02
 */
@Controller
@RequestMapping("/role")
public class SysRoleInfoController {

    @Autowired
    private SysRoleInfoService sysRoleInfoService;
    @Autowired
    private UserRoleMenuService roleInfoService;
    @Autowired
    private SysMenuService sysMenuService;

    @GetMapping("/list.html")
    public String list() {
        return "sys/role/roleList";
    }

    @RequestMapping("/add.html")
    @PreAuthorize("hasAuthority('role:add')")
    public ModelAndView toAdd(ModelAndView mv) {
        mv.setViewName("sys/role/roleAdd");
        return mv;
    }

    @GetMapping("/{id}.html")
    @PreAuthorize("hasAuthority('role:update')")
    public ModelAndView toUpdate(@PathVariable("id") String id, ModelAndView mv) {
        SysRoleInfo sysRoleAndMenu = sysRoleInfoService.getById(id);
        mv.setViewName("sys/role/roleUpdate");
        mv.addObject("sysRoleAndMenu", sysRoleAndMenu);
        mv.addObject("id", id);
        return mv;
    }


    @RequestMapping("roleList")
    @PreAuthorize("hasAuthority('role:roleList')")
    public ResponseEntity page(LayuiPage layuiPage, SysRoleInfo sysRoleInfo) {
        SystemCheckUtils.getInstance().checkMaxPage(layuiPage);
        IPage page = new Page<>(layuiPage.getPage(), layuiPage.getLimit());
        page.setRecords(sysRoleInfoService.list(sysRoleInfoService.lambdaQuery().like(StringUtils.isNotBlank(sysRoleInfo.getRoleName()), SysRoleInfo::getRoleName, sysRoleInfo.getRoleName()).getWrapper()));
        return ResponseEntity.ok(LayuiTools.toLayuiTableModel(page));
    }

    /**
     * 查询角色已赋予的权限
     *
     * @return
     */
    @RequestMapping(value = "/userRoleMenus", method = {RequestMethod.GET, RequestMethod.POST})
    @PreAuthorize("hasAuthority('role:userRoleMenus')")
    @ResponseBody
    public ResponseEntity<ApiModel> selectUserRoleMenus(@RequestParam("roleId") String roleId) {
        List<SysMenu> userMenus = sysMenuService.list(sysMenuService.lambdaQuery().eq(SysMenu::getDisable, DeleteType.NORMAL).orderByDesc(SysMenu::getSort).getWrapper());
        List<SysMenu> roleMenus = sysMenuService.queryRoleMenus(roleId);

        //转变为xtree3所需的json格式
        List<Xtree3> result = new Xtree3Builder<SysMenu>().parse(userMenus, t -> {
            boolean checked = false;
            if (CollectionUtils.isNotEmpty(roleMenus)) {
                //勾选已赋予的权限
                checked = roleMenus.stream().anyMatch(m -> m.getMenuCode().equals(t.getMenuCode()));
            }
            return new Xtree3Hierarchy(t.getMenuCode(), t.getParentMenuCode(), t.getMenuName(), checked, false, t.getMenuCode());
        }, hierarchy -> StringUtils.isBlank(hierarchy.getPid()));

        return ResponseEntity.ok(ApiModel.data(result));
    }

    @PostMapping("save")
    @SysLog(value = LogModules.SAVE, module = LogModules.ROLE)
    @PreAuthorize("hasAuthority('role:add')")
    public ResponseEntity<ApiModel> save(@Validated({AddGroup.class}) @RequestBody RoleMenuForm roleMenuForm) {
        roleInfoService.insertRoleAndMenu(roleMenuForm);
        return ResponseEntity.ok(ApiModel.ok());
    }


    @SysLog(value = LogModules.UPDATE, module = LogModules.ROLE)
    @PutMapping("update")
    @Caching(evict = {
            @CacheEvict(value = CacheKeyConstant.ROLE_CACHE_KEY, key = "'role:roleList'", beforeInvocation = true),
            @CacheEvict(value = CacheKeyConstant.MENU_CACHE_KEY, beforeInvocation = true, allEntries = true)}
    )
    @PreAuthorize("hasAuthority('role:update')")
    public ResponseEntity<ApiModel> update(@Validated({UpdateGroup.class}) @RequestBody RoleMenuForm roleMenuForm) {
        roleInfoService.updateRoleAndMenu(roleMenuForm);
        return ResponseEntity.ok(ApiModel.ok());
    }

    @SysLog(value = LogModules.DELETE, module = LogModules.ROLE)
    @DeleteMapping("delete/{id}")
    @Caching(evict = {
            @CacheEvict(value = CacheKeyConstant.ROLE_CACHE_KEY, key = "'role:roleList'", beforeInvocation = true),
            @CacheEvict(value = CacheKeyConstant.MENU_CACHE_KEY, beforeInvocation = true, allEntries = true)}
    )
    @PreAuthorize("hasAuthority('role:delete')")
    public ResponseEntity<ApiModel> delete(@PathVariable("id") String id) {
        SysRoleInfo sysRoleInfo = sysRoleInfoService.getById(id);
        sysRoleInfo.setDisable(DeleteType.DISABLE);
        sysRoleInfoService.updateById(sysRoleInfo);
        return ResponseEntity.ok(ApiModel.ok());
    }

    @GetMapping("recovery/{id}")
    @SysLog(value = LogModules.RECOVER, module = LogModules.ROLE)
    public ResponseEntity<ApiModel> recovery(@PathVariable("id") String id) {
        SysRoleInfo sysRoleInfo = sysRoleInfoService.getById(id);
        if (Objects.nonNull(sysRoleInfo)) {
            sysRoleInfo.setDisable(DeleteType.NORMAL);
            sysRoleInfoService.updateById(sysRoleInfo);
        }
        return ResponseEntity.ok(ApiModel.ok());
    }
}
