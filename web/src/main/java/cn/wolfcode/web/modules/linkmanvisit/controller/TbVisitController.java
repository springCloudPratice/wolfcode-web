package cn.wolfcode.web.modules.linkmanvisit.controller;

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
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.wolfcode.web.modules.linkmanvisit.entity.TbVisit;
import cn.wolfcode.web.modules.linkmanvisit.service.ITbVisitService;

import link.ahsj.core.annotations.AddGroup;
import link.ahsj.core.annotations.SameUrlData;
import link.ahsj.core.annotations.SysLog;
import link.ahsj.core.annotations.UpdateGroup;
import link.ahsj.core.entitys.ApiModel;
import link.ahsj.core.exception.AppServerException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author 姚鸿伟
 * @since 2022-06-22
 */
@Controller
@RequestMapping("visit")
public class TbVisitController extends BaseController {

    @Autowired
    private ITbVisitService entityService;
    @Autowired
    private ITbCustLinkmanService linkmanService;
    @Autowired
    private ITbCustomerService customerService;

    private static final String LogModule = "TbVisit";

    @GetMapping("/list.html")
    public ModelAndView list(ModelAndView mv) {
        List<TbCustLinkman> linkmans = linkmanService.list();
        mv.addObject("linkmans",linkmans);
        List<TbCustomer> customers = customerService.list();
        mv.addObject("customers",customers);
        mv.setViewName("linkman/visit/list");
        return mv;
    }

    @RequestMapping("/add.html")
    @PreAuthorize("hasAuthority('linkman:visit:add')")
    public ModelAndView toAdd(ModelAndView mv) {
        List<TbCustLinkman> linkmans = linkmanService.list();
        mv.addObject("linkmans",linkmans);
        List<TbCustomer> customers = customerService.list();
        mv.addObject("customers",customers);
        mv.setViewName("linkman/visit/add");
        return mv;
    }

    @GetMapping("/{id}.html")
    @PreAuthorize("hasAuthority('linkman:visit:update')")
    public ModelAndView toUpdate(@PathVariable("id") String id, ModelAndView mv) {
        List<TbCustLinkman> linkmans = linkmanService.list();
        mv.addObject("linkmans",linkmans);
        List<TbCustomer> customers = customerService.list();
        mv.addObject("customers",customers);
        mv.setViewName("linkman/visit/update");
        mv.addObject("obj", entityService.getById(id));
        mv.addObject("id", id);
        return mv;
    }

    @RequestMapping("list")
    @PreAuthorize("hasAuthority('linkman:visit:list')")
    public ResponseEntity page(LayuiPage layuiPage, String parameterName, Integer visitType, String start_date,String end_date) {
        System.out.println("联系人拜访参数接收:"+"  （搜索框parameterName：  "+parameterName+")(拜访方式: "+visitType+")"
        +"(开始时间:"+start_date+") (结束时间:"+end_date+")");
        LocalDate beginDate = null;
        LocalDate afterDate = null;
        if ((StringUtils.isNotBlank(start_date))&&(StringUtils.isNotBlank(end_date))){
        beginDate = LocalDate.parse(start_date, DateTimeFormatter.ofPattern("yyyy-MM-dd")).plusDays(1);
        afterDate = LocalDate.parse(end_date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        SystemCheckUtils.getInstance().checkMaxPage(layuiPage);
        IPage page = new Page<>(layuiPage.getPage(), layuiPage.getLimit());
        IPage<TbVisit> page1 = entityService.lambdaQuery()
                .like(StringUtils.isNotBlank(parameterName), TbVisit::getVisitReason, parameterName)
                .eq(visitType != null, TbVisit::getVisitType, visitType)
                .between((beginDate!=null)&&(afterDate!=null)&&StringUtils.isNotBlank(start_date)
                        &&StringUtils.isNotBlank(end_date)
                        ,TbVisit::getVisitDate,beginDate,afterDate)
                .page(page);
        List<TbVisit> records = page1.getRecords();
        for (TbVisit record : records) {
            String custId = record.getCustId();
            TbCustomer customer = customerService.getById(custId);
            record.setCustName(customer.getCustomerName());
            String linkmanId = record.getLinkmanId();
            TbCustLinkman linkman = linkmanService.getById(linkmanId);
            record.setLinkmanName(linkman.getLinkman());
        }
        return ResponseEntity.ok(LayuiTools.toLayuiTableModel(page1));
    }

    @SameUrlData
    @PostMapping("save")
    @SysLog(value = LogModules.SAVE, module =LogModule)
    @PreAuthorize("hasAuthority('linkman:visit:add')")
    public ResponseEntity<ApiModel> save(@Validated({AddGroup.class}) @RequestBody TbVisit entity, HttpServletRequest request) {
        String custId = entity.getCustId();
        entity.setInputTime(LocalDateTime.now(ZoneOffset.of("+16")));
        SysUser loginUser = (SysUser) request.getSession().getAttribute(LoginForm.LOGIN_USER_KEY);
        entity.setInputUser(loginUser.getUserId());
        entityService.save(entity);
        return ResponseEntity.ok(ApiModel.ok());
    }

    @SameUrlData
    @SysLog(value = LogModules.UPDATE, module = LogModule)
    @PutMapping("update")
    @PreAuthorize("hasAuthority('linkman:visit:update')")
    public ResponseEntity<ApiModel> update(@Validated({UpdateGroup.class}) @RequestBody TbVisit entity, HttpServletRequest request) {
        entity.setInputTime(LocalDateTime.now(ZoneOffset.of("+16")));
        SysUser loginUser = (SysUser) request.getSession().getAttribute(LoginForm.LOGIN_USER_KEY);
        entity.setInputUser(loginUser.getUserId());
        entityService.updateById(entity);
        return ResponseEntity.ok(ApiModel.ok());
    }

    @SysLog(value = LogModules.DELETE, module = LogModule)
    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAuthority('linkman:visit:delete')")
    public ResponseEntity<ApiModel> delete(@PathVariable("id") String id) {
        entityService.removeById(id);
        return ResponseEntity.ok(ApiModel.ok());
    }

}
