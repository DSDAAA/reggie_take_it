package org.itheima.reggie.controller;

import lombok.extern.slf4j.Slf4j;
import org.itheima.reggie.common.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/Common")
@Slf4j
public class CommonController {
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){

        log.info(file.toString());
        return null;
    }
}
