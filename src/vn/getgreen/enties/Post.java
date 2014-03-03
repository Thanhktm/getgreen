package vn.getgreen.enties;

import java.util.HashMap;

import vn.getgreen.view.URLDrawable;

public class Post extends BaseEnity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6976454660772201557L;

	public Post() {
		// TODO Auto-generated constructor stub
	}
	public HashMap<String, URLDrawable> map = new HashMap<String, URLDrawable>();
	
	private int post_id;
	private int thread_id;
	private int poster_user_id;
	private String poster_username;
	private long post_create_date;
	private String post_body;
	private String post_body_html;
	private String post_body_plain_text;
	private int post_like_count;
	private int post_attachment_count;
	private boolean post_is_published;
	private boolean post_is_deleted;
	private boolean post_is_first_post;
	private boolean post_is_liked;
	Links links;
	Permissions permissions;

	public int getPost_id() {
		return post_id;
	}
	public void setPost_id(int post_id) {
		this.post_id = post_id;
	}
	public int getThread_id() {
		return thread_id;
	}
	public void setThread_id(int thread_id) {
		this.thread_id = thread_id;
	}
	public int getPoster_user_id() {
		return poster_user_id;
	}
	public void setPoster_user_id(int poster_user_id) {
		this.poster_user_id = poster_user_id;
	}
	public String getPoster_username() {
		return poster_username;
	}
	public void setPoster_username(String poster_username) {
		this.poster_username = poster_username;
	}
	public long getPost_create_date() {
		return post_create_date;
	}
	public void setPost_create_date(long post_create_date) {
		this.post_create_date = post_create_date;
	}
	public String getPost_body() {
		return post_body;
	}
	public void setPost_body(String post_body) {
		this.post_body = post_body;
	}
	public String getPost_body_html() {
		return post_body_html;
	}
	public void setPost_body_html(String post_body_html) {
		this.post_body_html = post_body_html;
	}
	public String getPost_body_plain_text() {
		return post_body_plain_text;
	}
	public void setPost_body_plain_text(String post_body_plain_text) {
		this.post_body_plain_text = post_body_plain_text;
	}
	public int getPost_like_count() {
		return post_like_count;
	}
	public void setPost_like_count(int post_like_count) {
		this.post_like_count = post_like_count;
	}
	public int getPost_attachment_count() {
		return post_attachment_count;
	}
	public void setPost_attachment_count(int post_attachment_count) {
		this.post_attachment_count = post_attachment_count;
	}
	public boolean isPost_is_published() {
		return post_is_published;
	}
	public void setPost_is_published(boolean post_is_published) {
		this.post_is_published = post_is_published;
	}
	public boolean isPost_is_deleted() {
		return post_is_deleted;
	}
	public void setPost_is_deleted(boolean post_is_deleted) {
		this.post_is_deleted = post_is_deleted;
	}
	public boolean isPost_is_first_post() {
		return post_is_first_post;
	}
	public void setPost_is_first_post(boolean post_is_first_post) {
		this.post_is_first_post = post_is_first_post;
	}
	public boolean isPost_is_liked() {
		return post_is_liked;
	}
	public void setPost_is_liked(boolean post_is_liked) {
		this.post_is_liked = post_is_liked;
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
	
	

}
