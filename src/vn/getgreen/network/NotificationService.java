package vn.getgreen.network;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import vn.getgreen.enties.Notification;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class NotificationService extends GClient {
	public List<Notification> notifications = new ArrayList<Notification>();
	
	public NotificationService(Context context, ResponseListener responseListener) {
		super(context, responseListener);
	}

	public void list()
	{
		get(null, "notifications");
	}
	
	@Override
	public boolean parseJson(JSONObject jsonObject) {
		try {
			Gson gson = new Gson();
			if(!jsonObject.isNull("0"))
			{
				Type listType = new TypeToken<List<Notification>>() {
				}.getType();
				this.notifications = gson.fromJson(jsonObject.getJSONArray("0").toString(), listType);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return super.parseJson(jsonObject);
	}
}
