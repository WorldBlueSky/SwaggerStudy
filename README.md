@[toc]


<br>

# Swagger2 学习

<br>


#  1、前提准备

<br>

&emsp;&emsp; 在swagger2版本中，需要使用swagger2，并可以从浏览器中ui渲染，必须导入两个依赖 （这里放的是使用人数最多的依赖版本）


<br>


```xml
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>2.9.2</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/io.springfox/springfox-swagger-ui -->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>2.9.2</version>
        </dependency>
```

<br>

经过swagger注解之后，会将接口中的数据渲染到浏览器中，查看接口文档的地址是



```url
localhost:8080/swagger-ui.html
```



在开发的时候前后端分离需要生成接口文档，我们需要在 **启动类** 或者 **配置类** 上打开*Swagger服务,需要使用@EnableSwagger2 注解



```java
package com.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
```

<br>



# 2、快速体验

<br>

&emsp;&emsp;写一下控制层的接口, swagger 会默认根据@Conrtoller注解识别 Controller层里的接口，直接渲染到接口文档的ui界面中

<br>


```java
package com.study.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/get")
    public String get(String username,String password){
        return "get";
    }

    @PostMapping("/post")
    public String post(int a,int b){
        return "post";
    }
}
```



启动项目，输入swagger-ui的地址

```url
localhost:8080/swagger-ui.html
```



因为很多都没有进行配置，所以很多部分显示的都是默认信息，

<br>

