package cn.wolfcode.web.modules.sys.service.impl;

import cn.wolfcode.web.modules.sys.mapper.SysUserMapper;
import cn.wolfcode.web.modules.sys.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.wolfcode.web.modules.sys.entity.SysUser;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2019/4/1 16:22
 * @email chenshiyun2011@163.com
 */
@Service
@Log4j2
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public SysUser queryUserInfo(String id) {
        return baseMapper.queryUserInfo(id);
    }
}
