package org.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.itheima.reggie.common.CustomException;
import org.itheima.reggie.entity.Category;
import org.itheima.reggie.entity.Dish;
import org.itheima.reggie.entity.Setmeal;
import org.itheima.reggie.mapper.CategoryMapper;
import org.itheima.reggie.service.CategoryService;
import org.itheima.reggie.service.DishService;
import org.itheima.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper,Category> implements CategoryService{
    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper=new LambdaQueryWrapper<Dish>();

        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int count1 = dishService.count(dishLambdaQueryWrapper);


        if (count1>0){
            throw new CustomException("当前分类下关联了菜品，不能删除");
        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper=new LambdaQueryWrapper<>();

        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count2=setmealService.count();
        if(count2>0){
            throw new CustomException("当前菜品已经关联了套餐，不能删除");
        }
        super.removeById(id);
    }
}
