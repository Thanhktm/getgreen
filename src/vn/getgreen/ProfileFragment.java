package vn.getgreen;

import org.json.JSONObject;

import vn.getgreen.adapter.ProfileAdapter;
import vn.getgreen.adapter.ProfileAdapter.ProfileAdapterListener;
import vn.getgreen.common.BaseActivity;
import vn.getgreen.common.BaseFragment;
import vn.getgreen.enties.User;
import vn.getgreen.network.GClient;
import vn.getgreen.network.UserService;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

public class ProfileFragment extends BaseFragment implements
		ProfileAdapterListener {

	private ListView mListView;
	private ProfileAdapter mProfileAdapter;
	private UserService mUserService;
	private ProgressBar loading;
	public static final String KEY_USERID = "user_id";

	public static ProfileFragment create(int user_id)
	{
		ProfileFragment fragment = new ProfileFragment();
		Bundle args = new Bundle();
        args.putInt(KEY_USERID, user_id);
        fragment.setArguments(args);
        return fragment;
	}
	
	public ProfileFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
		mListView = (ListView) rootView.findViewById(R.id.list);
		loading = (ProgressBar) rootView.findViewById(R.id.loading);
		mUserService = new UserService(getActivity(), this);
		if(getArguments()!= null && getArguments().getInt(KEY_USERID, -1) != -1) 
		{
			mUserService.getInfo(getArguments().getInt(KEY_USERID, -1));
		}else {
			mUserService.getInfo();
		}
		return rootView;
	}

	@Override
	public void onStart(GClient client) {
		if(client == mUserService)
		{
			loading.setVisibility(View.VISIBLE);
		}
		super.onStart(client);
	}
	
	@Override
	public void onFinish(GClient client) {
		if(client == mUserService)
		{
			loading.setVisibility(View.GONE);
		}
		super.onFinish(client);
	}
	
	@Override
	public void onSuccess(GClient client, JSONObject jsonObject) {
		if(client == mUserService && mUserService.parseJson(jsonObject))
		{
			mProfileAdapter = new ProfileAdapter(getActivity(), mUserService.user, this, ((BaseActivity)getActivity()).mImageFetcher);
			mListView.setAdapter(mProfileAdapter);
			mProfileAdapter.notifyDataSetChanged();
			if(getActivity() instanceof ProfileActivity) {
				((ProfileActivity)getActivity()).user = mUserService.user;
				((ProfileActivity)getActivity()).invalidateOptionsMenu();
			}
		}
		super.onSuccess(client, jsonObject);
	}
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onImageProfile(User user) {
		// TODO Auto-generated method stub

	}

}
