package Utilities.POJO.response;

import lombok.Data;

@Data
public class GET_SigninWithValidCredentials {
	private String access_token;
	private String token_type;
}