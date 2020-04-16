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

		assertNull(focusPointsConfigurationProperties.getUrl());
		assertEquals(
				FocusPointsConfigurationProperties.TOKEN_REQUEST_PARAMETER_NAME_DEFAULT,
				focusPointsConfigurationProperties.getTokenRequestParameterName()
		);
		assertNull(focusPointsConfigurationProperties.getUsername());
		assertNull(focusPointsConfigurationProperties.getPassword());
	}

	@Test
	public void testValidateWithNullUrl() {
		FocusPointsConfigurationProperties focusPointsConfigurationProperties = this.createEmptyConfiguration();

		assertThrows(NullPointerException.class, () -> focusPointsConfigurationProperties.validate());
	}

	@Test
	public void testValidateWithEmptyUrl() {
		FocusPointsConfigurationProperties focusPointsConfigurationProperties = this.createEmptyConfiguration();
		focusPointsConfigurationProperties.setUrl(StringUtils.EMPTY);

		assertThrows(IllegalArgumentException.class, () -> focusPointsConfigurationProperties.validate());
	}

	@Test
	public void testValidateWithNullTokenRequestParameterName() {
		FocusPointsConfigurationProperties focusPointsConfigurationProperties = this.createEmptyConfiguration();
		this.setValidUrl(focusPointsConfigurationProperties);

		assertThrows(NullPointerException.class, () -> focusPointsConfigurationProperties.validate());
	}

	@Test
	public void testValidateWithEmptyTokenRequestParameterName() {
		FocusPointsConfigurationProperties focusPointsConfigurationProperties = this.createEmptyConfiguration();
		this.setValidUrl(focusPointsConfigurationProperties);
		focusPointsConfigurationProperties.setTokenRequestParameterName(StringUtils.EMPTY);

		assertThrows(IllegalArgumentException.class, () -> focusPointsConfigurationProperties.validate());
	}

	@Test
	public void testValidateWithNullUsername() {
		FocusPointsConfigurationProperties focusPointsConfigurationProperties = this.createEmptyConfiguration();
		this.setValidUrl(focusPointsConfigurationProperties);
		this.setValidTokenRequestParameterName(focusPointsConfigurationProperties);

		assertThrows(NullPointerException.class, () -> focusPointsConfigurationProperties.validate());
	}

	@Test
	public void testValidateWithEmptyUsername() {
		FocusPointsConfigurationProperties focusPointsConfigurationProperties = this.createEmptyConfiguration();
		this.setValidUrl(focusPointsConfigurationProperties);
		this.setValidTokenRequestParameterName(focusPointsConfigurationProperties);
		focusPointsConfigurationProperties.setUsername(StringUtils.EMPTY);

		assertThrows(IllegalArgumentException.class, () -> focusPointsConfigurationProperties.validate());
	}

	@Test
	public void testValidateWithNullPassword() {
		FocusPointsConfigurationProperties focusPointsConfigurationProperties = this.createEmptyConfiguration();
		this.setValidUrl(focusPointsConfigurationProperties);
		this.setValidTokenRequestParameterName(focusPointsConfigurationProperties);
		this.setValidUsername(focusPointsConfigurationProperties);

		assertThrows(NullPointerException.class, () -> focusPointsConfigurationProperties.validate());
	}

	@Test
	public void testValidateWithEmptyPassword() {
		FocusPointsConfigurationProperties focusPointsConfigurationProperties = this.createEmptyConfiguration();
		this.setValidUrl(focusPointsConfigurationProperties);
		this.setValidTokenRequestParameterName(focusPointsConfigurationProperties);
		this.setValidUsername(focusPointsConfigurationProperties);
		focusPointsConfigurationProperties.setPassword(StringUtils.EMPTY);

		assertThrows(IllegalArgumentException.class, () -> focusPointsConfigurationProperties.validate());
	}

	@Test
	public void testValid() {
		FocusPointsConfigurationProperties focusPointsConfigurationProperties = this.createEmptyConfiguration();
		this.setValidUrl(focusPointsConfigurationProperties);
		this.setValidTokenRequestParameterName(focusPointsConfigurationProperties);
		this.setValidUsername(focusPointsConfigurationProperties);
		this.setValidPassword(focusPointsConfigurationProperties);
		focusPointsConfigurationProperties.validate();
	}

	private FocusPointsConfigurationProperties createDefaultConfiguration() {
		return new FocusPointsConfigurationProperties();
	}

	private FocusPointsConfigurationProperties createEmptyConfiguration() {
		FocusPointsConfigurationProperties focusPointsConfigurationProperties = this.createDefaultConfiguration();
		focusPointsConfigurationProperties.setUrl(null);
		focusPointsConfigurationProperties.setTokenRequestParameterName(null);
		focusPointsConfigurationProperties.setUsername(null);
		focusPointsConfigurationProperties.setPassword(null);
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

	private void setValidUsername(FocusPointsConfigurationProperties focusPointsConfigurationProperties) {
		focusPointsConfigurationProperties.setUsername("testUsername");
	}

	private void setValidPassword(FocusPointsConfigurationProperties focusPointsConfigurationProperties) {
		focusPointsConfigurationProperties.setPassword("testPassword");
	}
}
