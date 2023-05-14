package org.itheima.reggie.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import javax.sound.midi.MetaEventListener;
import java.time.LocalDateTime;

@Component
@Slf4j
public class MyMetaObjecthandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {

        log.info("公共字段自动填充[upfate]...");
        log.info(metaObject.toString());
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("UpdateTime", LocalDateTime.now());
        metaObject.setValue("createUser", BaseContext.getCurrentId());
        metaObject.setValue("uodateUser", BaseContext.getCurrentId());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("公共字段自动填充[upfate]...");
        log.info(metaObject.toString());

        Long id=Thread.currentThread().getId();
        log.info("线程id为: {}",id);
/*
        metaObject.setValue("UpdateTime", LocalDateTime.now());
        metaObject.setValue("uodateUser",BaseContext.getCurrentId());*/
    }
}
