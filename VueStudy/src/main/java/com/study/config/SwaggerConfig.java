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
                                .paths(PathSelectors.regex("/swagger.*/.*"))  // 使用正则表达式，约束生成API文档的路由地址
                                .build();

        docket = docket.groupName("RAIN7");// 设置当前这一组文档的 空间名，或者设置docket的名字，因为可以有多个docket对应小组中不同人的接口


           return docket;
    }
}
