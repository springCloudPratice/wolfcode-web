package cn.wolfcode.web.modules.linkman.controller;

import cn.wolfcode.web.commons.entity.LayuiPage;
import cn.wolfcode.web.commons.utils.LayuiTools;
import cn.wolfcode.web.commons.utils.SystemCheckUtils;
import cn.wolfcode.web.modules.BaseController;
import cn.wolfcode.web.modules.custinfo.entity.TbCustomer;
import cn.wolfcode.web.modules.custinfo.service.ITbCustomerService;
import cn.wolfcode.web.modules.log.LogModules;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


import cn.wolfcode.web.modules.linkman.entity.TbCustLinkman;
import cn.wolfcode.web.modules.linkman.service.ITbCustLinkmanService;

import link.ahsj.core.annotations.AddGroup;
import link.ahsj.core.annotations.SameUrlData;
import link.ahsj.core.annotations.SysLog;
import link.ahsj.core.annotations.UpdateGroup;
import link.ahsj.core.entitys.ApiModel;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author 姚鸿伟
 * @since 2022-06-21
 */
@Controller
@RequestMapping("linkman")
@Log4j2
public class TbCustLinkmanController extends BaseController {

    @Autowired
    private ITbCustLinkmanService entityService;
    @Autowired
    private ITbCustomerService customerService;

    private static final String LogModule = "TbCustLinkman";

    @GetMapping("/list.html")
    public ModelAndView list(ModelAndView mv) {
        List<TbCustomer> list = customerService.list();
        mv.addObject("custs",list);
        mv.setViewName("user/linkman/list");
        return mv;
    }

    @RequestMapping("/add.html")
    @PreAuthorize("hasAuthority('user:linkman:add')")
    public ModelAndView toAdd(ModelAndView mv) {
        List<TbCustomer> list = customerService.list();
        mv.addObject("custs",list);
        mv.setViewName("user/linkman/add");
        return mv;
    }

    @GetMapping("/{id}.html")
    @PreAuthorize("hasAuthority('user:linkman:update')")
    public ModelAndView toUpdate(@PathVariable("id") String id, ModelAndView mv) {
        List<TbCustomer> list = customerService.list();
        mv.addObject("custs",list);
        mv.setViewName("user/linkman/update");
        mv.addObject("obj", entityService.getById(id));
        mv.addObject("id", id);
        return mv;
    }

    @RequestMapping("list")
    @PreAuthorize("hasAuthority('user:linkman:list')")
    public ResponseEntity page(LayuiPage layuiPage,String parameterName,String custId) {
        log.debug("客户信息参数接收:"+parameterName+"      客户id："+custId);
        SystemCheckUtils.getInstance().checkMaxPage(layuiPage);
        IPage<TbCustLinkman> page = new Page<>(layuiPage.getPage(), layuiPage.getLimit());
        IPage<TbCustLinkman> page1 = entityService.lambdaQuery()
                .eq(StringUtils.isNotBlank(custId),TbCustLinkman::getCustId,custId)
                .like(StringUtils.isNotBlank(parameterName),TbCustLinkman::getLinkman, parameterName)
                .or()
                .like(StringUtils.isNotBlank(parameterName),TbCustLinkman::getPhone, parameterName)
                .page(page);
        return ResponseEntity.ok(LayuiTools.toLayuiTableModel(page1));
    }

    @SameUrlData
    @PostMapping("save")
    @SysLog(value = LogModules.SAVE, module =LogModule)
    @PreAuthorize("hasAuthority('user:linkman:add')")
    public ResponseEntity<ApiModel> save(@Validated({AddGroup.class}) @RequestBody TbCustLinkman entity) {
        entityService.save(entity);
        return ResponseEntity.ok(ApiModel.ok());
    }

    @SameUrlData
    @SysLog(value = LogModules.UPDATE, module = LogModule)
    @PutMapping("update")
    @PreAuthorize("hasAuthority('user:linkman:update')")
    public ResponseEntity<ApiModel> update(@Validated({UpdateGroup.class}) @RequestBody TbCustLinkman entity) {
        entityService.updateById(entity);
        return ResponseEntity.ok(ApiModel.ok());
    }

    @SysLog(value = LogModules.DELETE, module = LogModule)
    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAuthority('user:linkman:delete')")
    public ResponseEntity<ApiModel> delete(@PathVariable("id") String id) {
        entityService.removeById(id);
        return ResponseEntity.ok(ApiModel.ok());
    }

}
