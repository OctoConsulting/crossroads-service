package gov.fbi.elabs.crossroads.ldap;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.session.hazelcast.HazelcastSessionRepository;
import org.springframework.stereotype.Component;

@Component
public class LDAPAuthenticationFilter implements Filter {

	@Value("${ldap.base}")
	private String base;

	@Value("${ldap.url}")
	private String url;

	@Value("${ldap.bind.user}")
	private String bindUser;

	@Value("${ldap.bind.pwd}")
	private String bindPwd;

	@Value("${session.interval}")
	private String sessionInterval;

	@Autowired
	HazelcastSessionRepository sessionRepo;

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain filter)
			throws IOException, ServletException {

		HttpServletRequest httpReq = ((HttpServletRequest) req);
		HttpServletResponse httpRes = ((HttpServletResponse) res);
		if (httpReq.getRequestURI().contains("/logout") || httpReq.getRequestURI().contains("/login")
				|| httpReq.getRequestURI().contains("/v1/batch") || httpReq.getRequestURI().contains("/v1/evidence")
				|| httpReq.getRequestURI().contains("/v1/location")
				|| httpReq.getRequestURI().contains("/v1/transferType")
				|| httpReq.getRequestURI().contains("/v1/transferReason")
				|| httpReq.getRequestURI().contains("/v1/custody")
				|| httpReq.getRequestURI().contains("/v1/employee")) {

			String sessionId = (String) httpReq.getHeader("X-Auth-Token");
			if (StringUtils.isNotEmpty(sessionId) && (httpReq.getRequestURI().contains("/v1/batch")
					|| httpReq.getRequestURI().contains("/v1/evidence")
					|| httpReq.getRequestURI().contains("/v1/location")
					|| httpReq.getRequestURI().contains("/v1/transferType")
					|| httpReq.getRequestURI().contains("/v1/transferReason")
					|| httpReq.getRequestURI().contains("/v1/custody")
					|| httpReq.getRequestURI().contains("/v1/employee"))) {
				String username = sessionId.split(":")[0];
				String sessionIdTmp = sessionId.split(":")[1];
				String uname = new String(Base64.decodeBase64(username), "utf-8");
				System.out.println("User Name >> " + uname);
				// EmployeeAuth empAuth =
				// employeeAuthUtil.getEmployeeAuthDetails(uname);
				req.setAttribute("username", uname);
				System.out.println(sessionRepo.getSession(sessionIdTmp));
				// if (sessionRepo.getSession(sessionIdTmp) != null) {
				// httpRes.setHeader("X-Auth-Token", sessionId);
				// } else {
				// httpRes.sendError(HttpServletResponse.SC_UNAUTHORIZED);
				// return;
				// }
			} else if (httpReq.getRequestURI().contains("/logout") && StringUtils.isNotEmpty(sessionId)) {
				String sessionIdTmp = sessionId.split(":")[1];
				if (StringUtils.isNotEmpty(sessionId)) {
					sessionRepo.delete(sessionIdTmp);
				}
			} else if (httpReq.getRequestURI().contains("/login")) {
				String username = httpReq.getHeader("username");
				String password = httpReq.getHeader("password");
				System.out.println("Username ::" + username);
				System.out.println("Password ::" + password);
				if (StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(password)) {
					System.out.println("Starting LDAP authentication success");
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
					System.out.println("****************************************");
					System.out.println("LDAP authentication success " + authenticate);
					if (authenticate) {
						sessionRepo.createSession();
						HttpSession session = httpReq.getSession(true);
						session.setAttribute("username", username);
						session.setMaxInactiveInterval(Integer.parseInt(sessionInterval));
						httpRes.setHeader("X-Auth-Token",
								Base64.encodeBase64String(username.getBytes("UTF-8")) + ":" + session.getId());
					} else {
						httpRes.sendError(HttpServletResponse.SC_UNAUTHORIZED);
						return;
					}
				} else {
					httpRes.sendError(HttpServletResponse.SC_UNAUTHORIZED);
					return;
				}
			} else {
				httpRes.sendError(HttpServletResponse.SC_UNAUTHORIZED);
				return;
			}
			filter.doFilter(httpReq, httpRes);
		} else {
			filter.doFilter(httpReq, httpRes);
		}
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

}