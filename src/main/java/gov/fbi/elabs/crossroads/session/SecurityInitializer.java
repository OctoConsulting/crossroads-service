package gov.fbi.elabs.crossroads.session;


import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

import gov.fbi.elabs.crossroads.ldap.SecurityConfig;


public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {

	public SecurityInitializer() {
		super(SecurityConfig.class, SessionConfig.class);
	}
}