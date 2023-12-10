package com.mxl.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mxl.reggie.entity.Setmeal;
import com.mxl.reggie.mapper.SetMealMapper;
import com.mxl.reggie.service.SetMealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SetMealServiceImpl extends ServiceImpl<SetMealMapper, Setmeal> implements SetMealService {
}
