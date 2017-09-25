package br.com.apptrechos.torcidapremiada.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.com.apptrechos.torcidapremiada.security.AppUserDetailsService;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan(basePackageClasses = AppUserDetailsService.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDatailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.userDetailsService(userDatailsService)
			.passwordEncoder(passwordEncoder());
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
			.antMatchers("/layout/**")
			.antMatchers("/javascripts/**")
			.antMatchers("/stylesheets/**")
			.antMatchers("/recuperacao")
			.antMatchers("/recuperacao/consulta/{token}")
			.antMatchers(HttpMethod.POST, "/recuperacao");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/usuario")
				.hasRole("CONSULTAR_USUARIO")
			.antMatchers("/usuario/cadastro")
				.hasRole("CADASTRAR_USUARIO")
			.antMatchers("/usuario/{codigo}")
				.hasRole("EDITAR_USUARIO")
			.antMatchers(HttpMethod.POST, "/usuario/**")
				.hasAnyRole("CADASTRAR_USUARIO", "EDITAR_USUARIO")
			.antMatchers(HttpMethod.POST, "/orgao")
				.hasRole("CADASTRAR_ORGAO")
			.antMatchers("/partida")
				.hasRole("CONSULTAR_PARTIDA")
			.antMatchers("/partida/cadastro")
				.hasRole("CADASTRAR_PARTIDA")
			.antMatchers("/partida/{codigo}")
				.hasRole("EDITAR_PARTIDA")
			.antMatchers(HttpMethod.POST, "/partida/**")
				.hasAnyRole("CADASTRAR_PARTIDA", "EDITAR_PARTIDA")
			.antMatchers(HttpMethod.POST, "/timeAdversario")
				.hasRole("CADASTRAR_ADVERSARIO")
			.antMatchers(HttpMethod.GET, "/contribuinte")
				.hasRole("CONSULTAR_CONTRIBUINTE")
			.antMatchers("/contribuinte/cadastro")
				.hasRole("CADASTRAR_CONTRIBUINTE")
			.antMatchers("/contribuinte/{codigo}")
				.hasRole("EDITAR_CONTRIBUINTE")
			.antMatchers(HttpMethod.POST, "/contribuinte/**")
				.hasAnyRole("CADASTRAR_CONTRIBUINTE", "EDITAR_CONTRIBUINTE")
			.antMatchers("/nota")
				.hasRole("CONSULTAR_NOTA")
			.antMatchers("/nota/cadastro")
				.hasRole("CADASTRAR_NOTA")
			.antMatchers("/nota/{codigo}")
				.hasRole("EDITAR_NOTA")
			.antMatchers(HttpMethod.POST, "/nota/**")
				.hasAnyRole("CADASTRAR_NOTA", "EDITAR_NOTA")
			.antMatchers("/imovel")
				.hasRole("CONSULTAR_IMOVEL")
			.antMatchers("/imovel/cadastro")
				.hasRole("CADASTRAR_IMOVEL")
			.antMatchers("/imovel/{codigo}")
				.hasRole("EDITAR_IMOVEL")
			.antMatchers(HttpMethod.POST, "/imovel/**")
				.hasAnyRole("CADASTRAR_IMOVEL", "EDITAR_IMOVEL")
			.antMatchers("/beneficio/pendente")
				.hasRole("CONSULTAR_BENEFICIOS_PENDENTES")
			.antMatchers("/beneficio/entregue")
				.hasRole("CONSULTAR_BENEFICIOS_ENTREGUES")
			.antMatchers("/beneficio/liberacao/{codigo}")
				.hasRole("CONCEDER_BENEFICIO_IMOVEL")
			.antMatchers("/beneficio/ingresso/{codigo}")
				.hasRole("CADASTRAR_INGRESSOS")
			.antMatchers("/beneficio/ingresso/cadastrar")
				.hasRole("CADASTRAR_INGRESSOS")
			.antMatchers("/relatorio/*")
				.hasRole("GERAR_RELATORIOS_GERENCIAIS")
			.anyRequest()
				.authenticated()
				.and()
			.formLogin()
				.loginPage("/login")
				.permitAll()
				.and()
			.rememberMe() 
                .key("remember-me-key")
				.and()
			.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.and()
			.exceptionHandling()
				.accessDeniedPage("/403")
				.and()
			.sessionManagement()
				.invalidSessionUrl("/login");		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
