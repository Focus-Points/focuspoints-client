package io.focuspoints.client.taglib.support;

import io.focuspoints.client.FocusPointsConfigurationProperties;
import io.focuspoints.client.UrlCreator;
import io.focuspoints.client.util.TagUtils;
import io.focuspoints.client.util.UrlUtils;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ImageOperationTag extends TagSupport {

	private static final long serialVersionUID = -1806302178121606858L;

	private String var;
	private String url;
	private String filename;

	@Setter(AccessLevel.NONE)
	private int scope = PageContext.PAGE_SCOPE;

	@Override
	public int doEndTag() throws JspException {
		if (this.getVar() == null) {
			try {
				this.pageContext.getOut().write(this.getValue());
			} catch (IOException e) {
				throw new JspException(e);
			}
		} else {
			this.pageContext.setAttribute(this.getVar(), this.getValue(), this.getScope());
		}

		return EVAL_PAGE;
	}
	
	protected FocusPointsConfigurationProperties getConfiguration() {
		Object obj = this.pageContext.getAttribute(
				FocusPointsConfigurationProperties.class.getName(), PageContext.APPLICATION_SCOPE);
		
		if(obj == null) {
			throw new IllegalStateException("No configuration found in servlet context for attribute " +
					FocusPointsConfigurationProperties.class.getName());
		}
		
		if(!FocusPointsConfigurationProperties.class.isAssignableFrom(obj.getClass())) {
			throw new IllegalStateException("Incompatible configuration type found in servlet context for attribte " +
					FocusPointsConfigurationProperties.class.getName() + ": " + obj.getClass().getName());
		}
		
		return (FocusPointsConfigurationProperties) obj; 
	}

	protected URL getImageUrl() {
		try {
			return new URL(this.getUrl());
		} catch (MalformedURLException e) {
			throw new IllegalStateException(String.format("%s is not an absolute URL", this.getUrl()));
		}
	}

	protected String getValue() throws JspException {
		if(!this.getConfiguration().isEnabled()) {
			return this.getImageUrl().toExternalForm();
		}
		
		String token = this.createToken();

		String imageOperationUrl =
				UrlCreator.getInstance()
						.create(token)
						.withFilename(this.getFilename())
						.build();

		return imageOperationUrl;

	}

	protected abstract String createToken();

	public void setUrl(String url) {
		if (!UrlUtils.isAbsoluteUrl(url)) {
			throw new IllegalArgumentException(String.format("%s is not an absolute URL", url));
		}
		this.url = url;
	}

	public void setScope(String scope) {
		this.scope = TagUtils.getScope(scope);
	}
}
