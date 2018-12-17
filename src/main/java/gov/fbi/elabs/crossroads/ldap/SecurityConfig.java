package gov.fbi.elabs.crossroads.ldap;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		System.out.println("calling configure with http..");
		http.csrf().disable().authorizeRequests().antMatchers(HttpMethod.OPTIONS, "*").permitAll();
		System.out.println("end of configure with http..");
	}

	/*
	 * @Override protected void configure(AuthenticationManagerBuilder auth)
	 * throws Exception { auth.authenticationProvider(authenticationProvider); }
	 */

	/*
	 * @Override public void configure(AuthenticationManagerBuilder auth) throws
	 * Exception { //UsernamePasswordAuthenticationToken token =
	 * (UsernamePasswordAuthenticationToken) auth.getObject();
	 * //System.out.println("User >> "+token.getName());
	 * //System.out.println("Password >> "+token.getCredentials().toString());
	 * /*
	 * auth.ldapAuthentication().userDnPatterns("userPrincipalName={0},ou=Users"
	 * ). groupSearchBase("ou=FA_Employees")
	 * .contextSource().url("ldap://38.127.196.10:389/dc=elab,dc=local").and().
	 * passwordCompare() .passwordEncoder(new
	 * LdapShaPasswordEncoder()).passwordAttribute("userPassword")
	 * .managerDn("cn=binduser,ou=users,dc=some,dc=domain,dc=com")
	 * .managerPassword("some pass");
	 */
	/*
	 * System.out.println("LDAP Auth starting....");
	 * auth.ldapAuthentication().userSearchFilter("(sAMAccountName={0})").
	 * userSearchBase("dc=elab,dc=local")
	 * .groupSearchBase("OU=Users,OU=FA_Employees,DC=elab,DC=local").
	 * contextSource()
	 * .url("ldap://38.127.196.10").port(389).managerDn("student9").
	 * managerPassword("Octo123!@#"); try {
	 * System.out.println("value >>>>>>> {0}   {1}");
	 * System.out.println("LDAP Auth ending...."+SecurityContextHolder.
	 * getContext());
	 * System.out.println("LDAP Auth ending...."+SecurityContextHolder.
	 * getContext().getAuthentication());
	 * System.out.println("LDAP Auth ending...."+SecurityContextHolder.
	 * getContext().getAuthentication().getPrincipal()); User user = ((User)
	 * SecurityContextHolder.getContext().getAuthentication().getPrincipal());
	 * System.out.println("UserName >>>"+user.getUsername()); }catch(Exception
	 * ex) { ex.printStackTrace(); }
	 */
	// }*/

}
