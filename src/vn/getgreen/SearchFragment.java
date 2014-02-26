package vn.getgreen;

import vn.getgreen.common.BaseFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SearchFragment extends BaseFragment {
	
	public SearchFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_community, container, false);
         
        return rootView;
    }

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		
	}
}
