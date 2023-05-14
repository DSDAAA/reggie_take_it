package org.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.itheima.reggie.entity.Setmeal;
import org.itheima.reggie.mapper.SetmealMapper;
import org.itheima.reggie.service.SetmealService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
}
