package io.focuspoints.client.util;

import javax.servlet.jsp.PageContext;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TagUtils {

	public static final String SCOPE_PAGE = "page";
	public static final String SCOPE_REQUEST = "request";
	public static final String SCOPE_SESSION = "session";
	public static final String SCOPE_APPLICATION = "application";

	public static int getScope(String scope) {
		switch (scope) {
			default:
			case SCOPE_PAGE:
				return PageContext.PAGE_SCOPE;
			case SCOPE_REQUEST:
				return PageContext.REQUEST_SCOPE;
			case SCOPE_SESSION:
				return PageContext.SESSION_SCOPE;
			case SCOPE_APPLICATION:
				return PageContext.APPLICATION_SCOPE;
		}
	}
}
