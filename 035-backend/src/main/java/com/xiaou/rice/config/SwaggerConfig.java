package com.xiaou.rice.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("水稻收割预约系统 API")
                        .version("v1.0")
                        .description("乡村振兴水稻收割预约与调度后端接口"))
                .externalDocs(new ExternalDocumentation()
                        .description("项目说明")
                        .url("https://example.com"));
    }
}
