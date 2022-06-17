package cn.wolfcode.web.modules.sys.conctroller;

import cn.wolfcode.web.modules.sys.service.MenuService;
import cn.wolfcode.web.modules.sys.service.SysUserService;
import cn.wolfcode.web.modules.sys.service.UserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.wolfcode.web.modules.report.service.DemoService;
import cn.wolfcode.web.modules.sys.entity.SysUser;
import cn.wolfcode.web.commons.entity.LayuiPage;
import cn.wolfcode.web.commons.utils.SystemCheckUtils;
import cn.wolfcode.web.modules.sys.vo.SysUserVo;
import link.ahsj.core.annotations.SysLog;
import link.ahsj.core.entitys.ApiModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Eastern unbeaten
 * @version 1.0
 * @date 2019/6/11 7:16
 * @mail chenshiyun2011@163.com
 */
@RestController
@RequestMapping("/open")
public class OpenApi {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private DemoService demoService;

    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;


    @SysLog(value = "openApi", module = "查询用户列表")
    @GetMapping("/userList")
    public ResponseEntity<ApiModel> userList() {
        List<SysUser> list = sysUserService.list();
        return ResponseEntity.ok(ApiModel.data(list));
    }


    @SysLog(value = "openApi", module = "查询用户列表")
    @GetMapping("/userList1")
    public ResponseEntity<ApiModel> userList1(LayuiPage layuiPage) {
        SystemCheckUtils.getInstance().checkMaxPage(layuiPage);
        IPage page = new Page<>(layuiPage.getPage(), layuiPage.getLimit());
        List<SysUserVo> sysUsers = userService.queryList(null, page);
        page.setRecords(sysUsers);
        return ResponseEntity.ok(ApiModel.data(sysUsers));
    }

    @GetMapping("/demo")
    public ResponseEntity<ApiModel> demo() {
        return ResponseEntity.ok(ApiModel.data(demoService.list()));
    }


    @GetMapping("/menu")
    public ResponseEntity<ApiModel> menu() {
        return ResponseEntity.ok(ApiModel.data(menuService.selectByUserName("admin")));
    }

}
