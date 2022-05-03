package en.upenn.bonz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import en.upenn.bonz.entity.Orders;

public interface OrderService extends IService<Orders> {
    void submit(Orders orders);
}
