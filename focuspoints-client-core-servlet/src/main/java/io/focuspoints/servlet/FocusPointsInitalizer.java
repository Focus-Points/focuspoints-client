package io.focuspoints.servlet;

import io.focuspoints.client.FocusPointsConfigurationProperties;
import io.focuspoints.client.TokenCreator;
import io.focuspoints.client.UrlCreator;
import java.util.function.Consumer;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.apache.commons.lang3.StringUtils;

@WebListener
public class FocusPointsInitalizer implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		FocusPointsConfigurationProperties focusPointsConfigurationProperties =
				getFocusPointsConfigurationProperties(sce);
		
		new TokenCreator(focusPointsConfigurationProperties);
		new UrlCreator(focusPointsConfigurationProperties);
	}
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		TokenCreator.destroy();
		UrlCreator.destroy();
	}
	
	private static FocusPointsConfigurationProperties getFocusPointsConfigurationProperties(ServletContextEvent sce) {
		ServletContext sc = sce.getServletContext();
		
		FocusPointsConfigurationProperties properties = new FocusPointsConfigurationProperties();
		
		setPropertyIfExists(sc, "focuspoints.url", properties::setUrl);
		setPropertyIfExists(sc, "focuspoints.token-request-parameter-name", properties::setTokenRequestParameterName);
		setPropertyIfExists(sc, "focuspoints.token-id", properties::setTokenId);
		setPropertyIfExists(sc, "focuspoints.token-secret", properties::setTokenSecret);
		
		return properties;
	}
	
	private static void setPropertyIfExists(ServletContext sc, String propertyName, Consumer<String> consumer) {
		String propertyValue = sc.getInitParameter(propertyName);
		
		if (StringUtils.isNotBlank(propertyValue)) {
			consumer.accept(propertyValue);
		}
	}
}
