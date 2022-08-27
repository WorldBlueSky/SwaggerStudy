package com.study.controller;

import com.study.pojo.User;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/swagger2")
public class ProblemController {
    @PostMapping("/post/{m}/{n}")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "m",value = "m参数的描述",dataType = "int",paramType = "path",example = "1"),
            @ApiImplicitParam(name = "n",value = "n参数的描述",dataType = "int",paramType = "path",example = "1")
    })
    public String  post(@PathVariable("m") int m, @PathVariable("n") int n){
        return String.format("%d+%d=%d",m,n,m+n);
    }

    @ApiOperation("get方法")
    @PutMapping("/getUser")
    public User getUser(){
        User user = new User("admin","admin");
        return user;
    }
}
