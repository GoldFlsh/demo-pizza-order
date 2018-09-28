package org.ryank.pizza.create;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.concurrent.ListenableFuture;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class DemoPizzaCreateApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoPizzaCreateApplication.class, args);
  }

  /**
   * @return Springfox swagger Docket with API Information
   */
  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
          .apis(RequestHandlerSelectors.basePackage("org.ryank"))
          .paths(PathSelectors.any())
          .build()
        .pathMapping("/pizza")
        .apiInfo(apiInfo())
        .tags(new Tag("Pizza Service", "APIs related to creating pizzas for use in ordering"))
        .enableUrlTemplating(true)
        .useDefaultResponseMessages(false);

  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder().title("Pizza Service")
        .description("Allows building new pizzas for order")
        .build();
  }
}
