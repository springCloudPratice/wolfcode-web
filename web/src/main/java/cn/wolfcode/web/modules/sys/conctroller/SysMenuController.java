package cn.wolfcode.web.modules.sys.conctroller;

import cn.wolfcode.web.modules.sys.service.MenuService;
import cn.wolfcode.web.modules.sys.service.SysMenuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import cn.wolfcode.web.commons.enums.DeleteType;
import cn.wolfcode.web.modules.log.LogModules;
import cn.wolfcode.web.modules.sys.entity.SysMenu;
import cn.wolfcode.web.commons.entity.CacheKeyConstant;
import cn.wolfcode.web.commons.utils.LayuiTools;
import link.ahsj.core.annotations.AddGroup;
import link.ahsj.core.annotations.SysLog;
import link.ahsj.core.annotations.UpdateGroup;
import link.ahsj.core.entitys.ApiModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;

/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2019/4/2 16:55
 * @email chenshiyun2011@163.com
 */
@Controller
@RequestMapping("/menu")
public class SysMenuController {

    @Autowired
    private MenuService menuService;
    @Autowired
    private SysMenuService sysMenuService;

    @GetMapping("/list.html")
    public String list() {
        return "sys/menu/menuList";
    }

    @GetMapping("/add.html")
    @PreAuthorize("hasAuthority('menu:add')")
    public ModelAndView toAdd(ModelAndView mv) {
        mv.setViewName("sys/menu/menuAdd");
        return mv;
    }

    @GetMapping("menuList")
    @PreAuthorize("hasAuthority('menu:menuList')")
    public ResponseEntity page() {
        List<SysMenu> list = sysMenuService.list();
        return ResponseEntity.ok(LayuiTools.toLayuiTreeTableModel(list));
    }


    @SysLog(value = LogModules.SAVE, module = LogModules.MENU)
    @PostMapping("save")
    @CacheEvict(value = CacheKeyConstant.MENU_CACHE_KEY, beforeInvocation = true, allEntries = true)
    @PreAuthorize("hasAuthority('menu:add')")
    public ResponseEntity<ApiModel> save(@Validated({AddGroup.class}) @RequestBody SysMenu sysMenu) {
        sysMenu.setMenuCode(IdWorker.getIdStr());
        sysMenuService.save(sysMenu);
        return ResponseEntity.ok(ApiModel.ok());
    }

    @GetMapping("/{id}.html")
    @PreAuthorize("hasAuthority('menu:update')")
    public ModelAndView toUpdate(@PathVariable("id") String id, ModelAndView mv) {
        SysMenu sysMenu = sysMenuService.getById(id);
        if (StringUtils.isNoneBlank(sysMenu.getParentMenuCode())) {
            String parentId = menuService.queryParentId(sysMenu.getParentMenuCode());
            mv.addObject("parentId", parentId);
        }
        mv.setViewName("sys/menu/menuUpdate");
        mv.addObject("id", id);
        mv.addObject("sysMenu", sysMenu);
        return mv;
    }

    @SysLog(value = LogModules.UPDATE, module = LogModules.MENU)
    @PutMapping("update")
    @CacheEvict(value = CacheKeyConstant.MENU_CACHE_KEY, beforeInvocation = true, allEntries = true)
    @PreAuthorize("hasAuthority('menu:update')")
    public ResponseEntity<ApiModel> update(@Validated({UpdateGroup.class}) @RequestBody SysMenu sysMenu) {
        if (StringUtils.isNoneBlank(sysMenu.getParentMenuCode())) {
            SysMenu sysMenuByParent = sysMenuService.getOne(new QueryWrapper<SysMenu>().eq("MENU_NAME", sysMenu.getParentMenuCode()));
            if (Objects.nonNull(sysMenuByParent)) {
                sysMenu.setParentMenuCode(sysMenuByParent.getMenuCode());
            }
            sysMenuService.updateById(sysMenu);
        } else {
            sysMenuService.updateById(sysMenu);
        }
        return ResponseEntity.ok(ApiModel.ok());
    }

    @SysLog(value = LogModules.DELETE, module = LogModules.MENU)
    @DeleteMapping("delete/{id}")
    @CacheEvict(value = CacheKeyConstant.MENU_CACHE_KEY, beforeInvocation = true, allEntries = true)
    @PreAuthorize("hasAuthority('menu:delete')")
    public ResponseEntity<ApiModel> delete(@PathVariable("id") String id) {
        SysMenu sysMenu = sysMenuService.getById(id);
        if (Objects.nonNull(sysMenu)) {
            sysMenu.setDisable(DeleteType.DISABLE);
            sysMenuService.updateById(sysMenu);
        }
        return ResponseEntity.ok(ApiModel.ok());
    }

    @GetMapping("recovery/{id}")
    @SysLog(value = LogModules.RECOVER, module = LogModules.MENU)
    public ResponseEntity<ApiModel> recovery(@PathVariable("id") String id) {
        SysMenu sysMenu = sysMenuService.getById(id);
        if (Objects.nonNull(sysMenu)) {
            sysMenu.setDisable(DeleteType.NORMAL);
            sysMenuService.updateById(sysMenu);
        }
        return ResponseEntity.ok(ApiModel.ok());
    }
}
