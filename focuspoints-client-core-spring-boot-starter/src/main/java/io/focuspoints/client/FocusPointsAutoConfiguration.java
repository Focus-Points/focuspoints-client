package io.focuspoints.client;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.ServletContextAttributeExporter;

@Configuration
@EnableConfigurationProperties(FocusPointsAutoConfigurationProperties.class)
public class FocusPointsAutoConfiguration {

	@Bean(destroyMethod = "destroy")
	@ConditionalOnMissingBean(name = "io.focuspoints.client.TokenCreator")
	public TokenCreator tokenCreator(FocusPointsConfigurationProperties focusPointsConfigurationProperties) {
		return new TokenCreator(focusPointsConfigurationProperties);
	}

	@Bean(destroyMethod = "destroy")
	@ConditionalOnMissingBean(name = "io.focuspoints.client.URLCreator")
	public UrlCreator urlCreator(FocusPointsConfigurationProperties focusPointsConfigurationProperties) {
		return new UrlCreator(focusPointsConfigurationProperties);
	}
	
	@Bean
	public ServletContextAttributeExporter servletContextAttributeExporter(
			FocusPointsAutoConfigurationProperties focusPointsConfigurationProperties) {
		
		Map<String, Object> attributes = new HashMap<>();
		attributes.put(FocusPointsConfigurationProperties.class.getName(), focusPointsConfigurationProperties);
		
		ServletContextAttributeExporter exporter = new ServletContextAttributeExporter();
		exporter.setAttributes(attributes);
		
		return exporter;
	}
}
