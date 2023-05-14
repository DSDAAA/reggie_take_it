package org.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.itheima.reggie.entity.Employee;
import org.itheima.reggie.mapper.EmployeeMapper;
import org.itheima.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

}
