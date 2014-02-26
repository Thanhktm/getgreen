package vn.getgreen;

import org.json.JSONObject;

import vn.getgreen.common.BaseFragment;
import vn.getgreen.enties.User;
import vn.getgreen.network.GClient;
import vn.getgreen.network.LoginService;
import vn.getgreen.view.GButton;
import vn.getgreen.view.GEditText;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public class SignInFragment extends BaseFragment {
	public static final int REQUEST_CODE_SIGNUP = 1;
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
				startActivityForResult(intent, REQUEST_CODE_SIGNUP);
			}
		});
        
        mBtnSignin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mUser.setUsername(mEditUsername.getText().toString());
				mUser.setPassword(mEditPassword.getText().toString());
				mLoginService.login(mUser);
				InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
				inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		});
        
        return rootView;
    }
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == REQUEST_CODE_SIGNUP && resultCode == Activity.RESULT_OK)
		{
			((MainActivity) getActivity()).initView(null);
		}
		
	}
	@Override
	public void onSuccess(GClient client, JSONObject jsonObject) {
		if(client instanceof LoginService && mLoginService.parseJson(jsonObject))
		{
			Toast.makeText(getActivity(), "Login success", Toast.LENGTH_SHORT).show();
			((MainActivity) getActivity()).initView(null);
		}
		super.onSuccess(client, jsonObject);
	}
	
	@Override
	public void onFailure(GClient client, int statusCode, JSONObject message) {
		// TODO Auto-generated method stub
		super.onFailure(client, statusCode, message);
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		
	}
	
}
