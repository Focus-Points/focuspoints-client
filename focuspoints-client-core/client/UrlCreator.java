package io.focuspoints.client;
import org.apache.commons.lang3.Validate;

public class UrlCreator {

    private static UrlCreator INSTANCE;
    
    private FocusPointsConfigurationProperties focusPointsConfiguration;
    
    private UrlCreator() {
        synchronized(UrlCreator.class) {
            if(INSTANCE != null) {
                throw new IllegalStateException("URICreator already instantiated");
            }
            INSTANCE = this;
        }
    }
    
    public UrlCreator(FocusPointsConfigurationProperties focusPointsConfiguration) {
        this();
        
        Validate.notNull(focusPointsConfiguration, "focusPointsConfiguration is required");
        
        this.focusPointsConfiguration = focusPointsConfiguration;
    }
    
    public static UrlCreator getInstance() {
        validateNotInstantiated();
        return INSTANCE;
    }
    
    public UrlBuilder create(String token) {
        String baseUrl = this.focusPointsConfiguration.getUrl();
        String tokenRequestParameterName = this.focusPointsConfiguration.getTokenRequestParameterName();
        
        return new UrlBuilder(baseUrl, tokenRequestParameterName, token);
    }

    public static void destroy() {
        validateNotInstantiated();
        INSTANCE = null;
    }

    private static void validateNotInstantiated() {
        if(INSTANCE == null) {
            throw new IllegalStateException("URLCreator not instantiated yet");
        }
    }
}
