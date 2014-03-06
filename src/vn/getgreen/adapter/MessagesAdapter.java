package vn.getgreen.adapter;

import java.util.List;

import vn.getgreen.R;
import vn.getgreen.enties.Message;
import vn.getgreen.enties.User;
import vn.getgreen.imagecache.ImageFetcher;
import vn.getgreen.view.GImageView;
import vn.getgreen.view.GTextView;
import vn.getgreen.view.GTimeStampView;
import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MessagesAdapter extends BaseAdapter {
	List<Message> messages;
	Context context;
	private ImageFetcher mImageFetcher;
	private User me;
	public MessagesAdapter(Context context, List<Message> messages, ImageFetcher imageFetcher) {
		this.messages = messages;
		this.context = context;
		this.mImageFetcher = imageFetcher;
		this.me = User.get(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return messages.size();
	}

	@Override
	public Object getItem(int position) {
		return messages.get(position);
	}

	@Override
	public long getItemId(int position) {
		return messages.get(position).getMessage_id();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Message message = messages.get(position);
		if(message.getCreator_user_id() == me.getUser_id())
		{
			// me
			if (true) {
	            LayoutInflater mInflater = (LayoutInflater)
	                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	            convertView = mInflater.inflate(R.layout.conversation_item_of_me, null);
	        }
			GTextView conversation_shortcontent = (GTextView) convertView.findViewById(R.id.conversation_shortcontent);
			GTextView conversation_attachment = (GTextView) convertView.findViewById(R.id.conversation_attachment);
			GTimeStampView senttime = (GTimeStampView) convertView.findViewById(R.id.senttime);
			
			conversation_shortcontent.setText(Html.fromHtml(message.getMessage_body_html()));
			senttime.setTime(message.getMessage_create_date());
			
			return convertView;
		} else {
			// Not me
			if (true) {
	            LayoutInflater mInflater = (LayoutInflater)
	                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	            convertView = mInflater.inflate(R.layout.conversation_item, null);
	        }
			GImageView conversation_authoricon = (GImageView) convertView.findViewById(R.id.conversation_authoricon);
			GTextView conversation_author = (GTextView) convertView.findViewById(R.id.conversation_author);
			GTextView conversation_shortcontent = (GTextView) convertView.findViewById(R.id.conversation_shortcontent);
			GTextView conversation_attachment = (GTextView) convertView.findViewById(R.id.conversation_attachment);
			GTimeStampView senttime = (GTimeStampView) convertView.findViewById(R.id.senttime);
			
			mImageFetcher.loadImage(message.getLinks().getCreator_avatar(), conversation_authoricon);
			conversation_author.setText(message.getCreator_username());
			conversation_shortcontent.setText(Html.fromHtml(message.getMessage_body_html()));
			senttime.setTime(message.getMessage_create_date());
			return convertView;
		}
	}

}
