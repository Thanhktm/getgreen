package vn.getgreen.adapter;

import java.util.List;

import vn.getgreen.R;
import vn.getgreen.enties.Post;
import vn.getgreen.enties.Thread;
import vn.getgreen.imagecache.ImageFetcher;
import vn.getgreen.view.GImageView;
import vn.getgreen.view.GShortContentView;
import vn.getgreen.view.GTextView;
import vn.getgreen.view.GTimeStampView;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources.Theme;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;

public class ThreadAdapter extends BaseAdapter {
	List<Thread> threads;
	Context context;
	ImageFetcher mImageFetcher;
	public ThreadAdapter(Context context, List<Thread> threads, ImageFetcher imageFetcher) {
		this.threads = threads;
		this.context = context;
		this.mImageFetcher = imageFetcher;
	}

	@Override
	public int getCount() {
		return threads.size();
	}

	@Override
	public Thread getItem(int position) {
		return threads.get(position);
	}

	@Override
	public long getItemId(int position) {
		return getItem(position).getThread_id();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.ics_topicitem, null);
        }
		// Call view
		GImageView iconNotify = (GImageView) convertView.findViewById(R.id.notification_flag);
		GImageView iconUser = (GImageView) convertView.findViewById(R.id.usericon);
		GImageView avatar = (GImageView) convertView.findViewById(R.id.avater_bg);
		
		GImageView approve = (GImageView) convertView.findViewById(R.id.approve);
		GImageView close = (GImageView) convertView.findViewById(R.id.close);
		GImageView attach = (GImageView) convertView.findViewById(R.id.attach);
		GImageView topic_sticky = (GImageView) convertView.findViewById(R.id.topic_sticky);
		GImageView topic_ann = (GImageView) convertView.findViewById(R.id.topic_ann);
		
		GTimeStampView topictime = (GTimeStampView) convertView.findViewById(R.id.topictime);
		GImageView breaker2 = (GImageView) convertView.findViewById(R.id.breaker2);
		
		GTextView view_num = (GTextView) convertView.findViewById(R.id.view_num);
		GImageView view_icon = (GImageView) convertView.findViewById(R.id.view_icon);
		GImageView breaker = (GImageView) convertView.findViewById(R.id.breaker);
		GTextView reply_num = (GTextView) convertView.findViewById(R.id.reply_num);
		GImageView reply_icon = (GImageView) convertView.findViewById(R.id.reply_icon);
		GTextView topicauthor = (GTextView) convertView.findViewById(R.id.topicauthor);
		GTextView topictitle = (GTextView) convertView.findViewById(R.id.topictitle);
		
		GShortContentView shortcontent = (GShortContentView) convertView.findViewById(R.id.shortcontent);
		
		RelativeLayout forumtitle_lay = (RelativeLayout) convertView.findViewById(R.id.forumtitle_lay);
		
		GImageView title_logo = (GImageView) convertView.findViewById(R.id.title_logo);
		GTextView forumtitle = (GTextView) convertView.findViewById(R.id.forumtitle);
		
		
        Thread thread = threads.get(position);
        // bind data
        topictitle.setText(thread.getThread_title());
        topicauthor.setText(thread.getCreator_username());
        Post post = thread.getFirst_post();
        if(post != null && post.getLinks() != null)
        {
        	mImageFetcher.loadThumbnailImage(post.getLinks().getPoster_avatar(), avatar, R.drawable.default_avatar_dark);
        	shortcontent.setText(thread.getFirst_post().getPost_body_plain_text());
        }
        view_num.setText(thread.getThread_view_count() + "");
        reply_num.setText(thread.getThread_post_count() + "");
        topic_sticky.setVisibility(thread.isThread_is_sticky() ? View.VISIBLE : View.GONE);
        topictime.setTime(thread.getThread_update_date());
        topictime.setVisibility(thread.isThread_is_sticky() ? View.GONE : View.VISIBLE);
		return convertView;
	}

}