![在这里插入图片描述](https://img-blog.csdnimg.cn/184030d9142646a181c9858065138d2a.png)

<br>

我们写的控制层接口已经识别到了，UserController。

<br>

![在这里插入图片描述](https://img-blog.csdnimg.cn/c644d0816ced4f17a005c696c16c5f45.png)

<br>

点开具体的接口，查看接口文档的具体信息

<br>

![在这里插入图片描述](https://img-blog.csdnimg.cn/f0ad0cf5c11c4840b247f5743926d2de.png)




<br>

# 3、Swagger 配置
<br>


## （1）设置基本信息

<br>

**Docket** :描述一组文档的所有信息，相当于Swagger文档全局的上下文对象，可以创建多个docket实现文档分组查看不同人员写的接口

<br>



```java
    /**
     * 创建以Docket类型的对象，并使用Spring容器进行管理
     * Docket是Swagger中的全局配置对象
     * @return
     */

    @Bean
    public Docket docket(){
       	Docket docket = new Docket(DocumentationType.SWAGGER_2); 
        // 指定Swagger文档的版本
       	return docket;
    }
```

<br>


**ApiInfo** ：是生成文档ui上面的一些作者、网址url、文档描述、文档版本号等信息，这些需要我们手动去设置，这个对象是通过构建器 **ApiInfoBuilder** 得到的


<br>

![在这里插入图片描述](https://img-blog.csdnimg.cn/e1512309341c4ca99e6c1da3f805cd25.png)


<br>

手动设置文档相关信息



```java
        ApiInfoBuilder builder = new ApiInfoBuilder();

        builder.title("文档的标题"); // 文档的标题
        builder.description("这是文档的一些描述，描述描述描述描述描述描述描述描述描述描述"); // 文档的描述
        builder.contact(new Contact("文档名字","相关的网站地址","相关的邮箱"));// 文档名字--对应的连接--发送的邮箱地址
        builder.version("2,0");// 版本号
        builder.license("文档相关的许可证"); // 许可证名字
        builder.licenseUrl("文档相关的许可证地址");// 许可证对应的网站地址

        ApiInfo apiInfo = builder.build();
        docket.apiInfo(apiInfo);
```

<br>

运行项目，查看效果

<br>

![在这里插入图片描述](https://img-blog.csdnimg.cn/6ac5cb2385be4ded93cd6976105ccca7.png)


<br>


## （2）设置接口文档的相关配置

<br>

&emsp;&emsp;主要通过 **ApiSelectBuilder** 提供的方法来进行设置接口的一些配置，ApiSelectBuilder 通过 docket.select() 方法拿到，最后需要使用build方法 在覆盖docket对象即可完成设置。如同下面的方式



```java
docket = dokcet.select().method1().method2.build();
```



介绍方法



![在这里插入图片描述](https://img-blog.csdnimg.cn/0d2f5d5b50ca4a4588a661a80b13d59c.png)



<br>


####  apis方法

<br>

实现设置包扫描路径、设置注解使用规则等功能



**参数**  **Predicate** 这个是规则选择器，我们会使用一些子类作为参数使用



> **RequestHandlerSelectors** 这个类型是选择处理器，也提供很多静态方法供我们使用，进行接口设置。

> **PathSelectors** 这个是路径选择器，可通过他提供的regex方法，使用正则表达式选择路由创建文档

> **Predicates**，规则相关的类提供的很多静态方法，取反、非空等



![在这里插入图片描述](https://img-blog.csdnimg.cn/a64b5fde50cb4aecb3ee5bfa43d84c0f.png)



<br>

####  paths方法 

设置符合路由文档创建，其中使用表达式

<br>

#### build 方法

将build的对象重新赋给docket

<br>

#### 1）设置扫描包路径
<br>


swagger默认是扫描启动类所在的包以及所有子包的路径，我们可以手动的进行指定



通过apis方法中的参数 **RequestHandlerSelectors** 提供的类方法可以实现扫描

![在这里插入图片描述](https://img-blog.csdnimg.cn/e7fd64df40f349159461b6cb0020c06d.png)


```java
 // select() 返回的是APISelectBuilder,提供很多方法
        ApiSelectorBuilder selectorBuilder = docket.select();
        selectorBuilder.apis(RequestHandlerSelectors.basePackage("com.study.controller"));
```

<br>

#### 2）自定义注解进行使用

<br>

这个就不讲了，一般用不着

<br>

#### 3）路由范围决定
<br>


决定某些路由下的接口可以创建文档，路由之外的路由不可以创建文档，使用paths方法



```java
  selectorBuilder.paths(PathSelectors.regex("/swagger/.*"));// 使用正则表达式，约束生成API文档的路由地址
         // 上面正则表达式的意思是 以 swagger开头的后面匹配任意多个字符的路由
```



#### 4）配置生效

<br>

使用build方法，再将配置好的docket对象赋给先前创建的docket



```java
docket = selectBuilder.build();
```



## （3）配置小结
<br>


通过配置docket我们做了一下事情

<br>


- 设置文档的基本信息（题目、描述...）
- 完成接口的一些创建规则（扫描具体路径、自动义注解、路由范围决定）
- 将之前设置的所有信息返回给docket，最终由spring托管生效


<br>


下面我们放一下swagger配置的完整的代码


<br>


```java
package com.study.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * 创建以Docket类型的对象，并使用Spring容器进行管理
     * Docket是Swagger中的全局配置对象
     * @return
     */

    @Bean
    public Docket docket(){

        Docket docket = new Docket(DocumentationType.SWAGGER_2); // 指定Swagger文档的版本

        ApiInfo apiInfo =new ApiInfoBuilder().title("Swagger2 学习文档")
                                             .contact(new Contact("Swagger名字小字","https://www.baidu.com","chenruiqi@163.com"))
                                             .description("这是文档的描述信息")
                                             .version("2.9")
                                             .license("许可证信息")
                                             .licenseUrl("https://www.baidu.com")
                                             .build();
        docket.apiInfo(apiInfo);

        // 由select() 生成selectBuilder,放入创建文档的规则等，在通过 build 返还给docket，使配置生效
        docket = docket.select().apis(RequestHandlerSelectors.basePackage("com.study.controller")) // 设置扫描包路径
                                .paths(PathSelectors.regex("/swagger/.*"))  // 使用正则表达式，约束生成API文档的路由地址
                                .build();
        
        docket = docket.groupName("RAIN7");// 设置当前这一组文档的 空间名，或者设置docket的名字，因为可以有多个docket对应小组中不同人的接口

           return docket;
    }
}

```

<br>

controller层的接口，设置 “/swagger” 路由

<br>


```java
package com.study.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/swagger")
public class UserController {

    @GetMapping("/get")
    public String get(String username,String password){
        return "get";
    }

    @PostMapping("/post")
    public String post(int a,int b){
        return "post";
    }
}

```



运行项目，查看结果

<br>

![在这里插入图片描述](https://img-blog.csdnimg.cn/d028bf10e24d4f6193b3c73f3f2dc595.png)

<br>


# 4、Swagger 常用注解

<br>

## （1）@Api
<br>


@Api 是类上的注解，控制整个类生成接口信息的内容



- value:类的名称，菜单的标签，只能当一个值

- tags：菜单的标签，可以有多个值，可以生成多个ui上的接口菜单，也就是当前接口的多个副本

- description：类接口的描述，已经过时



什么是接口菜单？


<br>

![在这里插入图片描述](https://img-blog.csdnimg.cn/2b92be9aaf9c42b6852188c0c9b2cbec.png)




```java
@RestController
@RequestMapping("/swagger")
@Api(tags = {"User控制器","User信息增删改查"},description = "这是当前控制器的描述")
public class UserController {

    @GetMapping("/get")
    public String get(String username,String password){
        return "get";
    }

    @PostMapping("/post")
    public String post(int a,int b){
        return "post";
    }
}
```



运行项目，查看ui效果


<br>


![在这里插入图片描述](https://img-blog.csdnimg.cn/ff415ac6546d44ce872cfba0b4db8e5c.png)

<br>


&emsp;&emsp;通常我们就是在类上使用 默认的value就可以了，在菜单的说明在这个菜单下的接口都是什么类型的，分个类说明一下就可以了。

<br>

## （2）@ApiOperation

<br>

方法注解，可以给类型定义，也可以给方法定义



- value：给当前方法的一个描述

- notes:  方法的标记信息

- tags：方法的多个副本，不太用，字符串数组



在方法上加上注解，标记方法描述 value、方法笔记 notes


<br>


```java
   @GetMapping("/get")
    @ApiOperation(value = "get方法的描述",notes = "get方法的笔记")
    public String get(String username,String password){
        return "get";
    }
```



查看效果接口的ui效果

<br>


![在这里插入图片描述](https://img-blog.csdnimg.cn/a38d5283a7444a86b667d1c5a064b0ac.png)



<br>


## （3）@ApiParam

<br>

描述属性（参数），还可以描述方法，这个注解并不是经常使用，经常使用@ApiImplicitParam作为代替

- name 参数名称

- value  参数的描述

- required  参数是否是必要的，默认为假

- example   参数举例，字符串类型，只能给非body类型的参数提供简单例子

- readOnly  默认为false

  

**如果加上@ApiParam，默认参数使用@RequestBody进行接收才能接收到，默认放到了请求的body中**

<br>

参数也加上 api注解



```java
    @GetMapping("/get")
    @ApiOperation(value = "get方法的描述",notes = "get方法的笔记")
    public String get(@ApiParam(name = "用户名(username)",value = "新增用户需要提供用户名",required = true) String username,
                      @ApiParam(name = "密码(password)",value = "新增用户需要提供密码",required = true) String password){
        return "get";
    }
```

<br>

查看ui效果

<br>

![在这里插入图片描述](https://img-blog.csdnimg.cn/877938e4c46b4c14954be5b92237f050.png)




## （4）@ApiImplicitParam、@ApiImplicitParams

<br>

&emsp;&emsp;放在方法上面,效果和@ApiParam一样，但是 这个注解范围更广,描述唯一的方法参数，我们经常使用这个参数作为方法参数的解释，因为在方法参数前面还得跟springmvc的注解表示参数的接受类型，代码过于冗余了



- name  参数名称
- value   参数描述
- parameterType    代表参数应该放在请求的什么地方 
  -  header-->放在请求头。请求参数的获取：@RequestHeader(代码中接收注解）
  -  query -->用于get请求的参数拼接。请求参数的获取：@RequestParam(代码中接收注解)
  -  path  -->（用于restful接口）-->请求参数的获取：@PathVariable(代码中接收注解)
  - body  -->放在请求体。请求参数的获取：@RequestBody(代码中接收注解)
- required  是否有必要

- dataType 参数的类型


<br>


**@ApiImplicitParams,他就是@ApiImplicitParam的集合，以数组的方式，放入注解中**

<br>



```java
    @PostMapping("/post/{m}/{n}")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "m",value = "m参数的描述",dataType = "int",paramType = "path",example = "1"),
            @ApiImplicitParam(name = "n",value = "n参数的描述",dataType = "int",paramType = "path",example = "1")
    })
    public String  post(@PathVariable("m") int m, @PathVariable("n") int n){
        return String.format("%d+%d=%d",m,n,m+n);
    }
```

<br>

ui效果

<br>

![在这里插入图片描述](https://img-blog.csdnimg.cn/fb2fc8f404554c01a3842142fbb50183.png)


<br>

## （5）@ApiIgnore

 <br>

放在方法上，意思是忽略这个接口，不生成文档

<br>

## （6）@ApiModel+@APiProperty

<br>

&emsp;&emsp;通过注解描述实体类型，为什么要描述实体？因为有时候接口返回的是一个实体对象，所以会生成关于返回对象的解释文档

<br>


**@ApiModel放在实体类上**



- value  实体类的名字

- description 实体类的描述

  



**@ApiProperty放在属性上**



- name  属性的名字
- value   属性的描述
- example  属性填入的值的例子
- required  属性是否必填


<br>

写一个User实体类，进行解释说明


<br>


```java
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

```

<br>

在controller层写一个接口返回User类型的响应

<br>



```java
   @ApiOperation("get方法")
    @PutMapping("/getUser")
    public User getUser(){
        User user = new User("admin","admin");
        return user;
    }
```


<br>

&emsp;&emsp;当我们在控制层的代码有返回值类型是 User对象的话，那么在接口文档的最下面就会有 Models的解释说明

<br>

![在这里插入图片描述](https://img-blog.csdnimg.cn/572570e8cc57468f978c129d94f9bd44.png)


<br>

记录就写到这里，希望大家多多练习!

