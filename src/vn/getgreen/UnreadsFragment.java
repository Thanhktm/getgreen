package vn.getgreen;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import vn.getgreen.adapter.ThreadAdapter;
import vn.getgreen.common.BaseFragment;
import vn.getgreen.enties.Thread;
import vn.getgreen.network.GClient;
import vn.getgreen.network.ThreadService;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class UnreadsFragment extends BaseFragment {
	
	public UnreadsFragment(){}
	List<Thread> threads = new ArrayList<Thread>();
	ThreadAdapter mThreadAdapter;
	ListView mListThread;
	ThreadService mThreadService;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_thread, container, false);
        mThreadAdapter = new ThreadAdapter(getActivity(), threads);
		mListThread = (ListView) rootView.findViewById(R.id.list);
		mListThread.setAdapter(mThreadAdapter);
		mThreadService = new ThreadService(getActivity(), this);
		onRefresh();
        return rootView;
    }
	
	

	@Override
	public void onSuccess(GClient client, JSONObject jsonObject) {
		if(client instanceof ThreadService && mThreadService.parseJson(jsonObject))
		{
			threads.clear();
			threads.addAll(mThreadService.threads);
			mThreadAdapter.notifyDataSetChanged();
		}
		super.onSuccess(client, jsonObject);
	}



	@Override
	public void onRefresh() {
		mThreadService.listUnreadByForum(null);
	}
	
}
