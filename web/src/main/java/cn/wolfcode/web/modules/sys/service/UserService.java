package cn.wolfcode.web.modules.sys.service;

import cn.wolfcode.web.modules.sys.entity.SysUserVerifyEntity;
import com.baomidou.mybatisplus.core.metadata.IPage;
import cn.wolfcode.web.modules.sys.form.PasswordForm;
import cn.wolfcode.web.modules.sys.entity.SysUser;
import cn.wolfcode.web.modules.sys.form.SysUserForm;
import cn.wolfcode.web.modules.sys.vo.SysUserVo;

import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.List;

/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2019/4/1 16:22
 * @email chenshiyun2011@163.com
 */
public interface UserService {

    /**
     * 保存用户相关信息
     *
     * @param currentUser
     * @param userInfo
     */
    void saveUserInfo(SysUser currentUser, SysUser userInfo);

    /**
     * 根据用户ID删除用户(修改为删除状态)
     *
     * @param currentUser
     * @param userIds
     */
    void deleteUser(SysUser currentUser, List<String> userIds);

    /**
     * 更新用户相关信息
     *
     * @param sysUser
     */
    void updateUserInfo(SysUser sysUser);

    /**
     * 重置用户密码
     *
     * @param currentUser
     * @param userId
     */
    void resetPassword(SysUser currentUser, String userId);

    /***
     * 恢复用户
     * @param currentUser
     * @param userId
     */
    void resetUser(SysUser currentUser, String userId);

    /**
     * 更新用户密码
     *
     * @param passwordForm
     * @param session
     * @throws Exception
     */
    void updatePassword(PasswordForm passwordForm, HttpSession session) throws Exception;


    /**
     * 查询用户相关信息
     *
     * @param id
     * @return
     */
    SysUser queryUserInfo(String id);

    /**
     * 查询用户列表
     *
     * @param sysUserForm
     * @param page
     * @return
     */
    List<SysUserVo> queryList(SysUserForm sysUserForm, IPage page);


    /**
     * 导入用户
     *
     * @param currentUser
     * @param users
     * @return
     */
    List<String> importUser(SysUser currentUser, List<SysUserVerifyEntity> users);

    /**
     * 根据ID获取用户
     *
     * @param id
     * @return
     */
    SysUser getById(Serializable id);
}
