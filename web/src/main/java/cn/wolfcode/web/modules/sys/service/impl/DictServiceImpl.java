package cn.wolfcode.web.modules.sys.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.wolfcode.web.modules.sys.service.DictService;
import cn.wolfcode.web.modules.sys.service.SysDictService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.wolfcode.web.modules.sys.entity.SysDict;
import cn.wolfcode.web.modules.sys.entity.SysDictVerifyEntity;
import link.ahsj.core.utils.base.AppAssertUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2019/4/3 18:05
 * @email chenshiyun2011@163.com
 */
@Service
@Log4j2
public class DictServiceImpl implements DictService {

    @Autowired
    private SysDictService sysDictService;

    @Override
    public List<String> importDict(List<SysDictVerifyEntity> dicts) {
        List<String> errors = new ArrayList<>(0);
        if (!CollectionUtils.isEmpty(dicts)) {
            dicts.stream().forEach(item -> {
                try {
                    importValidate(item);
                    //执行保存操作
                    sysDictService.save(item);
                } catch (Exception e) {
                    log.error(e);
                    errors.add(String.format("第%s行,数据保存失败,%s", item.getRowNum(), e.getMessage()));
                }
            });
        }
        return errors;
    }

    @Override
    public List<SysDict> queryDictList(Collection<String> diceCodes, String dictName) {

        List<SysDict> list = sysDictService.list(Wrappers.<SysDict>lambdaQuery()
                .like(StringUtils.isNotEmpty(dictName), SysDict::getDiceName, dictName)
                .in(CollectionUtil.isNotEmpty(diceCodes), SysDict::getDiceCode, diceCodes)
                .or()
                .in(CollectionUtil.isNotEmpty(diceCodes), SysDict::getParentDiceCode, diceCodes)
        );
        return list;
    }

    @Override
    public List<Map> queryList(Collection<String> diceCodes) {
        return sysDictService.queryList(diceCodes);
    }

    @Override
    public List<SysDict> queryDictCode(String dictName, String[] diceCodes) {
        return sysDictService.queryDictCode(dictName, diceCodes);
    }

    /*******************private method******************************/
    private void importValidate(SysDictVerifyEntity item) {
        if (!StringUtils.isEmpty(item.getParentDiceCode())) {
            int pdc = sysDictService.count(new QueryWrapper<SysDict>().eq("DICE_CODE", item.getParentDiceCode()));
            AppAssertUtil.isErr(pdc == 0, String.format("%s%s不存在", SysDict.PARENT_DICE_CODE, item.getParentDiceCode()));
        }
        int c = sysDictService.count(new QueryWrapper<SysDict>().eq("DICE_CODE", item.getDiceCode()));
        AppAssertUtil.isErr(c > 0, String.format("%s%s已存在", SysDict.DICE_CODE, item.getDiceCode()));
    }
}
