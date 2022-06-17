package cn.wolfcode.web.modules.sys.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.wolfcode.web.modules.sys.service.SysRoleByMenuService;
import cn.wolfcode.web.modules.sys.service.SysRoleInfoService;
import cn.wolfcode.web.modules.sys.service.UserRoleMenuService;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import cn.wolfcode.web.commons.config.SysConstant;
import cn.wolfcode.web.commons.enums.DeleteType;
import cn.wolfcode.web.modules.sys.entity.SysRoleByMenu;
import cn.wolfcode.web.modules.sys.entity.SysRoleInfo;
import cn.wolfcode.web.commons.entity.CacheKeyConstant;
import cn.wolfcode.web.modules.sys.form.RoleMenuForm;
import link.ahsj.core.components.tree.BusinessBuildUtils;
import link.ahsj.core.utils.base.AppAssertUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户角色关系 角色菜单关系 相关操作
 *
 * @author Eastern unbeaten
 * @email chenshiyun2011@163.com
 * @date 2019-08-03 15:58
 */
@Log4j2
@Service
public class UserRoleMenuServiceImpl implements UserRoleMenuService {

    @Autowired
    private SysRoleInfoService sysRoleInfoService;

    @Autowired
    private SysRoleByMenuService sysRoleByMenuService;

    @Cacheable(value = CacheKeyConstant.ROLE_CACHE_KEY, key = "'role:roleList'")
    @Override
    public List<SysRoleInfo> queryEnableRole() {
        return sysRoleInfoService.list(sysRoleInfoService.lambdaQuery().eq(SysRoleInfo::getDisable, DeleteType.NORMAL).getWrapper());
    }


    @CacheEvict(value = CacheKeyConstant.ROLE_CACHE_KEY, key = "'role:roleList'", beforeInvocation = true)
    @Override
    public boolean insertRoleAndMenu(RoleMenuForm roleMenuForm) {

        SysRoleInfo prole = sysRoleInfoService.getOne(sysRoleInfoService.lambdaQuery().eq(SysRoleInfo::getRoleId, roleMenuForm.getPid()).getWrapper());

        //查询出上级角色
        AppAssertUtil.isNull(prole, "所属角色不能空");

        //创建角色id
        String roleid = IdWorker.getIdStr();

        //创建出新的角色层级
        String businessLevel = prole.getBusinessLevel().concat(IdWorker.getIdStr()).concat(SysConstant.BUSI_PREFIX);

        SysRoleInfo sysRoleInfo = new SysRoleInfo(
                roleid,
                roleMenuForm.getPid(),
                prole.getNumberLevel() + 1,
                businessLevel,
                roleMenuForm.getRoleName(),
                roleMenuForm.getDescription(),
                roleMenuForm.getRoleCode(),
                LocalDateTime.now());
        //保存角色
        boolean isok = sysRoleInfoService.save(sysRoleInfo);
        //保存中间表关系
        addRoleMenus(roleMenuForm.getAuthMenuCodes(), roleid);

        return isok;
    }


    @Caching(evict = {
            @CacheEvict(value = CacheKeyConstant.ROLE_CACHE_KEY, key = "'role:roleList'", beforeInvocation = true),
            @CacheEvict(value = CacheKeyConstant.MENU_CACHE_KEY, beforeInvocation = true, allEntries = true)
    })
    @Override
    public boolean updateRoleAndMenu(RoleMenuForm roleMenuForm) {
        //查询出数据库角色信息
        SysRoleInfo sysRole = sysRoleInfoService.getById(roleMenuForm.getRoleId());


        List<SysRoleInfo> roles = new ArrayList<>();
        //上级出现变化情况      还要加上自身不是定级节点,自身就定级节点,就不能更换父节点
        if (!sysRole.getPid().equals(roleMenuForm.getPid())) {

            //查询出上级机构
            SysRoleInfo parentRole = sysRoleInfoService.getOne(sysRoleInfoService.lambdaQuery()
                    .eq(SysRoleInfo::getRoleId, roleMenuForm.getPid())
                    .getWrapper());

            //父业务节点
            String parentRoleBusiness = parentRole.getBusinessLevel();

            //自身原始业务节点
            String thisRoleBusiness = sysRole.getBusinessLevel();

            if (StringUtils.isEmpty(parentRoleBusiness)) {
                //异常
            }
            if (StringUtils.isEmpty(thisRoleBusiness)) {
                //异常
            }
            //判断是否是自己的子节点
            if (parentRoleBusiness.length() > thisRoleBusiness.length() && thisRoleBusiness.equals(parentRoleBusiness.substring(0, thisRoleBusiness.length()))) {
                AppAssertUtil.err("不能用自己的子节点作为父节点");
            } else {
                //查询所有子类节点
                List<SysRoleInfo> list = sysRoleInfoService.list(sysRoleInfoService.lambdaQuery()
                        .likeRight(SysRoleInfo::getBusinessLevel, sysRole.getBusinessLevel())
                        .getWrapper());

                //组出新的自身节点  =     父亲节点  + 自己的ID + ":"
                String newThisBusiness = parentRoleBusiness.concat(sysRole.getRoleId()).concat(SysConstant.BUSI_PREFIX);
                //设置新自身原始节点
                sysRole.setBusinessLevel(newThisBusiness);
                //自身角色等级更换
                sysRole.setNumberLevel(parentRole.getNumberLevel() + 1);


                log.debug("原始节点:" + sysRole.getBusinessLevel());
                log.debug("新的自身节点:" + newThisBusiness);

                //子类的节点替换 重组出新的子节点
                if (CollectionUtil.isNotEmpty(list)) {
                    BusinessBuildUtils.rearrangement(sysRole, list);
                    //所有改好的节点统一放集合
                    roles.addAll(list);
                }
            }


        }
        //把自己丢进去
        //设置角色信息
        sysRole.setDescription(roleMenuForm.getDescription());
        sysRole.setRoleName(roleMenuForm.getRoleName());
        sysRole.setRoleCode(roleMenuForm.getRoleCode());
        sysRole.setCreateTime(LocalDateTime.now());
        sysRole.setPid(roleMenuForm.getPid());
        roles.add(sysRole);


        //先删除中间表的信息
        sysRoleByMenuService.remove(sysRoleByMenuService.lambdaQuery().eq(StringUtils.isNotEmpty(roleMenuForm.getRoleId()), SysRoleByMenu::getRoleId, roleMenuForm.getRoleId()).getWrapper());
        //保存中间表关系
        addRoleMenus(roleMenuForm.getAuthMenuCodes(), roleMenuForm.getRoleId());

        //更新所有角色
        return sysRoleInfoService.updateBatchById(roles);
    }

    /**
     * 保存角色菜单关系
     *
     * @param authMenuCodes 权限集合
     * @param roleId        角色集合
     */
    private void addRoleMenus(List<String> authMenuCodes, String roleId) {
        if (CollectionUtil.isNotEmpty(authMenuCodes) && StringUtils.isNotBlank(roleId)) {
            List<SysRoleByMenu> sysRoleByMenus = new ArrayList<>(authMenuCodes.size());
            authMenuCodes.forEach((authMenuCode) -> {
                if (StringUtils.isNotBlank(authMenuCode)) {
                    sysRoleByMenus.add(new SysRoleByMenu(roleId, authMenuCode));
                }
            });
            sysRoleByMenuService.saveBatch(sysRoleByMenus);
        }
    }


}
