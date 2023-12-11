package com.mxl.reggie.DTO;

import com.mxl.reggie.entity.Setmeal;
import com.mxl.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
