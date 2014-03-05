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
	private String user_email;
	private int user_message_count;
	private long user_register_date;
	private int user_like_count;
	private boolean user_is_valid;
	private boolean user_is_verified;
	private boolean user_is_visitor;
	private String refresh_token;
	private String access_token;
	private String user_title;
	private int user_dob_day;
	private int user_dob_month;
	private int user_dob_year;
	private int user_timezone_offset;
	private boolean user_has_password;
	private int user_unread_conversation_count;
	private String one_time_token;
	
	public static boolean isLogin(Context context)
	{
		User user = User.get(context);
		if(user == null || user.getAccess_token() == null || user.getAccess_token().isEmpty()) return false;
		return true;
		
	}
	Permissions permissions;
	Permissions self_permissions;
	Links links;
	
	
	public String getOne_time_token() {
		return one_time_token;
	}

	public void setOne_time_token(String one_time_token) {
		this.one_time_token = one_time_token;
	}

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

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getUser_title() {
		return user_title;
	}

	public void setUser_title(String user_title) {
		this.user_title = user_title;
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

	public int getUser_dob_day() {
		return user_dob_day;
	}

	public void setUser_dob_day(int user_dob_day) {
		this.user_dob_day = user_dob_day;
	}

	public int getUser_dob_month() {
		return user_dob_month;
	}

	public void setUser_dob_month(int user_dob_month) {
		this.user_dob_month = user_dob_month;
	}

	public int getUser_dob_year() {
		return user_dob_year;
	}

	public void setUser_dob_year(int user_dob_year) {
		this.user_dob_year = user_dob_year;
	}

	public int getUser_timezone_offset() {
		return user_timezone_offset;
	}

	public void setUser_timezone_offset(int user_timezone_offset) {
		this.user_timezone_offset = user_timezone_offset;
	}

	public boolean isUser_has_password() {
		return user_has_password;
	}

	public void setUser_has_password(boolean user_has_password) {
		this.user_has_password = user_has_password;
	}

	public int getUser_unread_conversation_count() {
		return user_unread_conversation_count;
	}

	public void setUser_unread_conversation_count(int user_unread_conversation_count) {
		this.user_unread_conversation_count = user_unread_conversation_count;
	}

	public Permissions getSelf_permissions() {
		return self_permissions;
	}

	public void setSelf_permissions(Permissions self_permissions) {
		this.self_permissions = self_permissions;
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
