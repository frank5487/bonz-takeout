package en.upenn.bonz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import en.upenn.bonz.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
