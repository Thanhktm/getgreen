package vn.getgreen.network;

import java.lang.reflect.Type;
import java.util.List;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import vn.getgreen.enties.Forum;
import vn.getgreen.enties.Thread;
import android.content.Context;

public class ThreadService extends GClient {

	
	public List<Thread> threads;
	
	public ThreadService(Context context, ResponseListener responseListener) {
		super(context, responseListener);
	}

	/**
	 * List threads in singel forum
	 * @param forum
	 */
	public void listByForum(Forum forum)
	{
		get(null, "threads/&forum_id=" + forum.getForum_id());
	}
	
	@Override
	public boolean parseJson(JSONObject jsonObject) {
		try {
			Gson gson = new Gson();
			if(!jsonObject.isNull("threads"))
			{
				Type listType = new TypeToken<List<Thread>>() {
				}.getType();
				this.threads = gson.fromJson(jsonObject.getJSONArray("threads").toString(), listType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.parseJson(jsonObject);
	}
}
