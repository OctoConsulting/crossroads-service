package gov.fbi.elabs.crossroads.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.stereotype.Service;

@Service
public class LDAPService {

	@Value("${ldap.base}")
	private String base;

	@Value("${ldap.url}")
	private String url;

	@Value("${ldap.bind.user}")
	private String bindUser;

	@Value("${ldap.bind.pwd}")
	private String bindPwd;

	public Boolean authenticateUser(String username, String password) {
		LdapTemplate ldapSpringTemplate = new LdapTemplate();
		LdapContextSource contextSource = new LdapContextSource();
		contextSource.setUserDn(bindUser);
		contextSource.setPassword(bindPwd);
		contextSource.setUrl(url);
		contextSource.setBase(base);
		ldapSpringTemplate.setContextSource(contextSource);
		ldapSpringTemplate.setIgnorePartialResultException(true);
		contextSource.afterPropertiesSet();
		System.out.println("****************************************");
		AndFilter queryFilter = new AndFilter();
		queryFilter.and(new EqualsFilter("sAMAccountName", username));
		Boolean authenticate = ldapSpringTemplate.authenticate("", queryFilter.toString(), password);
		return authenticate;
	}

}
