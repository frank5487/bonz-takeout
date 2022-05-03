package en.upenn.bonz.controller;

import en.upenn.bonz.common.R;
import en.upenn.bonz.entity.ShoppingCart;
import en.upenn.bonz.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart) {
        log.info("shoppingCart: {}", shoppingCart);

        ShoppingCart cart = shoppingCartService.add(shoppingCart);

        return R.success(cart);
    }

    @PostMapping("/sub")
    public R<ShoppingCart> sub(@RequestBody ShoppingCart shoppingCart) {
        log.info("shoppingCart: {}", shoppingCart);

        ShoppingCart cart = shoppingCartService.sub(shoppingCart);

        return R.success(cart);
    }

    @GetMapping("/list")
    public R<List<ShoppingCart>> showItemsInCart() {
        log.info("show shopping cart's items");

        List<ShoppingCart> carts = shoppingCartService.showItemsInCart();

        return R.success(carts);
    }

    @DeleteMapping("/clean")
    public R<String> clean() {
        log.info("clean the shopping cart...");

        shoppingCartService.clean();

        return R.success("clean the shopping cart...");
    }
}
