package vn.getgreen.common;

import org.json.JSONObject;

import vn.getgreen.network.GClient;
import vn.getgreen.network.ResponseListener;
import android.app.Activity;

public class BaseActivity extends Activity implements ResponseListener {

	public BaseActivity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onStart(GClient client) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinish(GClient client) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFailure(GClient client, JSONObject message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSuccess(GClient client, JSONObject jsonObject) {
		// TODO Auto-generated method stub
		
	}

}
