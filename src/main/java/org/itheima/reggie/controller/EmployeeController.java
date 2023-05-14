package org.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.itheima.reggie.common.R;
import org.itheima.reggie.entity.Employee;
import org.itheima.reggie.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    /**
     *
     *
     */
    @Autowired
    private EmployeeService employeeService;
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request,@RequestBody Employee employee){
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);
        if(emp==null){
            return R.error("登录失败");
        }
        if(!emp.getPassword().equals(password)){
            return R.error("登录失败");
        }
        if(emp.getStatus()==0){
            return R.error("账号已禁用");
        }
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }
    @PostMapping
    public R<String> save(HttpServletRequest request,@RequestBody Employee employee){
        log.info("新增员工，员工信息：{}",employee.toString());

        //设置初始密码123456，需要进行md5加密
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        /*employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());*/
        /*Long empId = (Long) request.getSession().getAttribute("employee");
        employee.setCreateUser(empId);
        employee.setUpdateUser(empId);*/

        employeeService.save(employee);
        return R.success("新增员工成功");
    }
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        log.info("page = {},pageSize = {},name = {}",page,pageSize,name);

        Page pageInfo=new Page(page,pageSize);

        LambdaQueryWrapper<Employee> queryWrapper=new LambdaQueryWrapper();

        queryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);

        queryWrapper.orderByDesc(Employee::getUpdateTime);

        employeeService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }
    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody Employee employee){
        log.info(employee.toString());

        Long empId=(Long)request.getSession().getAttribute("employee");
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(empId);
        employeeService.updateById(employee);
        return R.success("员工信息修改成功");
    }
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        log.info("根据id查询员工信息");
        Employee employee=employeeService.getById(id);
        return R.success(employee);
    }
}
