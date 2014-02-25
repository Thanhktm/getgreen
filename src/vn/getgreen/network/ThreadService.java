package vn.getgreen.network;

import java.lang.reflect.Type;
import java.util.List;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;

import vn.getgreen.enties.Forum;
import vn.getgreen.enties.Thread;
import android.content.Context;

public class ThreadService extends GClient {
	public static final String ORDER_NATURAL = "natural";
	public static final String ORDER_CREATE_DATE = "thread_create_date";
	public static final String ORDER_CREATE_DATE_REVERSE = "thread_create_date_reverse";
	public static final String ORDER_UPDATE_DATE = "thread_update_date";
	public static final String ORDER_UPDATE_DATE_REVERSE = "thread_update_date_reverse";
	
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
	
	public void listByForums(List<Forum> forums, boolean isSticky, String order)
	{
		StringBuilder stringBuilder = new StringBuilder();
		for (Forum forum : forums) {
			stringBuilder.append(forum.getForum_id());
			stringBuilder.append(",");
		}
		stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
		RequestParams requestParams = new RequestParams();
		if(isSticky)
		{
			requestParams.put("sticky", "1");
		}
		if(order != null) requestParams.put("order", order);
		
		get(requestParams, "threads/&forum_id=" + stringBuilder.toString());
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
