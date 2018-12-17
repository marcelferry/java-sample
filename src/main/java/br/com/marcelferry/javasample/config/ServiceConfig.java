package br.com.marcelferry.javasample.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "br.com.marcelferry.javasample.bussines.service" })
public class ServiceConfig {
}
