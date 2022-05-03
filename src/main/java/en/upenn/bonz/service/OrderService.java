package en.upenn.bonz.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import en.upenn.bonz.dto.OrdersDto;
import en.upenn.bonz.entity.Orders;


public interface OrderService extends IService<Orders> {
    void submit(Orders orders);

    Page<Orders> showOrderInPage(int page, int pageSize, String number, String beginTime, String endTime);

    Page<OrdersDto> showOrdersDtoInPage(int page, int pageSize);

    void copyOrderById(Long id);
}
