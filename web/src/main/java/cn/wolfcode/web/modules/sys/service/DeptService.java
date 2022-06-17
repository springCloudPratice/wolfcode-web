package cn.wolfcode.web.modules.sys.service;

import cn.wolfcode.web.modules.sys.entity.SysDept;
import link.ahsj.core.components.layui.treeselect.MultipleTreeSelect;

import java.util.List;

/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2019/4/10 9:50
 * @email chenshiyun2011@163.com
 */
public interface DeptService {

    /**
     * 查询全部部门并解析成树形结构
     *
     * @return
     */
    List<MultipleTreeSelect> parseTree();


    /**
     * 查询全部部门树形结构,并无解析
     *
     * @return
     */
    List<MultipleTreeSelect> queryAllDeptTree();

    /**
     * 保存一个部门
     *
     * @param sysDept
     */
    void saveDeptInfo(SysDept sysDept);

    /**
     * 根据ID获取一个部门
     *
     * @param deptId
     * @return
     */
    SysDept getDeptById(String deptId);

    /**
     * 更改一个部门信息
     *
     * @param sysDept
     */
    void updateDeptInfo(SysDept sysDept);

    /**
     * 根据部门ID删除一个部门
     *
     * @param deptId
     */
    void deleteDeptById(String deptId);

    /**
     * 查询全部正常状态的部门
     *
     * @return
     */
    List<SysDept> queryAllDept();
}
