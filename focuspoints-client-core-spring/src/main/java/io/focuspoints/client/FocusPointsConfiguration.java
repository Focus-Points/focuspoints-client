package io.focuspoints.client;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.context.support.ServletContextAttributeExporter;

@Configuration
public class FocusPointsConfiguration {

	@Autowired
	private Environment env;

	@Bean
	public FocusPointsConfigurationProperties focusPointsConfigurationProperties() {
		FocusPointsConfigurationProperties properties = new FocusPointsConfigurationProperties();

		this.setPropertyIfExists("focuspoints.enabled", properties::setEnabled, Boolean::valueOf);
		this.setPropertyIfExists("focuspoints.url", properties::setUrl);
		this.setPropertyIfExists("focuspoints.token-request-parameter-name", properties::setTokenRequestParameterName);
		this.setPropertyIfExists("focuspoints.token-id", properties::setTokenId);
		this.setPropertyIfExists("focuspoints.token-secret", properties::setTokenSecret);

		return properties;
	}

	private void setPropertyIfExists(String propertyName, Consumer<String> consumer) {
		this.setPropertyIfExists(propertyName, consumer, String::valueOf);
	}
	
	private <T> void setPropertyIfExists(String propertyName, Consumer<T> consumer, Function<String, T> converter) {
		if(!this.env.containsProperty(propertyName)) {
			return ;
		}
		
		String propertyValue = this.env.resolvePlaceholders(this.env.getProperty(propertyName));
		
		if(StringUtils.isBlank(propertyValue)) {
			return;
		}
		
		T propertyValueConverted = converter.apply(propertyValue);
		
		if(propertyValueConverted == null) {
			return;
		}
		
		consumer.accept(propertyValueConverted);
	}

	@Bean
	public TokenCreator tokenCreator(FocusPointsConfigurationProperties focusPointsConfigurationProperties) {
		return new TokenCreator(focusPointsConfigurationProperties);
	}

	@Bean
	public UrlCreator urlCreator(FocusPointsConfigurationProperties focusPointsConfigurationProperties) {
		return new UrlCreator(focusPointsConfigurationProperties);
	}
	
	@Bean
	public ServletContextAttributeExporter servletContextAttributeExporter(
			FocusPointsConfigurationProperties focusPointsConfigurationProperties) {
		
		Map<String, Object> attributes = new HashMap<>();
		attributes.put(FocusPointsConfigurationProperties.class.getName(), focusPointsConfigurationProperties);
		
		ServletContextAttributeExporter exporter = new ServletContextAttributeExporter();
		exporter.setAttributes(attributes);
		
		return exporter;
	}
}
