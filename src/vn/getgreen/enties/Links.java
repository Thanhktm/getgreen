package vn.getgreen.enties;

import com.google.gson.annotations.SerializedName;

public class Links extends BaseEnity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7165428127962236908L;

	public Links() {
		// TODO Auto-generated constructor stub
	}

	private String permalink;
	private String detail;
	@SerializedName("sub-categories") private String sub_categories;
	@SerializedName("sub-forums") private String sub_forums;
	private String threads;
	private String avatar;
	private String followers;
	private String followings;
	
	private String thread;
	private String poster;
	private String likes;
	private String poster_avatar;
	
	private String forum;
	private String posts;
	
	private String first_poster;
	private String first_post;
	
	private String last_poster;
	private String last_post;
	private String conversation;
	private String creator;
	private String creator_avatar;
	private String messages;
	private int pages;
	private String next	;
	private String prev;
	
	public int getPages() {
		return pages;
	}
	public void setPages(int pages) {
		this.pages = pages;
	}
	public String getNext() {
		return next;
	}
	public void setNext(String next) {
		this.next = next;
	}
	public String getPrev() {
		return prev;
	}
	public void setPrev(String prev) {
		this.prev = prev;
	}
	public String getMessages() {
		return messages;
	}
	public void setMessages(String messages) {
		this.messages = messages;
	}
	public String getConversation() {
		return conversation;
	}
	public void setConversation(String conversation) {
		this.conversation = conversation;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getCreator_avatar() {
		return creator_avatar;
	}
	public void setCreator_avatar(String creator_avatar) {
		this.creator_avatar = creator_avatar;
	}
	public String getThread() {
		return thread;
	}
	public void setThread(String thread) {
		this.thread = thread;
	}
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	}
	public String getLikes() {
		return likes;
	}
	public void setLikes(String likes) {
		this.likes = likes;
	}
	public String getPoster_avatar() {
		return poster_avatar;
	}
	public void setPoster_avatar(String poster_avatar) {
		this.poster_avatar = poster_avatar;
	}
	public String getForum() {
		return forum;
	}
	public void setForum(String forum) {
		this.forum = forum;
	}
	public String getPosts() {
		return posts;
	}
	public void setPosts(String posts) {
		this.posts = posts;
	}
	public String getFirst_poster() {
		return first_poster;
	}
	public void setFirst_poster(String first_poster) {
		this.first_poster = first_poster;
	}
	public String getFirst_post() {
		return first_post;
	}
	public void setFirst_post(String first_post) {
		this.first_post = first_post;
	}
	public String getLast_poster() {
		return last_poster;
	}
	public void setLast_poster(String last_poster) {
		this.last_poster = last_poster;
	}
	public String getLast_post() {
		return last_post;
	}
	public void setLast_post(String last_post) {
		this.last_post = last_post;
	}

	
	public String getPermalink() {
		return permalink;
	}
	public void setPermalink(String permalink) {
		this.permalink = permalink;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getSub_categories() {
		return sub_categories;
	}
	public void setSub_categories(String sub_categories) {
		this.sub_categories = sub_categories;
	}
	public String getSub_forums() {
		return sub_forums;
	}
	public void setSub_forums(String sub_forums) {
		this.sub_forums = sub_forums;
	}
	public String getThreads() {
		return threads;
	}
	public void setThreads(String threads) {
		this.threads = threads;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getFollowers() {
		return followers;
	}
	public void setFollowers(String followers) {
		this.followers = followers;
	}
	public String getFollowings() {
		return followings;
	}
	public void setFollowings(String followings) {
		this.followings = followings;
	}
	
	
}
