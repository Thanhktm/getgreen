package vn.getgreen.network;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import vn.getgreen.enties.Conversation;
import vn.getgreen.enties.Links;
import vn.getgreen.enties.User;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;

public class ConversationService extends GClient {
	public int conversations_total;
	public Links links;
	public List<Conversation> conversations = new ArrayList<Conversation>();
	public Conversation conversation;
	
	public ConversationService(Context context,
			ResponseListener responseListener) {
		super(context, responseListener);
		// TODO Auto-generated constructor stub
	}
	public void list()
	{
		get(null, "conversations");
	}
	
	/**
	 * Create a conversation for others
	 * @param conversation_title
	 * @param message_body
	 * @param recipients
	 */
	public void create(String conversation_title, String message_body, String recipients)
	{
		RequestParams params = new RequestParams();
		params.put("conversation_title", conversation_title);
		params.put("message_body", message_body);
		params.put("recipients", recipients);
		post(params, "converstations");
	}
	
	@Override
	public boolean parseJson(JSONObject jsonObject) {
		try {
			Gson gson = new Gson();
			if(!jsonObject.isNull("conversations_total"))
			{
				conversations_total = jsonObject.getInt("conversations_total");
			}
			if(!jsonObject.isNull("links"))
			{
				links = gson.fromJson(jsonObject.getJSONObject("links").toString(), Links.class);
			}
			if(!jsonObject.isNull("conversations"))
			{
				Type listType = new TypeToken<List<Conversation>>() {
				}.getType();
				conversations = gson.fromJson(jsonObject.getJSONArray("conversations").toString(), listType);
			}
			if(!jsonObject.isNull("converstation"))
			{
				conversation = gson.fromJson(jsonObject.getJSONObject("conversation").toString(), Conversation.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return super.parseJson(jsonObject);
	}
}
