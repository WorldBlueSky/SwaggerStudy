package com.study.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/swagger")
@Api(tags = {"User控制器","User信息增删改查"},description = "这是当前控制器的描述")
public class UserController {

    @GetMapping("/get")
    @ApiOperation(value = "get方法的描述",notes = "get方法的笔记")
    public String get(String username,String password){
        return "get";
    }

    @PostMapping("/post")
    public String post(int a,int b){
        return "post";
    }
}
