package vn.getgreen.enties;

import java.util.List;

public class Conversation extends BaseEnity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2771115029055946230L;

	public Conversation() {
		// TODO Auto-generated constructor stub
	}

	private int conversation_id;
	private String conversation_title;
	private int creator_user_id;
	private String creator_username;
	private long conversation_create_date;
	private long conversation_update_date;
	private int conversation_message_count;
	private boolean conversation_has_new_message;
	private boolean conversation_is_open;
	private boolean conversation_is_deleted;
	private Message first_message;
	private List<User> recipients;
	private Links links;
	Permissions permissions;

	public int getConversation_id() {
		return conversation_id;
	}
	public void setConversation_id(int conversation_id) {
		this.conversation_id = conversation_id;
	}
	public String getConversation_title() {
		return conversation_title;
	}
	public void setConversation_title(String conversation_title) {
		this.conversation_title = conversation_title;
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
	public long getConversation_create_date() {
		return conversation_create_date;
	}
	public void setConversation_create_date(long conversation_create_date) {
		this.conversation_create_date = conversation_create_date;
	}
	public long getConversation_update_date() {
		return conversation_update_date;
	}
	public void setConversation_update_date(long conversation_update_date) {
		this.conversation_update_date = conversation_update_date;
	}
	public int getConversation_message_count() {
		return conversation_message_count;
	}
	public void setConversation_message_count(int conversation_message_count) {
		this.conversation_message_count = conversation_message_count;
	}
	public boolean isConversation_has_new_message() {
		return conversation_has_new_message;
	}
	public void setConversation_has_new_message(boolean conversation_has_new_message) {
		this.conversation_has_new_message = conversation_has_new_message;
	}
	public boolean isConversation_is_open() {
		return conversation_is_open;
	}
	public void setConversation_is_open(boolean conversation_is_open) {
		this.conversation_is_open = conversation_is_open;
	}
	public boolean isConversation_is_deleted() {
		return conversation_is_deleted;
	}
	public void setConversation_is_deleted(boolean conversation_is_deleted) {
		this.conversation_is_deleted = conversation_is_deleted;
	}
	public Message getFirst_message() {
		return first_message;
	}
	public void setFirst_message(Message first_message) {
		this.first_message = first_message;
	}
	public List<User> getRecipients() {
		return recipients;
	}
	public void setRecipients(List<User> recipients) {
		this.recipients = recipients;
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
