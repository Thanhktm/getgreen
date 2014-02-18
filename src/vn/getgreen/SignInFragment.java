package vn.getgreen;

import org.json.JSONObject;

import vn.getgreen.common.BaseFragment;
import vn.getgreen.enties.User;
import vn.getgreen.network.ForumService;
import vn.getgreen.network.GClient;
import vn.getgreen.view.GButton;
import vn.getgreen.view.GEditText;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class SignInFragment extends BaseFragment {
	
	private GEditText mEditUsername;
	private GEditText mEditPassword;
	private GButton mBtnSignin;
	private GButton mBtnCreate;
	
	private User mUser;
	private ForumService mForumService;
	
	public SignInFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.sign_in, container, false);
        mEditUsername = (GEditText) rootView.findViewById(R.id.email);
        mEditPassword = (GEditText) rootView.findViewById(R.id.password);
        mBtnSignin = (GButton) rootView.findViewById(R.id.sign_in);
        mBtnCreate = (GButton) rootView.findViewById(R.id.create_account);
        mForumService = new ForumService(getActivity(), this);
        
        mBtnCreate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), RegisterActivity.class);
				startActivity(intent);
			}
		});
        
        mBtnSignin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mForumService.list();
			}
		});
        
        return rootView;
    }
	
	@Override
	public void onSuccess(GClient client, JSONObject jsonObject) {
		if(client instanceof ForumService && mForumService.parseJson(jsonObject))
		{
			
		}
		super.onSuccess(client, jsonObject);
	}
	
	@Override
	public void onFailure(GClient client, JSONObject message) {
		// TODO Auto-generated method stub
		super.onFailure(client, message);
	}
	
}
