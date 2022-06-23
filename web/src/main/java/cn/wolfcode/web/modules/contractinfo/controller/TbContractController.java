package cn.wolfcode.web.modules.contractinfo.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.wolfcode.web.commons.entity.LayuiPage;
import cn.wolfcode.web.commons.utils.LayuiTools;
import cn.wolfcode.web.commons.utils.SystemCheckUtils;
import cn.wolfcode.web.modules.BaseController;
import cn.wolfcode.web.modules.custinfo.entity.TbCustomer;
import cn.wolfcode.web.modules.custinfo.service.ITbCustomerService;
import cn.wolfcode.web.modules.log.LogModules;
import cn.wolfcode.web.modules.sys.entity.SysUser;
import cn.wolfcode.web.modules.sys.form.LoginForm;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.wolfcode.web.modules.contractinfo.entity.TbContract;
import cn.wolfcode.web.modules.contractinfo.service.ITbContractService;

import link.ahsj.core.annotations.AddGroup;
import link.ahsj.core.annotations.SameUrlData;
import link.ahsj.core.annotations.SysLog;
import link.ahsj.core.annotations.UpdateGroup;
import link.ahsj.core.entitys.ApiModel;
import link.ahsj.core.exception.AppServerException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 写代码没有出息的
 * @since 2022-06-22
 */
@Controller
@Log4j2
@RequestMapping("contractinfo")
public class TbContractController extends BaseController {

    @Autowired
    private ITbContractService entityService;
    @Autowired
    private ITbCustomerService customerService;

    private static final String LogModule = "TbContract";

    @GetMapping("/list.html")
    public String list() {
        return "contract/contractinfo/list";
    }

    @RequestMapping("/add.html")
    @PreAuthorize("hasAuthority('contract:contractinfo:add')")
    public ModelAndView toAdd(ModelAndView mv) {
        //查出客户
        List<TbCustomer> list = customerService.list();
        //返回页面
        mv.addObject("custs",list);
        mv.setViewName("contract/contractinfo/add");
        return mv;
    }

    @GetMapping("/{id}.html")
    @PreAuthorize("hasAuthority('contract:contractinfo:update')")
    public ModelAndView toUpdate(@PathVariable("id") String id, ModelAndView mv) {
        //查出客户
        List<TbCustomer> list = customerService.list();
        //返回页面
        mv.addObject("custs",list);
        mv.setViewName("contract/contractinfo/update");
        mv.addObject("obj", entityService.getById(id));
        mv.addObject("id", id);
        return mv;
    }

    @RequestMapping("list")
    @PreAuthorize("hasAuthority('contract:contractinfo:list')")
    public ResponseEntity page(LayuiPage layuiPage,String parameterName,String affixSealStatus,String auditStatus,String nullifyStatus) {
        SystemCheckUtils.getInstance().checkMaxPage(layuiPage);
        IPage<TbContract> page = new Page<>(layuiPage.getPage(), layuiPage.getLimit());
        IPage<TbContract> page1 = entityService.lambdaQuery()
                .eq(StringUtils.isNotBlank(affixSealStatus),TbContract::getAffixSealStatus,affixSealStatus)
                .eq(StringUtils.isNotBlank(auditStatus),TbContract::getAuditStatus,auditStatus)
                .eq(StringUtils.isNotBlank(nullifyStatus),TbContract::getNullifyStatus,nullifyStatus)
                .like(StringUtils.isNotBlank(parameterName), TbContract::getContractName,parameterName)
                .or()
                .like(StringUtils.isNotBlank(parameterName),TbContract::getContractCode,parameterName)
                .page(page);
        //判断我的集合不空
        if(CollectionUtil.isNotEmpty(page1.getRecords())){
            page1.getRecords().forEach(obj->{
                //拿到企业客户 id
                String custId1 = obj.getCustId();
                //查询出企业客户名称
                TbCustomer customer = customerService.getById(custId1);
                //判断不等于空的时候
                if (Objects.nonNull(customer)){
                    //设置企业客户名称到我们的联系人里面去
                    obj.setCustomerName(customer.getCustomerName());
                }
            });
        }
        return ResponseEntity.ok(LayuiTools.toLayuiTableModel(page1));
//        return ResponseEntity.ok(LayuiTools.toLayuiTableModel(entityService.page(page)));
    }

    @SameUrlData
    @PostMapping("save")
    @SysLog(value = LogModules.SAVE, module =LogModule)
    @PreAuthorize("hasAuthority('contract:contractinfo:add')")
    public ResponseEntity<ApiModel> save(@Validated({AddGroup.class}) @RequestBody TbContract entity, HttpServletRequest request) {
        SysUser loginUser = (SysUser) request.getSession().getAttribute(LoginForm.LOGIN_USER_KEY);
        entity.setInputUser(loginUser.getUserId());
        entity.setInputTime(LocalDateTime.now(ZoneOffset.of("+16")));
        LocalDate startDate = entity.getStartDate();
        LocalDate endDate = entity.getEndDate();
        if(startDate.compareTo(endDate)>0){
            throw  new AppServerException("合同开始时间不能晚于合同结束时间");
        }
        if(isContainChinese(entity.getContractCode())){
            throw  new AppServerException("合同编码不能含有中文");
        }
        entityService.save(entity);
        return ResponseEntity.ok(ApiModel.ok());
    }

    @SameUrlData
    @SysLog(value = LogModules.UPDATE, module = LogModule)
    @PutMapping("update")
    @PreAuthorize("hasAuthority('contract:contractinfo:update')")
    public ResponseEntity<ApiModel> update(@Validated({UpdateGroup.class}) @RequestBody TbContract entity, HttpServletRequest request) {
        SysUser loginUser = (SysUser) request.getSession().getAttribute(LoginForm.LOGIN_USER_KEY);
        entity.setInputUser(loginUser.getUserId());
        entity.setUpdateTime(LocalDateTime.now(ZoneOffset.of("+16")));
        LocalDate startDate = entity.getStartDate();
        LocalDate endDate = entity.getEndDate();
        if(startDate.compareTo(endDate)>0){
            throw  new AppServerException("合同开始时间不能晚于合同结束时间");
        }
        if(isContainChinese(entity.getContractCode())){
            throw  new AppServerException("合同编码不能含有中文");
        }
        entityService.updateById(entity);
        return ResponseEntity.ok(ApiModel.ok());
    }

    @SysLog(value = LogModules.DELETE, module = LogModule)
    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAuthority('contract:contractinfo:delete')")
    public ResponseEntity<ApiModel> delete(@PathVariable("id") String id) {
        entityService.removeById(id);
        return ResponseEntity.ok(ApiModel.ok());
    }
    public static boolean isContainChinese(String str) {

                 Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
                 Matcher m = p.matcher(str);
                 if (m.find()) {
                         return true;
                     }
                 return false;
             }

}
