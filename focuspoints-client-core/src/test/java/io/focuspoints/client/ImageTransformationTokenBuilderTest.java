package io.focuspoints.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.URL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ImageTransformationTokenBuilderTest {

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
				() -> new ImageTransformationTokenBuilder(this.issuer, this.secret, null, 0, 0)
		);
	}

	@Test
	public void testConstructor_NegativeWidth() {
		assertThrows(
				IllegalArgumentException.class,
				() -> new ImageTransformationTokenBuilder(this.issuer, this.secret, this.url, -1, 0)
		);
	}

	@Test
	public void testConstructor_NegativeHeight() {
		assertThrows(
				IllegalArgumentException.class,
				() -> new ImageTransformationTokenBuilder(this.issuer, this.secret, this.url, 0, -1)
		);
	}

	@Test
	public void testFocusPointX_InvalidNegative() {
		assertThrows(
				IllegalArgumentException.class,
				() -> new ImageTransformationTokenBuilder(this.issuer, this.secret, this.url, 0, 0).withFocusPointX(-2.0)
		);
	}

	@Test
	public void testFocusPointX_InvalidPoitive() {
		assertThrows(
				IllegalArgumentException.class,
				() -> new ImageTransformationTokenBuilder(this.issuer, this.secret, this.url, 0, 0).withFocusPointX(2.0)
		);
	}

	@Test
	public void testFocusPointY_InvalidNegative() {
		assertThrows(
				IllegalArgumentException.class,
				() -> new ImageTransformationTokenBuilder(this.issuer, this.secret, this.url, 0, 0).withFocusPointY(-2.0)
		);
	}

	@Test
	public void testFocusPointY_InvalidPoitive() {
		assertThrows(
				IllegalArgumentException.class,
				() -> new ImageTransformationTokenBuilder(this.issuer, this.secret, this.url, 0, 0).withFocusPointY(2.0)
		);
	}

	@Test
	public void testBuild() {
		String expectedToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJ3aWR0aCI6MTAwLCJpc3MiOiJ0ZXN0SXNzdWVyIiwiYWN0aW9uIjoidHJhbnNmb3JtIiwidXJsIjoiaHR0cDovL3d3dy5kb21haW4uZXh0IiwiaGVpZ2h0IjoyMDB9.2zFP6_ybx-p2ZNXoclkfviebVtmsWqFuPdLt-zoolB23_HZ7ArDpC2_-vrXEKN7toNiasf8_Km0SiXYAQGmL9Q";

		String token = new ImageTransformationTokenBuilder(this.issuer, this.secret, this.url, 100, 200).build();

		assertEquals(expectedToken, token);
	}

	@Test
	public void testBuild_WithFocusPoint() {
		String expectedToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJmb2N1c1BvaW50WSI6MC4yLCJmb2N1c1BvaW50WCI6MC4xLCJ3aWR0aCI6MTAwLCJpc3MiOiJ0ZXN0SXNzdWVyIiwiYWN0aW9uIjoidHJhbnNmb3JtIiwidXJsIjoiaHR0cDovL3d3dy5kb21haW4uZXh0IiwiaGVpZ2h0IjoyMDB9.lRXL_QiBqV_-y06_R81bQbUPbPNgcWbQL0LrKz7bnw-ozuADQeKMnI4DyElLVX-b-MiLPValbBPFU_XwcFqj1A";

		String token = new ImageTransformationTokenBuilder(this.issuer, this.secret, this.url, 100, 200)
				.withFocusPointX(0.1)
				.withFocusPointY(0.2)
				.build();

		assertEquals(expectedToken, token);
	}
}
