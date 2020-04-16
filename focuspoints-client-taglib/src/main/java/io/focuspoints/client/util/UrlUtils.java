package io.focuspoints.client.util;

import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UrlUtils {

	private static final Pattern ABSOLUTE_URL_PATTERN =
			Pattern.compile("\\A[a-z0-9.+-]+://.*", Pattern.CASE_INSENSITIVE);

	/**
	 * Decides if a URL is absolute based on whether it contains a valid scheme name, as
	 * defined in RFC 1738.
	 */
	public static boolean isAbsoluteUrl(String url) {
		return ABSOLUTE_URL_PATTERN.matcher(url).matches();
	}
}
