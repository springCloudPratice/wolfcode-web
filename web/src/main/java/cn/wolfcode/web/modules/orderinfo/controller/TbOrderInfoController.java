package cn.wolfcode.web.modules.orderinfo.controller;

import cn.wolfcode.web.commons.entity.LayuiPage;
import cn.wolfcode.web.commons.utils.LayuiTools;
import cn.wolfcode.web.commons.utils.SystemCheckUtils;
import cn.wolfcode.web.modules.BaseController;
import cn.wolfcode.web.modules.custinfo.entity.TbCustomer;
import cn.wolfcode.web.modules.custinfo.service.ITbCustomerService;
import cn.wolfcode.web.modules.linkman.entity.TbCustLinkman;
import cn.wolfcode.web.modules.linkman.service.ITbCustLinkmanService;
import cn.wolfcode.web.modules.log.LogModules;
import cn.wolfcode.web.modules.sys.entity.SysUser;
import cn.wolfcode.web.modules.sys.form.LoginForm;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.wolfcode.web.modules.orderinfo.entity.TbOrderInfo;
import cn.wolfcode.web.modules.orderinfo.service.ITbOrderInfoService;

import link.ahsj.core.annotations.AddGroup;
import link.ahsj.core.annotations.SameUrlData;
import link.ahsj.core.annotations.SysLog;
import link.ahsj.core.annotations.UpdateGroup;
import link.ahsj.core.entitys.ApiModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 谭浩
 * @since 2022-06-22
 */
@Controller
@RequestMapping("orderinfo")
public class TbOrderInfoController extends BaseController {

    @Autowired
    private ITbOrderInfoService entityService;
    @Autowired
    private ITbCustomerService customerService;
    @Autowired
    private ITbCustLinkmanService custLinkmanService;

    private static final String LogModule = "TbOrderInfo";

    @GetMapping("/list.html")
    public ModelAndView list(ModelAndView mv) {
        //查出我们的所有企业客户
        List<TbCustomer> list = customerService.list();
        //返回到我们的页面上面去
        mv.addObject("custs", list);
        mv.setViewName("user/orderinfo/list");
        return mv;
    }

    @RequestMapping("/add.html")
    @PreAuthorize("hasAuthority('user:orderinfo:add')")
    public ModelAndView toAdd(ModelAndView mv) {
        //查出我们的所有企业客户
        List<TbCustomer> list = customerService.list();
        //返回到我们的页面上面去
        mv.addObject("custs", list);

        mv.setViewName("user/orderinfo/add");
        return mv;
    }

    @GetMapping("/{id}.html")
    @PreAuthorize("hasAuthority('user:orderinfo:update')")
    public ModelAndView toUpdate(@PathVariable("id") String id, ModelAndView mv) {
        //查出我们的所有企业客户
        List<TbCustomer> list = customerService.list();
        //返回到我们的页面上面去
        mv.addObject("custs", list);
        mv.setViewName("user/orderinfo/update");
        mv.addObject("obj", entityService.getById(id));
        mv.addObject("id", id);
        return mv;
    }

    @RequestMapping("list")
    @PreAuthorize("hasAuthority('user:orderinfo:list')")
    public ResponseEntity page(LayuiPage layuiPage,String custName,String parameterName,String receiveTime,String receiveTime1) {

        SystemCheckUtils.getInstance().checkMaxPage(layuiPage);
        IPage page = new Page<>(layuiPage.getPage(), layuiPage.getLimit());
        IPage<TbOrderInfo> page1 = entityService
                .lambdaQuery()
                .eq(StringUtils.isNotBlank(custName),TbOrderInfo::getCustId,custName)
                .and(StringUtils.isNotBlank(parameterName),q->
                        q.like(TbOrderInfo::getProdName,parameterName)
                                .or()
                                .like(TbOrderInfo::getLinkPhone,parameterName)
                )
                .ge(StringUtils.isNotBlank(receiveTime),TbOrderInfo::getInputTime,receiveTime)
                .le(StringUtils.isNotBlank(receiveTime1),TbOrderInfo::getInputTime,receiveTime1)
                .page(page);
        System.out.println("第一时间" + receiveTime);
        System.out.println("第2时间" + receiveTime1);

        return ResponseEntity.ok(LayuiTools.toLayuiTableModel(page1));
    }

    @SameUrlData
    @PostMapping("save")
    @SysLog(value = LogModules.SAVE, module = LogModule)
    @PreAuthorize("hasAuthority('user:orderinfo:add')")
    public ResponseEntity<ApiModel> save(@Validated({AddGroup.class}) @RequestBody TbOrderInfo entity,HttpServletRequest request,String custId ) {
        entity.setInputTime(LocalDateTime.now());
        SysUser loginUser = (SysUser) request.getSession().getAttribute(LoginForm.LOGIN_USER_KEY);
        entity.setInputUserId(loginUser.getUserId());
        entityService.save(entity);
        return ResponseEntity.ok(ApiModel.ok());
    }

    @SameUrlData
    @SysLog(value = LogModules.UPDATE, module = LogModule)
    @PutMapping("update")
    @PreAuthorize("hasAuthority('user:orderinfo:update')")
    public ResponseEntity<ApiModel> update(@Validated({UpdateGroup.class}) @RequestBody TbOrderInfo entity) {
        entityService.updateById(entity);
        return ResponseEntity.ok(ApiModel.ok());
    }

    @SysLog(value = LogModules.DELETE, module = LogModule)
    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAuthority('user:orderinfo:delete')")
    public ResponseEntity<ApiModel> delete(@PathVariable("id") String id) {
        entityService.removeById(id);
        return ResponseEntity.ok(ApiModel.ok());
    }



}
