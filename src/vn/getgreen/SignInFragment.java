package vn.getgreen;

import org.json.JSONObject;

import vn.getgreen.common.BaseFragment;
import vn.getgreen.enties.User;
import vn.getgreen.network.ForumService;
import vn.getgreen.network.GClient;
import vn.getgreen.network.LoginService;
import vn.getgreen.view.GButton;
import vn.getgreen.view.GEditText;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

public class SignInFragment extends BaseFragment {
	
	private GEditText mEditUsername;
	private GEditText mEditPassword;
	private GButton mBtnSignin;
	private GButton mBtnCreate;
	
	private User mUser;
	private LoginService mLoginService;
	
	public SignInFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.sign_in, container, false);
        mEditUsername = (GEditText) rootView.findViewById(R.id.email);
        mEditPassword = (GEditText) rootView.findViewById(R.id.password);
        mBtnSignin = (GButton) rootView.findViewById(R.id.sign_in);
        mBtnCreate = (GButton) rootView.findViewById(R.id.create_account);
        mLoginService = new LoginService(getActivity(), this);
        this.mUser = new User();
        
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
				mUser.setUsername(mEditUsername.getText().toString());
				mUser.setPassword(mEditPassword.getText().toString());
				mLoginService.login(mUser);
				
			}
		});
        
        return rootView;
    }
	
	@Override
	public void onSuccess(GClient client, JSONObject jsonObject) {
		if(client instanceof LoginService && mLoginService.parseJson(jsonObject))
		{
			Toast.makeText(getActivity(), "Login success", Toast.LENGTH_SHORT).show();
		}
		super.onSuccess(client, jsonObject);
	}
	
	@Override
	public void onFailure(GClient client, JSONObject message) {
		// TODO Auto-generated method stub
		super.onFailure(client, message);
	}
	
}
