package com.example.checkcheck.common.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    @Bean
    fun openApi() : OpenAPI = OpenAPI()
        .components(Components())
        .info(swaggerInfo())

    private fun swaggerInfo() : Info = Info()
        .title("췍췍 서버 Api 명세")
        .description("A&I 미니 프로젝트 2팀 췍췍 서버의 Api 명세서입니다.")
        .version("1.0.0")
}