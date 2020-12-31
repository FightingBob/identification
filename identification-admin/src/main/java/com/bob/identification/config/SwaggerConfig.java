package com.bob.identification.config;

import com.bob.identification.common.config.BaseSwaggerConfig;
import com.bob.identification.common.domain.SwaggerProperties;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**Swagger API文档配置
 * Created by LittleBob on 2020/12/17/017.
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig extends BaseSwaggerConfig {
    @Override
    protected SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.bob.identification.controller")
                .title("防伪后台系统")
                .description("防伪后台相关接口文档")
                .contactName("bob")
                .version("2.0")
                .enableSecurity(true)
                .build();
    }
}
