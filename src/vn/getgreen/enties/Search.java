package vn.getgreen.enties;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import vn.getgreen.common.Constants;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Search extends BaseEnity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8450735410911216781L;

	private String query;
	private String order;
	private int forum_id;
	private String forum_name;
	
	private int thread_id;
	private String thread_title;
	private int search_mode;
	
	public Search() {
		// TODO Auto-generated constructor stub
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public int getForum_id() {
		return forum_id;
	}

	public void setForum_id(int forum_id) {
		this.forum_id = forum_id;
	}

	public String getForum_name() {
		return forum_name;
	}

	public void setForum_name(String forum_name) {
		this.forum_name = forum_name;
	}

	public int getThread_id() {
		return thread_id;
	}

	public void setThread_id(int thread_id) {
		this.thread_id = thread_id;
	}

	public String getThread_title() {
		return thread_title;
	}

	public void setThread_title(String thread_title) {
		this.thread_title = thread_title;
	}

	public int getSearch_mode() {
		return search_mode;
	}

	public void setSearch_mode(int search_mode) {
		this.search_mode = search_mode;
	}
	
	public static void save(Context context, List<Search> searchs)
	{
		SharedPreferences settings = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		Gson gson = new Gson();
		String json = gson.toJson(searchs);
		editor.putString(Search.class.getSimpleName(), json);
		editor.commit();
	}
	

	public static List<Search> get(Context context)
	{
		SharedPreferences settings = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);
		String json = settings.getString(Search.class.getSimpleName(), "");
		ArrayList<Search> searchs = null;
		Gson gson = new Gson();
		Type listType = new TypeToken<List<Search>>() {
		}.getType();
		searchs = gson.fromJson(json, listType);
		return searchs;
	}	
}
