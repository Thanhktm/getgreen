package vn.getgreen.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import vn.getgreen.common.DateUtils;

import android.content.Context;
import android.util.AttributeSet;

public class GTimeStampView extends GTextView {

	public GTimeStampView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public GTimeStampView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public GTimeStampView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public void setTime(long time)
	{
		Date date = new Date((long)time*1000);
		if(DateUtils.isToday(date))
		{
			SimpleDateFormat sdf = new SimpleDateFormat("hh:mma");
			setText(sdf.format(date));	
			return;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd hh:mma");
		setText(sdf.format(date));	
		
	}

}
