package vn.getgreen;

import vn.getgreen.common.BaseFragment;
import vn.getgreen.enties.User;
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
	
	public SignInFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.sign_in, container, false);
        mEditUsername = (GEditText) rootView.findViewById(R.id.email);
        mEditPassword = (GEditText) rootView.findViewById(R.id.password);
        mBtnSignin = (GButton) rootView.findViewById(R.id.sign_in);
        mBtnCreate = (GButton) rootView.findViewById(R.id.create_account);
        
        
        mBtnCreate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), RegisterActivity.class);
				startActivity(intent);
			}
		});
        return rootView;
    }
	
}
