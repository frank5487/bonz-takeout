package en.upenn.bonz.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import en.upenn.bonz.common.R;
import en.upenn.bonz.dto.SetmealDto;
import en.upenn.bonz.entity.Setmeal;
import en.upenn.bonz.service.SetmealDishService;
import en.upenn.bonz.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;

    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto) {
        log.info("setmealDto: {}", setmealDto);

        setmealService.saveWithDish(setmealDto);

        return R.success("add a new set meal...");
    }

    @GetMapping("/page")
    public R<Page<SetmealDto>> showSetmealInPage(int page, int pageSize, String name) {

        Page<SetmealDto> setmealDtoPage = setmealService.showSetmealInPage(page, pageSize, name);

        return R.success(setmealDtoPage);
    }

    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        log.info("ids: {}", ids);

        setmealService.deleteWithDish(ids);

        return R.success("delete set meal with dish...");
    }

    @PostMapping("/status/{status}")
    public R<String> changeStatus(@PathVariable Integer status, @RequestParam List<Long> ids) {
        log.info("status: {}, ids: {}", status, ids);

        setmealService.changeStatusById(status, ids);

        return R.success("change status...");
    }

    @GetMapping("/list")
    public R<List<Setmeal>> getSelectedlist(Setmeal setmeal) {
        log.info("setmeal: {}", setmeal);

        List<Setmeal> setmealList = setmealService.getSelectedList(setmeal);

        return R.success(setmealList);
    }
}
