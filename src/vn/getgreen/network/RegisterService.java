package vn.getgreen.network;

import org.json.JSONObject;

import vn.getgreen.common.Constants;
import vn.getgreen.enties.User;
import android.content.Context;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

public class RegisterService extends GClient {
	public User user;

	public RegisterService(Context context, ResponseListener responseListener) {
		super(context, responseListener);
	}

	public void register(User user) {
		RequestParams params = new RequestParams();
		params.put("username", user.getUsername());
		params.put("email", user.getEmail());
		params.put("password", user.getPassword());
		params.put("client_id", Constants.API_KEY);
		post(params, "users");
	}

	@Override
	public boolean parseJson(JSONObject jsonObject) {
		try {
			Gson gson = new Gson();
			if (!jsonObject.isNull("user")) {
				this.user = gson.fromJson(jsonObject.getJSONObject("user")
						.toString(), User.class);
			} else {
				return false;
			}

		} catch (Exception e) {

		}
		return super.parseJson(jsonObject);
	}

}
