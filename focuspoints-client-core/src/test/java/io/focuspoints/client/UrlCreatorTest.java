package io.focuspoints.client;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.spy;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class UrlCreatorTest {

	private FocusPointsConfigurationProperties focusPointsConfigurationProperties;

	private String url = "http://www.domain.ect";
	private String tokenRequestParameterName = "testJwtParameterName";

	private String token = "testToken";

	private boolean instanceCreated;

	@BeforeEach
	public void setUp() throws Exception {
		this.focusPointsConfigurationProperties = spy(new FocusPointsConfigurationProperties());

		this.focusPointsConfigurationProperties.setUrl(this.url);
		this.focusPointsConfigurationProperties.setTokenRequestParameterName(this.tokenRequestParameterName);

		this.instanceCreated = false;
	}

	@AfterEach
	public void tearDown() throws Exception {
		if (this.instanceCreated) {
			UrlCreator.destroy();
		}
	}

	@Test
	public void testNotInstanted() {
		assertThrows(IllegalStateException.class, () -> UrlCreator.getInstance());
	}

	@Test
	public void testConstructor_NullConfiguration() {
		assertThrows(NullPointerException.class, () -> this.createUrlCreatorInstance(null));
	}

	@Test
	public void testConstructor() {
		this.createUrlCreatorInstance();
	}

	@Test
	public void testMultipleConstructions() {
		assertThrows(IllegalStateException.class, () -> {
			this.createUrlCreatorInstance();
			this.createUrlCreatorInstance();
		});
	}

	@Test
	public void testCreate() {
		this.createUrlCreatorInstance();

		UrlBuilder builder = UrlCreator.getInstance().create(this.token);

		this.verifyConfigurationPropertiesCalled();

		assertNotNull(builder);
	}

	@Test
	public void testDestroy() {
		this.createUrlCreatorInstance();
		UrlCreator.destroy();
		this.instanceCreated = false;
	}

	@Test
	public void testDestroy_NotInstantiated() {
		assertThrows(IllegalStateException.class, () -> UrlCreator.destroy());

		;
	}

	private UrlCreator createUrlCreatorInstance() {
		return this.createUrlCreatorInstance(this.focusPointsConfigurationProperties);
	}

	private UrlCreator createUrlCreatorInstance(FocusPointsConfigurationProperties focusPointsConfigurationProperties) {
		this.instanceCreated = true;
		return new UrlCreator(focusPointsConfigurationProperties);
	}

	private void verifyConfigurationPropertiesCalled() {
		Mockito.verify(this.focusPointsConfigurationProperties).getUrl();
		Mockito.verify(this.focusPointsConfigurationProperties).getTokenRequestParameterName();
	}
}
