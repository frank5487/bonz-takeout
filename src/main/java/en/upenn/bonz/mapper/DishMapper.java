package en.upenn.bonz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import en.upenn.bonz.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
