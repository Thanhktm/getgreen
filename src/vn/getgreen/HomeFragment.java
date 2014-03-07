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
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class HomeFragment extends BaseFragment {
	
	public HomeFragment(){}
	
	List<Thread> threads = new ArrayList<Thread>();
	ThreadAdapter mThreadAdapter;
	ListView mListThread;
	ThreadService mThreadService;
	ForumService mForumService;
	private RelativeLayout ll;
	private ProgressBar loading;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_thread, container, false);
        mThreadAdapter = new ThreadAdapter(getActivity(), threads, ((MainActivity) getActivity()).mImageFetcher);
		mListThread = (ListView) rootView.findViewById(R.id.list);
		ll = (RelativeLayout) rootView.findViewById(R.id.ll);
		loading = (ProgressBar) rootView.findViewById(R.id.loading);
		
		mListThread.setAdapter(mThreadAdapter);
		mThreadService = new ThreadService(getActivity(), this);
		mForumService = new ForumService(getActivity(), this);
		onRefresh();
		mListThread.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View convertView, int position,
					long itemId) {
				Thread thread = threads.get(position);
				Intent intent = new Intent(getActivity(), PostsActivity.class);
				intent.putExtra(Thread.class.getName(), thread);
				startActivity(intent);
			}
		});
        return rootView;
    }
	
	@Override
	public void onStart(GClient client) {
		if (client instanceof ForumService && threads.size() == 0) {
			loading.setVisibility(View.VISIBLE);
		}
		super.onStart(client);
	}
	
	@Override
	public void onFinish(GClient client) {
		if(client instanceof ThreadService)
		{
			loading.setVisibility(View.GONE);
		}
		super.onFinish(client);
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

	@Override
	public void onRefresh() {
		mForumService.list();		
	}
	
}
