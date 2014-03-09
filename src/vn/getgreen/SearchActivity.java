package vn.getgreen;

import vn.getgreen.common.BaseActivity;
import vn.getgreen.enties.Forum;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

public class SearchActivity extends BaseActivity {
	public SearchView mSearchView;
	private Forum forum = null;
	SearchFragment fragment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile_activity);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		
		Intent intent = getIntent();
		if (intent.getSerializableExtra(Forum.class.getName())!= null) {
			forum = (Forum) intent.getSerializableExtra(Forum.class.getName());
		}
		fragment = SearchFragment.create((Forum)intent.getSerializableExtra(Forum.class.getName()));
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager
				.beginTransaction()
				.replace(R.id.frame_container, fragment, "search_fragment")
				.commit();

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		MenuItem itemSearch =  menu.findItem(R.id.advancesearch);
		itemSearch.setVisible(true);
		mSearchView = (SearchView) itemSearch.getActionView();
		mSearchView.setOnQueryTextListener((SearchFragment)fragment);
		return true;
	}
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub

	}

}
