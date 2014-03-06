package vn.getgreen.adapter;

import java.util.List;

import vn.getgreen.R;
import vn.getgreen.enties.Notification;
import vn.getgreen.imagecache.ImageFetcher;
import vn.getgreen.view.GImageView;
import vn.getgreen.view.GShortContentView;
import vn.getgreen.view.GTextView;
import vn.getgreen.view.GTimeStampView;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class AlertAdapter extends BaseAdapter{

	private List<Notification> notifications;
	private Context context;
	private ImageFetcher mImageFetcher;
	
	public AlertAdapter(Context context, List<Notification> notifications, ImageFetcher imageFetcher) {
		this.context = context;
		this.notifications = notifications;
		this.mImageFetcher = imageFetcher;
	}

	@Override
	public int getCount() {
		return notifications.size();
	}

	@Override
	public Object getItem(int position) {
		return notifications.get(position);
	}

	@Override
	public long getItemId(int position) {
		return notifications.get(position).getNotification_id();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.ics_alert_item, null);
        }
		GImageView alert_icon = (GImageView) convertView.findViewById(R.id.alert_icon);
		GImageView typeIcon = (GImageView) convertView.findViewById(R.id.typeIcon);
		GTimeStampView timestamp = (GTimeStampView) convertView.findViewById(R.id.timestamp);
		GTextView alert_name = (GTextView) convertView.findViewById(R.id.alert_name);
		GShortContentView alert_message = (GShortContentView) convertView.findViewById(R.id.alert_message);
		
		Notification notification = this.notifications.get(position);
		timestamp.setTime(notification.getNotification_create_date());
		alert_name.setText(notification.getCreator_username());
		alert_message.setText(notification.getNotification_plain_text());
		return convertView;
	}
}
