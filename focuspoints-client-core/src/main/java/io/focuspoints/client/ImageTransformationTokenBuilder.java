package io.focuspoints.client;

import com.auth0.jwt.JWTCreator.Builder;
import java.net.URL;
import org.apache.commons.lang3.Validate;

public class ImageTransformationTokenBuilder extends AbstractImageTokenBuilder<ImageTransformationTokenBuilder> {

	private static final String ACTION = "transform";

	private static final String CLAIM_URL = "url";
	private static final String CLAIM_WIDTH = "width";
	private static final String CLAIM_HEIGHT = "height";
	private static final String CLAIM_FOCUS_POINT_X = "focusPointX";
	private static final String CLAIM_FOCUS_POINT_Y = "focusPointY";

	private URL url;
	private int width;
	private int height;
	private Double focusPointX;
	private Double focusPointY;

	public ImageTransformationTokenBuilder(String issuer, String secret, URL url, int width, int height) {
		super(issuer, secret);
		this.withUrl(url).withWidth(width).withHeight(height);
	}

	public final ImageTransformationTokenBuilder withUrl(URL url) {
		Validate.notNull(url, "url is required");
		this.url = url;
		return this;
	}

	public final ImageTransformationTokenBuilder withWidth(int width) {
		Validate.isTrue(width >= 0, "width must be a positive integer");
		this.width = width;
		return this;
	}

	public final ImageTransformationTokenBuilder withHeight(int height) {
		Validate.isTrue(height >= 0, "height must be a positive integer");
		this.height = height;
		return this;
	}

	public ImageTransformationTokenBuilder withFocusPointX(Double focusPointX) {
		if(focusPointX != null) {
			Validate.inclusiveBetween(-1, 1, focusPointX, "focusPointX must be a decimal between -1 and 1");
		}
		this.focusPointX = focusPointX;
		return this;
	}

	public ImageTransformationTokenBuilder withFocusPointY(Double focusPointY) {
		if(focusPointY != null) {
			Validate.inclusiveBetween(-1, 1, focusPointY, "focusPointY must be a decimal between -1 and 1");
		}
		this.focusPointY = focusPointY;
		return this;
	}

	@Override
	protected void buildInternal(Builder jwtBuilder) {
		super.buildInternal(jwtBuilder);

		jwtBuilder
				.withClaim(CLAIM_URL, this.url.toExternalForm())
				.withClaim(CLAIM_WIDTH, this.width)
				.withClaim(CLAIM_HEIGHT, this.height);

		if (this.focusPointX != null) {
			jwtBuilder.withClaim(CLAIM_FOCUS_POINT_X, this.focusPointX);
		}

		if (this.focusPointY != null) {
			jwtBuilder.withClaim(CLAIM_FOCUS_POINT_Y, this.focusPointY);
		}
	}

	@Override
	protected String getAction() {
		return ACTION;
	}
}
