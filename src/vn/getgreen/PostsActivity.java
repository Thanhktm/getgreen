package vn.getgreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import vn.getgreen.PageFragment.PageListener;
import vn.getgreen.common.BaseActivity;
import vn.getgreen.common.DialogBuilder;
import vn.getgreen.common.DialogBuilder.GDialogListener;
import vn.getgreen.enties.Post;
import vn.getgreen.enties.Thread;
import vn.getgreen.enties.User;
import vn.getgreen.network.GClient;
import vn.getgreen.network.PostService;
import vn.getgreen.view.GEditText;
import vn.getgreen.view.GImageView;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

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
        reply.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				inputMethodManager.hideSoftInputFromWindow(quickqeply.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
				mPostService.createPost(mThread, quickqeply.getText().toString());
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
		
	}
	
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PageFragment.create(position, mThread, PostsActivity.this, NUM_PAGES, mImageFetcher);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
    @Override
    public void onStart(GClient client) {
    	if(client instanceof PostService)
    	{
    		loading.setVisibility(View.VISIBLE);
    	}
    	super.onStart(client);
    }
    
    @Override
    public void onFinish(GClient client) {
    	if(client instanceof PostService)
    	{
    		loading.setVisibility(View.INVISIBLE);
    		
    	}
    	super.onFinish(client);
    }
    
    @Override
    public void onSuccess(GClient client, JSONObject jsonObject) {
    	if(client instanceof PostService)
    	{
    		mPager.setCurrentItem(NUM_PAGES - 1, true);
    	}
    	super.onSuccess(client, jsonObject);
    }
    
	@Override
	public void onLoadComplete(int page, List<Post> post) {
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
}

