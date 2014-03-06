package vn.getgreen;

import org.json.JSONObject;

import vn.getgreen.common.BaseActivity;
import vn.getgreen.enties.User;
import vn.getgreen.network.GClient;
import vn.getgreen.network.LoginService;
import vn.getgreen.network.RegisterService;
import vn.getgreen.network.UserService;
import vn.getgreen.view.GButton;
import vn.getgreen.view.GEditText;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public class RegisterActivity extends BaseActivity {

	private GEditText mEditEmail;
	private GEditText mEditUsername;
	private GEditText mEditPassword;
	private GEditText mEditConfirmPassword;
	private GButton mBtnCreate;
	private RegisterService mRegisterService;
	private LoginService mLoginService;
	private User mUser;
	private UserService mUserService;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forum_regist_layout);
		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		
		mRegisterService = new RegisterService(this, this);
		mLoginService = new LoginService(this, this);
		mUserService = new UserService(this, this);
		mUser = new User();
		
		mEditUsername = (GEditText) findViewById(R.id.username);
		mEditEmail = (GEditText) findViewById(R.id.email);
		mEditPassword = (GEditText) findViewById(R.id.password);
		mEditConfirmPassword = (GEditText) findViewById(R.id.confirm_password);
		mBtnCreate = (GButton) findViewById(R.id.register_button);
		mBtnCreate.setOnClickListener(btnCreateClick);
		
		
	}
	
	OnClickListener btnCreateClick = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(!mEditConfirmPassword.getText().toString().equals(mEditPassword.getText().toString()))
			{
				Toast.makeText(RegisterActivity.this, "Xác nhận password chưa khớp", Toast.LENGTH_SHORT).show();
				return;
			}
			
			mUser.setUser_email(mEditEmail.getText().toString());
			mUser.setUsername(mEditUsername.getText().toString());
			mUser.setPassword(mEditPassword.getText().toString());
			mRegisterService.register(mUser);
			InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			v.setEnabled(false);
		}
	};

	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem)
	{       
	    onBackPressed();
	    return true;
	}
	
	@Override
	public void onSuccess(GClient client, JSONObject jsonObject) {
		if(client instanceof RegisterService && mRegisterService.parseJson(jsonObject))
		{
			mLoginService.login(mUser);
		}
		
		if(client instanceof LoginService && mLoginService.parseJson(jsonObject))
		{
			mUserService.getInfo();
		}
		if (client instanceof UserService && mUserService.parseJson(jsonObject)) {
			Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
			setResult(RESULT_OK);
			finish();
		}
		super.onSuccess(client, jsonObject);
	}
	
	@Override
	public void onFinish(GClient client) {
		mBtnCreate.setEnabled(true);
		super.onFinish(client);
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		
	}
}
