package com.study.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "User类的名字",description = "User类的描述")
public class User implements Serializable {
    @ApiModelProperty(name = "username的名字",value = "username的描述",required = true,example = "admin",hidden = false)
    public String username;

    @ApiModelProperty(name = "password的名字",value = "password的描述",required = true,example = "admin",hidden = false)
    public String password;

    public String getUsername() {
        return username;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
