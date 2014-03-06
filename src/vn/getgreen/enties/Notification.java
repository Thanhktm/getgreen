package vn.getgreen.enties;

public class Notification extends BaseEnity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6320585329196203807L;
	private int notification_id;
	private long notification_create_date;
	private int creator_user_id;
	private String creator_username;
	private String notification_html;
	
	public Notification() {
		// TODO Auto-generated constructor stub
	}

	public int getNotification_id() {
		return notification_id;
	}

	public void setNotification_id(int notification_id) {
		this.notification_id = notification_id;
	}

	public long getNotification_create_date() {
		return notification_create_date;
	}

	public void setNotification_create_date(long notification_create_date) {
		this.notification_create_date = notification_create_date;
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

	public String getNotification_html() {
		return notification_html;
	}

	public void setNotification_html(String notification_html) {
		this.notification_html = notification_html;
	}

	public String getNotification_plain_text() {
		if(notification_html != null) return android.text.Html.fromHtml(notification_html).toString();
		return "";
	}

}
