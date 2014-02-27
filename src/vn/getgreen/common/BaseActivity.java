package vn.getgreen.common;

import org.json.JSONObject;

import vn.getgreen.R;
import vn.getgreen.enties.User;
import vn.getgreen.network.GClient;
import vn.getgreen.network.LoginService;
import vn.getgreen.network.ResponseListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public abstract class BaseActivity extends Activity implements ResponseListener {

	public BaseActivity() {
		// TODO Auto-generated constructor stub
	}

	LoginService mLoginService;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mLoginService = new LoginService(this, this);
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
	public void onFailure(GClient client, int statusCode, JSONObject message) {
		// TODO Auto-generated method stub
		if(statusCode == 403)
		{
			mLoginService.login(User.get(this));
		}
	}

	@Override
	public void onSuccess(GClient client, JSONObject jsonObject) {
		// TODO Auto-generated method stub
		if (client instanceof LoginService && mLoginService.parseJson(jsonObject)) {
			onRefresh();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_refresh:
			onRefresh();
			return true;
		default:
		}
		return super.onOptionsItemSelected(item);
	}
	/**
	 * For request first page or reload
	 */
	public abstract void onRefresh();

}
