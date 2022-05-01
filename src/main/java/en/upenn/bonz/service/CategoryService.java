package en.upenn.bonz.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import en.upenn.bonz.entity.Category;

import java.util.List;

public interface CategoryService extends IService<Category> {
    Page<Category> showListInPage(int page, int pageSize);

    void remove(Long id);

    List<Category> getSeletedList(Category category);
}
