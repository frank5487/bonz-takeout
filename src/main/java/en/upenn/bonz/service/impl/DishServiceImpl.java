package en.upenn.bonz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import en.upenn.bonz.common.CustomException;
import en.upenn.bonz.dto.DishDto;
import en.upenn.bonz.entity.Category;
import en.upenn.bonz.entity.Dish;
import en.upenn.bonz.entity.DishFlavor;
import en.upenn.bonz.mapper.DishMapper;
import en.upenn.bonz.service.CategoryService;
import en.upenn.bonz.service.DishFlavorService;
import en.upenn.bonz.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;

    @Lazy
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisTemplate redisTemplate;

    @CacheEvict(value = "dishCache", allEntries = true)
    @Transactional
    @Override
    public void saveWithFlavor(DishDto dishDto) {
        // add basic info to dish table
        this.save(dishDto);

        // add dish flavor(list) to dish_flavor table
        Long dishId = dishDto.getId();
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item)->{
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);

//        String key = "dish_" + dishDto.getCategoryId() + "_1";
//        redisTemplate.delete(key);
    }

    @Override
    public Page<DishDto> showDishInPage(int page, int pageSize, String name) {
        Page<Dish> dishPage = new Page<>(page, pageSize);

        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.like(StringUtils.isNotEmpty(name), Dish::getName, name);
        dishLambdaQueryWrapper.orderByDesc(Dish::getUpdateTime);
        this.page(dishPage, dishLambdaQueryWrapper);

        Page<DishDto> dishDtoPage = new Page<>();
        // copy dishPage to dishDtoPage
        BeanUtils.copyProperties(dishPage, dishDtoPage, "records");

        List<Dish> records = dishPage.getRecords();
        List<DishDto> dishDtoList = records.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);

            Long cid = item.getCategoryId();
            Category category = categoryService.getById(cid);
            if (category != null) {
                dishDto.setCategoryName(category.getName());
            }

            return dishDto;
        }).collect(Collectors.toList());

        dishDtoPage.setRecords(dishDtoList);

        return dishDtoPage;
    }

    @Override
    public DishDto getByIdWithFlavor(Long id) {
        Dish dish = this.getById(id);
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish, dishDto);

        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, id);
        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);

        dishDto.setFlavors(flavors);

        return dishDto;
    }

    @CacheEvict(value = "dishCache", allEntries = true)
    @Transactional
    @Override
    public void updateWithFlavor(DishDto dishDto) {

        // update dish info
        this.updateById(dishDto);

        // clean current dish's flavor -- delete operation from dish_flavor table
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dishDto.getId());
        dishFlavorService.remove(queryWrapper);

        // renew flavor -- insert operation for dish_flavor table
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item)->{
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);

//        String key = "dish_" + dishDto.getCategoryId() + "_1";
//        redisTemplate.delete(key);
    }

    @CacheEvict(value = "dishCache", allEntries = true)
    @Override
    public void changeStatusById(Integer status, List<Long> ids) {

        List<Dish> dishes = new ArrayList<>();
        for (Long id : ids) {
            Dish dish = new Dish();
            dish.setId(id);
            dish.setStatus(status);
            dishes.add(dish);
        }

        this.updateBatchById(dishes);
    }

    @CacheEvict(value = "dishCache", allEntries = true)
    @Override
    public void deleteById(List<Long> ids) {

        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Dish::getId, ids);
        queryWrapper.eq(Dish::getStatus, 1);

        int count = this.count(queryWrapper);
        if (count > 0) {
            throw new CustomException("onsale now..., which is not be able to be deleted...");
        }

        this.removeByIds(ids);
    }

//    @Override
//    public List<Dish> getSelectedList(Dish dish) {
//
//        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(Dish::getCategoryId, dish.getCategoryId());
//        queryWrapper.eq(Dish::getStatus, 1);
//        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
//
//        List<Dish> list = this.list(queryWrapper);
//
//        return list;
//    }

    @Cacheable(value = "dishCache", key = "#dish.categoryId + '_' + #dish.status", unless = "#result == null")
    @Override
    public List<DishDto> showDishInList(Dish dish) {

        List<DishDto> dishDtoList = null;

        // generate key for dishes in different category
        //String key = "dish_" + dish.getCategoryId() + "_" + dish.getStatus();

        // get data from redis first
        //dishDtoList = (List<DishDto>) redisTemplate.opsForValue().get(key);

        // if there is data in redis, return the result
//        if (dishDtoList != null) {
//            log.info("query data from redis");
//            return dishDtoList;
//        }

        // else query from database
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
        List<Dish> dishList = this.list(dishLambdaQueryWrapper);

        dishDtoList = dishList.stream().map((item)->{
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);

            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                dishDto.setCategoryName(category.getName());
            }

            Long dishId = item.getId();
            LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
            dishFlavorLambdaQueryWrapper.eq(DishFlavor::getDishId, dishId);
            List<DishFlavor> dishFlavorList = dishFlavorService.list(dishFlavorLambdaQueryWrapper);

            dishDto.setFlavors(dishFlavorList);

            return dishDto;
        }).collect(Collectors.toList());

        // store data into redis
        //redisTemplate.opsForValue().set(key, dishDtoList, 30, TimeUnit.MINUTES);

        return dishDtoList;
    }
}
