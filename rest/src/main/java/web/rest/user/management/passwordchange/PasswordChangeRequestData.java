package web.rest.user.management.passwordchange;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PasswordChangeRequestData implements Serializable {

	private final String oldPassword;
	private final String newPassword;

	public PasswordChangeRequestData(String oldPassword, String newPassword) {
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}

	public String getOldPassword() {
		return this.oldPassword;
	}

	public String getNewPassword() {
		return this.newPassword;
	}
}
