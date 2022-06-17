package cn.wolfcode.web.modules.sys.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.json.JSONUtil;
import cn.wolfcode.web.modules.sys.entity.SysUserVerifyEntity;
import cn.wolfcode.web.modules.sys.mapper.UserMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.wolfcode.web.commons.config.SysConstant;
import cn.wolfcode.web.commons.enums.DeleteType;
import cn.wolfcode.web.modules.sys.form.LoginForm;
import cn.wolfcode.web.modules.sys.form.PasswordForm;
import cn.wolfcode.web.modules.sys.entity.SysDept;
import cn.wolfcode.web.modules.sys.entity.SysRoleInfo;
import cn.wolfcode.web.modules.sys.entity.SysUser;
import cn.wolfcode.web.modules.sys.entity.SysUserRole;
import cn.wolfcode.web.modules.sys.service.SysDeptService;
import cn.wolfcode.web.modules.sys.service.SysRoleInfoService;
import cn.wolfcode.web.modules.sys.service.SysUserRoleServer;
import cn.wolfcode.web.modules.sys.service.SysUserService;
import cn.wolfcode.web.commons.components.nebula.PasswordEncryptor;
import cn.wolfcode.web.commons.entity.CacheKeyConstant;
import cn.wolfcode.web.commons.entity.CodeMsg;
import cn.wolfcode.web.modules.sys.form.SysUserForm;
import cn.wolfcode.web.modules.sys.service.DeptService;
import cn.wolfcode.web.modules.sys.service.UserService;
import cn.wolfcode.web.modules.sys.vo.SysUserVo;
import link.ahsj.core.entitys.ApiModel;
import link.ahsj.core.exception.AppServerException;
import link.ahsj.core.utils.base.AppAssertUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2019/4/1 16:22
 * @email chenshiyun2011@163.com
 */
