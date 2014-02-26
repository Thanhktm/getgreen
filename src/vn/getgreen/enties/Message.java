package vn.getgreen.enties;

public class Message extends BaseEnity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4837299590726531038L;

	public Message() {
		// TODO Auto-generated constructor stub
	}
	private int message_id;
	private int conversation_id;
	private int creator_user_id;
	private String creator_username;
	private long message_create_date;
	private String message_body;
	private String message_body_html;
	private String message_body_plain_text;
	private Links links;

	public int getMessage_id() {
		return message_id;
	}
	public void setMessage_id(int message_id) {
		this.message_id = message_id;
	}
	public int getConversation_id() {
		return conversation_id;
	}
	public void setConversation_id(int conversation_id) {
		this.conversation_id = conversation_id;
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
	public long getMessage_create_date() {
		return message_create_date;
	}
	public void setMessage_create_date(long message_create_date) {
		this.message_create_date = message_create_date;
	}
	public String getMessage_body() {
		return message_body;
	}
	public void setMessage_body(String message_body) {
		this.message_body = message_body;
	}
	public String getMessage_body_html() {
		return message_body_html;
	}
	public void setMessage_body_html(String message_body_html) {
		this.message_body_html = message_body_html;
	}
	public String getMessage_body_plain_text() {
		return message_body_plain_text;
	}
	public void setMessage_body_plain_text(String message_body_plain_text) {
		this.message_body_plain_text = message_body_plain_text;
	}
	public Links getLinks() {
		return links;
	}
	public void setLinks(Links links) {
		this.links = links;
	}
	

}
