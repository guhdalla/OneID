package br.com.fiap.oneid.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
			.antMatchers("/entranceid/**", "/config/**")
				.hasRole("JURIDICO")
			.antMatchers("/entranceid/**", "/config/**")
				.authenticated()
			.and()
				.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/entranceid")
			.and()
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login")
			.and()
				.csrf()
					.disable()
			.headers().frameOptions().disable()
		;
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
	}

}
