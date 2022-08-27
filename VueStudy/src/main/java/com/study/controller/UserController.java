package com.study.controller;

import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/swagger")
@Api(tags = {"User控制器","User信息增删改查"},description = "这是当前控制器的描述")
public class UserController {

    @GetMapping("/get")
    @ApiOperation(value = "get方法的描述",notes = "get方法的笔记")
    @ApiImplicitParams(value = { @ApiImplicitParam(name = "用户名",value = "用于新增的用户名",paramType = "body",dataType = "String",required = true,example = "admin"),
            @ApiImplicitParam(name = "密码",value = "用于新增的密码",paramType = "body",dataType = "String",required = true,example = "admin")}
    )

    public String get(@RequestBody String username,@RequestBody String password){
        return "get";
    }

}
