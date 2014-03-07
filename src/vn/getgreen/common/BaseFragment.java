package vn.getgreen.common;

import org.json.JSONObject;

import vn.getgreen.MainActivity;
import vn.getgreen.enties.User;
import vn.getgreen.network.GClient;
import vn.getgreen.network.LoginService;
import vn.getgreen.network.ResponseListener;
import android.app.Fragment;
import android.os.Bundle;

public abstract class BaseFragment extends Fragment implements ResponseListener {

	protected LoginService mLoginService;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	public BaseFragment() {
	}

	@Override
	public void onStart(GClient client) {
		
	}

	@Override
	public void onFinish(GClient client) {
		
	}

	@Override
	public void onFailure(GClient client, int statusCode, JSONObject message) {
		if(statusCode == 403)
		{
			User.save(getActivity(), null);
			if(getActivity() instanceof MainActivity)
			{
				((MainActivity)getActivity()).initView(null);
			}
		}
	}

	@Override
	public void onSuccess(GClient client, JSONObject jsonObject) {
	}

	/**
	 * For request first page or reload
	 */
	public abstract void onRefresh();
}
