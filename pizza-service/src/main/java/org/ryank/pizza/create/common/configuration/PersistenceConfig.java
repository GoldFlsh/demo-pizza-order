package org.ryank.pizza.create.common.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableJpaRepositories(basePackages = "org.ryank")
@Configuration
public class PersistenceConfig {

}
