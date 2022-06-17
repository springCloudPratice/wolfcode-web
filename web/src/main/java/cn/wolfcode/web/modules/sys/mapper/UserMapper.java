package cn.wolfcode.web.modules.sys.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cn.wolfcode.web.modules.sys.form.SysUserForm;
import cn.wolfcode.web.modules.sys.vo.SysUserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2019/4/10 11:40
 * @email chenshiyun2011@163.com
 */
public interface UserMapper {

    /**
     * 查询用户列表
     *
     * @param sysUserForm
     * @param page
     * @return
     */
    List<SysUserVo> queryList(@Param("sysUserForm") SysUserForm sysUserForm , @Param("page")IPage page);

}
