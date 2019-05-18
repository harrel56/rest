package server.rest.usermanagement.passwordchange;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@SuppressWarnings("serial")
public class PasswordChangeRequestData implements Serializable {

    @NotNull
    private final String oldPassword;
    @NotNull
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
