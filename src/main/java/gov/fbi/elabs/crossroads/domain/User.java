package gov.fbi.elabs.crossroads.domain;

public class User {

	private String sessionId;

	private String Username;

	/**
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * @param sessionId
	 *            the sessionId to set
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return Username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		Username = username;
	}

}
