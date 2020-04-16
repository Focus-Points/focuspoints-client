package io.focuspoints.client;

import java.net.URL;

import org.apache.commons.lang3.Validate;

import com.auth0.jwt.JWTCreator.Builder;

public class ImageResizeTokenBuilder extends TokenBuilder {
    
    private static final String ACTION = "resize";
    
    private static final String CLAIM_URL = "url";
    private static final String CLAIM_WIDTH = "width";
    private static final String CLAIM_HEIGHT = "height";
    
    private URL url;
    private int width;
    private int height;
    
    ImageResizeTokenBuilder(String issuer, String secret, URL url, int width, int height) {
        super(issuer, secret);
        
        Validate.notNull(url, "url is required");
        Validate.isTrue(width >= 0, "width must be a positive integer");
        Validate.isTrue(height >= 0, "height must be a positive integer");
        
        this.url = url;
        this.width = width;
        this.height= height;
    }
    
    public ImageResizeTokenBuilder withUrl(URL url) {
        this.url = url;
        return this;
    }
    
    public ImageResizeTokenBuilder withWidth(int width) {
        this.width = width;
        return this;
    }
    
    public ImageResizeTokenBuilder withHeight(int height) {
        this.height = height;
        return this;
    }
    
    @Override
    protected void buildInternal(Builder jwtBuilder) {
        super.buildInternal(jwtBuilder);
        
        jwtBuilder.withClaim(CLAIM_URL, this.url.toExternalForm())
                  .withClaim(CLAIM_WIDTH, this.width)
                  .withClaim(CLAIM_HEIGHT, this.height);
    }
    
    @Override
    protected String getAction() {
        return ACTION;
    }
}