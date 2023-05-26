package dy.whatsong.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableWebMvc
public class SwaggerConfig {

	private static final String API_NAME = "WhatSong API 명세서";
	private static final String API_VERSION = "v.1.0.0";
	private static final String API_DESCRIPTION = "WhatSong API Request Spec";


	@Bean
	public Docket swaggerAPI(){
		return new Docket(DocumentationType.OAS_30)
				.useDefaultResponseMessages(true)
				.select()
				.apis(RequestHandlerSelectors.basePackage("dy.whatsong.domain"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title(API_NAME)
				.description(API_DESCRIPTION)
				.version(API_VERSION)
				.build();
	}

}
