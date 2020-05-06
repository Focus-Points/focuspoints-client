package io.focuspoints.client;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.ServletContextAttributeExporter;

@Configuration
public class FocusPointsAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	@ConfigurationProperties(prefix = "focuspoints")
	public FocusPointsConfigurationProperties focuspointsConfigurationProprties() {
		return new FocusPointsConfigurationProperties();
	}
	
	@Bean(destroyMethod = "destroy")
	@ConditionalOnMissingBean
	public TokenCreator tokenCreator(FocusPointsConfigurationProperties focusPointsConfigurationProperties) {
		return new TokenCreator(focusPointsConfigurationProperties);
	}

	@Bean(destroyMethod = "destroy")
	@ConditionalOnMissingBean
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
