package io.focuspoints.servlet;

import io.focuspoints.client.FocusPointsConfigurationProperties;
import io.focuspoints.client.TokenCreator;
import io.focuspoints.client.UrlCreator;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.apache.commons.lang3.StringUtils;

@WebListener
public class FocusPointsInitalizer implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext sc = sce.getServletContext();
		
		FocusPointsConfigurationProperties focusPointsConfigurationProperties =
				getFocusPointsConfigurationProperties(sc);
		
		sc.setAttribute(FocusPointsConfigurationProperties.class.getName(), focusPointsConfigurationProperties);
		
		new TokenCreator(focusPointsConfigurationProperties);
		new UrlCreator(focusPointsConfigurationProperties);
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		TokenCreator.destroy();
		UrlCreator.destroy();
	}
	
	private static FocusPointsConfigurationProperties getFocusPointsConfigurationProperties(ServletContext sc) {
		FocusPointsConfigurationProperties properties = new FocusPointsConfigurationProperties();
		
		setPropertyIfExists(sc, "focuspoints.enabled", properties::setEnabled, Boolean::valueOf);
		setPropertyIfExists(sc, "focuspoints.url", properties::setUrl);
		setPropertyIfExists(sc, "focuspoints.token-request-parameter-name", properties::setTokenRequestParameterName);
		setPropertyIfExists(sc, "focuspoints.token-id", properties::setTokenId);
		setPropertyIfExists(sc, "focuspoints.token-secret", properties::setTokenSecret);
		
		return properties;
	}
	
	private static void setPropertyIfExists(ServletContext sc, String propertyName, Consumer<String> consumer) {
		setPropertyIfExists(sc, propertyName, consumer, String::valueOf);
	}
	
	private static <T> void setPropertyIfExists(ServletContext sc, String propertyName, Consumer<T> consumer, Function<String, T> converter) {
		String propertyValue = sc.getInitParameter(propertyName);
		
		if(StringUtils.isBlank(propertyValue)) {
			return;
		}
		
		T propertyValueConverted = converter.apply(propertyValue);
		
		if(propertyValueConverted == null) {
			return;
		}
		
		consumer.accept(propertyValueConverted);
	}
}
