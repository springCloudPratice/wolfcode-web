package cn.wolfcode.web.modules.sys.service.impl;

import cn.wolfcode.web.modules.sys.mapper.SysRoleInfoMapper;
import cn.wolfcode.web.modules.sys.service.SysRoleInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.wolfcode.web.modules.sys.entity.SysRoleInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 系统角色表 服务实现类
 * </p>
 *
 * @author Aimer
 * @since 2019-04-02
 */
@Service
public class SysRoleInfoServiceImpl extends ServiceImpl<SysRoleInfoMapper, SysRoleInfo> implements SysRoleInfoService {

    @Override
    public List<SysRoleInfo> queryUserRole(String userId) {
        return baseMapper.queryUserRole(userId);
    }

}
