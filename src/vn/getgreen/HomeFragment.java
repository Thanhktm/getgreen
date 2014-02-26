package vn.getgreen;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import vn.getgreen.adapter.ThreadAdapter;
import vn.getgreen.common.BaseFragment;
import vn.getgreen.enties.Thread;
import vn.getgreen.network.ForumService;
import vn.getgreen.network.GClient;
import vn.getgreen.network.ThreadService;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class HomeFragment extends BaseFragment {
	
	public HomeFragment(){}
	
	List<Thread> threads = new ArrayList<Thread>();
	ThreadAdapter mThreadAdapter;
	ListView mListThread;
	ThreadService mThreadService;
	ForumService mForumService;
	private RelativeLayout ll;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_thread, container, false);
        mThreadAdapter = new ThreadAdapter(getActivity(), threads);
		mListThread = (ListView) rootView.findViewById(R.id.list);
		ll = (RelativeLayout) rootView.findViewById(R.id.ll);
		
		mListThread.setAdapter(mThreadAdapter);
		mThreadService = new ThreadService(getActivity(), this);
		mForumService = new ForumService(getActivity(), this);
		mForumService.list();
        return rootView;
    }
	
	@Override
	public void onSuccess(GClient client, JSONObject jsonObject) {
		if (client instanceof ForumService && mForumService.parseJson(jsonObject)) {
			mThreadService.listByForums(mForumService.forums, true, ThreadService.ORDER_CREATE_DATE_REVERSE);
		}
		if(client instanceof ThreadService && mThreadService.parseJson(jsonObject))
		{
			threads.clear();
			threads.addAll(mThreadService.threads);
			mThreadAdapter.notifyDataSetChanged();
			if(threads.size() == 0){
				ll.setVisibility(View.VISIBLE);
			} else
			{
				ll.setVisibility(View.GONE);
			}
		}
		super.onSuccess(client, jsonObject);
	}
	
}
