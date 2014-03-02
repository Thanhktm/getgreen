package vn.getgreen;

import org.json.JSONObject;

import vn.getgreen.PageFragment.PageListener;
import vn.getgreen.common.BaseActivity;
import vn.getgreen.enties.Thread;
import vn.getgreen.network.GClient;
import vn.getgreen.network.PostService;
import vn.getgreen.view.GEditText;
import vn.getgreen.view.GImageView;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class PostsActivity extends BaseActivity implements PageListener{
	private Thread mThread;
	private int NUM_PAGES = 1;
	private PagerAdapter mPagerAdapter;
	private ProgressBar progress;
	private RelativeLayout quickBar;
	private GEditText quickqeply;
	private ProgressBar loading;
	private GImageView showAll;
	private GImageView reply;
	private PostService mPostService;
	
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
		
		progress = (ProgressBar) findViewById(R.id.progressbar);
		quickBar = (RelativeLayout) findViewById(R.id.quickbar);
		quickqeply = (GEditText) findViewById(R.id.quickqeply);
		loading = (ProgressBar) findViewById(R.id.loading);
		showAll = (GImageView) findViewById(R.id.show_all);
		reply = (GImageView) findViewById(R.id.reply);
		
		Intent intent = getIntent();
		if(intent.getSerializableExtra(Thread.class.getName())!= null)
		{
			mThread = (Thread) intent.getSerializableExtra(Thread.class.getName());
			NUM_PAGES = mThread.getThread_post_count() / PostService.LIMIT_POSTS_PER_PAGE;
			if(mThread.getThread_post_count() % PostService.LIMIT_POSTS_PER_PAGE != 0) NUM_PAGES += 1;
			quickBar.setVisibility(mThread.getPermissions().isPost() ? View.VISIBLE : View.GONE);
		}
        mPager = (ViewPager) findViewById(R.id.vPager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {

            }
        });
        
        mPostService = new PostService(this, this);
        reply.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				mPostService.createPost(mThread, quickqeply.getText().toString());
				loading.setVisibility(View.VISIBLE);
			}
		});
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
	/**
     * A simple pager adapter that represents 5 {@link ScreenSlidePageFragment} objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PageFragment.create(position, mThread, PostsActivity.this);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
    
    @Override
    public void onSuccess(GClient client, JSONObject jsonObject) {
    	if(client instanceof PostService)
    	{
    		loading.setVisibility(View.VISIBLE);
    		mPager.setCurrentItem(NUM_PAGES, true);
    	}
    	super.onSuccess(client, jsonObject);
    }
    
	@Override
	public void onLoadComplete() {
		progress.setVisibility(View.GONE);
	}

	@Override
	public void onStartLoad() {
		// TODO Auto-generated method stub
		progress.setVisibility(View.VISIBLE);
	}
}
