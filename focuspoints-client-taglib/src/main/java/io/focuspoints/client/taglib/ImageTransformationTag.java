package io.focuspoints.client.taglib;

import io.focuspoints.client.TokenCreator;
import io.focuspoints.client.taglib.support.ImageOperationTag;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageTransformationTag extends ImageOperationTag {

	private static final long serialVersionUID = 4146992538098487985L;

	private int width;
	private int height;
	private Double focusPointX;
	private Double focusPointY;

	@Override
	protected String createToken() {
		return TokenCreator.getInstance()
				.createImageTransformationToken(this.getImageUrl(), this.getWidth(), this.getHeight())
				.withFocusPointX(this.getFocusPointX())
				.withFocusPointY(this.getFocusPointY())
				.build();
	}
}
