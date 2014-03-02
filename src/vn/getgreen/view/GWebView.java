package vn.getgreen.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

public class GWebView extends WebView {

	public GWebView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public GWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public GWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void loadData(String data, String mimeType, String encoding) {
		super.loadData(data, mimeType, encoding);
		
	}

}
