package jtweet.apiproxy;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import twitter4j.http.AccessToken;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class ApiUser {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private String username;
	@Persistent
	private String token;
	@Persistent
	private String sec;
	@Persistent
	private String passWord;

	public ApiUser(String username, String token, String sec) {
		this.username = username;
		this.token = token;
		this.sec = sec;
	}

	public ApiUser(AccessToken token) {
		username = token.getScreenName();
		this.token = token.getToken();
		sec = token.getTokenSecret();
		this.passWord = sec;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setSec(String sec) {
		this.sec = sec;
	}

	public void setToken(AccessToken token) {
		this.token = token.getToken();
		sec = token.getTokenSecret();
		this.passWord = sec;
	}

	public String getUserName() {
		return username;
	}

	public String getToken() {
		return token;
	}

	public String getSec() {
		return sec;
	}

	public void setPassword(String password) {
		this.passWord = password;
	}

	public String getPassword() {
		return passWord;
	}

	@Override
	public String toString() {
		return String.format("用户名:%s,密码:%s,oAuthToken:%s,oAuthTokenSecret:%s", this.username, this.passWord, this.token, this.sec);
	}
}
