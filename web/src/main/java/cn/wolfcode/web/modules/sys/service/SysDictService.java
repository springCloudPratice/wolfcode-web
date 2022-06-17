package cn.wolfcode.web.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.wolfcode.web.modules.sys.entity.SysDict;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2019/4/3 18:04
 * @email chenshiyun2011@163.com
 */
public interface SysDictService extends IService<SysDict> {

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
