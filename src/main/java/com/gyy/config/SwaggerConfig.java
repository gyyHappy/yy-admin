package com.gyy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger 配置类
 * @author GYY
 * @version 1.0
 * @date 2020/7/4 20:51
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${swagger2.enable}")
    private boolean enable;

    @Bean
    public Docket createDocket(){
        List<Parameter> parameterList = new ArrayList<>();

        ParameterBuilder tokenPar = new ParameterBuilder();
        tokenPar.name("token")
                .description("swagger测试用（模拟token传入）非必填 header")
                .modelRef(new ModelRef("String"))
                .parameterType("header")
                .required(false);
        parameterList.add(tokenPar.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())//创建该Api的基本信息（这些基本信息会展现在文档页面中)
                .select()//函数返回一个ApiSelectorBuilder实例用来控制哪些接口暴露给Swagger ui来展现
                .apis(RequestHandlerSelectors.basePackage("com.gyy.controller"))//指定需要扫描的包路径
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(parameterList)
                .enable(enable);
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("yy-admin")
                .description("yy-admin 接口文档")
                .termsOfServiceUrl("")
                .version("1.0")
                .build();
    }
}