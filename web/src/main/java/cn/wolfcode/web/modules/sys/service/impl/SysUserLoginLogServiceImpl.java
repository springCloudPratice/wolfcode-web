package cn.wolfcode.web.modules.sys.service.impl;

import cn.wolfcode.web.modules.sys.mapper.SysUserLoginLogMapper;
import cn.wolfcode.web.modules.sys.service.SysUserLoginLogService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.wolfcode.web.modules.sys.entity.SysUserLoginLog;
import cn.wolfcode.web.modules.sys.form.SysLoginLogForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户登录日志表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-04-08
 */
@Service
public class SysUserLoginLogServiceImpl extends ServiceImpl<SysUserLoginLogMapper, SysUserLoginLog> implements SysUserLoginLogService {
    @Autowired
    private SysUserLoginLogMapper sysUserLoginLogMapper;

    /**
     * 查询列表
     *
     * @param sysLoginLogForm
     * @param page
     * @return
     */
    @Override
    public List<SysUserLoginLog> queryLoginLogList(SysLoginLogForm sysLoginLogForm, IPage page) {
        return sysUserLoginLogMapper.queryLoginLogList(sysLoginLogForm,page);
    }
}
