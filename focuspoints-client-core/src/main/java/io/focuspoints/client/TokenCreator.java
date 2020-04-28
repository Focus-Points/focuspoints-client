package io.focuspoints.client;

import java.net.URL;
import org.apache.commons.lang3.Validate;

public class TokenCreator {

	private static TokenCreator INSTANCE;

	private FocusPointsConfigurationProperties focusPointsConfiguration;

	private TokenCreator() {
		synchronized (TokenCreator.class) {
			if (INSTANCE != null) {
				throw new IllegalStateException("TokenCreator already instantiated");
			}
			INSTANCE = this;
		}
	}

	public TokenCreator(FocusPointsConfigurationProperties focusPointsConfiguration) {
		this();

		Validate.notNull(focusPointsConfiguration, "focusPointsConfiguration is required");

		this.focusPointsConfiguration = focusPointsConfiguration;
	}

	public static TokenCreator getInstance() {
		validateNotInstantiated();
		return INSTANCE;
	}

	public ImageResizeTokenBuilder createImageResizeToken(URL url, int width, int height) {
		String issuer = this.focusPointsConfiguration.getTokenId();
		String secret = this.focusPointsConfiguration.getTokenSecret();

		return new ImageResizeTokenBuilder(issuer, secret, url, width, height);
	}

	public ImageTransformationTokenBuilder createImageTransformationToken(URL url, int width, int height) {
		String issuer = this.focusPointsConfiguration.getTokenId();
		String secret = this.focusPointsConfiguration.getTokenSecret();

		return new ImageTransformationTokenBuilder(issuer, secret, url, width, height);
	}

	public static void destroy() {
		validateNotInstantiated();
		INSTANCE = null;
	}

	private static void validateNotInstantiated() {
		if (INSTANCE == null) {
			throw new IllegalStateException("Token creator not instantiated yet");
		}
	}
}
