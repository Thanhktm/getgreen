package vn.getgreen.common;

import org.json.JSONObject;

import vn.getgreen.R;
import vn.getgreen.enties.User;
import vn.getgreen.imagecache.ImageCache;
import vn.getgreen.imagecache.ImageFetcher;
import vn.getgreen.network.GClient;
import vn.getgreen.network.LoginService;
import vn.getgreen.network.ResponseListener;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;

public abstract class BaseActivity extends FragmentActivity implements ResponseListener {
	public String signature;
	public ImageFetcher mImageFetcher;
	public BaseActivity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		signature = String.format(getResources().getString(R.string.format), Build.MODEL);
		final DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		final int height = displayMetrics.heightPixels;
		final int width = displayMetrics.widthPixels;

		final int imageWidth = width / Constants.SCALED_IMAGE;
		final int imageHeight = height / Constants.SCALED_IMAGE;

		ImageCache.ImageCacheParams cacheParams = new ImageCache.ImageCacheParams(this, Constants.IMAGE_CACHE_DIR);
		cacheParams.setMemCacheSizePercent(Constants.CACHE_SIZE);

		// The ImageFetcher takes care of loading images into our ImageView
		// children asynchronously
		mImageFetcher = new ImageFetcher(this, imageWidth, imageHeight);
		mImageFetcher.addImageCache(getSupportFragmentManager(), cacheParams);
		mImageFetcher.setImageFadeIn(false);
	}

	@Override
	public void onStart(GClient client) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinish(GClient client) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFailure(GClient client, int statusCode, JSONObject message) {
		// TODO Auto-generated method stub
		if(statusCode == 403)
		{
			User.save(this, null);
		}
	}

	@Override
	public void onSuccess(GClient client, JSONObject jsonObject) {
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_refresh:
			onRefresh();
			return true;
		default:
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * For request first page or reload
	 */
	public abstract void onRefresh();

}
