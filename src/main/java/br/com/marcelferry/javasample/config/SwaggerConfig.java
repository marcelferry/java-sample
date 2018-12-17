package br.com.marcelferry.javasample.config;

import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.base.Predicate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {                                    
    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.any())              
          .paths(multipartPaths()) 
          .build()
          .apiInfo(apiInfo())
          .useDefaultResponseMessages(false)                                   
          .globalResponseMessage(RequestMethod.GET,                     
            newArrayList(
            		new ResponseMessageBuilder()   
	              .code(500)
	              .message("Erro interno na aplicação")
	              .responseModel(new ModelRef("Error"))
	              .build(),
	            new ResponseMessageBuilder() 
	                .code(403)
	                .message("Você não tem acesso ao recurso solicitado")
	                .build(),
	            new ResponseMessageBuilder() 
	                .code(401)
	                .message("Você não está autenticado para acessar esse recurso")
	                .build()));
    }
    
    private Predicate<String> multipartPaths() {
        return regex("/api/v1/.*");
    }
    
    private ApiInfo apiInfo() {

        ApiInfo apiInfo = new ApiInfoBuilder()
                .title ("API de Exemplo")
                .description ("Essa é a API de Exemplo criada para o treinamento.")
                .license("License of API")
                .licenseUrl("API license URL")
                .termsOfServiceUrl("Terms of service")
                .version("1.0.0")
                .contact(new Contact("MarcelFerry","www.marcelferry.com", "me@marcelferry.com.br"))
                .build();
        		
        return apiInfo;
    }
}