package com.mxl.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mxl.reggie.entity.Employee;
import com.mxl.reggie.mapper.EmployeeMapper;
import com.mxl.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee>  implements EmployeeService {

}
