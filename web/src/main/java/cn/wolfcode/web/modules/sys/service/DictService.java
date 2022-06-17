package cn.wolfcode.web.modules.sys.service;

import cn.wolfcode.web.modules.sys.entity.SysDict;
import cn.wolfcode.web.modules.sys.entity.SysDictVerifyEntity;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2019/4/3 18:04
 * @email chenshiyun2011@163.com
 */
public interface DictService  {


    /**
     * 导入数据字典
     *
     * @param list
     * @return
     */
    List<String> importDict(List<SysDictVerifyEntity> list);

    /**
     * 根据字典编码查询列表
     *
     * @param diceCodes
     * @return
     */
    List<SysDict> queryDictList(Collection<String> diceCodes,String dictName);


    /**
     * 根据字典编码查询列表
     *
     * @param diceCodes
     * @return
     */
    List<Map> queryList(Collection<String> diceCodes);

    /**
     * 根据条件查询字典编码
     *
     * @param dictName
     * @param diceCodes
     * @return
     */
    List<SysDict> queryDictCode(String dictName, String[] diceCodes);
}
