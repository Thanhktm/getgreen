package vn.getgreen.network;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import vn.getgreen.enties.Forum;
import vn.getgreen.enties.Thread;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;

public class ThreadService extends GClient {
	public static final String ORDER_NATURAL = "natural";
	public static final String ORDER_CREATE_DATE = "thread_create_date";
	public static final String ORDER_CREATE_DATE_REVERSE = "thread_create_date_reverse";
	public static final String ORDER_UPDATE_DATE = "thread_update_date";
	public static final String ORDER_UPDATE_DATE_REVERSE = "thread_update_date_reverse";
	
	public List<Thread> threads = new ArrayList<Thread>();
	public static final int LIMIT_THREADS_PER_PAGE = 20;
	public int threads_total;
	public Thread thread;
	
	public ThreadService(Context context, ResponseListener responseListener) {
		super(context, responseListener);
	}

	/**
	 * List threads in single forum
	 * @param forum
	 */
	public void listByForum(Forum forum)
	{
		get(null, "threads/&forum_id=" + forum.getForum_id());
	}
	
	/**
	 * List threads unread in single forum
	 * @param forum if null will search all
	 */
	public void listUnreadByForum(Forum forum)
	{
		RequestParams params = new RequestParams();
		params.put("limit", "32");
		if(forum != null) params.put("forum_id", forum.getForum_id());
		get(params, "threads/new");
	}
	
	/**
	 * Create new thread, must set first_post instance of Post Entity
	 * @param thread
	 */
	public void create(Thread thread)
	{
		RequestParams params = new RequestParams();
		params.put("forum_id", thread.getForum_id() +"");
		params.put("thread_title", thread.getThread_title());
		params.put("post_body", thread.getFirst_post().getPost_body());
		post(params, "threads");
	}
	
	/**
	 * Delete a thread
	 * @param thread
	 */
	public void remove(Thread thread)
	{
		delete(null, "threads/"+thread.getThread_id());
	}
	
	/**
	 * get list threads by forums
	 * @param forums contains threads
	 * @param isSticky true return threads sticky
	 * @param order Please view static variable 
	 */
	public void listByForums(List<Forum> forums, boolean isSticky, String order)
	{
		StringBuilder stringBuilder = new StringBuilder();
		for (Forum forum : forums) {
			stringBuilder.append(forum.getForum_id());
			stringBuilder.append(",");
		}
		stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
		RequestParams requestParams = new RequestParams();
		if(isSticky)
		{
			requestParams.put("sticky", "1");
		}
		if(order != null) requestParams.put("order", order);
		
		get(requestParams, "threads/&forum_id=" + stringBuilder.toString());
	}
	/**
	 * Follow some thread, user must loged in
	 * @param thread
	 */
	public void follow(Thread thread)
	{
		post(null, "threads/"+thread.getThread_id()+"/followers");
	}
	
	/**
	 * UnFollow some thread, user must loged in
	 * @param thread
	 */
	public void unfollow(Thread thread)
	{
		delete(null, "threads/"+thread.getThread_id()+"/followers");
	}
	
	/**
	 * Get threads which user following
	 * @param page
	 * @param new_only
	 */
	public void threadsFollowing(int page, boolean new_only)
	{
		RequestParams params = new RequestParams();
		params.put("page", page+"");
		params.put("new_only", new_only +"");
		params.put("limit", LIMIT_THREADS_PER_PAGE +"");
		get(params, "users/threads/following");
	}
	
	/**
	 * Search threads by forum or all
	 * @param query
	 * @param forum
	 */
	public void search(String query, Forum forum)
	{
		RequestParams params = new RequestParams();
		params.put("q", query);
		//if(forum != null) params.put("forum_id", forum.getForum_id()+"");
		params.put("limit", LIMIT_THREADS_PER_PAGE);
		post(params, "search/threads");
	}
	
	@Override
	public boolean parseJson(JSONObject jsonObject) {
		try {
			Gson gson = new Gson();
			if(!jsonObject.isNull("threads"))
			{
				Type listType = new TypeToken<List<Thread>>() {
				}.getType();
				this.threads = gson.fromJson(jsonObject.getJSONArray("threads").toString(), listType);
			}
			if(!jsonObject.isNull("threads_total"))
			{
				this.threads_total = jsonObject.getInt("threads_total");
			}
			if(!jsonObject.isNull("thread"))
			{
				thread = gson.fromJson(jsonObject.getJSONObject("thread").toString(), Thread.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return super.parseJson(jsonObject);
	}
}
