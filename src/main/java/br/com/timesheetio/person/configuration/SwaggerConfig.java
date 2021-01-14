package br.com.timesheetio.person.configuration;

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
    
    @Bean
    public Docket customImplementation(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.timesheetio.person"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }    
	
    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("Timesheet.io Person")
            .description("Responsavel por administrar as pessoas do timesheet.io")
            .license("MIT")
            .licenseUrl("http://opensource.org/licenses/MIT")
            .termsOfServiceUrl("http://www.youtube.com")
            .version("1.0.0")
            .contact(new Contact("Diego de J Cordeiro","http://localhost:8081/support", "planosdiego@gmail.com"))
            .build();
    }
}
