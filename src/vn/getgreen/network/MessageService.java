package vn.getgreen.network;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import vn.getgreen.enties.Conversation;
import vn.getgreen.enties.Message;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;

public class MessageService extends GClient {
	public static final int LIMIT_MESSAGE_PER_PAGE = 20;
	public List<Message> messages = new ArrayList<Message>();
	public int messages_total;
	public Message message;
	
	public MessageService(Context context, ResponseListener responseListener) {
		super(context, responseListener);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Load messages by a conversation
	 * @param conversation
	 * @param page
	 */
	public void list(Conversation conversation, int page)
	{
		RequestParams params = new RequestParams();
		params.put("conversation_id", conversation.getConversation_id() + "");
		params.put("page", page + "");
		params.put("limmit", LIMIT_MESSAGE_PER_PAGE + "");
		get(params, "conversation-messages");
	}
	
	/**
	 * Create new message for a conversation
	 * @param converstaion_id
	 * @param message_body
	 */
	public void createMessage(int converstaion_id, String message_body)
	{
		RequestParams params = new RequestParams();
		params.put("conversation_id", converstaion_id + "");
		params.put("message_body", message_body);
		post(params, "conversation-messages");
	}
	
	@Override
	public boolean parseJson(JSONObject jsonObject) {
		try {
			Gson gson = new Gson();
			if(!jsonObject.isNull("messages"))
			{
				Type listType = new TypeToken<List<Message>>() {
				}.getType();
				this.messages = gson.fromJson(jsonObject.getJSONArray("messages").toString(), listType);
			}
			if(!jsonObject.isNull("messages_total"))
			{
				this.messages_total = jsonObject.getInt("messages_total");
			}
			if(!jsonObject.isNull("message"))
			{
				this.message = gson.fromJson(jsonObject.getJSONObject("message").toString(), Message.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return super.parseJson(jsonObject);
	}
}
