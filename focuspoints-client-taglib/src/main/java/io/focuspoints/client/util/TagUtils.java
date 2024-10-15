package io.focuspoints.client.util;

import jakarta.servlet.jsp.PageContext;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TagUtils {

	public static final String SCOPE_PAGE = "page";
	public static final String SCOPE_REQUEST = "request";
	public static final String SCOPE_SESSION = "session";
	public static final String SCOPE_APPLICATION = "application";

	public static int getScope(String scope) {
        return switch (scope) {
            case SCOPE_REQUEST -> PageContext.REQUEST_SCOPE;
            case SCOPE_SESSION -> PageContext.SESSION_SCOPE;
            case SCOPE_APPLICATION -> PageContext.APPLICATION_SCOPE;
            default -> PageContext.PAGE_SCOPE;
        };
	}
}
