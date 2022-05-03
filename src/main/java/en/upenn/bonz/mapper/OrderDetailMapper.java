package en.upenn.bonz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import en.upenn.bonz.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {
}
