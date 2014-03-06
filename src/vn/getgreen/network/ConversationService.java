package vn.getgreen.network;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import vn.getgreen.enties.Conversation;
import vn.getgreen.enties.Links;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ConversationService extends GClient {
	public int conversations_total;
	public Links links;
	public List<Conversation> conversations = new ArrayList<Conversation>();
	
	
	public ConversationService(Context context,
			ResponseListener responseListener) {
		super(context, responseListener);
		// TODO Auto-generated constructor stub
	}
	public void list()
	{
		get(null, "conversations");
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.parseJson(jsonObject);
	}
}
