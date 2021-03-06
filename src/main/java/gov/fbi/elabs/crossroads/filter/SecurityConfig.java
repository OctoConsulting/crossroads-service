package gov.fbi.elabs.crossroads.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;

@Configuration
@EnableWebSecurity
@EnableGlobalAuthentication
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${ldap.base}")
	private String base;

	@Value("${ldap.url}")
	private String url;

	@Value("${ldap.bind.user}")
	private String bindUser;

	@Value("${ldap.bind.pwd}")
	private String bindPwd;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
				.antMatchers("/login", "/v2/api-docs/**", "/swagger-resources", "/swagger-ui.html/**", "/images/**",
						"/webjars/springfox-swagger-ui/**", "/configuration/ui", "/configuration/security")
				.permitAll().and().authorizeRequests().anyRequest().authenticated().and().httpBasic();
		// http.authorizeRequests().anyRequest().permitAll();
		/*
		 * http.csrf().disable() .authorizeRequests().antMatchers("/login")
		 * .authenticated().antMatchers(HttpMethod.OPTIONS, "*").permitAll()
		 * .and().httpBasic();
		 * http.authorizeRequests().anyRequest().permitAll();
		 */
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.ldapAuthentication().userSearchFilter("(sAMAccountName={0})").userSearchBase("dc=elab,dc=local")
				.groupSearchBase(base).contextSource().url(url).port(389).managerDn(bindUser).managerPassword(bindPwd);
	}

	@Bean
	HeaderHttpSessionStrategy sessionStrategy() {
		return new HeaderHttpSessionStrategy();
	}
}
