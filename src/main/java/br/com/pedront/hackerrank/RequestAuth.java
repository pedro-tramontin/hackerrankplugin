package br.com.pedront.hackerrank;

import com.google.gson.annotations.SerializedName;

/**
 * @author pnakano
 * @version $Revision: $<br/>
 *          $Id: $
 * @since 22/12/17 14:15
 */
public class RequestAuth {
    private String login;

    private String password;

    @SerializedName("remember_me")
    private String rememberMe;

    public RequestAuth(final String login, final String password, final String rememberMe) {
        this.login = login;
        this.password = password;
        this.rememberMe = rememberMe;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getRememberMe() {
        return rememberMe;
    }
}
