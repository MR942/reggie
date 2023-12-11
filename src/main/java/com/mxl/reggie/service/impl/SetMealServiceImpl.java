package com.mxl.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mxl.reggie.DTO.SetmealDto;
import com.mxl.reggie.common.CustomException;
import com.mxl.reggie.entity.Setmeal;
import com.mxl.reggie.entity.SetmealDish;
import com.mxl.reggie.mapper.SetMealMapper;
import com.mxl.reggie.service.SetMealService;
import com.mxl.reggie.service.SetmealDishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SetMealServiceImpl extends ServiceImpl<SetMealMapper, Setmeal> implements SetMealService {

    @Autowired
    private SetmealDishService setmealDishService;
    /**
     * 新增套餐，并且存放到两个表中
     * @param setmealDto
     */
    @Override
    @Transactional  //添加事务注解 因为涉及到两个表
    public void saveWithDish(SetmealDto setmealDto) {
        //1 保存套餐的基本信息 setmeal
        this.save(setmealDto);

        //2 保存和菜品的关联信息 setmeal_dish表
        List<SetmealDish> setmealDishList = setmealDto.getSetmealDishes();
        setmealDishList.stream().map((item)->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());


        setmealDishService.saveBatch(setmealDishList);
    }

    /**
     * 删除套餐和菜品关联数据
     * @param ids
     */
    @Override
    @Transactional
    public void removeWith(List<Long> ids) {
        //查询套餐状态，可以删除再删

        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId,ids);
        queryWrapper.eq(Setmeal::getStatus,1);
        int count = this.count(queryWrapper);
        if(count>0){
            //不能删除，抛出异常
            throw new CustomException("套餐正在售卖中，不能删除");
        }
        //可以删除先删除套餐表数据 -----setmeal

        this.removeByIds(ids);
        //删除关系表数据

        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SetmealDish::getDishId,ids);

        setmealDishService.remove(lambdaQueryWrapper);

    }
}
