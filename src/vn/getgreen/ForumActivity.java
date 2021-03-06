package vn.getgreen;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import vn.getgreen.adapter.ForumAdapter;
import vn.getgreen.common.BaseActivity;
import vn.getgreen.enties.Category;
import vn.getgreen.enties.Forum;
import vn.getgreen.enties.User;
import vn.getgreen.network.ForumService;
import vn.getgreen.network.GClient;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ForumActivity extends BaseActivity {

	public ForumActivity() {
	}

	private ForumService mForumService;
	private ListView mListForum;
	private ForumAdapter mForumAdapter;
	private List<Forum> forums;
	private RelativeLayout ll;
	private ProgressBar loading;
	Forum forum = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_forum);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		
		mForumService = new ForumService(this, this);

		forums = new ArrayList<Forum>();
		mForumAdapter = new ForumAdapter(this, forums);
		ll = (RelativeLayout) findViewById(R.id.ll);
		loading = (ProgressBar) findViewById(R.id.loading);
		
		mListForum = (ListView) findViewById(R.id.list);
		mListForum.setAdapter(mForumAdapter);
		
		onRefresh();
		
		mListForum.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> viewGroup, View convertView, int postion,
					long arg3) {
				Forum forum = forums.get(postion);
				if(forum.getForum_thread_count() == 0)
				{
					// No threads maybe has sub-forum
					Intent intent = new Intent(ForumActivity.this, ForumActivity.class);
					intent.putExtra(Forum.class.getName(), forum);
					startActivity(intent);
				}else {
					// New thread
					Intent intent = new Intent(ForumActivity.this, ThreadActivity.class);
					intent.putExtra(Forum.class.getName(), forum);
					startActivity(intent);
				}
				
			}
		});
	}
	
	@Override
	public void onStart(GClient client) {
		if(client instanceof ForumService && forums.size() == 0)
		{
			loading.setVisibility(View.VISIBLE);
		}
		super.onStart(client);
	}

	@Override
	public void onFinish(GClient client) {
		if(client instanceof ForumService)
		{
			loading.setVisibility(View.GONE);
		}
		super.onFinish(client);
	}
	public void onRefresh() {
		Intent intent = getIntent();
		if(intent.getSerializableExtra(Category.class.getName()) != null)
		{
			// Get forums by category
			Category category = (Category) intent.getSerializableExtra(Category.class.getName());
			setTitle(category.getCategory_title());
			mForumService.listByCategory(category);
		}
		
		if(intent.getSerializableExtra(Forum.class.getName()) != null)
		{
			// Get sub-forum by parent forum
			forum = (Forum) intent.getSerializableExtra(Forum.class.getName());
			setTitle(forum.getForum_title());
			mForumService.listByForum(forum);
		}
	}

	@Override
	public void onSuccess(GClient client, JSONObject jsonObject) {
		if (client instanceof ForumService
				&& mForumService.parseJson(jsonObject)) {
			forums.clear();
			forums.addAll(mForumService.forums);
			mForumAdapter.notifyDataSetChanged();
			if(forums.size() == 0){
				ll.setVisibility(View.VISIBLE);
			} else
			{
				ll.setVisibility(View.GONE);
			}
		}
		super.onSuccess(client, jsonObject);
	}
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		if(User.isLogin(this))menu.findItem(R.id.action_search).setVisible(true);
		return super.onPrepareOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem)
	{   
		switch (menuItem.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			return true;
		case R.id.action_search:
		{
			Intent intent = new Intent(this, SearchActivity.class);
			intent.putExtra(Forum.class.getName(), forum);
			startActivity(intent);
			return true;
		}
		default:
			break;
		}
	    return super.onOptionsItemSelected(menuItem);
	}
}
