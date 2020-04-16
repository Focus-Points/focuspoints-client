package io.focuspoints.client.thymeleaf;

import io.focuspoints.client.TokenCreator;
import io.focuspoints.client.UrlCreator;
import java.net.MalformedURLException;
import java.net.URL;

public class Focuspoints {

	public String resize(String url, String filename, int width, int height) {
		String token =
				TokenCreator.getInstance()
						.createImageResizeToken(this.toUrl(url), width, height)
						.build();

		return this.createImageOperationUrl(token, filename);
	}

	public String transform(String url, String filename, int width, int height) {
		return this.transform(url, filename, width, height, 0, 0);
	}

	public String transform(String url, String filename, int width, int height, double focusPointX,
			double focusPointY) {
		
		String token =
				TokenCreator.getInstance()
						.createImageTransformationToken(this.toUrl(url), width, height)
						.withFocusPointX(focusPointX)
						.withFocusPointY(focusPointY)
						.build();

		return this.createImageOperationUrl(token, filename);
	}

	private URL toUrl(String url) {
		try {
			return new URL(url);
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException(url + " is not a valid absolute URL", e);
		}
	}

	private String createImageOperationUrl(String token, String filename) {
		return UrlCreator.getInstance()
				.create(token)
				.withFilename(filename)
				.build();
	}
}
