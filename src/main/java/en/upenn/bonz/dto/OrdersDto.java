package en.upenn.bonz.dto;

import en.upenn.bonz.entity.OrderDetail;
import en.upenn.bonz.entity.Orders;
import lombok.Data;
import java.util.List;

@Data
public class OrdersDto extends Orders {

    private List<OrderDetail> orderDetails;
	
}
