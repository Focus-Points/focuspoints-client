package io.focuspoints.client;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import org.apache.commons.lang3.Validate;

public abstract class AbstractImageTokenBuilder<T extends AbstractImageTokenBuilder<T>> {

	private static final String CLAIM_ACTION = "action";
	
	private String issuer;
	private String secret;

	protected AbstractImageTokenBuilder(String issuer, String secret) {
		this.withIssuer(issuer);
		this.withSecret(secret);
	}
	
	@SuppressWarnings("unchecked")
	public final T withIssuer(String issuer) {
		Validate.notBlank(issuer, "issuer is required");
		this.issuer = issuer;
		return (T) this;
	}
	
	@SuppressWarnings("unchecked")
	public final T withSecret(String secret) {
		Validate.notBlank(secret, "secret is required");
		this.secret = secret;
		return (T) this;
	}

	public final String build() {
		JWTCreator.Builder jwtBuilder = JWT.create();

		this.buildInternal(jwtBuilder);

		return jwtBuilder
				.withIssuer(this.issuer)
				.withClaim(CLAIM_ACTION, this.getAction())
				.sign(Algorithm.HMAC512(this.secret));

	}

	protected void buildInternal(JWTCreator.Builder jwtBuilder) {
	}

	protected abstract String getAction();
}
