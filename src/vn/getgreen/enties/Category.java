package vn.getgreen.enties;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import vn.getgreen.common.Constants;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Category extends BaseEnity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5148144934561970979L;
	public Category() {
		// TODO Auto-generated constructor stub
	}
	private int category_id;
	private String category_title;
	private String category_description;
	private Permissions permissions;
	private Links links;
	public int getCategory_id() {
		return category_id;
	}
	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}
	public String getCategory_title() {
		return category_title;
	}
	public void setCategory_title(String category_title) {
		this.category_title = category_title;
	}
	public String getCategory_description() {
		return category_description;
	}
	public void setCategory_description(String category_description) {
		this.category_description = category_description;
	}
	public Permissions getPermissions() {
		return permissions;
	}
	public void setPermissions(Permissions permissions) {
		this.permissions = permissions;
	}
	public Links getLinks() {
		return links;
	}
	public void setLinks(Links links) {
		this.links = links;
	}
	
	public static void save(Context context, List<Category> categories)
	{
		SharedPreferences settings = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		Gson gson = new Gson();
		String json = gson.toJson(categories);
		editor.putString(Category.class.getSimpleName(), json);
		editor.commit();
	}
	

	public static List<Category> get(Context context)
	{
		SharedPreferences settings = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);
		String json = settings.getString(Category.class.getSimpleName(), "");
		ArrayList<Category> categories = null;
		Gson gson = new Gson();
		Type listType = new TypeToken<List<Category>>() {
		}.getType();
		categories = gson.fromJson(json, listType);
		return categories;
	}	
	
}
