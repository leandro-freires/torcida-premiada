package br.com.apptrechos.torcidapremiada.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import br.com.apptrechos.torcidapremiada.service.OrgaoService;

@Configuration
@ComponentScan(basePackageClasses = { OrgaoService.class })
public class ServiceConfig {

}
