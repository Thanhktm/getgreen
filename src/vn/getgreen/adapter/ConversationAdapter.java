package vn.getgreen.adapter;

import java.util.List;

import vn.getgreen.R;
import vn.getgreen.enties.Conversation;
import vn.getgreen.view.GImageView;
import vn.getgreen.view.GShortContentView;
import vn.getgreen.view.GTextView;
import vn.getgreen.view.GTimeStampView;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ConversationAdapter extends BaseAdapter {
	List<Conversation> conversations;
	Context context;
	public ConversationAdapter(Context context, List<Conversation> conversations) {
		this.conversations = conversations;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return conversations.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return conversations.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.ics_conversation_item, null);
        }
		
		GImageView uneadicon = (GImageView) convertView.findViewById(R.id.uneadicon);
		GImageView pmauthoricon = (GImageView) convertView.findViewById(R.id.pmauthoricon);
		GImageView avater_bg = (GImageView) convertView.findViewById(R.id.avater_bg);
		GTimeStampView senttime = (GTimeStampView) convertView.findViewById(R.id.senttime);
		GTextView pmauthor = (GTextView) convertView.findViewById(R.id.pmauthor);
		GTextView pmtitle = (GTextView) convertView.findViewById(R.id.pmtitle);
		GShortContentView shortcontent = (GShortContentView) convertView.findViewById(R.id.shortcontent);

		Conversation conversation = conversations.get(position);
        
		if(conversation.isConversation_is_open())
		{
			uneadicon.setVisibility(View.VISIBLE);
		}else {
			uneadicon.setVisibility(View.GONE);
		}
		pmauthoricon.setImageUrl(conversation.getLinks().getCreator_avatar(), R.drawable.default_avatar_dark);
		senttime.setTime(conversation.getConversation_create_date());
		pmauthor.setText(conversation.getCreator_username());
		pmtitle.setText(conversation.getConversation_title());
		shortcontent.setText(conversation.getFirst_message().getMessage_body_plain_text());
		return convertView;
	}

}
