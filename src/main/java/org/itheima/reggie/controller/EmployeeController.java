package org.itheima.reggie.controller;

import lombok.extern.slf4j.Slf4j;
import org.itheima.reggie.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeChontroller {

    @Autowired
    private EmployeeService employeeService;
}