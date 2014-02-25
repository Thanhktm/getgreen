package vn.getgreen.cache;

import android.content.Context;
import android.util.AttributeSet;

public class SquareSmartImageView extends SmartImageView {

	public SquareSmartImageView(Context context) {
		super(context);
	}

	public SquareSmartImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SquareSmartImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int height = getMeasuredHeight();
		setMeasuredDimension(height, height);
	}
}
