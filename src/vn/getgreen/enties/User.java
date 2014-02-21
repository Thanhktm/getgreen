package vn.getgreen.enties;

import vn.getgreen.common.Constants;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class User extends BaseEnity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7943294761287131696L;
	private int user_id;
	private String username;
	private String password;
	private String email;
	private int user_message_count;
	private long user_register_date;
	private int user_like_count;
	private boolean user_is_valid;
	private boolean user_is_verified;
	private boolean user_is_visitor;
	private String refresh_token;
	private String access_token;
	
	Permissions permissions;
	Links links;
	
	
	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getUser_message_count() {
		return user_message_count;
	}

	public void setUser_message_count(int user_message_count) {
		this.user_message_count = user_message_count;
	}

	public long getUser_register_date() {
		return user_register_date;
	}

	public void setUser_register_date(long user_register_date) {
		this.user_register_date = user_register_date;
	}

	public int getUser_like_count() {
		return user_like_count;
	}

	public void setUser_like_count(int user_like_count) {
		this.user_like_count = user_like_count;
	}

	public boolean isUser_is_valid() {
		return user_is_valid;
	}

	public void setUser_is_valid(boolean user_is_valid) {
		this.user_is_valid = user_is_valid;
	}

	public boolean isUser_is_verified() {
		return user_is_verified;
	}

	public void setUser_is_verified(boolean user_is_verified) {
		this.user_is_verified = user_is_verified;
	}

	public boolean isUser_is_visitor() {
		return user_is_visitor;
	}

	public void setUser_is_visitor(boolean user_is_visitor) {
		this.user_is_visitor = user_is_visitor;
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

	public String getRefresh_token() {
		return refresh_token;
	}

	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	/**
	 * Support method
	 */
	
	public static void save(Context context, User user) {
		SharedPreferences settings = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		Gson gson = new Gson();
		String json = gson.toJson(user);
		editor.putString(User.class.getSimpleName(), json);
		editor.commit();
	}

	public static User get(Context context) {
		SharedPreferences settings = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);
		String json = settings.getString(User.class.getSimpleName(), "");
		User user = null;
		Gson gson = new Gson();
		user = gson.fromJson(json, User.class);
		return user;
	}
	

}
