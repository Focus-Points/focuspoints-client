package io.focuspoints.client.thymeleaf;

import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;

public class FocusPointsDialect extends AbstractDialect implements IExpressionObjectDialect {

	private final IExpressionObjectFactory IMAGE_SERVICE_EXPRESSION_OBJECTS_FACTORY;

	public FocusPointsDialect() {
		super("FocusPoints");
		this.IMAGE_SERVICE_EXPRESSION_OBJECTS_FACTORY = new FocuspointsExpressionFactory();
	}

	@Override
	public IExpressionObjectFactory getExpressionObjectFactory() {
		return this.IMAGE_SERVICE_EXPRESSION_OBJECTS_FACTORY;
	}
}
