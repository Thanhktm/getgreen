package vn.getgreen.network;

import org.json.JSONObject;

public interface ResponseListener {
	public void onStart(GClient client);
	
	public void onFinish(GClient client);
	
	public void onFailure(GClient client, int statusCode, JSONObject message);
	
	public void onSuccess(GClient client, JSONObject jsonObject);
}
