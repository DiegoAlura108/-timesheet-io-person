package br.com.timesheetio.person.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.client.LinkDiscoverer;
import org.springframework.hateoas.client.LinkDiscoverers;
import org.springframework.hateoas.mediatype.collectionjson.CollectionJsonLinkDiscoverer;
import org.springframework.plugin.core.SimplePluginRegistry;
import org.springframework.stereotype.Component;

import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Component
@EnableSwagger2
public class SwaggerConfig {
    
    @Bean
    public LinkDiscoverers discoverers() {
        List<LinkDiscoverer> plugins = new ArrayList<>();
        plugins.add(new CollectionJsonLinkDiscoverer());
        return new LinkDiscoverers(SimplePluginRegistry.create(plugins));
    }

    @Bean
    public Docket customImplementation(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
                .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.cloud")))
                .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.data.rest.webmvc")))
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
