package com.vergilyn.examples.swagger.controller;

import com.alibaba.fastjson.JSON;
import com.vergilyn.examples.swagger.dto.UserDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author VergiLyn
 * @date 2020-01-13
 */
@Api("测试swagger")
@RestController
@RequestMapping("/swagger")
@Slf4j
public class SwaggerTestController {

    public static final String SUCCESS = "success";

    @ApiOperation(value = "json对象参数")
    @PostMapping("/json_object_param")
    public String jsonObjectParam(@RequestBody UserDto user) {
        return JSON.toJSONString(user);
    }
}
