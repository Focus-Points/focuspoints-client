package io.focuspoints.client;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.net.URL;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TokenCreatorTest {

	private FocusPointsConfigurationProperties focusPointsConfigurationProperties;

	private String username = "testUsername";
	private String password = "testPassword";

	private URL url;

	private boolean instanceCreated;

	@BeforeEach
	public void setUp() throws Exception {
		this.focusPointsConfigurationProperties = spy(new FocusPointsConfigurationProperties());

		this.focusPointsConfigurationProperties.setUsername(this.username);
		this.focusPointsConfigurationProperties.setPassword(this.password);

		this.url = new URL("http://www.domain.ect");

		this.instanceCreated = false;
	}

	@AfterEach
	public void tearDown() throws Exception {
		if (this.instanceCreated) {
			TokenCreator.destroy();
		}
	}

	@Test
	public void testNotInstanted() {
		assertThrows(IllegalStateException.class, () -> TokenCreator.getInstance());
	}

	@Test
	public void testConstructor_NullConfiguration() {
		assertThrows(NullPointerException.class, () -> this.createTokenCreatorInstance(null));
	}

	@Test
	public void testConstructor() {
		this.createTokenCreatorInstance();
	}

	@Test
	public void testMultipleConstructions() {
		assertThrows(IllegalStateException.class, () -> {
			this.createTokenCreatorInstance();
			this.createTokenCreatorInstance();
		});
	}

	@Test
	public void testCreateImageResizeToken() {
		this.createTokenCreatorInstance();

		ImageResizeTokenBuilder builder = TokenCreator.getInstance().createImageResizeToken(this.url, 100, 200);

		this.verifyConfigurationPropertiesCalled();

		assertNotNull(builder);
	}

	@Test
	public void testCreateImageTransformToken() {
		this.createTokenCreatorInstance();

		ImageTransformationTokenBuilder builder =
				TokenCreator.getInstance().createImageTransformationToken(this.url, 100, 200);

		this.verifyConfigurationPropertiesCalled();

		assertNotNull(builder);
	}

	@Test
	public void testDestroy() {
		this.createTokenCreatorInstance();
		TokenCreator.destroy();
		this.instanceCreated = false;
	}

	@Test
	public void testDestroyNotInstantiated() {
		assertThrows(IllegalStateException.class, () -> TokenCreator.destroy());
	}

	private TokenCreator createTokenCreatorInstance() {
		return this.createTokenCreatorInstance(this.focusPointsConfigurationProperties);
	}

	private TokenCreator createTokenCreatorInstance(
			FocusPointsConfigurationProperties focusPointsConfigurationProperties) {
		this.instanceCreated = true;
		return new TokenCreator(focusPointsConfigurationProperties);
	}

	private void verifyConfigurationPropertiesCalled() {
		verify(this.focusPointsConfigurationProperties).getUsername();
		verify(this.focusPointsConfigurationProperties).getPassword();
	}
}
