package vn.getgreen.adapter;

import java.util.List;

import vn.getgreen.R;
import vn.getgreen.enties.Post;
import vn.getgreen.view.GImageView;
import vn.getgreen.view.GTextView;
import vn.getgreen.view.GTimeStampView;
import vn.getgreen.view.GWebView;
import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

public class PageAdapter extends BaseAdapter {
	List<Post> posts;
	Context context;
	public PageAdapter(Context context, List<Post> posts) {
		this.posts = posts;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return posts.size();
	}

	@Override
	public Post getItem(int position) {
		// TODO Auto-generated method stub
		return posts.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.threaditem, null);
        }
		// User
		GImageView iconLay = (GImageView) convertView.findViewById(R.id.iconLay);
		GImageView onlineStatus = (GImageView) convertView.findViewById(R.id.onlineStatus);
		GTextView post_author_name = (GTextView) convertView.findViewById(R.id.post_author_name);
		GTimeStampView post_reply_time = (GTimeStampView) convertView.findViewById(R.id.post_reply_time);
		
		LinearLayout post_content =(LinearLayout) convertView.findViewById(R.id.post_content);
		LinearLayout post_attach =(LinearLayout) convertView.findViewById(R.id.post_attach);
		GTextView content_webview = (GTextView) convertView.findViewById(R.id.content_webview);
		
		LinearLayout post_thanks =(LinearLayout) convertView.findViewById(R.id.post_thanks);
		GTextView thanks_text = (GTextView) convertView.findViewById(R.id.thanks_text);
		
		LinearLayout post_like = (LinearLayout) convertView.findViewById(R.id.post_like);
		GTextView like_info_text = (GTextView) convertView.findViewById(R.id.like_info_text);
		
		Post post = posts.get(position);
		iconLay.setImageUrl(post.getLinks().getPoster_avatar(), R.drawable.default_avatar_dark);
		post_author_name.setText(post.getPoster_username());
		post_reply_time.setTime(post.getPost_create_date());
		
		if (post.getPost_like_count() > 0) {
			post_like.setVisibility(View.VISIBLE);
			like_info_text.setText(post.getPost_like_count()+"");
		} else {
			post_like.setVisibility(View.GONE);
		}
		
		return convertView;
	}

}
