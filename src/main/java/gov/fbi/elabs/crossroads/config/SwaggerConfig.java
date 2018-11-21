package gov.fbi.elabs.crossroads.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    
    private static final String env = System.getenv("DEPLOY_ENV");
    @Bean
    public Docket feedsApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select().apis(RequestHandlerSelectors.basePackage("gov.fbi"))
                .build();
                
    }
    
    public ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("CrossRoads API")
                .description("Swagger Documentation for all the CrossRoads API")
                .version("1.0.0")
                .build();
    }
}