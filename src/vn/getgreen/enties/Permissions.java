package vn.getgreen.enties;

public class Permissions extends BaseEnity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6696678223171607475L;
	public Permissions() {
		// TODO Auto-generated constructor stub
	}
	
	private boolean view;
	private boolean edit;
	private boolean delete;
	private boolean create_thread;
	private boolean follow;
	private boolean like;
	private boolean reply;
	private boolean post;
	private boolean create_conversation;
	
	public boolean isCreate_conversation() {
		return create_conversation;
	}
	public void setCreate_conversation(boolean create_conversation) {
		this.create_conversation = create_conversation;
	}
	public boolean isPost() {
		return post;
	}
	public void setPost(boolean post) {
		this.post = post;
	}
	public boolean isReply() {
		return reply;
	}
	public void setReply(boolean reply) {
		this.reply = reply;
	}
	public boolean isLike() {
		return like;
	}
	public void setLike(boolean like) {
		this.like = like;
	}
	public boolean isView() {
		return view;
	}
	public void setView(boolean view) {
		this.view = view;
	}
	public boolean isEdit() {
		return edit;
	}
	public void setEdit(boolean edit) {
		this.edit = edit;
	}
	public boolean isDelete() {
		return delete;
	}
	public void setDelete(boolean delete) {
		this.delete = delete;
	}
	public boolean isCreate_thread() {
		return create_thread;
	}
	public void setCreate_thread(boolean create_thread) {
		this.create_thread = create_thread;
	}

	public boolean isFollow() {
		return follow;
	}
	public void setFollow(boolean follow) {
		this.follow = follow;
	}

	
}
