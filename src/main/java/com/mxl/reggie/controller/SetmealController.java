package com.mxl.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mxl.reggie.DTO.SetmealDto;
import com.mxl.reggie.common.R;
import com.mxl.reggie.entity.Category;
import com.mxl.reggie.entity.Setmeal;
import com.mxl.reggie.service.CategoryService;
import com.mxl.reggie.service.SetMealService;
import com.mxl.reggie.service.SetmealDishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 套餐管理
 */
@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {
    @Autowired
    private SetmealDishService setmealDishService;
    @Autowired
    private SetMealService setMealService;
    @Autowired
    private CategoryService categoryService;

    /**
     * 新增套餐
     * @param setmealDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){
        log.info(setmealDto.toString());
        setMealService.saveWithDish(setmealDto);
        return R.success("新增套餐成功");
    }

    @GetMapping("/page")
    public R<Page> page(int page,int pageSize, String name){

        //分页构造器对象
        Page<Setmeal> pageinfo = new Page<>(page,pageSize);
        //因为上面那个对象不能覆盖所有信息
        Page<SetmealDto> setmealDtoPage = new Page<>();
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(name!= null,Setmeal::getName,name);
        //添加排序条件
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        setMealService.page(pageinfo,queryWrapper);
        BeanUtils.copyProperties(pageinfo,setmealDtoPage,"record");

        //新建record对象
        List<Setmeal> records = pageinfo.getRecords();
        List<SetmealDto> list  = records.stream().map((item)->{
            SetmealDto setmealDto = new SetmealDto();
            //进行拷贝
            BeanUtils.copyProperties(item,setmealDto);
            //分类id
            Long categoryId = item.getCategoryId();
            //根据分类id查询分类对象
            Category category = categoryService.getById(categoryId);
            //判断
            if(category!=null){
                //找到名称
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }
            return setmealDto;

        }).collect(Collectors.toList());
        setmealDtoPage.setRecords(list);
        return R.success(setmealDtoPage);
    }

    /**
     * 删除套餐
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        log.info(ids.toString());
        setMealService.removeWith(ids);
        return R.success("删除成功");
    }

}
