package com.moex.mp.sys.scheduler.mpsysscheduler.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.RelativePathProvider;
import springfox.documentation.spring.web.plugins.Docket;

import javax.servlet.ServletContext;
import java.sql.Date;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class SwaggerDocumentationConfig {

    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES =
            new HashSet<>(Collections.singletonList("application/json"));

    @Bean
    ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("SchedulerService")
                .description("Scheduler Service")
                .license("")
                .licenseUrl("http://unlicense.org")
                .termsOfServiceUrl("")
                .version("1.0.0")
                .contact(new Contact("Sergey Chernolyas", "", "sergey_chernolyas@epam.com"))
                .build();
    }

    @Bean
    public Docket customImplementation(ServletContext servletContext){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.moex.mp.sys.scheduler.mpsysscheduler"))
                .build()

                .pathProvider(new RelativePathProvider(servletContext) {
                    @Override
                    public String getApplicationBasePath() {
                        return "/api/v1/";
                    }
                })
                .consumes(DEFAULT_PRODUCES_AND_CONSUMES)
                .produces(DEFAULT_PRODUCES_AND_CONSUMES)
                .directModelSubstitute(LocalDate.class, Date.class)
                .directModelSubstitute(OffsetDateTime.class, java.util.Date.class)
                .apiInfo(apiInfo());
    }
}
