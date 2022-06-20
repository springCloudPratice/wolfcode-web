package cn.wolfcode.web.modules.custinfo.service.impl;

import cn.wolfcode.web.modules.custinfo.entity.TbCustomer;
import cn.wolfcode.web.modules.custinfo.mapper.TbCustomerMapper;
import cn.wolfcode.web.modules.custinfo.service.ITbCustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 客户信息 服务实现类
 * </p>
 *
 * @author 姚鸿伟
 * @since 2022-06-20
 */
@Service
public class TbCustomerServiceImpl extends ServiceImpl<TbCustomerMapper, TbCustomer> implements ITbCustomerService {

}
