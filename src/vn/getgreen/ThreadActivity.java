package vn.getgreen;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import vn.getgreen.adapter.ThreadAdapter;
import vn.getgreen.common.BaseActivity;
import vn.getgreen.common.DialogBuilder;
import vn.getgreen.common.DialogBuilder.GDialogListener;
import vn.getgreen.enties.Forum;
import vn.getgreen.enties.Permissions;
import vn.getgreen.enties.Thread;
import vn.getgreen.network.GClient;
import vn.getgreen.network.ThreadService;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ThreadActivity extends BaseActivity {
	public static int REQUEST_CODE_NEWTOPIC = 1;
	
	List<Thread> threads = new ArrayList<Thread>();
	ThreadAdapter mThreadAdapter;
	ListView mListThread;
	ThreadService mThreadService;
	ThreadService mThreadRemoveService;
	ThreadService mThreadSubscribe;
	Forum forum;
	
	public ThreadActivity() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_thread);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		
		mThreadAdapter = new ThreadAdapter(this, threads, mImageFetcher);
		mListThread = (ListView) findViewById(R.id.list);
		mListThread.setAdapter(mThreadAdapter);
		mThreadService = new ThreadService(this, this);
		mThreadRemoveService = new ThreadService(this, this);
		mThreadSubscribe = new ThreadService(this, this);
		
		mListThread.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					final int position, long itemId) {
				final Thread thread = threads.get(position);
				Permissions permissions = thread.getPermissions();
				
	            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
	                    ThreadActivity.this,
	                    android.R.layout.simple_list_item_1);
	            arrayAdapter.add(getResources().getString(R.string.ForumMenuAdapter_topic_menu_subscribe));
	            if(permissions.isDelete())arrayAdapter.add(getResources().getString(R.string.ThreadActivity_dlgitem_delete));
	            
	            new DialogBuilder(ThreadActivity.this, arrayAdapter, new GDialogListener() {
	        		
	        		@Override
	        		public void onClick(DialogInterface dialog, int which) {
	        			switch (which) {
	        			case 0:
	        				mThreadSubscribe.follow(thread);
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
		
		mListThread.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View convertView, int position,
					long itemId) {
				Thread thread = threads.get(position);
				Intent intent = new Intent(ThreadActivity.this, PostsActivity.class);
				intent.putExtra(Thread.class.getName(), thread);
				startActivity(intent);
			}
		});
		onRefresh();
	}
	@Override
	public void onSuccess(GClient client, JSONObject jsonObject) {
		if(client == mThreadService && mThreadService.parseJson(jsonObject))
		{
			threads.clear();
			threads.addAll(mThreadService.threads);
			mThreadAdapter.notifyDataSetChanged();
		}
		if(client == mThreadRemoveService)
		{
			onRefresh();
		}
		super.onSuccess(client, jsonObject);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.findItem(R.id.action_search).setVisible(true);
		menu.findItem(R.id.action_newtopic).setVisible(forum.getPermissions().isCreate_thread());
		menu.findItem(R.id.action_refresh).setVisible(true);
		return super.onPrepareOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem)
	{       
		switch (menuItem.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			return true;
		case R.id.action_refresh:
			onRefresh();
			return true;
		case R.id.action_newtopic:
			Intent intent = new Intent(this, NewTopicActivity.class);
			intent.putExtra(NewTopicActivity.MODE_CODE, NewTopicActivity.MODE_NEW_TOPIC);
			intent.putExtra(Forum.class.getName(), forum);
			startActivityForResult(intent, REQUEST_CODE_NEWTOPIC);
			return true;
		default:
			break;
		}
	    return true;
	}

	@Override
	public void onRefresh() {
		Intent intent = getIntent();
		if(intent.getSerializableExtra(Forum.class.getName()) != null)
		{
			// Get sub-forum by parent forum
			forum = (Forum) intent.getSerializableExtra(Forum.class.getName());
			setTitle(forum.getForum_title());
			mThreadService.listByForum(forum);
		}		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == REQUEST_CODE_NEWTOPIC && resultCode == Activity.RESULT_OK)
		{
			onRefresh();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
