package en.upenn.bonz.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import en.upenn.bonz.common.R;
import en.upenn.bonz.dto.DishDto;
import en.upenn.bonz.entity.Dish;
import en.upenn.bonz.service.DishFlavorService;
import en.upenn.bonz.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    /**
     * add a new dish
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {
        log.info(dishDto.toString());

        dishService.saveWithFlavor(dishDto);

        return R.success("add a new dish");
    }

    @GetMapping("/page")
    public R<Page<DishDto>> page(int page, int pageSize, String name) {
//        log.info("page: {}, pageSize: {}, name: {}", page, pageSize, name);

        Page<DishDto> dishDtoPage = dishService.showDishInPage(page, pageSize, name);

        return R.success(dishDtoPage);
    }

    @GetMapping("/{id}")
    public R<DishDto> getById(@PathVariable Long id) {
        DishDto dishDto = dishService.getByIdWithFlavor(id);

        return R.success(dishDto);
    }

    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto) {
        dishService.updateWithFlavor(dishDto);

        return R.success("update the dish info...");
    }

    @PostMapping("/status/{status}")
    public R<String> changeStatus(@PathVariable Integer status, @RequestParam List<Long> ids) {
        log.info("status: {}, ids: {}", status, ids);

        dishService.changeStatusById(status, ids);

        return R.success("change dishes status...");
    }

    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        log.info("ids: {}", ids);

        dishService.deleteById(ids);

        return R.success("delete dishes...");
    }

//    @GetMapping("/list")
//    public R<List<Dish>> getSelectedList(Dish dish) {
//        log.info("dish: {}", dish);
//
//        List<Dish> dishes = dishService.getSelectedList(dish);
//
//        return R.success(dishes);
//    }

    @GetMapping("/list")
    public R<List<DishDto>> showDishInList(Dish dish) {
        log.info("dish: {}", dish);

        List<DishDto> dishDtoList = dishService.showDishInList(dish);

        return R.success(dishDtoList);
    }
}
