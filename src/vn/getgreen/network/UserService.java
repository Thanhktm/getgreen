package vn.getgreen.network;

import java.util.Calendar;

import org.json.JSONObject;

import vn.getgreen.common.Constants;
import vn.getgreen.common.EncryptUtils;
import vn.getgreen.enties.User;
import android.content.Context;

import com.google.gson.Gson;

public class UserService extends GClient {
	public User user;
	private boolean isMyInfo = false;
	public UserService(Context context, ResponseListener responseListener) {
		super(context, responseListener);
		// TODO Auto-generated constructor stub
	}
	
	public void getInfo()
	{
		get(null, "users/me");
		isMyInfo = true;
	}
	
	public void getInfo(int user_id)
	{
		get(null, "users/"+user_id);
	}
	
	@Override
	public boolean parseJson(JSONObject jsonObject) {
		try {
			Gson gson = new Gson();
			if(!jsonObject.isNull("user"))
			{
				this.user = gson.fromJson(jsonObject.getJSONObject("user").toString(), User.class);
				if (isMyInfo) {
					User userCached = User.get(context);
					this.user.setAccess_token(userCached.getAccess_token());
					this.user.setPassword(userCached.getPassword());
					if(userCached.getOne_time_token() == null)
					{
						Calendar calendar = Calendar.getInstance();
						calendar.add(Calendar.DATE, Constants.DAYS_TOKEN_EXPRIED);
						int time = (int) (calendar.getTimeInMillis() / 1000);
						String once = EncryptUtils.md5(user.getUser_id()+""+time+user.getAccess_token()+Constants.CLIENT_SECRET);
						String ott = String.format("%d,%d,%s,%s", user.getUser_id(), time, once, Constants.API_KEY);
						user.setOne_time_token(ott);	
					}else
					{
						user.setOne_time_token(userCached.getOne_time_token());
					}
					
					User.save(context, user);
					isMyInfo = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return super.parseJson(jsonObject);
	}

}
