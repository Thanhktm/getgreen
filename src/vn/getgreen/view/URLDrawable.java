package vn.getgreen.view;

import java.io.Serializable;

import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

@SuppressWarnings("deprecation")
public class URLDrawable extends BitmapDrawable implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4266264702040540475L;
	// the drawable that you need to set, you could set the initial drawing
	// with the loading image if you need to
	protected Drawable drawable;

	@Override
	public void draw(Canvas canvas) {
		// override the draw to facilitate refresh function later
		if (drawable != null) {
			drawable.draw(canvas);
		}
	}
}
