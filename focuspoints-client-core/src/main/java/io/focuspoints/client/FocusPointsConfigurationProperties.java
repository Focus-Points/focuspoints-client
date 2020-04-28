package io.focuspoints.client;

import javax.annotation.PostConstruct;
import lombok.Data;
import org.apache.commons.lang3.Validate;

@Data
public class FocusPointsConfigurationProperties {

	public static final String URL_DEFAULT = "https://image.focuspoints.io";
	public static final String TOKEN_REQUEST_PARAMETER_NAME_DEFAULT = "_jwt";

	private String url = URL_DEFAULT;
	private String tokenRequestParameterName = TOKEN_REQUEST_PARAMETER_NAME_DEFAULT;

	private String tokenId;
	private String tokenSecret;

	@PostConstruct
	public void validate() {
		Validate.notBlank(this.url, "url is required");
		Validate.notBlank(this.tokenRequestParameterName, "tokenRequestParameterName is required");
		Validate.notBlank(this.tokenId, "tokenId is required");
		Validate.notBlank(this.tokenSecret, "tokenSecret is required");
	}
}
