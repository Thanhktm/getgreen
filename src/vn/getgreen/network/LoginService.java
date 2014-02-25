package vn.getgreen.network;

import org.json.JSONObject;

import vn.getgreen.common.Constants;
import vn.getgreen.enties.User;
import android.content.Context;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

public class LoginService extends GClient{

	public User user;
	public LoginService(Context context, ResponseListener responseListener) {
		super(context, responseListener);
	}

	public void login(User user)
	{
		RequestParams params = new RequestParams();
		params.put("grant_type", "password");
		params.put("username", user.getUsername());
		params.put("password", user.getPassword());
		params.put("client_id", Constants.API_KEY);
		post(params, "oauth/token");
	}
	
	@Override
	public boolean parseJson(JSONObject jsonObject) {
		try {
			Gson gson = new Gson();
			this.user = gson.fromJson(jsonObject.toString(), User.class);
			User userCached = User.get(context);
			if(userCached != null)
			{
				userCached.setAccess_token(user.getAccess_token());
				userCached.setRefresh_token(user.getRefresh_token());
				User.save(context, userCached);
			}else {
				User.save(context, user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.parseJson(jsonObject);
	}

}
