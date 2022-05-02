package en.upenn.bonz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import en.upenn.bonz.entity.User;

public interface UserService extends IService<User> {
    User getUser(String phone);
}
