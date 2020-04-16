package io.focuspoints.client;

import java.net.URL;

import org.apache.commons.lang3.Validate;

import com.auth0.jwt.JWTCreator.Builder;

public class ImageTransformationTokenBuilder extends TokenBuilder {
    
    private static final String ACTION = "transform";
    
    private static final String CLAIM_URL = "url";
    private static final String CLAIM_WIDTH = "width";
    private static final String CLAIM_HEIGHT = "height";
    private static final String CLAIM_FOCUS_POINT_X = "focusPointX";
    private static final String CLAIM_FOCUS_POINT_Y = "focusPointY";
    
    private URL url;
    private int width;
    private int height;
    private Double focusPointX;
    private Double focusPointY;
    
    ImageTransformationTokenBuilder(String issuer, String secret, URL url, int width, int height) {
        super(issuer, secret);
        
        Validate.notNull(url, "url is required");
        Validate.isTrue(width >= 0, "width must be a positive integer");
        Validate.isTrue(height >= 0, "height must be a positive integer");
        
        this.url = url;
        this.width = width;
        this.height = height;
    }
    
    public ImageTransformationTokenBuilder withUrl(URL url) {
        this.url = url;
        return this;
    }
    
    public ImageTransformationTokenBuilder withWidth(int width) {
        this.width = width;
        return this;
    }
    
    public ImageTransformationTokenBuilder withHeight(int height) {
        this.height = height;
        return this;
    }
    
    public ImageTransformationTokenBuilder withFocusPointX(Double focusPointX) {
        this.focusPointX = focusPointX;
        return this;
    }
    
    public ImageTransformationTokenBuilder withFocusPointY(Double focusPointY) {
        this.focusPointY = focusPointY;
        return this;
    }
    
    @Override
    protected void buildInternal(Builder jwtBuilder) {
        super.buildInternal(jwtBuilder);
        
        jwtBuilder.withClaim(CLAIM_URL, this.url.toExternalForm())
                  .withClaim(CLAIM_WIDTH, this.width)
                  .withClaim(CLAIM_HEIGHT, this.height);
                  
        if(this.focusPointX != null) {
            jwtBuilder.withClaim(CLAIM_FOCUS_POINT_X, this.focusPointX);
        }
        
        if(this.focusPointY != null) {
            jwtBuilder.withClaim(CLAIM_FOCUS_POINT_Y, this.focusPointY);
        }
    }
    
    @Override
    protected String getAction() {
        return ACTION;
    }
}