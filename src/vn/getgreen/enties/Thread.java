package vn.getgreen.enties;

public class Thread extends BaseEnity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4481176762494298155L;

	public Thread() {
		// TODO Auto-generated constructor stub
	}
	
	private int thread_id;
	private int forum_id;
	private String thread_title;
	private int thread_view_count;
	private int creator_user_id;
	private String creator_username;
	private long thread_create_date;
	private long thread_update_date;
	private int thread_post_count;
	private boolean thread_is_published;
	private boolean thread_is_deleted;
	private boolean thread_is_sticky;
	private Post first_post;
	private Links links;
	private Permissions permissions;
	
	public int getThread_id() {
		return thread_id;
	}
	public void setThread_id(int thread_id) {
		this.thread_id = thread_id;
	}
	public int getForum_id() {
		return forum_id;
	}
	public void setForum_id(int forum_id) {
		this.forum_id = forum_id;
	}
	public String getThread_title() {
		return thread_title;
	}
	public void setThread_title(String thread_title) {
		this.thread_title = thread_title;
	}
	public int getThread_view_count() {
		return thread_view_count;
	}
	public void setThread_view_count(int thread_view_count) {
		this.thread_view_count = thread_view_count;
	}
	public int getCreator_user_id() {
		return creator_user_id;
	}
	public void setCreator_user_id(int creator_user_id) {
		this.creator_user_id = creator_user_id;
	}
	public String getCreator_username() {
		return creator_username;
	}
	public void setCreator_username(String creator_username) {
		this.creator_username = creator_username;
	}
	public long getThread_create_date() {
		return thread_create_date;
	}
	public void setThread_create_date(long thread_create_date) {
		this.thread_create_date = thread_create_date;
	}
	public long getThread_update_date() {
		return thread_update_date;
	}
	public void setThread_update_date(long thread_update_date) {
		this.thread_update_date = thread_update_date;
	}
	public int getThread_post_count() {
		return thread_post_count;
	}
	public void setThread_post_count(int thread_post_count) {
		this.thread_post_count = thread_post_count;
	}
	public boolean isThread_is_published() {
		return thread_is_published;
	}
	public void setThread_is_published(boolean thread_is_published) {
		this.thread_is_published = thread_is_published;
	}
	public boolean isThread_is_deleted() {
		return thread_is_deleted;
	}
	public void setThread_is_deleted(boolean thread_is_deleted) {
		this.thread_is_deleted = thread_is_deleted;
	}
	public boolean isThread_is_sticky() {
		return thread_is_sticky;
	}
	public void setThread_is_sticky(boolean thread_is_sticky) {
		this.thread_is_sticky = thread_is_sticky;
	}
	public Post getFirst_post() {
		return first_post;
	}
	public void setFirst_post(Post first_post) {
		this.first_post = first_post;
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
