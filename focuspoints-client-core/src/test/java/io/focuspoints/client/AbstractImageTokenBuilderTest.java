package io.focuspoints.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;

public class AbstractImageTokenBuilderTest {

    private String issuer = "testIssuer";
    private String secret = "testSecret";
    
    @Test
    public void testConstructor_NullIssuer() {
    	assertThrows(
				NullPointerException.class,
				() -> new TestTokenBuilder(null, this.secret)
		);
    }
    
    @Test
    public void testConstructor_BlankIssuer() {
        assertThrows(
        		IllegalArgumentException.class,
				() -> new TestTokenBuilder("", this.secret)
		);
    }
    
    @Test
    public void testConstructorWithNullSecret() {
        assertThrows(
        		NullPointerException.class,
				() -> new TestTokenBuilder(this.issuer, null)
		);
    }
    
    @Test
    public void testConstructorWithBlankSecret() {
        assertThrows(
        		IllegalArgumentException.class,
				() -> new TestTokenBuilder(this.issuer, "")
		);
    }
    
    @Test
    public void testBuild() {
        String expectedToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJ0ZXN0SXNzdWVyIiwiYWN0aW9uIjoidGVzdEFjdGlvbiJ9.qME7SDyRb3U-VxktQjK8E63V_eXcs4-KVMJmeCo9NP2I0LJZzGcKzYPXGLsKNLqemQ9Wu5h6iW_Qx6e9upxjEw";
        
        TestTokenBuilder builder = spy(new TestTokenBuilder(this.issuer, this.secret));
        
        String token = builder.build();
        
        verify(builder).getAction();
        verify(builder).buildInternal(notNull());
        
        assertEquals(expectedToken, token);
    }
    
    
    private static class TestTokenBuilder extends AbstractImageTokenBuilder<TestTokenBuilder> {

        public static final String ACTION = "testAction";
        
        TestTokenBuilder(String issuer, String secret) {
            super(issuer, secret);
        }

        @Override
        protected String getAction() {
            return ACTION;
        }
    }
}
