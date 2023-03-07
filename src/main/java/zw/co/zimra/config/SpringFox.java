package zw.co.zimra.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * @author Andrew
 */
@Configuration
@EnableSwagger2
public class SpringFox {

    @Bean
    public Docket apiDocumentationBean() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("zw.co.zimra"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(new ApiInfoBuilder()
                        .version("1.0")
                        .title("ZIMRA App")
                        .contact(new Contact("ZIMRA", "https://zimra.co.zw/",
                                "amunhanga@zimra.co.zw/"))
                        .description("Documentation for ZIMRA application")
                        .build());
    }
}

