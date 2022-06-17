package cn.wolfcode.web.modules.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import cn.wolfcode.web.commons.enums.DeleteType;
import cn.wolfcode.web.modules.sys.entity.SysConfig;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @author Eastern unbeaten
 * @version 1.0
 * @date 2019/7/15 21:28
 * @mail chenshiyun2011@163.com
 */
public interface SysConfigService extends IService<SysConfig> {


    /**
     * 获取默认的value直
     *
     * @param key
     * @return
     */
    default String getConfig(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        SysConfig sysConfig = this.lambdaQuery()
                .eq(SysConfig::getConfigKey, key)
                .eq(SysConfig::getDisable, DeleteType.NORMAL)
                .one();
        if (Objects.isNull(sysConfig)) {
            return null;
        }
        return sysConfig.getConfigValue();

    }

    /**
     * 根据ID删除,包含所属子节点
     *
     * @param id
     */
    void deleteById(String id);

    /**
     * 根据ID恢复,包含所属子节点
     *
     * @param id
     */
    void recoverById(String id);
}
