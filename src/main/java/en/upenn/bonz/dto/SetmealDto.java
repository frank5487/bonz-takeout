package en.upenn.bonz.dto;

import en.upenn.bonz.entity.Setmeal;
import en.upenn.bonz.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
