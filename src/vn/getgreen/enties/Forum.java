package vn.getgreen.enties;

import java.util.ArrayList;
import java.util.List;

import vn.getgreen.common.Constants;
import android.content.Context;
import android.content.SharedPreferences;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Forum extends BaseEnity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5943586761597891397L;
	public Forum() {
		// TODO Auto-generated constructor stub
	}
	private int forum_id;
	private String forum_title;
	private String forum_description;
	private int forum_thread_count;
	private int forum_post_count;
	private Links links;
	private Permissions permissions;
	
	public int getForum_id() {
		return forum_id;
	}
	public void setForum_id(int forum_id) {
		this.forum_id = forum_id;
	}
	public String getForum_title() {
		return forum_title;
	}
	public void setForum_title(String forum_title) {
		this.forum_title = forum_title;
	}
	public String getForum_description() {
		return forum_description;
	}
	public void setForum_description(String forum_description) {
		this.forum_description = forum_description;
	}
	public int getForum_thread_count() {
		return forum_thread_count;
	}
	public void setForum_thread_count(int forum_thread_count) {
		this.forum_thread_count = forum_thread_count;
	}
	public int getForum_post_count() {
		return forum_post_count;
	}
	public void setForum_post_count(int forum_post_count) {
		this.forum_post_count = forum_post_count;
	}
	public Links getLinks() {
		return links;
	}
	public void setLinks(Links links) {
		this.links = links;
	}
	public Permissions getPermissions() {
		return permissions;
	}
	public void setPermissions(Permissions permissions) {
		this.permissions = permissions;
	}
	
	
	public static void save(Context context, List<Forum> forums)
	{
		SharedPreferences settings = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		Gson gson = new Gson();
		String json = gson.toJson(forums);
		editor.putString(Forum.class.getSimpleName(), json);
		editor.commit();
	}
	

	public static List<Forum> get(Context context)
	{
		SharedPreferences settings = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);
		String json = settings.getString(Forum.class.getSimpleName(), "");
		ArrayList<Forum> forums = null;
		Gson gson = new Gson();
		Type listType = new TypeToken<List<Forum>>() {
		}.getType();
		forums = gson.fromJson(json, listType);
		return forums;
	}

}
