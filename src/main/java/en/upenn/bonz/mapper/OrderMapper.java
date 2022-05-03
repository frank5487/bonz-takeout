package en.upenn.bonz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import en.upenn.bonz.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
}
