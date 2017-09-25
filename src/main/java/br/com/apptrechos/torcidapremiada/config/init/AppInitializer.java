package br.com.apptrechos.torcidapremiada.config.init;

import javax.servlet.Filter;

import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import br.com.apptrechos.torcidapremiada.config.JpaConfig;
import br.com.apptrechos.torcidapremiada.config.SecurityConfig;
import br.com.apptrechos.torcidapremiada.config.ServiceConfig;
import br.com.apptrechos.torcidapremiada.config.WebConfig;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { JpaConfig.class, SecurityConfig.class, ServiceConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { WebConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}
	
	@Override
	protected Filter[] getServletFilters() {
		HttpPutFormContentFilter httpPutFormContentFilter = new HttpPutFormContentFilter();
		
		return new Filter[] {httpPutFormContentFilter};
	}

}
