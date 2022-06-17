package cn.wolfcode.web.modules.sys.service.impl;

import cn.wolfcode.web.modules.sys.mapper.SysUserOperationLogMapper;
import cn.wolfcode.web.modules.sys.service.SysUserOperationLogService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.wolfcode.web.modules.sys.entity.SysUserOperationLog;
import cn.wolfcode.web.modules.sys.form.SysOperationLogForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户操作日志表 服务实现类
 * </p>
 *
 * @author Aimer
 * @since 2019-04-11
 */
@Service
public class SysUserOperationLogServiceImpl extends ServiceImpl<SysUserOperationLogMapper, SysUserOperationLog> implements SysUserOperationLogService {
    @Autowired
    private SysUserOperationLogMapper sysUserOperationLogMapper;

    /**
     * 查询功能
     *
     * @param sysOperationLogForm
     * @param page
     * @return
     */
    @Override
    public List<SysUserOperationLog> queryOperationList(SysOperationLogForm sysOperationLogForm, IPage page) {
        return sysUserOperationLogMapper.queryOperationList(sysOperationLogForm,page);
    }

    /**
     * 查询操作类型
     *
     * @return
     */
    @Override
    public List<SysUserOperationLog> queryType() {
        return sysUserOperationLogMapper.queryType();
    }
}
