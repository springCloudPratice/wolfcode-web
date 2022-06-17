package cn.wolfcode.web.modules.test.controller;

import cn.wolfcode.web.modules.report.entity.Demo;
import cn.wolfcode.web.modules.report.service.DemoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.wolfcode.web.modules.log.LogModules;
import cn.wolfcode.web.commons.entity.LayuiPage;
import cn.wolfcode.web.commons.utils.LayuiTools;
import cn.wolfcode.web.commons.utils.SystemCheckUtils;
import cn.wolfcode.web.modules.BaseController;
import link.ahsj.core.annotations.AddGroup;
import link.ahsj.core.annotations.SameUrlData;
import link.ahsj.core.annotations.SysLog;
import link.ahsj.core.annotations.UpdateGroup;
import link.ahsj.core.entitys.ApiModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author Eastern unbeaten
 * @Email chenshiyun2011@163.com
 * @Date 2020/2/12 3:45 下午
 */
@Controller
@RequestMapping("/demo")
public class DemoController extends BaseController {

    @Autowired
    private DemoService demoService;

    private static final String LogModule = "demo";



    @GetMapping("/list.html")
    public String list() {
        return "test/demo/demoList";
    }

    @RequestMapping("/add.html")
    @PreAuthorize("hasAuthority('demo:add')")
    public ModelAndView toAdd(ModelAndView mv) {
        mv.setViewName("test/demo/demoAdd");
        return mv;
    }

    @GetMapping("/{id}.html")
    @PreAuthorize("hasAuthority('demo:update')")
    public ModelAndView toUpdate(@PathVariable("id") String id, ModelAndView mv) {
        mv.setViewName("test/demo/demoUpdate");
        mv.addObject("obj", demoService.getById(id));
        mv.addObject("id", id);
        return mv;
    }


    @RequestMapping("demoList")
    @PreAuthorize("hasAuthority('demo:demoList')")
    public ResponseEntity page(LayuiPage layuiPage) {
        SystemCheckUtils.getInstance().checkMaxPage(layuiPage);
        IPage page = new Page<>(layuiPage.getPage(), layuiPage.getLimit());
        return ResponseEntity.ok(LayuiTools.toLayuiTableModel(demoService.page(page)));
    }


    @SameUrlData
    @PostMapping("save")
    @SysLog(value = LogModules.SAVE, module = LogModule)
    @PreAuthorize("hasAuthority('demo:add')")
    public ResponseEntity<ApiModel> save(@Validated({AddGroup.class}) @RequestBody Demo demo) {
        demoService.save(demo);
        return ResponseEntity.ok(ApiModel.ok());
    }

    @SameUrlData
    @SysLog(value = LogModules.UPDATE, module = LogModule)
    @PutMapping("update")
    @PreAuthorize("hasAuthority('demo:update')")
    public ResponseEntity<ApiModel> update(@Validated({UpdateGroup.class}) @RequestBody Demo demo) {
        demoService.updateById(demo);
        return ResponseEntity.ok(ApiModel.ok());
    }

    @SysLog(value = LogModules.DELETE, module = LogModule)
    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAuthority('demo:delete')")
    public ResponseEntity<ApiModel> delete(@PathVariable("id") String id) {
        demoService.removeById(id);
        return ResponseEntity.ok(ApiModel.ok());
    }

}
