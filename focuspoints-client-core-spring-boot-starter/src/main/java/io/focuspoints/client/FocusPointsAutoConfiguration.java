package io.focuspoints.client;

import io.focuspoints.client.TokenCreator;
import io.focuspoints.client.UrlCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
public class FocusPointsAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public FocusPointsConfigurationProperties focusPointsConfigurationProperties() {
		return new FocusPointsConfigurationProperties();
	}

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
}
