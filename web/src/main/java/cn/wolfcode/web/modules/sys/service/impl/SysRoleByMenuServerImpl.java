package cn.wolfcode.web.modules.sys.service.impl;

import cn.wolfcode.web.modules.sys.mapper.SysRoleByMenuMapper;
import cn.wolfcode.web.modules.sys.service.SysRoleByMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.wolfcode.web.modules.sys.entity.SysRoleByMenu;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2019/4/10 13:37
 * @email chenshiyun2011@163.com
 */
@Service
@Log4j2
public class SysRoleByMenuServerImpl extends ServiceImpl<SysRoleByMenuMapper, SysRoleByMenu> implements SysRoleByMenuService {

    @Autowired
    private SysRoleByMenuMapper sysRoleByMenuMapper;

    @Override
    public void deleteRoleAndMenu(String roleId) {
        sysRoleByMenuMapper.deleteRoleAndMenu(roleId);
    }

    @Override
    public void deleteMenuAndRole(String menuId) {
        sysRoleByMenuMapper.deleteMenuAndRole(menuId);
    }
}
