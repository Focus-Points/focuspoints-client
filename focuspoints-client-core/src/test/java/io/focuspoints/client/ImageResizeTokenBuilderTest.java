package io.focuspoints.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.URL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ImageResizeTokenBuilderTest {

	private String issuer = "testIssuer";
	private String secret = "testSecret";

	private URL url;

	@BeforeEach
	public void setUp() throws Exception {
		this.url = new URL("http://www.domain.ext");
	}

	@Test
	public void testConstructor_NullUrl() {
		assertThrows(
				NullPointerException.class,
				() -> new ImageResizeTokenBuilder(this.issuer, this.secret, null, 0, 0)
		);
	}

	@Test
	public void testConstructor_NegativeWidth() {
		assertThrows(
				IllegalArgumentException.class,
				() -> new ImageResizeTokenBuilder(this.issuer, this.secret, this.url, -1, 0)
		);
	}

	@Test
	public void testConstructor_NegativeHeight() {
		assertThrows(
				IllegalArgumentException.class,
				() -> new ImageResizeTokenBuilder(this.issuer, this.secret, this.url, 0, -1)
		);
	}

	@Test
	public void testBuild() {
		String expectedToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJ3aWR0aCI6MTAwLCJpc3MiOiJ0ZXN0SXNzdWVyIiwiYWN0aW9uIjoicmVzaXplIiwidXJsIjoiaHR0cDovL3d3dy5kb21haW4uZXh0IiwiaGVpZ2h0IjoyMDB9.0GUQs9lXsva1y5joz9V3sVq936nqagZyhbQK2T1Okq6rGxYFcIPeTxTPvK9Ckolv1hNb6Y5lCE9GchvFJ6yNpg";

		String token = new ImageResizeTokenBuilder(this.issuer, this.secret, this.url, 100, 200).build();

		assertEquals(expectedToken, token);
	}
}
