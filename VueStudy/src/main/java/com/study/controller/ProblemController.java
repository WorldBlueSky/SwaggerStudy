package com.study.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/swagger2")
public class ProblemController {
    @PostMapping("/post")
    public String  post(){
        return "/swagger2/post";
    }
}
