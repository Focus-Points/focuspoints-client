package io.focuspoints.client.taglib;

import io.focuspoints.client.TokenCreator;
import io.focuspoints.client.taglib.support.ImageOperationTag;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageResizeTag extends ImageOperationTag {

	private static final long serialVersionUID = 2022367703373959556L;

	private int width;
	private int height;

	@Override
	protected String createToken() {
		return TokenCreator.getInstance()
				.createImageResizeToken(this.getImageUrl(), this.getWidth(), this.getHeight())
				.build();
	}
}
