package cn.wolfcode.web.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.wolfcode.web.modules.sys.entity.SysUser;

/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2019/4/1 16:22
 * @email chenshiyun2011@163.com
 */
public interface SysUserService extends IService<SysUser> {


    /**
     * 查询用户相关信息
     *
     * @param id
     * @return
     */
    SysUser queryUserInfo(String id);

}
