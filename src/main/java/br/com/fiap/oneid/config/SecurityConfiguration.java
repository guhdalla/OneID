package br.com.fiap.oneid.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import br.com.fiap.oneid.service.AuthenticationService;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthenticationService authenticationService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(authenticationService)
			.passwordEncoder(AuthenticationService.getPasswordEncoder())
		;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.anyRequest()
				.authenticated()
			.and()
				.formLogin()
				.defaultSuccessUrl("/entranceid")
			.and()
				.logout()
				.logoutSuccessUrl("/login")
		;
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
	}

}
