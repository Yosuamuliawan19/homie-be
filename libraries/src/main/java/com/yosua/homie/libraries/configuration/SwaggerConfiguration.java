package com.yosua.homie.libraries.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

  private static final String STRING = "string";
  private static final String HEADER = "header";

  @Bean
  public Docket init() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.yosua.homie.rest.web.controller"))
        .paths(regex("/.*"))
        .build()
        .globalOperationParameters(Arrays.asList(
            new ParameterBuilder().name("accessToken").parameterType(HEADER)
                .modelRef(new ModelRef(STRING)).required(true).defaultValue("TOKEN")
                .description("user's access token").build(),
            new ParameterBuilder().name("channelId").parameterType(HEADER)
                .modelRef(new ModelRef(STRING)).required(true).defaultValue("WEB")
                .description("client's channel id").build()))
        .genericModelSubstitutes(DeferredResult.class, ResponseEntity.class);
  }
}