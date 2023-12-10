package com.mxl.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mxl.reggie.DTO.DishDto;
import com.mxl.reggie.entity.Dish;

public interface DishService extends IService<Dish> {
    //新增菜品，同时插入菜品对应的口味数据，需要操作；两张表：dish\dishF
    public void saveWithFlavor(DishDto dishDto);

    // 根据id查询菜品信息和相应口味信息
    public DishDto getByIdWithFlavor(Long id);

    public void updateWithFlavor(DishDto dishDto);

}
