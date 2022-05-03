package en.upenn.bonz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import en.upenn.bonz.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {
}
