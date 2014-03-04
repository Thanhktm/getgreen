package vn.getgreen.network;

import java.lang.reflect.Type;
import java.util.List;

import org.json.JSONObject;

import vn.getgreen.enties.Links;
import vn.getgreen.enties.Post;
import vn.getgreen.enties.Thread;
import vn.getgreen.view.GWebView;
import android.content.Context;
import android.widget.LinearLayout.LayoutParams;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;

public class PostService extends GClient {
	public static final int LIMIT_POSTS_PER_PAGE = 10;
	public static final String ORDER_NATURAL = "natural";
	public static final String ORDER_NATURAL_REVERSE = "natural_reverse";
	public List<Post> posts;
	public int posts_total;
	public Links links;
	
	public PostService(Context context, ResponseListener responseListener) {
		super(context, responseListener);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Get list post by thread
	 * @param thread which content posts
	 * @param page number page
	 * @param order please see constants
	 */
	public void listByThread(Thread thread, int page, String order)
	{
		RequestParams params = new RequestParams();
		params.put("thread_id", thread.getThread_id() + "");
		params.put("page", page + "");
		params.put("limit", LIMIT_POSTS_PER_PAGE +"");
		params.put("order", order);
		get(params, "posts");
	}
	
	public void createPost(Thread thread, String body)
	{
		RequestParams params = new RequestParams();
		params.put("thread_id", thread.getThread_id() + "");
		params.put("post_body", body);
		post(params, "posts");
	}
	@Override
	public boolean parseJson(JSONObject jsonObject) {
		try {
			Gson gson = new Gson();
			if(!jsonObject.isNull("posts"))
			{
				Type listType = new TypeToken<List<Post>>() {
				}.getType();
				posts = gson.fromJson(jsonObject.getJSONArray("posts").toString(), listType);
			}
			if(!jsonObject.isNull("posts_total"))
			{
				posts_total = jsonObject.getInt("posts_total");
			}
			if(!jsonObject.isNull("links"))
			{
				links = gson.fromJson(jsonObject.getJSONObject("links").toString(), Links.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return super.parseJson(jsonObject);
	}
}
