package io.focuspoints.client.support;

import io.focuspoints.client.FocusPointsConfigurationProperties;
import io.focuspoints.client.TokenCreator;
import io.focuspoints.client.UrlCreator;
import java.util.function.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class FocusPointsConfiguration {

    @Autowired
    private Environment env;
    
    @Bean
    public FocusPointsConfigurationProperties focusPointsConfigurationProperties() {
        FocusPointsConfigurationProperties properties = new FocusPointsConfigurationProperties();
        
        this.setPropertyIfExists("tfe.focuspoints.url", properties::setUrl);
        this.setPropertyIfExists("tfe.focuspoints.token-request-parameter-name", properties::setTokenRequestParameterName);
        this.setPropertyIfExists("tfe.focuspoints.username", properties::setUsername);
        this.setPropertyIfExists("tfe.focuspoints.password", properties::setPassword);
        
        return properties;
    }
    
    private void setPropertyIfExists(String propertyName, Consumer<String> consumer) {
        if(this.env.containsProperty(propertyName)) {
            consumer.accept(this.env.getProperty(propertyName));
        }
    }
    
    @Bean
    public TokenCreator tokenCreator(FocusPointsConfigurationProperties focusPointsConfigurationProperties) {
        return new TokenCreator(focusPointsConfigurationProperties);
    }
    
    @Bean
    public UrlCreator urlCreator(FocusPointsConfigurationProperties focusPointsConfigurationProperties) {
        return new UrlCreator(focusPointsConfigurationProperties);
    }
}
