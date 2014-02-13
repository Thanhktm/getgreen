package vn.getgreen;

import vn.getgreen.common.BaseFragment;
import vn.getgreen.enties.User;
import vn.getgreen.view.GEditText;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SignIn extends BaseFragment {
	
	private GEditText mEditUsername;
	private GEditText mEditPassword;
	private User mUser;
	
	public SignIn(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.sign_in, container, false);

        return rootView;
    }
}
