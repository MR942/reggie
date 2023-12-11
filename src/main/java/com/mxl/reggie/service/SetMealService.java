package com.mxl.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mxl.reggie.DTO.SetmealDto;
import com.mxl.reggie.entity.Setmeal;

import java.util.List;

public interface SetMealService extends IService<Setmeal> {
    /**
     * 新增套餐，并且存放到两个表中
     * @param setmealDto
     */
    public void saveWithDish(SetmealDto setmealDto);

    /**
     * 删除套餐和菜品关联数据
     * @param ids
     */
    public void removeWith(List<Long> ids);
}
