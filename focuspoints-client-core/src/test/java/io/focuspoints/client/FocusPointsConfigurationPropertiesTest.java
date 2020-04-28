package io.focuspoints.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

public class FocusPointsConfigurationPropertiesTest {

	@Test
	public void testDefaults() {
		FocusPointsConfigurationProperties focusPointsConfigurationProperties = this.createDefaultConfiguration();

		assertEquals(FocusPointsConfigurationProperties.URL_DEFAULT, focusPointsConfigurationProperties.getUrl());
		assertEquals(
				FocusPointsConfigurationProperties.TOKEN_REQUEST_PARAMETER_NAME_DEFAULT,
				focusPointsConfigurationProperties.getTokenRequestParameterName()
		);
		assertNull(focusPointsConfigurationProperties.getTokenId());
		assertNull(focusPointsConfigurationProperties.getTokenSecret());
	}

	@Test
	public void testValidate_NullUrl() {
		FocusPointsConfigurationProperties focusPointsConfigurationProperties = this.createEmptyConfiguration();
		focusPointsConfigurationProperties.setUrl(null);

		assertThrows(NullPointerException.class, () -> focusPointsConfigurationProperties.validate());
	}

	@Test
	public void testValidate_EmptyUrl() {
		FocusPointsConfigurationProperties focusPointsConfigurationProperties = this.createEmptyConfiguration();
		focusPointsConfigurationProperties.setUrl(StringUtils.EMPTY);

		assertThrows(IllegalArgumentException.class, () -> focusPointsConfigurationProperties.validate());
	}

	@Test
	public void testValidate_NullTokenRequestParameterName() {
		FocusPointsConfigurationProperties focusPointsConfigurationProperties = this.createEmptyConfiguration();
		this.setValidUrl(focusPointsConfigurationProperties);

		assertThrows(NullPointerException.class, () -> focusPointsConfigurationProperties.validate());
	}

	@Test
	public void testValidate_EmptyTokenRequestParameterName() {
		FocusPointsConfigurationProperties focusPointsConfigurationProperties = this.createEmptyConfiguration();
		this.setValidUrl(focusPointsConfigurationProperties);
		focusPointsConfigurationProperties.setTokenRequestParameterName(StringUtils.EMPTY);

		assertThrows(IllegalArgumentException.class, () -> focusPointsConfigurationProperties.validate());
	}

	@Test
	public void testValidate_NullTokenId() {
		FocusPointsConfigurationProperties focusPointsConfigurationProperties = this.createEmptyConfiguration();
		this.setValidUrl(focusPointsConfigurationProperties);
		this.setValidTokenRequestParameterName(focusPointsConfigurationProperties);

		assertThrows(NullPointerException.class, () -> focusPointsConfigurationProperties.validate());
	}

	@Test
	public void testValidate_EmptyTokenId() {
		FocusPointsConfigurationProperties focusPointsConfigurationProperties = this.createEmptyConfiguration();
		this.setValidUrl(focusPointsConfigurationProperties);
		this.setValidTokenRequestParameterName(focusPointsConfigurationProperties);
		focusPointsConfigurationProperties.setTokenId(StringUtils.EMPTY);

		assertThrows(IllegalArgumentException.class, () -> focusPointsConfigurationProperties.validate());
	}

	@Test
	public void testValidate_NullTokenSecret() {
		FocusPointsConfigurationProperties focusPointsConfigurationProperties = this.createEmptyConfiguration();
		this.setValidUrl(focusPointsConfigurationProperties);
		this.setValidTokenRequestParameterName(focusPointsConfigurationProperties);
		this.setValidTokenId(focusPointsConfigurationProperties);

		assertThrows(NullPointerException.class, () -> focusPointsConfigurationProperties.validate());
	}

	@Test
	public void testValidate_EmptyTokenSecret() {
		FocusPointsConfigurationProperties focusPointsConfigurationProperties = this.createEmptyConfiguration();
		this.setValidUrl(focusPointsConfigurationProperties);
		this.setValidTokenRequestParameterName(focusPointsConfigurationProperties);
		this.setValidTokenId(focusPointsConfigurationProperties);
		focusPointsConfigurationProperties.setTokenSecret(StringUtils.EMPTY);

		assertThrows(IllegalArgumentException.class, () -> focusPointsConfigurationProperties.validate());
	}

	@Test
	public void testValid() {
		FocusPointsConfigurationProperties focusPointsConfigurationProperties = this.createEmptyConfiguration();
		this.setValidUrl(focusPointsConfigurationProperties);
		this.setValidTokenRequestParameterName(focusPointsConfigurationProperties);
		this.setValidTokenId(focusPointsConfigurationProperties);
		this.setValidTokenSecret(focusPointsConfigurationProperties);
		focusPointsConfigurationProperties.validate();
	}

	private FocusPointsConfigurationProperties createDefaultConfiguration() {
		return new FocusPointsConfigurationProperties();
	}

	private FocusPointsConfigurationProperties createEmptyConfiguration() {
		FocusPointsConfigurationProperties focusPointsConfigurationProperties = this.createDefaultConfiguration();
		focusPointsConfigurationProperties.setUrl(null);
		focusPointsConfigurationProperties.setTokenRequestParameterName(null);
		focusPointsConfigurationProperties.setTokenId(null);
		focusPointsConfigurationProperties.setTokenSecret(null);
		return focusPointsConfigurationProperties;
	}

	private void setValidUrl(FocusPointsConfigurationProperties focusPointsConfigurationProperties) {
		focusPointsConfigurationProperties.setUrl("http://www.domain.ext");
	}

	private void setValidTokenRequestParameterName(
			FocusPointsConfigurationProperties focusPointsConfigurationProperties) {
		focusPointsConfigurationProperties
				.setTokenRequestParameterName(FocusPointsConfigurationProperties.TOKEN_REQUEST_PARAMETER_NAME_DEFAULT);
	}

	private void setValidTokenId(FocusPointsConfigurationProperties focusPointsConfigurationProperties) {
		focusPointsConfigurationProperties.setTokenId("testTokenId");
	}

	private void setValidTokenSecret(FocusPointsConfigurationProperties focusPointsConfigurationProperties) {
		focusPointsConfigurationProperties.setTokenSecret("testTokenSecret");
	}
}
