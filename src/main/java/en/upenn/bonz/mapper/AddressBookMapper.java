package en.upenn.bonz.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import en.upenn.bonz.entity.AddressBook;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {
}
