package com.vergilyn.examples.swagger.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Swagger配置类
 *
 * @see <a href="https://github.com/binarywang/weixin-java-pay-demo/blob/master/src/main/java/com/github/binarywang/demo/wx/pay/config/SwaggerConfig.java">SwaggerConfig</a>
 */
@Configuration
@EnableSwagger2
@Slf4j
public class SwaggerConfig extends WebMvcConfigurationSupport implements EnvironmentAware {
	private Environment environment;

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html")  // 访问地址
				.addResourceLocations("classpath:/META-INF/resources/");

		registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

		super.addResourceHandlers(registry);
	}

	@Bean
	public Docket docket() {
		//最重要的就是这里，定义了/test/.*开头的rest接口都分在了test分组里，可以通过/v2/api-docs?group=test得到定义的json
		log.info("Starting Swagger");
		StopWatch watch = new StopWatch();
		watch.start();
		Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .groupName("swagger")
                .apiInfo(this.apiInfo())
                .select()
				.apis(RequestHandlerSelectors.any())
                .paths(regex("/swagger/.*"))
                .build();
		watch.stop();
		log.info("Started Swagger in {} ms", watch.getTotalTimeMillis());
		return docket;
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
                .title("vergilyn-swagger-examples")
                .description("vergilyn swagger演示接口")
				.contact(new Contact("vergilyn", "https://github.com/vergilyn", "vergilyn@vip.qq.com"))
                .license("")
				.licenseUrl("").version("1.0.0").build();
	}

}
