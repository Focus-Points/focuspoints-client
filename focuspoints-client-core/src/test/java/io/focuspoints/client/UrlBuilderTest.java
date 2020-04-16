package io.focuspoints.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

public class UrlBuilderTest {

    @Test
    public void testConstructor_NullBaseUrl() {
        String baseUrl = null;
        String tokenRequestParameterName = "testJwtParameterName";
        String token = "testToken";
        
        assertThrows(
        		NullPointerException.class,
        		() -> new UrlBuilder(baseUrl, tokenRequestParameterName, token)
        );
    }
    
    @Test
    public void testConstructor_EmptyBaseUrl() {
        String baseUrl = StringUtils.EMPTY;
        String tokenRequestParameterName = "testJwtParameterName";
        String token = "testToken";
        
        assertThrows(
        		IllegalArgumentException.class,
        		() -> new UrlBuilder(baseUrl, tokenRequestParameterName, token)
        );
    }
    
    @Test
    public void testConstructor_NullRequestParameterName() {
        String baseUrl = "http://www.domain.ext";
        String tokenRequestParameterName = null;
        String token = "testToken";
        
        assertThrows(
        		NullPointerException.class,
        		() -> new UrlBuilder(baseUrl, tokenRequestParameterName, token)
        );
    }
    
    @Test
    public void testConstructor_EmptyRequestParameterName() {
        String baseUrl = "http://www.domain.ext";
        String tokenRequestParameterName = StringUtils.EMPTY;
        String token = "testToken";
        
        assertThrows(
        		IllegalArgumentException.class,
        		() -> new UrlBuilder(baseUrl, tokenRequestParameterName, token)
        );
    }
    
    @Test
    public void testConstructor_NullToken() {
        String baseUrl = "http://www.domain.ext";
        String tokenRequestParameterName = "testJwtParameterName";
        String token = null;
        
        assertThrows(
        		NullPointerException.class,
        		() -> new UrlBuilder(baseUrl, tokenRequestParameterName, token)
        );
    }
    
    @Test
    public void testConstructor_EmptyToken() {
        String baseUrl = "http://www.domain.ext";
        String tokenRequestParameterName = "testJwtParameterName";
        String token = StringUtils.EMPTY;
        
        assertThrows(
        		IllegalArgumentException.class,
        		() -> new UrlBuilder(baseUrl, tokenRequestParameterName, token)
        );
    }
    
    @Test
    public void testBuild_NoFilename() {
        String baseUrl = "http://www.domain.ext";
        String tokenRequestParameterName = "testJwtParameterName";
        String token = "testToken";
        
        String expectedUrl = String.format(
                "%s?%s=%s",
                baseUrl,
                tokenRequestParameterName,
                token
        );
        
        String actualUrl = new UrlBuilder(baseUrl, tokenRequestParameterName, token).build();
        
        assertEquals(expectedUrl, actualUrl);
    }
    
    @Test
    public void testBuild_Filename() {
        String baseUrl = "http://www.domain.ext";
        String tokenRequestParameterName = "testJwtParameterName";
        String token = "testToken";
        String filename = "testFilename";
        
        String expectedUrl = String.format(
                "%s/%s?%s=%s",
                baseUrl,
                filename,
                tokenRequestParameterName,
                token
        );
        
        String actualUrl =
                new UrlBuilder(baseUrl, tokenRequestParameterName, token)
                    .withFilename(filename)
                    .build();
        
        assertEquals(expectedUrl, actualUrl);
    }
    
    @Test
    public void testBuild_FilenameAndBaseUrlWithPathWithoutTrailingSlash() {
        String baseUrl = "http://www.domain.ext/images";
        String tokenRequestParameterName = "testJwtParameterName";
        String token = "testToken";
        String filename = "testFilename";
        
        String expectedUrl = String.format(
                "%s/%s?%s=%s",
                baseUrl,
                filename,
                tokenRequestParameterName,
                token
        );
        
        String actualUrl =
                new UrlBuilder(baseUrl, tokenRequestParameterName, token)
                    .withFilename(filename)
                    .build();
        
        assertEquals(expectedUrl, actualUrl);
    }
    
    @Test
    public void testBuild_FilenameAndBaseUrlWithPathWithTrailingSlash() {
        String baseUrl = "http://www.domain.ext/images/";
        String tokenRequestParameterName = "testJwtParameterName";
        String token = "testToken";
        String filename = "testFilename";
        
        String expectedUrl = String.format(
                "%s%s?%s=%s",
                baseUrl,
                filename,
                tokenRequestParameterName,
                token
        );
        
        String actualUrl =
                new UrlBuilder(baseUrl, tokenRequestParameterName, token)
                    .withFilename(filename)
                    .build();
        
        assertEquals(expectedUrl, actualUrl);
    }
    
    @Test
    public void testBuild_TokenOverride() {
        String baseUrl = "http://www.domain.ext";
        String tokenRequestParameterName = "testJwtParameterName";
        String token1 = "testToken1";
        String token2 = "testToken2";
        
        String expectedUrl = String.format(
                "%s?%s=%s",
                baseUrl,
                tokenRequestParameterName,
                token2
        );
        
        String actualUrl =
                new UrlBuilder(baseUrl, tokenRequestParameterName, token1)
                    .withToken(token2)
                    .build();
        
        assertEquals(expectedUrl, actualUrl);
    }
}
