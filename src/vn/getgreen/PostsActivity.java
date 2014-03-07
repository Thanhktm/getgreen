package vn.getgreen;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import vn.getgreen.PageFragment.PageListener;
import vn.getgreen.common.BaseActivity;
import vn.getgreen.common.DialogBuilder;
import vn.getgreen.common.DialogBuilder.GDialogListener;
import vn.getgreen.enties.Permissions;
import vn.getgreen.enties.Post;
import vn.getgreen.enties.Thread;
import vn.getgreen.enties.User;
import vn.getgreen.network.GClient;
import vn.getgreen.network.PostService;
import vn.getgreen.view.GEditText;
import vn.getgreen.view.GImageView;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class PostsActivity extends BaseActivity implements PageListener{
	private Thread mThread;
	private int NUM_PAGES = 1;
	private PagerAdapter mPagerAdapter;
	private LinearLayout progress;
	private RelativeLayout quickBar;
	private GEditText quickqeply;
	private ProgressBar loading;
	private GImageView showAll;
	private GImageView reply;
	private PostService mPostService;
	private PostService mPostDelete;
	private PostService mPostLike;
	
	public static final int REQUEST_CODE_NEWPOST = 1;
	
	private int currentPage = 0;
    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;
	public PostsActivity() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.thread_pager);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		
		progress = (LinearLayout) findViewById(R.id.progress);
		quickBar = (RelativeLayout) findViewById(R.id.quickbar);
		quickqeply = (GEditText) findViewById(R.id.quickqeply);
		loading = (ProgressBar) findViewById(R.id.loading);
		showAll = (GImageView) findViewById(R.id.show_all);
		reply = (GImageView) findViewById(R.id.reply);
		mPager = (ViewPager) findViewById(R.id.vPager);
		
		Intent intent = getIntent();
		if(intent.getSerializableExtra(Thread.class.getName())!= null)
		{
			mThread = (Thread) intent.getSerializableExtra(Thread.class.getName());
			NUM_PAGES = mThread.getThread_post_count() / PostService.LIMIT_POSTS_PER_PAGE;
			if(mThread.getThread_post_count() % PostService.LIMIT_POSTS_PER_PAGE != 0) NUM_PAGES += 1;
			quickBar.setVisibility(User.isLogin(this) ? View.VISIBLE : View.GONE);
			setTitle(mThread.getThread_title());
		}
        
        mPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        
        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
            	currentPage = position;
            }
        });
        
        mPostService = new PostService(this, this);
        mPostDelete = new PostService(this, this);
        mPostLike = new PostService(this, this);
        
        reply.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				inputMethodManager.hideSoftInputFromWindow(quickqeply.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
				mPostService.createPost(mThread, quickqeply.getText().toString()+"\n\n"+signature);
				quickqeply.getText().clear();
			}
		});
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.findItem(R.id.action_refresh).setVisible(true);
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			break;
		default:
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}
	
	@Override
	public void onRefresh() {
		PageFragment fragment = ((ScreenSlidePagerAdapter)mPagerAdapter).getFragment(currentPage);
		fragment.onRefresh();
	}
	
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
    	private Map<Integer, PageFragment> mPageReferenceMap = new HashMap<Integer, PageFragment>();
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
        	PageFragment fragment  = PageFragment.create(position, mThread, PostsActivity.this, NUM_PAGES, mImageFetcher);
        	mPageReferenceMap.put(Integer.valueOf(position), fragment);
            return fragment;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
        
        @Override
		public void destroyItem(View container, int position, Object object) {
			super.destroyItem(container, position, object);
			mPageReferenceMap.remove(Integer.valueOf(position));
		}
        
        /**
         * After an orientation change, the fragments are saved in the adapter, and
         * I don't want to double save them: I will retrieve them and put them in my
         * list again here.
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            PageFragment fragment = (PageFragment) super.instantiateItem(container,
                    position);
            mPageReferenceMap.put(position, fragment);
            return fragment;
        }
        
        public PageFragment getFragment(int key) {
			
			return mPageReferenceMap.get(key);
		}
    }
    @Override
    public void onStart(GClient client) {
    	if(client == mPostService)
    	{
    		loading.setVisibility(View.VISIBLE);
    	}
    	super.onStart(client);
    }
    
    @Override
    public void onFinish(GClient client) {
    	if(client == mPostService)
    	{
    		loading.setVisibility(View.INVISIBLE);
    	}
    	super.onFinish(client);
    }
    
    @Override
    public void onSuccess(GClient client, JSONObject jsonObject) {
    	if(client == mPostService)
    	{
    		mPager.setCurrentItem(NUM_PAGES - 1, false);
    		onRefresh();
    	}
    	if(client == mPostDelete || client == mPostLike)
    	{
    		onRefresh();
    	}
    	super.onSuccess(client, jsonObject);
    }
    
	@Override
	public void onLoadComplete(int posts_total, List<Post> post) {
		mThread.setThread_post_count(posts_total);
		NUM_PAGES = mThread.getThread_post_count() / PostService.LIMIT_POSTS_PER_PAGE;
		if(mThread.getThread_post_count() % PostService.LIMIT_POSTS_PER_PAGE != 0) NUM_PAGES += 1;
		mPagerAdapter.notifyDataSetChanged();
	}

	@Override
	public void onStartLoad() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onPrevPage() {
		mPager.setCurrentItem(mPager.getCurrentItem() - 1, true);
	}

	@Override
	public void onFastPrevPage() {
		mPager.setCurrentItem(0, true);
	}

	@Override
	public void onNextPage() {
		mPager.setCurrentItem(mPager.getCurrentItem() + 1, true);
	}

	@Override
	public void onFastNextPage() {
		mPager.setCurrentItem(NUM_PAGES - 1, true);
	}

	@Override
	public void onPageBtn() {
		  final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                  PostsActivity.this,
                  android.R.layout.simple_list_item_1);
		  for(int i = 1; i <= NUM_PAGES; i++)
		  {
			  int begin_post = i*PostService.LIMIT_POSTS_PER_PAGE - PostService.LIMIT_POSTS_PER_PAGE + 1;
			  int end_post = i*PostService.LIMIT_POSTS_PER_PAGE;
			  if (end_post > mThread.getThread_post_count()) {
				end_post = mThread.getThread_post_count();
			  }
			  arrayAdapter.add("Page "+i+ " (" +begin_post + " - " + end_post + " )");
		  }
		new DialogBuilder(this, arrayAdapter, new GDialogListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mPager.setCurrentItem(which, false);
			}
		});
	}

	@Override
	public void onActionWithPost(final Post post) {
		if(!User.isLogin(this))
		{
			Toast.makeText(this, getResources().getString(R.string.require_login), Toast.LENGTH_SHORT).show();
			return;
		}
		final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                PostsActivity.this,
                android.R.layout.simple_list_item_1);
		Permissions permissions = post.getPermissions();
		final Resources res = getResources();
		if(post.isPost_is_liked()) arrayAdapter.add(res.getString(R.string.QuickAction_unLike));
		if(permissions.isLike() && !post.isPost_is_liked()) arrayAdapter.add(res.getString(R.string.QuickAction_Like));
		arrayAdapter.add(res.getString(R.string.QuickAction_Quote));
		if(permissions.isEdit()) arrayAdapter.add(res.getString(R.string.QuickAction_Edit));
		if(permissions.isDelete()) arrayAdapter.add(res.getString(R.string.QuickAction_delete));
		
		new DialogBuilder(this, arrayAdapter, new GDialogListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String command = arrayAdapter.getItem(which);
				if(command.equals(res.getString(R.string.QuickAction_Like)))
				{
					mPostLike.like(post);
				}
				if(command.equals(res.getString(R.string.QuickAction_unLike)))
				{
					mPostLike.unLike(post);
				}
				if(command.equals(res.getString(R.string.QuickAction_Edit)))
				{
					Intent intent = new Intent(PostsActivity.this, NewTopicActivity.class);
					intent.putExtra(NewTopicActivity.MODE_CODE, NewTopicActivity.MODE_EDIT_POST);
					intent.putExtra(Thread.class.getName(), mThread);
					intent.putExtra(Post.class.getName(), post);
					startActivityForResult(intent, REQUEST_CODE_NEWPOST);
				}
				if(command.equals(res.getString(R.string.QuickAction_delete)))
				{
					mPostDelete.remove(post);
				}
				if(command.equals(res.getString(R.string.QuickAction_Quote)))
				{
					Intent intent = new Intent(PostsActivity.this, NewTopicActivity.class);
					intent.putExtra(NewTopicActivity.MODE_CODE, NewTopicActivity.MODE_NEW_POST_QUOTE);
					intent.putExtra(Thread.class.getName(), mThread);
					intent.putExtra(Post.class.getName(), post);
					startActivityForResult(intent, REQUEST_CODE_NEWPOST);
				}
			}
		});
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == REQUEST_CODE_NEWPOST && resultCode == Activity.RESULT_OK)
		{
			mPager.setCurrentItem(NUM_PAGES - 1, false);
			onRefresh();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}

