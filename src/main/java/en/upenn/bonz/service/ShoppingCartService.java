package en.upenn.bonz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import en.upenn.bonz.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService extends IService<ShoppingCart> {
    ShoppingCart add(ShoppingCart shoppingCart);

    ShoppingCart sub(ShoppingCart shoppingCart);

    List<ShoppingCart> showItemsInCart();

    void clean();
}
