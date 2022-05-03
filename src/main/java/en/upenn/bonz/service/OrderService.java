package en.upenn.bonz.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import en.upenn.bonz.entity.Orders;

import java.util.Date;

public interface OrderService extends IService<Orders> {
    void submit(Orders orders);

    Page<Orders> showOrderInPage(int page, int pageSize, String number, String beginTime, String endTime);
}
