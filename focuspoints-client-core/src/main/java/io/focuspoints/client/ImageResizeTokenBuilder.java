package io.focuspoints.client;

import com.auth0.jwt.JWTCreator.Builder;
import java.net.URL;
import org.apache.commons.lang3.Validate;

public class ImageResizeTokenBuilder extends AbstractImageTokenBuilder<ImageResizeTokenBuilder> {

	private static final String ACTION = "resize";

	private static final String CLAIM_URL = "url";
	private static final String CLAIM_WIDTH = "width";
	private static final String CLAIM_HEIGHT = "height";

	private URL url;
	private int width;
	private int height;

	public ImageResizeTokenBuilder(String issuer, String secret, URL url, int width, int height) {
		super(issuer, secret);
		this.withUrl(url).withWidth(width).withHeight(height);
	}

	public final ImageResizeTokenBuilder withUrl(URL url) {
		Validate.notNull(url, "url is required");
		this.url = url;
		return this;
	}

	public final ImageResizeTokenBuilder withWidth(int width) {
		Validate.isTrue(width >= 0, "width must be a positive integer");
		this.width = width;
		return this;
	}

	public final ImageResizeTokenBuilder withHeight(int height) {
		Validate.isTrue(height >= 0, "height must be a positive integer");
		this.height = height;
		return this;
	}

	@Override
	protected void buildInternal(Builder jwtBuilder) {
		super.buildInternal(jwtBuilder);

		jwtBuilder
				.withClaim(CLAIM_URL, this.url.toExternalForm())
				.withClaim(CLAIM_WIDTH, this.width)
				.withClaim(CLAIM_HEIGHT, this.height);
	}

	@Override
	protected String getAction() {
		return ACTION;
	}
}
