package cn.wolfcode.web.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.wolfcode.web.modules.sys.entity.SysRoleInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统角色表 服务类
 * </p>
 *
 * @author Aimer
 * @since 2019-04-02
 */
public interface SysRoleInfoService extends IService<SysRoleInfo> {

    /**
     * 查询用户角色
     *
     * @param userId
     * @return
     */
    List<SysRoleInfo> queryUserRole(@Param("userId") String userId);

}
