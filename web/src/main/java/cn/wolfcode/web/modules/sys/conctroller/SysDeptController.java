package cn.wolfcode.web.modules.sys.conctroller;

import cn.wolfcode.web.modules.sys.service.DeptService;
import cn.wolfcode.web.modules.sys.service.SysDeptService;
import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.wolfcode.web.commons.enums.DeleteType;
import cn.wolfcode.web.modules.log.LogModules;
import cn.wolfcode.web.modules.sys.entity.SysDept;
import cn.wolfcode.web.commons.entity.LayuiPage;
import cn.wolfcode.web.commons.utils.LayuiTools;
import cn.wolfcode.web.commons.utils.SystemCheckUtils;
import link.ahsj.core.annotations.AddGroup;
import link.ahsj.core.annotations.SameUrlData;
import link.ahsj.core.annotations.SysLog;
import link.ahsj.core.annotations.UpdateGroup;
import link.ahsj.core.components.layui.treeselect.MultipleTreeSelect;
import link.ahsj.core.entitys.ApiModel;
import link.ahsj.core.utils.base.AppAssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;

/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2019/4/10 9:38
 * @email chenshiyun2011@163.com
 */
@Controller
@RequestMapping("dept")
public class SysDeptController {

    @Autowired
    private DeptService deptService;
    @Autowired
    private SysDeptService sysDeptService;


    @GetMapping("/list.html")
    public String list() {
        return "sys/dept/deptList";
    }

    @GetMapping("/add.html")
    @PreAuthorize("hasAuthority('dept:add')")
    public ModelAndView toAdd(ModelAndView mv) {
        mv.setViewName("sys/dept/deptAdd");
        return mv;
    }

    @GetMapping("/{deptId}.html")
    @PreAuthorize("hasAuthority('dept:update')")
    public ModelAndView toUpdate(@PathVariable("deptId") String deptId, ModelAndView mv) {
        SysDept sysDept = deptService.getDeptById(deptId);
        mv.setViewName("sys/dept/deptUpdate");
        mv.addObject("sysDept", sysDept);
        if (sysDept.getParentDeptId() != null) {
            mv.addObject("parentDept", deptService.getDeptById(sysDept.getParentDeptId()));
        } else {
            AppAssertUtil.isNull(sysDept, "数据不存在!");
        }
        return mv;
    }

    /**
     * ========================= service method =========================
     **/

    @GetMapping("tree")
    public ResponseEntity xtree() {
        List<MultipleTreeSelect> xtreeList = deptService.parseTree();
        return ResponseEntity.ok(xtreeList);
    }

    @GetMapping("deptList")
    @SysLog(value = LogModules.LIST, module = LogModules.ORG)
    @PreAuthorize("hasAuthority('dept:deptList')")
    public ResponseEntity page(LayuiPage layuiPage, SysDept sysDept) {
        SystemCheckUtils.getInstance().checkMaxPage(layuiPage);
        IPage page = new Page<>(layuiPage.getPage(), layuiPage.getLimit());

        //校验是否有查询机构层级
        if(StringUtils.isNotEmpty(sysDept.getDeptId())){
            SysDept dept = sysDeptService.getById(sysDept.getDeptId());
            if(dept!=null){
                sysDept.setBusinessLevel(dept.getBusinessLevel());
            }
        }

        AbstractWrapper wrapper = sysDeptService.lambdaQuery()
                .eq(sysDept.getDisable() != null, SysDept::getDisable, sysDept.getDisable())
                .like(StringUtils.isNotBlank(sysDept.getDeptName()),SysDept::getDeptName,sysDept.getDeptName())
                .likeRight(StringUtils.isNotBlank(sysDept.getBusinessLevel()),SysDept::getBusinessLevel,sysDept.getBusinessLevel())
                .orderByAsc(SysDept::getNumberLevel)
                .getWrapper();
        return ResponseEntity.ok(LayuiTools.toLayuiTableModel(sysDeptService.page(page, wrapper)));
    }



    @SameUrlData
    @SysLog(value = LogModules.SAVE, module = LogModules.ORG)
    @PostMapping("save")
    @PreAuthorize("hasAuthority('dept:add')")
    public ResponseEntity<ApiModel> save(@Validated({AddGroup.class}) @RequestBody SysDept sysDept) {
        deptService.saveDeptInfo(sysDept);
        return ResponseEntity.ok(ApiModel.ok());
    }

    @SysLog(value = LogModules.DELETE, module = LogModules.ORG)
    @DeleteMapping("/delete/{deptId}")
    @PreAuthorize("hasAuthority('dept:delete')")
    public ResponseEntity<ApiModel> delete(@PathVariable() String deptId) {
        SysDept sysDept = sysDeptService.getById(deptId);
        if (Objects.nonNull(sysDept)) {
            sysDeptService.update(sysDeptService.lambdaUpdate()
                    .set(SysDept::getDisable, DeleteType.DISABLE)
                    .likeRight(SysDept::getBusinessLevel, sysDept.getBusinessLevel())
                    .getWrapper());
        }
        return ResponseEntity.ok(ApiModel.ok());
    }

    @GetMapping("recovery/{deptId}")
    @SysLog(value = LogModules.RECOVER, module = LogModules.ORG)
    @PreAuthorize("hasAuthority('dept:recovery')")
    public ResponseEntity<ApiModel> recovery(@PathVariable() String deptId) {
        SysDept sysDept = sysDeptService.getById(deptId);
        if (Objects.nonNull(sysDept)) {
            sysDeptService.update(sysDeptService.lambdaUpdate()
                    .set(SysDept::getDisable, DeleteType.NORMAL)
                    .likeRight(SysDept::getBusinessLevel, sysDept.getBusinessLevel())
                    .getWrapper());
        }
        return ResponseEntity.ok(ApiModel.ok());
    }

    @SameUrlData
    @SysLog(value = LogModules.UPDATE, module = LogModules.ORG)
    @PutMapping("update")
    @PreAuthorize("hasAuthority('dept:update')")
    public ResponseEntity<ApiModel> update(@Validated({UpdateGroup.class}) @RequestBody SysDept sysDept) {
        deptService.updateDeptInfo(sysDept);
        return ResponseEntity.ok(ApiModel.ok());
    }

}
