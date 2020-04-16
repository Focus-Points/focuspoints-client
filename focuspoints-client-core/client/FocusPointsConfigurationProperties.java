package io.focuspoints.client;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.Validate;

import lombok.Data;

@Data
public class FocusPointsConfigurationProperties {

    public static final String TOKEN_REQUEST_PARAMETER_NAME_DEFAULT = "_jwt";
    
    private String url;
    private String tokenRequestParameterName = TOKEN_REQUEST_PARAMETER_NAME_DEFAULT;
    
    private String username;
    private String password;
    
    @PostConstruct
    public void validate() {
        Validate.notBlank(this.url, "url is required");
        Validate.notBlank(this.tokenRequestParameterName, "tokenRequestParameterName is required");
        Validate.notBlank(this.username, "username is required");
        Validate.notBlank(this.password, "password is required");
    }
}
