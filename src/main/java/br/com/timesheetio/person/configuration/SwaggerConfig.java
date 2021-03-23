package br.com.timesheetio.person.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.OAuth;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    
	@Value("${host.link.authorization}")
	private String linkAuthorization;
	
	public static final String TAG_PERSON_OPERATION = "People management";
	
    @Bean
    public Docket customImplementationVersionOne(){
        return new Docket(DocumentationType.SWAGGER_2)
        		.useDefaultResponseMessages(false)
        		.groupName("version 1.0.0")
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.timesheetio.person"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfoVersionOne())
                .tags(new Tag(TAG_PERSON_OPERATION, "Set of operations used to manage the registration of people."))
                .securitySchemes(Collections.singletonList(getSecuritySchemes()))
                .securityContexts(Arrays.asList(getSecurityContext()));
    }    
    
    @Bean
    public Docket customImplementationVersionTwo(){
        return new Docket(DocumentationType.SWAGGER_2)
        		.useDefaultResponseMessages(false)
        		.groupName("version 2.0.0")
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.timesheetio.person"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfoVersionTwo())
                .tags(new Tag(TAG_PERSON_OPERATION, "Set of operations used to manage the registration of people."));
    }  
	
    ApiInfo apiInfoVersionOne() {
    	
        return new ApiInfoBuilder()
            .title("Timesheet.io Person")
            .description("Responsible for managing people on timesheet.io")
            .license("MIT")
            .licenseUrl("http://opensource.org/licenses/MIT")
            .termsOfServiceUrl("http://www.youtube.com")
            .version("1.0.0")
            .contact(new Contact("Diego de J Cordeiro","http://localhost:8081/support", "planosdiego@gmail.com"))
            .build();
    }
    
    ApiInfo apiInfoVersionTwo() {
    	
        return new ApiInfoBuilder()
            .title("Timesheet.io Person")
            .description("Responsible for managing people on timesheet.io")
            .license("MIT")
            .licenseUrl("http://opensource.org/licenses/MIT")
            .termsOfServiceUrl("http://www.youtube.com")
            .version("2.0.0")
            .contact(new Contact("Diego de J Cordeiro","http://localhost:8081/support", "planosdiego@gmail.com"))
            .build();
    }
    
    private OAuth getSecuritySchemes() {
    	
    	List<GrantType> grantTypes = new ArrayList<>();
    	grantTypes.add(new ResourceOwnerPasswordCredentialsGrant(linkAuthorization));
    	
    	List<AuthorizationScope> authorizationScopes = new ArrayList<>();
    	authorizationScopes.add(new AuthorizationScope("read", "Somente consulta liberada."));
    	authorizationScopes.add(new AuthorizationScope("write", "Somente escrita liberada."));
    	
    	return new OAuth("Authorization Scope", authorizationScopes, grantTypes);
    }
    
    private SecurityContext getSecurityContext(){
    	
    	AuthorizationScope[] authorizationScopes = new AuthorizationScope [] {
    														new AuthorizationScope("read", "Somente consulta liberada."),
    														new AuthorizationScope("write", "Somente escrita liberada.")};
    	
    	List<SecurityReference> securityReferences = new ArrayList<>();
    	securityReferences.add(new SecurityReference("Authorization Scope", authorizationScopes));
    	
    	return SecurityContext.builder()
    			.securityReferences(securityReferences)
    			.build();
    }
}
