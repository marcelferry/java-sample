package br.com.marcelferry.javasample.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("br.com.marcelferry.javasample.data.repository.jpa")
@EntityScan(basePackages="br.com.marcelferry.javasample.data.model.jpa")
public class JpaConfig {

}
