package vn.getgreen;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import vn.getgreen.adapter.ThreadAdapter;
import vn.getgreen.common.BaseActivity;
import vn.getgreen.enties.Forum;
import vn.getgreen.enties.Thread;
import vn.getgreen.network.GClient;
import vn.getgreen.network.ThreadService;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

public class ThreadActivity extends BaseActivity {

	List<Thread> threads = new ArrayList<Thread>();
	ThreadAdapter mThreadAdapter;
	ListView mListThread;
	ThreadService mThreadService;
	
	public ThreadActivity() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_thread);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		
		mThreadAdapter = new ThreadAdapter(this, threads);
		mListThread = (ListView) findViewById(R.id.list);
		mListThread.setAdapter(mThreadAdapter);
		mThreadService = new ThreadService(this, this);
		Intent intent = getIntent();
		if(intent.getSerializableExtra(Forum.class.getName()) != null)
		{
			// Get sub-forum by parent forum
			Forum forum = (Forum) intent.getSerializableExtra(Forum.class.getName());
			setTitle(forum.getForum_title());
			mThreadService.listByForum(forum);
		}
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
	public boolean onOptionsItemSelected(MenuItem menuItem)
	{       
	    onBackPressed();
	    return true;
	}
	
}
