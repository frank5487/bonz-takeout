package en.upenn.bonz.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import en.upenn.bonz.common.R;
import en.upenn.bonz.entity.Category;
import en.upenn.bonz.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody Category category) {
        log.info("category: {}", category);

        categoryService.save(category);

        return R.success("save category info...");
    }

    @GetMapping("/page")
    public R<Page<Category>> page(int page, int pageSize) {
        log.info("page: {}, pageSize: {}", page, pageSize);

        Page<Category> pageInfo = categoryService.showListInPage(page, pageSize);

        return R.success(pageInfo);
    }

    @DeleteMapping
    public R<String> delete(Long id) {
        log.info("delete category id: {}", id);

        categoryService.remove(id);

        return R.success("delete category...");
    }

    @PutMapping
    public R<String> update(@RequestBody Category category) {
        log.info("update cateogry info: {}", category);

        categoryService.updateById(category);

        return R.success("update category...");
    }

    @GetMapping("/list")
    public R<List<Category>> selectedList(Category category) {
        log.info("show what contents in the category and display it in the selected list...");

        List<Category> categories = categoryService.getSeletedList(category);

        return R.success(categories);
    }
}
