package io.focuspoints.client.thymeleaf;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.expression.IExpressionObjectFactory;

public class FocuspointsExpressionFactory implements IExpressionObjectFactory {

	private static final String FOCUSPOINTS_EVALUATION_VARIABLE_NAME = "focuspoints";

	private static final Set<String> ALL_EXPRESSION_OBJECT_NAMES =
			Collections.unmodifiableSet(new HashSet<>(Arrays.asList(FOCUSPOINTS_EVALUATION_VARIABLE_NAME)));

	@Override
	public Set<String> getAllExpressionObjectNames() {
		return ALL_EXPRESSION_OBJECT_NAMES;
	}

	@Override
	public Object buildObject(IExpressionContext context, String expressionObjectName) {
		if (FOCUSPOINTS_EVALUATION_VARIABLE_NAME.equals(expressionObjectName)) {
			return new Focuspoints();
		}
		return null;
	}

	@Override
	public boolean isCacheable(String expressionObjectName) {
		return expressionObjectName != null
			&& FOCUSPOINTS_EVALUATION_VARIABLE_NAME.equals(expressionObjectName);
	}
}