@Service
@Log4j2
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleServer sysUserRoleServer;
    @Autowired
    private PasswordEncryptor passwordEncryptor;
    @Autowired
    private DeptService deptService;
    @Autowired
    private SysRoleInfoService roleInfoService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveUserInfo(SysUser currentUser, SysUser sysUser) {
        AppAssertUtil.isNull(currentUser, CodeMsg.SYS_ERR_MSG);
        sysUser.setCreateUserId(currentUser.getUserId());

        int usernameCount = sysUserService.count(
                Wrappers.<SysUser>lambdaQuery()
                        .eq(SysUser::getUsername, sysUser.getUsername())
        );

        int phoneCount = sysUserService.count(
                Wrappers.<SysUser>lambdaQuery()
                        .eq(SysUser::getPhone, sysUser.getPhone())
        );
        AppAssertUtil.isErr(usernameCount > 0, String.format("用户名%s已经在系统注册,请更换其他用户名", sysUser.getUsername()));
        AppAssertUtil.isErr(phoneCount > 0, String.format("手机号%s已经在系统注册,请更换其他手机号码", sysUser.getPhone()));

        sysUser.setPassword(passwordEncryptor.encrypt(PasswordEncryptor.DEFAULT_PASSWORD));
        sysUser.setOldPassword(sysUser.getPassword());
        sysUserService.save(sysUser);

        saveUserRoles(sysUser);
    }

    /**
     * 保存用户角色关系
     *
     * @param sysUser
     */
    private void saveUserRoles(SysUser sysUser) {
        if (Objects.nonNull(sysUser) && CollectionUtil.isNotEmpty(sysUser.getRoleIds())) {
            List<SysUserRole> sysUserRoles = new ArrayList<>(sysUser.getRoleIds().size());
            sysUser.getRoleIds().forEach(id -> sysUserRoles.add(new SysUserRole(sysUser.getUserId(), id)));
            sysUserRoleServer.saveBatch(sysUserRoles);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = CacheKeyConstant.USER_CACHE_KEY, beforeInvocation = true, allEntries = true)
    @Override
    public void deleteUser(SysUser currentUser, List<String> userIds) {
        AppAssertUtil.isNull(currentUser, CodeMsg.SYS_ERR_MSG);
        //非超级管理员不能删除
        AppAssertUtil.isErr(!SysConstant.SUPER_USER.equals(currentUser.getUsername()), CodeMsg.AUTH_FAIL_MSG);

        if (!CollectionUtils.isEmpty(userIds)) {
            //生成出所有需要删除的用户
            List<SysUser> users = new ArrayList<>();
            userIds.forEach(id -> {
                SysUser sysUser = new SysUser();
                sysUser.setUserId(id);
                sysUser.setDeleteTime(LocalDateTime.now());
                sysUser.setLoginStatus(DeleteType.DISABLE);
                sysUser.setDisable(DeleteType.DISABLE);
                users.add(sysUser);
            });
            //调用批量删除
            sysUserService.updateBatchById(users);
        }
    }

    @CacheEvict(value = CacheKeyConstant.USER_CACHE_KEY, beforeInvocation = true, allEntries = true)
    @Override
    public void updateUserInfo(SysUser sysUser) {
        sysUser.setUpdateTime(LocalDateTime.now());
        Integer usernameCount = sysUserService.lambdaQuery()
                .ne(SysUser::getUserId, sysUser.getUserId())
                .eq(SysUser::getUsername, sysUser.getUsername())
                .count();
        Integer phoneCount = sysUserService.lambdaQuery()
                .ne(SysUser::getUserId, sysUser.getUserId())
                .eq(SysUser::getPhone, sysUser.getPhone())
                .count();
        AppAssertUtil.isErr(usernameCount > 0, String.format("用户名%s已经在系统注册,请更换其他用户名", sysUser.getUsername()));
        AppAssertUtil.isErr(phoneCount > 0, String.format("手机号%s已经在系统注册,请更换其他手机号码", sysUser.getPhone()));


        //更新用户
        sysUserService.updateById(sysUser);

        //删除用户旧的角色
        sysUserRoleServer.remove(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getUserId, sysUser.getUserId()));

        //重新分配用户角色
        saveUserRoles(sysUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = CacheKeyConstant.USER_CACHE_KEY, beforeInvocation = true, allEntries = true)
    @Override
    public void resetPassword(SysUser currentUser, String userId) {
        AppAssertUtil.isNull(currentUser, CodeMsg.SYS_ERR_MSG);
        //非超级管理员不能重置密码
        AppAssertUtil.isErr(!SysConstant.SUPER_USER.equals(currentUser.getUsername()), CodeMsg.AUTH_FAIL_MSG);

        SysUser user = sysUserService.lambdaQuery().eq(SysUser::getUserId, userId).eq(SysUser::getDisable, DeleteType.NORMAL).one();
        AppAssertUtil.isNull(user, "用户不存在");
        user.setPassword(passwordEncryptor.encrypt(PasswordEncryptor.DEFAULT_PASSWORD));
        user.setOldPassword(user.getPassword());
        sysUserService.updateById(user);
    }

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = CacheKeyConstant.USER_CACHE_KEY, beforeInvocation = true, allEntries = true)
    @Override
    public void resetUser(SysUser currentUser, String userId) {
        AppAssertUtil.isNull(currentUser, CodeMsg.SYS_ERR_MSG);
        //非超级管理员不能恢复
        AppAssertUtil.isErr(!SysConstant.SUPER_USER.equals(currentUser.getUsername()), CodeMsg.AUTH_FAIL_MSG);
        sysUserService.update(Wrappers.<SysUser>lambdaUpdate()
                .set(SysUser::getDisable, DeleteType.NORMAL)
                .set(SysUser::getLoginStatus, DeleteType.NORMAL)
                .eq(SysUser::getUserId, userId));
    }

    @CacheEvict(value = CacheKeyConstant.USER_CACHE_KEY, beforeInvocation = true, allEntries = true)
    @Override
    public void updatePassword(PasswordForm passwordForm, HttpSession session) throws Exception {
        //获取当前用户
        SysUser currentUser = (SysUser) session.getAttribute(LoginForm.LOGIN_USER_KEY);
        AppAssertUtil.isNull(currentUser, CodeMsg.SYS_ERR_MSG);

        //获取session存储的私钥
        String privateKey = (String) session.getAttribute(LoginForm.KEY_PAIR_STR);
        AppAssertUtil.isNull(privateKey, CodeMsg.SYS_ERR_MSG);
        RSA rsa = new RSA(privateKey, null);

        //解密账号和密码
        String oldPassword = rsa.decryptStr(passwordForm.getOldPassword(), KeyType.PrivateKey);
        String newPassword = rsa.decryptStr(passwordForm.getNewPassword(), KeyType.PrivateKey);
        AppAssertUtil.isErr(oldPassword.equals(newPassword), "不能使用最近的旧密码");

        SysUser user = sysUserService.getById(currentUser.getUserId());
        AppAssertUtil.isNull(user, "用户不存在");
        AppAssertUtil.isErr(!passwordEncryptor.encrypt(oldPassword).equals(user.getPassword()), "原密码输入有误");

        //更新用户密码
        sysUserService.update(
                Wrappers.<SysUser>lambdaUpdate()
                        .eq(SysUser::getUserId, user.getUserId())
                        .set(SysUser::getPassword, passwordEncryptor.encrypt(newPassword))
        );

    }

    @Override
    public SysUser queryUserInfo(String id) {
        return sysUserService.queryUserInfo(id);
    }

    @Override
    public List<SysUserVo> queryList(SysUserForm sysUserForm, IPage page) {

        AppAssertUtil.isNull(sysUserForm, CodeMsg.VALID_ERR_MSG);
        if (StringUtils.isNotEmpty(sysUserForm.getDeptId())) {
            SysDept sysDept = sysDeptService.lambdaQuery()
                    .eq(SysDept::getDeptId, sysUserForm.getDeptId())
                    .one();
            if (Objects.nonNull(sysDept)) {
                sysUserForm.setBusinessLevel(sysDept.getBusinessLevel());
            }
        }

        List<SysUserVo> sysUsers = userMapper.queryList(sysUserForm, page);
        if (!CollectionUtils.isEmpty(sysUsers)) {
            sysUsers.forEach(user -> {
                List<SysRoleInfo> roles = roleInfoService.queryUserRole(user.getUserId());
                if (CollectionUtil.isNotEmpty(roles)) {
                    user.setRoleNames(roles.stream().map(SysRoleInfo::getRoleName).collect(Collectors.joining("|")));
                }
            });
        }
        return sysUsers;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<String> importUser(SysUser currentUser, List<SysUserVerifyEntity> users) {
        List<String> errors = new ArrayList<>(0);
        if (!CollectionUtils.isEmpty(users)) {
            // 去重操作,以防客户导入重复用户名
            ArrayList<SysUserVerifyEntity> entities = users.stream()
                    .collect(collectingAndThen(toCollection(() -> new TreeSet<>(Comparator.comparing(SysUser::getUsername))), ArrayList::new));
            // 查询全部用户
            List<SysUser> sysUsers = sysUserService.list();
            List<String> userNames = sysUsers.stream().map(SysUser::getUsername).collect(Collectors.toList());
            // 查询全部角色
            List<SysRoleInfo> roleInfos = roleInfoService.list();
            List<String> roleNames = roleInfos.stream().map(SysRoleInfo::getRoleName).collect(Collectors.toList());
            // 获取到所有的部门
            List<SysDept> sysDepts = deptService.queryAllDept();
            List<String> deptNames = sysDepts.stream().map(SysDept::getDeptName).collect(Collectors.toList());
            entities.forEach(item -> {
                try {
                    // 验证数据
                    validationData(userNames, roleInfos, roleNames, sysDepts, deptNames, item);

                    // 设置默认密码
                    item.setPassword(passwordEncryptor.encrypt(PasswordEncryptor.DEFAULT_PASSWORD));
                    // 创建人ID
                    item.setCreateUserId(currentUser.getCreateUserId());
                    //执行保存操作
                    sysUserService.save(item);
                    sysUserRoleServer.save(new SysUserRole(item.getUserId(), item.getRoleId()));
                } catch (AppServerException e) {
                    log.error(e);
                    errors.add(String.format("第%s行,数据保存失败,%s", item.getRowNum(), JSONUtil.isJson(e.getMessage()) ?
                            JSONUtil.toBean(e.getMessage(), ApiModel.class).getMessage() : e.getMessage()));
                } catch (Exception e) {
                    log.error(e);
                    errors.add("出现未知异常,请联系管理员.");
                }
            });
        }
        return errors;
    }

    @Cacheable(value = CacheKeyConstant.USER_CACHE_KEY, key = "'user:'.concat(#id)")
    @Override
    public SysUser getById(Serializable id) {
        return sysUserService.getById(id);
    }

    /**
     * 私有的验证导入数据方法
     *
     * @param userNames
     * @param roleInfos
     * @param roleNames
     * @param sysDepts
     * @param deptNames
     * @param item
     */
    private void validationData(List<String> userNames, List<SysRoleInfo> roleInfos, List<String> roleNames, List<SysDept> sysDepts, List<String> deptNames, SysUserVerifyEntity item) {
        if (userNames.contains(item.getUsername())) {
            AppAssertUtil.isErr(true, "用户名已存在");
        }
        if (!roleNames.contains(item.getRoleName())) {
            AppAssertUtil.isErr(true, "输入角色不存在");
        }
        if (!deptNames.contains(item.getDeptName())) {
            AppAssertUtil.isErr(true, "输入机构不存在");
        }

        roleInfos.forEach(role -> {
            if (role.getRoleName().equals(item.getRoleName())) {
                item.setRoleId(role.getRoleId());
            }
        });

        sysDepts.forEach(dept -> {
            if (dept.getDeptName().equals(item.getDeptName())) {
                item.setDeptId(dept.getDeptId());
            }
        });
    }
}
