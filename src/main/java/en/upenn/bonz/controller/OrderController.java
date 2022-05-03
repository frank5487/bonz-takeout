package en.upenn.bonz.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import en.upenn.bonz.common.R;
import en.upenn.bonz.dto.OrdersDto;
import en.upenn.bonz.entity.Orders;
import en.upenn.bonz.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders) {
        log.info("orders: {}", orders);

        orderService.submit(orders);

        return R.success("submit order...");
    }

    @GetMapping("/page")
    public R<Page<Orders>> showOrderInPageForBackEnd(int page, int pageSize, String number, String beginTime, String endTime) {
        log.info("page: {}, pageSize: {}, number: {}, beginTime: {}, endTime: {}", page, pageSize, number, beginTime, endTime);

        Page<Orders> ordersPage = orderService.showOrderInPage(page, pageSize, number, beginTime, endTime);

        return R.success(ordersPage);
    }

    @PutMapping
    public R<String> changeStatus(@RequestBody Orders orders) {
        log.info("orders: {}", orders);

        orderService.updateById(orders);

        return R.success("change order status...");
    }

    @GetMapping("/userPage")
    public R<Page<OrdersDto>> showUserOrderInPageForFrontEnd(int page, int pageSize) {
        log.info("page: {}, pageSize: {}", page, pageSize);

        Page<OrdersDto> ordersDtoPage = orderService.showOrdersDtoInPage(page, pageSize);

        return R.success(ordersDtoPage);
    }

    @PostMapping("/again")
    public R<String> oneMoreOrder(@RequestBody Orders orders) {
        log.info("orders: {}", orders);


        orderService.copyOrderById(orders.getId());

        return R.success("order one more...");
    }
}
