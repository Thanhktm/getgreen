package vn.getgreen;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import vn.getgreen.adapter.ThreadAdapter;
import vn.getgreen.adapter.ThreadAdapter.UserListener;
import vn.getgreen.common.BaseActivity;
import vn.getgreen.common.BaseFragment;
import vn.getgreen.enties.Forum;
import vn.getgreen.enties.Thread;
import vn.getgreen.network.ForumService;
import vn.getgreen.network.GClient;
import vn.getgreen.network.ThreadService;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.SearchView;

public class SearchFragment extends BaseFragment implements UserListener, OnQueryTextListener{
	
	public SearchFragment(){}
	
	List<Thread> threads = new ArrayList<Thread>();
	ThreadAdapter mThreadAdapter;
	ListView mListThread;
	ThreadService mThreadService;
	private RelativeLayout ll;
	private ProgressBar loading;
	private Forum forum = null;
	
	public static SearchFragment create(Forum forum)
	{
		SearchFragment fragment = new SearchFragment();
		Bundle args = new Bundle();
		args.putSerializable(Forum.class.getName(), forum);
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_thread, container, false);
        if(getArguments() != null && getArguments().getSerializable(Forum.class.getName()) != null)
        {
        	forum = (Forum)getArguments().getSerializable(Forum.class.getName());
        }
        
        mThreadAdapter = new ThreadAdapter(getActivity(), threads, this, ((BaseActivity) getActivity()).mImageFetcher);
		mListThread = (ListView) rootView.findViewById(R.id.list);
		ll = (RelativeLayout) rootView.findViewById(R.id.ll);
		loading = (ProgressBar) rootView.findViewById(R.id.loading);
		
		mListThread.setAdapter(mThreadAdapter);
		mThreadService = new ThreadService(getActivity(), this);
		
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
	}

	@Override
	public void onUserSelected(int user_id) {
		Intent intent = new Intent(getActivity(), ProfileActivity.class);
		intent.putExtra(ProfileActivity.KEY_USER_ID, user_id);
		startActivity(intent);		
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		mThreadService.search(query, forum);
		return true;
	}
}
