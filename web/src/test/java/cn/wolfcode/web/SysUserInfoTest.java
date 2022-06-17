package cn.wolfcode.web;

import cn.wolfcode.WebApplication;
import cn.wolfcode.web.modules.sys.service.SysUserService;
import cn.wolfcode.web.modules.sys.service.UserRoleMenuService;
import cn.wolfcode.web.modules.sys.entity.SysUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2019/4/2 9:34
 * @email chenshiyun2011@163.com
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebApplication.class, SysUserInfoTest.class})
public class SysUserInfoTest {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private UserRoleMenuService roleInfoService;

    @Test
    public void testSave() {
        List<SysUser> list = sysUserService.list();
        list.forEach(System.out::println);
    }

    @Test
    public void testSelect() {
        List list = sysUserService.list(sysUserService.lambdaQuery().like(true, SysUser::getUsername, "admin").getWrapper());
        list.forEach(System.out::println);
    }
}
