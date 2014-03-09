package vn.getgreen.view;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import vn.getgreen.R;
import vn.getgreen.common.DateUtils;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class GTextView extends TextView {

	public GTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public GTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public GTextView(Context context) {
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
	public void setSince(long time)
	{
		Date date = new Date((long)time*1000);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd");
		setText(sdf.format(date));	
	}
	
	public void setBirtDay(int year, int month, int day, String format)
	{
		Calendar calendar = Calendar.getInstance();
		String birth = day +"/"+ month +"/"+ year +" ("+ format + (calendar.get(Calendar.YEAR)-year)+")";
		setText(birth);
	}
}
