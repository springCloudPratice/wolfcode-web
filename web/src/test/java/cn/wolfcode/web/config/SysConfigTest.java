package cn.wolfcode.web.config;

import cn.wolfcode.web.commons.enums.DeleteType;
import cn.wolfcode.web.modules.sys.service.SysConfigService;
import cn.wolfcode.web.modules.sys.entity.SysConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Eastern unbeaten
 * @Email chenshiyun2011@163.com 
 * @Date 2020/2/29 3:16 下午
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SysConfigTest {

    @Autowired
    private SysConfigService sysConfigService;

    @Test
    public void valueTest() {
        String config = sysConfigService.getConfig(SysConfig.SYSTEM_XSS_ENABLED);
        System.out.println(config);
    }

    @Test
    public void testSave() {
        List<SysConfig> list = new ArrayList<SysConfig>() {{
            add(new SysConfig("system","-","系统配置","-", DeleteType.NORMAL));
            add(new SysConfig("system1","system","系统配置","systemValue",DeleteType.NORMAL));
            add(new SysConfig("system2","system","系统配置","systemValue",DeleteType.NORMAL));
            add(new SysConfig("system3","system","系统配置","systemValue",DeleteType.NORMAL));
            add(new SysConfig("system4","system","系统配置","systemValue",DeleteType.NORMAL));
            add(new SysConfig("config","-","config配置","-",DeleteType.NORMAL));
            add(new SysConfig("config1","config","系统配置","configValue",DeleteType.NORMAL));
            add(new SysConfig("config2","config","系统配置","configValue",DeleteType.NORMAL));
            add(new SysConfig("config3","config","系统配置","configValue",DeleteType.NORMAL));
        }};
        sysConfigService.saveBatch(list);
    }

    @Test
    public void list() {
        List<SysConfig> list = sysConfigService.list();
        list.forEach(System.out::println);
    }


}
