package cn.wolfcode.web.modules.sys.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.wolfcode.web.modules.sys.service.DeptService;
import cn.wolfcode.web.modules.sys.service.SysDeptService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.wolfcode.web.commons.config.SysConstant;
import cn.wolfcode.web.commons.enums.DeleteType;
import cn.wolfcode.web.modules.sys.entity.SysDept;
import cn.wolfcode.web.commons.entity.CodeMsg;
import link.ahsj.core.components.layui.treeselect.Hierarchy;
import link.ahsj.core.components.layui.treeselect.MultipleTreeSelect;
import link.ahsj.core.components.layui.treeselect.MultipleTreeSelectBuilder;
import link.ahsj.core.components.tree.BusinessBuildUtils;
import link.ahsj.core.utils.base.AppAssertUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2019/4/10 9:50
 * @email chenshiyun2011@163.com
 */
@Log4j2
@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private SysDeptService sysDeptService;

//    @Cacheable(value = CacheKeyConstant.DEPT_CACHE_KEY, key = "'dept:deptList'")
    @Override
    public List<MultipleTreeSelect> parseTree() {
        List<SysDept> deptList = sysDeptService.list(sysDeptService.lambdaQuery().eq(SysDept::getDisable, DeleteType.NORMAL).getWrapper());

        List<MultipleTreeSelect> xtreeList = new MultipleTreeSelectBuilder<SysDept>().parse(deptList,
                sysDept -> new Hierarchy(sysDept.getDeptId(), sysDept.getParentDeptId(), sysDept.getDeptName(), sysDept.getDeptId()),
                hierarchy -> SysConstant.ADMIN_ORG_PID.equals(hierarchy.getPid()));

        return xtreeList;
    }

    @Override
    public List<MultipleTreeSelect> queryAllDeptTree() {
        List<MultipleTreeSelect> collect = new ArrayList<>();
        List<SysDept> list = sysDeptService.list(Wrappers.<SysDept>lambdaQuery().eq(SysDept::getDisable, DeleteType.NORMAL));
        if (CollectionUtil.isNotEmpty(list)) {
            collect = list.stream().map(item ->
                    new MultipleTreeSelect(
                            item.getDeptId(),
                            item.getParentDeptId(),
                            item.getDeptName(),
                            false,
                            Collections.emptyList()
                    )).collect(Collectors.toList());
        }
        return collect;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveDeptInfo(SysDept sysDept) {
        String idStr = IdWorker.getIdStr();
        sysDept.setDeptId(idStr);

        //查询出上级机构
        SysDept pdept = sysDeptService.getOne(sysDeptService.lambdaQuery().eq(SysDept::getDeptId, sysDept.getParentDeptId()).getWrapper());
        AppAssertUtil.isNull(pdept, "上级机构不能空");
        AppAssertUtil.isNull(pdept.getNumberLevel(), CodeMsg.VALID_ERR_MSG, "DeptServiceImpl.saveDeptInfo方法:机构层级没有初始化");
        AppAssertUtil.isNull(pdept.getBusinessLevel(), CodeMsg.VALID_ERR_MSG, "DeptServiceImpl.saveDeptInfo方法:机构业务层级没有初始化");
        //生成机构层级
        sysDept.setNumberLevel(pdept.getNumberLevel() + 1);
        //组装业务层级id
        String businessLevel = pdept.getBusinessLevel().concat(idStr).concat(SysConstant.BUSI_PREFIX);
        sysDept.setBusinessLevel(businessLevel);

        sysDeptService.save(sysDept);
    }

    @Override
    public SysDept getDeptById(String deptId) {
        return sysDeptService.getById(deptId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDeptInfo(SysDept sysDept) {

        //拿出上级机构

        //查询出数据库角色信息
        SysDept osysDept = sysDeptService.getById(sysDept.getDeptId());


        List<SysDept> depts = new ArrayList<>();
        //上级机构出现变化情况      还要加上自身不是顶级节点,自身就是顶节点,就不能更换父节点
        if (!osysDept.getParentDeptId().equals(sysDept.getParentDeptId())) {
            //查询出上级机构

            SysDept pdept = sysDeptService.getOne(sysDeptService.lambdaQuery().eq(SysDept::getDeptId, sysDept.getParentDeptId()).getWrapper());

            AppAssertUtil.isNull(pdept, "上级机构不能空");

            //父业务节点
            String parentRoleBusiness = pdept.getBusinessLevel();

            //自身原始业务节点
            String thisRoleBusiness = osysDept.getBusinessLevel();

            if (StringUtils.isEmpty(parentRoleBusiness)) {
                //异常
            }
            if (StringUtils.isEmpty(thisRoleBusiness)) {
                //异常
            }
            //判断是否是自己的子节点
            if (parentRoleBusiness.length() > thisRoleBusiness.length() && thisRoleBusiness.equals(parentRoleBusiness.substring(0, thisRoleBusiness.length()))) {
                AppAssertUtil.err("不能用自己的子节点作为父节点");
            } else {
                //查询所有子类节点
                List<SysDept> list = sysDeptService.list(sysDeptService.lambdaQuery()
                        .likeRight(SysDept::getBusinessLevel, osysDept.getBusinessLevel())
                        .notIn(SysDept::getDeptId, osysDept.getDeptId())
                        .getWrapper());

                //组出新的自身节点  =     父亲节点  + 自己的ID + ":"
                String newThisBusiness = parentRoleBusiness.concat(sysDept.getDeptId()).concat(SysConstant.BUSI_PREFIX);
                //设置新自身原始节点
                osysDept.setBusinessLevel(newThisBusiness);
                //自身角色等级更换
                osysDept.setNumberLevel(pdept.getNumberLevel() + 1);

                log.debug("原始节点:" + osysDept.getBusinessLevel());
                log.debug("新的自身节点:" + newThisBusiness);

                //子类的节点替换 重组出新的子节点
                if (CollectionUtil.isNotEmpty(list)) {
                    BusinessBuildUtils.rearrangement(osysDept, list);
                    depts.addAll(list);

                }
            }
        }

        //设置可以更变的信息
        osysDept.setDeptName(sysDept.getDeptName());
        osysDept.setParentDeptId(sysDept.getParentDeptId());
        //设置自己到集合里去
        depts.add(osysDept);


        //更新全部
        sysDeptService.updateBatchById(depts);
    }

    @Override
    public void deleteDeptById(String deptId) {
        sysDeptService.removeById(deptId);
    }

    @Override
    public List<SysDept> queryAllDept() {
        return sysDeptService.list(new LambdaQueryWrapper<SysDept>().eq(SysDept::getDisable, DeleteType.NORMAL));
    }
}
