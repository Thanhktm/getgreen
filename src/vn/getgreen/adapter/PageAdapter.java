package vn.getgreen.adapter;

import java.util.List;

import vn.getgreen.R;
import vn.getgreen.enties.Post;
import vn.getgreen.imagecache.ImageFetcher;
import vn.getgreen.view.GImageView;
import vn.getgreen.view.GTextView;
import vn.getgreen.view.GTimeStampView;
import vn.getgreen.view.URLImageParser;
import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

public class PageAdapter extends BaseAdapter {
	List<Post> posts;
	Context context;
	ImageFetcher mImageFetcher;
	public PageAdapter(Context context, List<Post> posts, ImageFetcher imageFetcher) {
		this.posts = posts;
		this.context = context;
		this.mImageFetcher = imageFetcher;
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
		ViewHolder viewHolder = null;
		View view = null;
		if (true) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.threaditem, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.iconLay = (GImageView) view.findViewById(R.id.iconLay);
            viewHolder.onlineStatus = (GImageView) view.findViewById(R.id.onlineStatus);
            viewHolder.post_author_name = (GTextView) view.findViewById(R.id.post_author_name);
            viewHolder.post_reply_time = (GTimeStampView) view.findViewById(R.id.post_reply_time);
    		
            viewHolder.post_content =(LinearLayout) view.findViewById(R.id.post_content);
            viewHolder.post_attach =(LinearLayout) view.findViewById(R.id.post_attach);
            viewHolder.content = (GTextView) view.findViewById(R.id.content);
    		
            viewHolder.post_thanks =(LinearLayout) view.findViewById(R.id.post_thanks);
            viewHolder.thanks_text = (GTextView) view.findViewById(R.id.thanks_text);
    		
            viewHolder.post_like = (LinearLayout) view.findViewById(R.id.post_like);
            viewHolder.like_info_text = (GTextView) view.findViewById(R.id.like_info_text);
            view.setTag(viewHolder);
        }else {
        	view = convertView;
            viewHolder = ((ViewHolder) view.getTag());
		}
		
		Post post = posts.get(position);
		if(mImageFetcher != null)mImageFetcher.loadThumbnailImage(post.getLinks().getPoster_avatar(), viewHolder.iconLay, R.drawable.default_avatar_dark);
		//viewHolder.iconLay.setImageUrl(post.getLinks().getPoster_avatar(), R.drawable.default_avatar_dark);
		viewHolder.post_author_name.setText(post.getPoster_username());
		viewHolder.post_reply_time.setTime(post.getPost_create_date());
		
		URLImageParser p = new URLImageParser(viewHolder.content, context, post.map);
		Spanned htmlSpan = Html.fromHtml(post.getPost_body_html() , p, null);
		viewHolder.content.setText(htmlSpan);
		
		if (post.getPost_like_count() > 0) {
			viewHolder.post_like.setVisibility(View.VISIBLE);
			viewHolder.like_info_text.setText(post.getPost_like_count()+"");
		} else {
			viewHolder.post_like.setVisibility(View.GONE);
		}
		
		return view;
	}
	
	static class ViewHolder {
        protected GImageView iconLay;
        protected GImageView onlineStatus;
        protected GTextView post_author_name;
        protected GTimeStampView post_reply_time;
        
        protected LinearLayout post_content;
        protected LinearLayout post_attach;
        protected GTextView content;
        protected LinearLayout post_thanks;
        protected GTextView thanks_text;
        protected LinearLayout post_like;
        protected GTextView like_info_text;
        
        
    }

}
