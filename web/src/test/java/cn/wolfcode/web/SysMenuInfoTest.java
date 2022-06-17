package cn.wolfcode.web;

import cn.wolfcode.WebApplication;
import cn.wolfcode.web.commons.enums.DeleteType;
import cn.wolfcode.web.modules.sys.mapper.MenuMapper;
import cn.wolfcode.web.modules.sys.entity.SysMenu;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2019/4/2 9:34
 * @email chenshiyun2011@163.com
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebApplication.class, SysMenuInfoTest.class})
public class SysMenuInfoTest {

    @Autowired
    private MenuMapper menuMapper;

    @Test
    public void testSave(){
        SysMenu sysMenu = new SysMenu();
        sysMenu.setMenuName("地图");
        sysMenu.setMenuCode("map");
        sysMenu.setMenuIcon("");
        sysMenu.setMenuType(2);
        sysMenu.setSort(31);
        sysMenu.setMenuCode("echarts");
        sysMenu.setMenuUrl("echarts/map.ftl");
        sysMenu.setDisable(DeleteType.NORMAL);
        menuMapper.insert(sysMenu);
    }

}
