package vn.getgreen.common;

import org.json.JSONObject;

import vn.getgreen.enties.User;
import vn.getgreen.network.GClient;
import vn.getgreen.network.LoginService;
import vn.getgreen.network.ResponseListener;
import android.app.Fragment;
import android.os.Bundle;

public abstract class BaseFragment extends Fragment implements ResponseListener {

	LoginService mLoginService;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mLoginService = new LoginService(getActivity(), this);
	}
	public BaseFragment() {
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
	public void onFailure(GClient client, int statusCode, JSONObject message) {
		// TODO Auto-generated method stub
		if(statusCode == 403)
		{
			mLoginService.login(User.get(getActivity()));
		}
	}

	@Override
	public void onSuccess(GClient client, JSONObject jsonObject) {
		// TODO Auto-generated method stub
		if (client instanceof LoginService && mLoginService.parseJson(jsonObject)) {
			onRefresh();
		}
	}

	/**
	 * For request first page or reload
	 */
	public abstract void onRefresh();
}
