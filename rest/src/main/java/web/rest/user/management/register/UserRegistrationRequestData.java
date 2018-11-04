package web.rest.user.management.register;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserRegistrationRequestData implements Serializable {

	private final String login;
	private final String password;
	private final String email;

	public UserRegistrationRequestData(String login, String password, String email) {
		this.login = login;
		this.password = password;
		this.email = email;
	}

	public String getLogin() {
		return this.login;
	}

	public String getPassword() {
		return this.password;
	}

	public String getEmail() {
		return this.email;
	}
}
