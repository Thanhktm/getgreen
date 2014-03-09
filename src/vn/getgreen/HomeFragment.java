package vn.getgreen;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import vn.getgreen.adapter.ThreadAdapter;
import vn.getgreen.adapter.ThreadAdapter.UserListener;
import vn.getgreen.common.BaseFragment;
import vn.getgreen.common.DialogBuilder;
import vn.getgreen.common.DialogBuilder.GDialogListener;
import vn.getgreen.enties.Permissions;
import vn.getgreen.enties.Thread;
import vn.getgreen.network.ForumService;
import vn.getgreen.network.GClient;
import vn.getgreen.network.ThreadService;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class HomeFragment extends BaseFragment implements UserListener {
	
	public HomeFragment(){}
	
	List<Thread> threads = new ArrayList<Thread>();
	ThreadAdapter mThreadAdapter;
	ListView mListThread;
	ThreadService mThreadService;
	ForumService mForumService;
	private RelativeLayout ll;
	private ProgressBar loading;
	ThreadService mThreadRemoveService;
	ThreadService mThreadSubscribe;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_thread, container, false);
        mThreadAdapter = new ThreadAdapter(getActivity(), threads, this, ((MainActivity) getActivity()).mImageFetcher);
		mListThread = (ListView) rootView.findViewById(R.id.list);
		ll = (RelativeLayout) rootView.findViewById(R.id.ll);
		loading = (ProgressBar) rootView.findViewById(R.id.loading);
		
		mThreadRemoveService = new ThreadService(getActivity(), this);
		mThreadSubscribe = new ThreadService(getActivity(), this);
		mThreadService = new ThreadService(getActivity(), this);
		mForumService = new ForumService(getActivity(), this);
		
		mListThread.setAdapter(mThreadAdapter);
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
		mListThread.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					final int position, long itemId) {
				final Thread thread = threads.get(position);
				Permissions permissions = thread.getPermissions();
				
	            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
	                    getActivity(),
	                    android.R.layout.simple_list_item_1);
	            arrayAdapter.add(getResources().getString(R.string.ForumMenuAdapter_topic_menu_unsubscribe));
	            if(permissions.isDelete())arrayAdapter.add(getResources().getString(R.string.ThreadActivity_dlgitem_delete));
	            
	            new DialogBuilder(getActivity(), arrayAdapter, new GDialogListener() {
	        		
	        		@Override
	        		public void onClick(DialogInterface dialog, int which) {
	        			switch (which) {
	        			case 0:
	        				mThreadSubscribe.unfollow(thread);
	        				break;
						case 1:
							mThreadRemoveService.remove(thread);
							break;
						default:
							break;
						}
	        			dialog.dismiss();
	        		}
	        	});
				return true;
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

	@Override
	public void onUserSelected(int user_id) {
		Intent intent = new Intent(getActivity(), ProfileActivity.class);
		intent.putExtra(ProfileActivity.KEY_USER_ID, user_id);
		startActivity(intent);		
	}
	
}
