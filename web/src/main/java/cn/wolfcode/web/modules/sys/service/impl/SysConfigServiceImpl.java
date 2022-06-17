package cn.wolfcode.web.modules.sys.service.impl;

import cn.wolfcode.web.modules.sys.mapper.SysConfigMapper;
import cn.wolfcode.web.modules.sys.service.SysConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.wolfcode.web.commons.enums.DeleteType;
import cn.wolfcode.web.modules.sys.entity.SysConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Eastern unbeaten
 * @version 1.0
 * @date 2019/7/15 21:28
 * @mail chenshiyun2011@163.com
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        SysConfig sysConfig = this.getById(id);
        sysConfig.setDisable(DeleteType.DISABLE);
        this.updateById(sysConfig);
        List<SysConfig> normalConfigs = lambdaQuery().eq(SysConfig::getDisable, DeleteType.NORMAL).ne(SysConfig::getConfigKey, "-").list();
        parseConfigs(normalConfigs, sysConfig.getConfigKey(), DeleteType.DISABLE);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recoverById(String id) {
        SysConfig sysConfig = this.getById(id);
        sysConfig.setDisable(DeleteType.NORMAL);
        this.updateById(sysConfig);
        List<SysConfig> disableConfigs = lambdaQuery().eq(SysConfig::getDisable, DeleteType.DISABLE).ne(SysConfig::getConfigKey, "-").list();
        parseConfigs(disableConfigs, sysConfig.getConfigKey(), DeleteType.NORMAL);
    }

    private void parseConfigs(List<SysConfig> sysConfigs, String parentKey, DeleteType type) {
        for (SysConfig sc : sysConfigs) {
            if (parentKey.equals(sc.getParentKey())) {
                parseConfigs(sysConfigs, sc.getConfigKey(), type);
                sc.setDisable(type);
                this.updateById(sc);
            }
        }
    }

}
