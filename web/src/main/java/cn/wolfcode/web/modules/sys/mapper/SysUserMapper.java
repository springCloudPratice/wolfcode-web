package cn.wolfcode.web.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.wolfcode.web.modules.sys.entity.SysUser;
import org.apache.ibatis.annotations.Param;

/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2019/4/1 16:22
 * @email chenshiyun2011@163.com
 */
public interface SysUserMapper extends BaseMapper<SysUser> {



    /**
     * 查询用户相关信息
     *
     * @param id
     * @return
     */
    SysUser queryUserInfo(@Param("userId") String id);
}
