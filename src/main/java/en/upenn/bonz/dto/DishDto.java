package en.upenn.bonz.dto;

import en.upenn.bonz.entity.Dish;
import en.upenn.bonz.entity.DishFlavor;
import lombok.Data;
import java.util.List;

@Data
public class DishDto extends Dish {

    //菜品对应的口味数据
    private List<DishFlavor> flavors;

    private String categoryName;

    private Integer copies;
}
