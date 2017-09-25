package br.com.apptrechos.torcidapremiada.config;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.support.DomainClassConverter;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.format.number.NumberStyleFormatter;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;
import org.springframework.web.servlet.view.jasperreports.JasperReportsMultiFormatView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsViewResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import com.github.mxab.thymeleaf.extras.dataattribute.dialect.DataAttributeDialect;

import br.com.apptrechos.torcidapremiada.controller.UsuarioController;
import br.com.apptrechos.torcidapremiada.thymeleaf.TorcidaPremiadaDialect;
import nz.net.ultraq.thymeleaf.LayoutDialect;

@Configuration
@EnableWebMvc
@EnableSpringDataWebSupport
@ComponentScan(basePackageClasses = UsuarioController.class)
public class WebConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {
	private ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;	
	}

	private ITemplateResolver templateResolver() {
		SpringResourceTemplateResolver springResourceTemplateResolver = new SpringResourceTemplateResolver();
		springResourceTemplateResolver.setApplicationContext(this.applicationContext);
		springResourceTemplateResolver.setPrefix("classpath:/templates/");
		springResourceTemplateResolver.setSuffix(".html");
		springResourceTemplateResolver.setTemplateMode(TemplateMode.HTML);
		
		return springResourceTemplateResolver;
	}

	@Bean
	public TemplateEngine templateEngine() {
		SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
		springTemplateEngine.setEnableSpringELCompiler(true);
		springTemplateEngine.setTemplateResolver(templateResolver());
		springTemplateEngine.addDialect(new LayoutDialect());
		springTemplateEngine.addDialect(new SpringSecurityDialect());
		springTemplateEngine.addDialect(new DataAttributeDialect());
		springTemplateEngine.addDialect(new TorcidaPremiadaDialect());
		
		return springTemplateEngine;
	}
	
	@Bean
	public ViewResolver jasperReportsViewResolver(DataSource dataSource) {
		JasperReportsViewResolver jasperReportsViewResolver = new JasperReportsViewResolver();
		jasperReportsViewResolver.setPrefix("classpath:/relatorios/");
		jasperReportsViewResolver.setSuffix(".jasper");
		jasperReportsViewResolver.setViewNames("relatorio_*");
		jasperReportsViewResolver.setViewClass(JasperReportsMultiFormatView.class);
		jasperReportsViewResolver.setJdbcDataSource(dataSource);
		jasperReportsViewResolver.setOrder(0);
		return jasperReportsViewResolver;
	}
	
	@Bean
	public ViewResolver viewResolver() {
		ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
		thymeleafViewResolver.setTemplateEngine(templateEngine());
		thymeleafViewResolver.setCharacterEncoding("UTF-8");
		thymeleafViewResolver.setOrder(1);
		return thymeleafViewResolver;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
	}
	
	@Bean
	public FormattingConversionService mvcConversionService() {
		DefaultFormattingConversionService defaultFormattingConversionService = new DefaultFormattingConversionService();
		DateTimeFormatterRegistrar dateTimeFormatterRegistrar = new DateTimeFormatterRegistrar();
		dateTimeFormatterRegistrar.setDateFormatter(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		dateTimeFormatterRegistrar.setTimeFormatter(DateTimeFormatter.ofPattern("HH:mm:ss"));
		dateTimeFormatterRegistrar.registerFormatters(defaultFormattingConversionService);
		
		NumberStyleFormatter integerFormatter = new NumberStyleFormatter("#,##0");
		defaultFormattingConversionService.addFormatterForFieldType(Integer.class, integerFormatter);
		
		return defaultFormattingConversionService;
	}
	
	@Bean
	public DomainClassConverter<FormattingConversionService> domainClassConverter() {
		return new DomainClassConverter<FormattingConversionService>(mvcConversionService());
	}
	
	@Bean
	public LocaleResolver localeResolver() {
		return new FixedLocaleResolver(new Locale("pt", "BR"));
	}
}
