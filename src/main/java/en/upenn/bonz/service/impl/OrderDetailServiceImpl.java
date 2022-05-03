package en.upenn.bonz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import en.upenn.bonz.entity.OrderDetail;
import en.upenn.bonz.mapper.OrderDetailMapper;
import en.upenn.bonz.service.OrderDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
