package en.upenn.bonz.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import en.upenn.bonz.dto.SetmealDto;
import en.upenn.bonz.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    void saveWithDish(SetmealDto setmealDto);

    Page<SetmealDto> showSetmealInPage(int page, int pageSize, String name);

    void deleteWithDish(List<Long> ids);

    void changeStatusById(Integer status, List<Long> ids);

    List<Setmeal> getSelectedList(Setmeal setmeal);
}
