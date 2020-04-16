package io.focuspoints.client;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

public class UrlBuilder {

    private String baseUrl;
    private String tokenRequestParameterName;
    
    private String token;
    private String filename;
    
    public UrlBuilder(String baseUrl, String tokenRequestParameterName, String token) {
        Validate.notBlank(baseUrl);
        Validate.notBlank(tokenRequestParameterName);
        Validate.notBlank(token);
        
        this.baseUrl = baseUrl;
        this.tokenRequestParameterName = tokenRequestParameterName;
        this.token = token;
    }
    
    public UrlBuilder withToken(String token) {
        this.token = token;
        return this;
    }
    
    public UrlBuilder withFilename(String filename) {
        this.filename = filename;
        return this;
    }
    
    public String build() {
        io.mikael.urlbuilder.UrlBuilder urlBuilder =
                io.mikael.urlbuilder.UrlBuilder.fromString(this.baseUrl)
                          .addParameter(this.tokenRequestParameterName, this.token);
        
        if(StringUtils.isNotBlank(this.filename)) {
            StringBuilder path = new StringBuilder(urlBuilder.path);
            
            if(path.length() > 0 &&
                    path.charAt(path.length() - 1) != '/') {
                path.append("/");
            }
            
            path.append(this.filename);
            
            urlBuilder = urlBuilder.withPath(path.toString());
        }
        
        return urlBuilder.toString();
    }
}