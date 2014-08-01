package vn.getgreen.network;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import vn.getgreen.enties.Links;
import vn.getgreen.enties.Post;
import vn.getgreen.enties.Thread;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;

public class PostService extends GClient {
	public static final int LIMIT_POSTS_PER_PAGE = 10;
	public static final String ORDER_NATURAL = "natural";
	public static final String ORDER_NATURAL_REVERSE = "natural_reverse";
	public List<Post> posts = new ArrayList<Post>();
	public int posts_total;
	public Links links;
	public Post post;
	
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
	
	/**
	 * Create new post for a thread
	 * @param thread
	 * @param body
	 */
	public void createPost(Thread thread, String body)
	{
		RequestParams params = new RequestParams();
		params.put("thread_id", thread.getThread_id() + "");
		params.put("post_body", body);
		post(params, "posts");
	}
	
	/**
	 * Make like a post
	 * @param post
	 */
	public void like(Post post)
	{
		post(null, "posts/"+post.getPost_id()+"/likes");
	}
	
	/**
	 * Unlike one post
	 * @param post
	 */
	public void unLike(Post post)
	{
		delete(null, "posts/"+post.getPost_id()+"/likes");
	}
	
	/**
	 * Delete one post
	 * @param post
	 */
	public void remove(Post post)
	{
		delete(null, "posts/"+post.getPost_id());
	}
	
	public void editPost(Post post)
	{
		RequestParams params = new RequestParams();
		params.put("post_body", post.getPost_body());
		put(params,"posts/"+post.getPost_id());
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
			if(!jsonObject.isNull("post"))
			{
				post = gson.fromJson(jsonObject.getJSONObject("post").toString(), Post.class);
			}
			if(!jsonObject.isNull("thread"))
			{
				post = gson.fromJson(jsonObject.getJSONObject("thread").getJSONObject("first_post").toString(), Post.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return super.parseJson(jsonObject);
	}
}
