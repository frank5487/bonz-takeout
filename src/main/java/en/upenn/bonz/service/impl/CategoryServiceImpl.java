package en.upenn.bonz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import en.upenn.bonz.common.CustomException;
import en.upenn.bonz.entity.Category;
import en.upenn.bonz.entity.Dish;
import en.upenn.bonz.entity.Setmeal;
import en.upenn.bonz.mapper.CategoryMapper;
import en.upenn.bonz.service.CategoryService;
import en.upenn.bonz.service.DishService;
import en.upenn.bonz.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    @Override
    public Page<Category> showListInPage(int page, int pageSize) {
        Page<Category> pageInfo = new Page<>(page, pageSize);

        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        this.page(pageInfo, queryWrapper);

        return pageInfo;
    }

    /**
     * remove category by id with condition
     * @param id
     */
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int dishCount = dishService.count(dishLambdaQueryWrapper);

        // query current category, if it is related with other dishes, then throw exception
        if (dishCount > 0) {
            throw new CustomException("this category has related with dishes, please delete dishes first...");
        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
        int setmealCount = setmealService.count(setmealLambdaQueryWrapper);

        // query current category, if it is related with other set meals, then throw exception
        if (setmealCount > 0) {
            throw new CustomException("this category has related with set meals, please delete dishes first...");
        }

        this.removeById(id);
    }

    @Override
    public List<Category> getSeletedList(Category category) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(category.getType() != null, Category::getType, category.getType());
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);

        List<Category> list = this.list(queryWrapper);

        return list;
    }
}
