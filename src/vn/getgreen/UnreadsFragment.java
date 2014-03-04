package vn.getgreen;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import vn.getgreen.adapter.ThreadAdapter;
import vn.getgreen.common.BaseFragment;
import vn.getgreen.enties.Thread;
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
        mThreadAdapter = new ThreadAdapter(getActivity(), threads, ((MainActivity)getActivity()).mImageFetcher);
		mListThread = (ListView) rootView.findViewById(R.id.list);
		mListThread.setAdapter(mThreadAdapter);
		mThreadService = new ThreadService(getActivity(), this);
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
