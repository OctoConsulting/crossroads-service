package com.gov.travelservice.config;



import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

import com.gov.travelservice.filter.SimpleCorsFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;
    
    @Bean
	@Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
   	     return super.authenticationManagerBean();
   	}
    
	@Override
		public void configure(WebSecurity web) throws Exception {
	    	super.configure(web);
				web.ignoring().antMatchers("/**/*.js", "/", "/index.html", "index.html");
		}
	  
	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
            http.addFilterBefore(new SimpleCorsFilter(), ChannelProcessingFilter.class).cors().and().formLogin().and().authorizeRequests()
	           .antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
	           .antMatchers("/**/*.js", "/", "/index.html", "index.html", "/h2/**", "/**/authenticate/**", "/**").permitAll()
	           .anyRequest().authenticated()
	     //   .and()
	      //  .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
	        .and()
	        .logout()
	        .and()
	        .csrf().disable()
	        .headers()
	            .frameOptions().disable();
	    }
	    
        @Autowired
        public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
            auth.jdbcAuthentication()
                    .dataSource(this.dataSource)
                    .authoritiesByUsernameQuery("select u.username,r.role_name from ts.roles r, ts.user u, ts.user_roles ur where u.username = ? and u.id = ur.uid and ur.role_id = r.role_id and ur.is_deleted = false")
                    .usersByUsernameQuery("SELECT username, password, true enabled FROM ts.user where username = ?")
                    .passwordEncoder(new BCryptPasswordEncoder());
        }

}
