package com.mxl.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mxl.reggie.common.CustomException;
import com.mxl.reggie.entity.Category;
import com.mxl.reggie.entity.Dish;
import com.mxl.reggie.entity.Setmeal;
import com.mxl.reggie.mapper.CategoryMapper;
import com.mxl.reggie.service.CategoryService;
import com.mxl.reggie.service.DishService;
import com.mxl.reggie.service.SetMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService{

    @Autowired
    private DishService dishService;
    @Autowired
    private SetMealService setMealService;
    /**
     * 根据id删除分类，删除之前进行判断
     * @param id
     */
    @Override
    public void remove(Long id) {
        //构造查询条件
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件，根据分类id进行查询
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int count = dishService.count(dishLambdaQueryWrapper);

        // 分类关联菜品，抛出异常
        if(count>0){
            // 已经关联了，抛出异常
            throw new CustomException("当前分类下关联了菜品");
        }

        // 关联套餐，抛出异常
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count1 = setMealService.count(setmealLambdaQueryWrapper);
        if(count1>0){
            // 已经关联了，抛出异常
        throw new CustomException("当前分类下关联了套餐");
        }

        // 正常删除
        super.removeById(id);

    }
}
