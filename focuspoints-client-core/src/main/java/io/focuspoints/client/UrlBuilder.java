package io.focuspoints.client;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

public class UrlBuilder {

	private String baseUrl;
	private String tokenRequestParameterName;

	private String token;
	private String filename;

	public UrlBuilder(String baseUrl, String tokenRequestParameterName, String token) {
		this.withBaseUrl(baseUrl).withTokenRequestParameterName(tokenRequestParameterName).withToken(token);
	}
	
	public final UrlBuilder withBaseUrl(String baseUrl) {
		Validate.notBlank(baseUrl);
		this.baseUrl = baseUrl;
		return this;
	}
	
	public final UrlBuilder withTokenRequestParameterName(String tokenRequestParameterName) {
		Validate.notBlank(tokenRequestParameterName);
		this.tokenRequestParameterName = tokenRequestParameterName;
		return this;
	}
	
	public final UrlBuilder withToken(String token) {
		Validate.notBlank(token);
		this.token = token;
		return this;
	}

	public final UrlBuilder withFilename(String filename) {
		this.filename = filename;
		return this;
	}

	public String build() {
		io.mikael.urlbuilder.UrlBuilder urlBuilder =
				io.mikael.urlbuilder.UrlBuilder.fromString(this.baseUrl)
						.addParameter(this.tokenRequestParameterName, this.token);

		if (StringUtils.isNotBlank(this.filename)) {
			StringBuilder path = new StringBuilder(urlBuilder.path);

			if (path.length() > 0 &&
				path.charAt(path.length() - 1) != '/') {
				path.append("/");
			}

			path.append(this.filename);

			urlBuilder = urlBuilder.withPath(path.toString());
		}

		return urlBuilder.toString();
	}
}
