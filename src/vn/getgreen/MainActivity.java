package vn.getgreen;

import java.util.ArrayList;

import org.json.JSONObject;

import vn.getgreen.adapter.NavDrawerListAdapter;
import vn.getgreen.common.BaseActivity;
import vn.getgreen.common.BaseFragment;
import vn.getgreen.enties.User;
import vn.getgreen.model.NavDrawerItem;
import vn.getgreen.network.GClient;
import vn.getgreen.network.LoginService;
import vn.getgreen.network.UserService;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

public class MainActivity extends BaseActivity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	public static final String FRAGMENT_TAG = "MAIN_FRAGMENT";
	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;
	public User user;
	private Fragment fragment;
	private UserService mUserService;
	public SearchView mSearchView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTitle = mDrawerTitle = getTitle();
		user = User.get(this);
		mUserService = new UserService(this, this);
		
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(
				R.array.nav_drawer_items_not_signin);

		initView(savedInstanceState);
		if(User.isLogin(this))mUserService.getInfo();
	}

	@Override
	public void onFailure(GClient client, int statusCode, JSONObject message) {
		if (client instanceof LoginService) {
			User user = new User();
			User.save(this, user);
			initView(null);
		}
		super.onFailure(client, statusCode, message);
	}

	public void initView(Bundle savedInstanceState) {
		// nav drawer icons from resources
		navMenuIcons = getResources().obtainTypedArray(
				R.array.nav_drawer_icons_not_signin);
		navDrawerItems.clear();
		// adding nav drawer items to array
		// Home
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons
				.getResourceId(0, -1)));
		if (User.isLogin(this)) {
			navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons
					.getResourceId(2, -1)));
			navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons
					.getResourceId(3, -1)));
		} else {
			// Sign in
			navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons
					.getResourceId(1, -1)));
		}

		// Forum categories
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons
				.getResourceId(4, -1)));

		if (User.isLogin(this)) {
			navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons
					.getResourceId(5, -1)));
			navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons
					.getResourceId(6, -1)));
			// Search
			navDrawerItems.add(new NavDrawerItem(navMenuTitles[7], navMenuIcons
				.getResourceId(7, -1)));

			navDrawerItems.add(new NavDrawerItem(navMenuTitles[8], navMenuIcons
					.getResourceId(8, -1)));
		}
		// Recycle the typed array
		navMenuIcons.recycle();
		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer_dark, // nav menu toggle icon
				R.string.app_name, // nav drawer open - description for
									// accessibility
				R.string.app_name // nav drawer close - description for
									// accessibility
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			if (User.isLogin(this)) {
				displayView(3);
			} else {
				displayView(1);
			}

		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		switch (item.getItemId()) {
		case R.id.action_logout:
			User.save(this, null);
			initView(null);
			break;
		case R.id.action_search:
			Intent intent = new Intent(this, SearchActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		if (!drawerOpen) {
			
			if (fragment instanceof HomeFragment) {
				menu.findItem(R.id.action_refresh).setVisible(true);
			}
			if (fragment instanceof SignInFragment) {

			}
			if (fragment instanceof AlertFragment) {
				menu.findItem(R.id.action_refresh).setVisible(true);
			}
			if (fragment instanceof SubscribeFragment) {
				menu.findItem(R.id.action_refresh).setVisible(true);
			}
			if (fragment instanceof CategoriesFragment) {
				menu.findItem(R.id.action_refresh).setVisible(true);
				if(User.isLogin(this))menu.findItem(R.id.action_search).setVisible(true);
			}
			if (fragment instanceof ConversationsFragment) {
				menu.findItem(R.id.action_refresh).setVisible(true);
			}
			if (fragment instanceof UnreadsFragment) {
				menu.findItem(R.id.action_refresh).setVisible(true);
			}
			if (fragment instanceof SearchFragment) {
				MenuItem itemSearch =  menu.findItem(R.id.advancesearch);
				itemSearch.setVisible(true);
				mSearchView = (SearchView) itemSearch.getActionView();
				mSearchView.setOnQueryTextListener((SearchFragment)fragment);
			}

		} else {
			menu.findItem(R.id.action_settings).setVisible(false);
			menu.findItem(R.id.action_refresh).setVisible(false);
			menu.findItem(R.id.action_newtopic).setVisible(false);
			menu.findItem(R.id.action_settings).setVisible(false);
			menu.findItem(R.id.action_subscribe).setVisible(false);
			menu.findItem(R.id.action_unsubscribe).setVisible(false);
		}
		menu.findItem(R.id.action_logout).setVisible(drawerOpen && User.isLogin(this));

		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		fragment = null;
		if (User.isLogin(this)) {
			switch (position) {
			case 0:
				fragment = new HomeFragment();
				break;
			case 1:
				fragment = new AlertFragment();
				break;
			case 2:
				fragment = new SubscribeFragment();
				break;
			case 3:
				fragment = new CategoriesFragment();
				break;
			case 4:
				fragment = new ConversationsFragment();
				break;
			case 5:
				fragment = new UnreadsFragment();
				break;
			case 6:
				fragment = new SearchFragment();
				break;
			case 7:
				fragment = new ProfileFragment();
				break;
			default:
				break;
			}
		} else {
			switch (position) {
			case 0:
				fragment = new HomeFragment();
				break;
			case 1:
				fragment = new SignInFragment();
				break;
			case 2:
				fragment = new CategoriesFragment();
				break;

			default:
				break;
			}
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment, FRAGMENT_TAG)
					.commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			NavDrawerItem navDrawerItem = (NavDrawerItem) adapter
					.getItem(position);
			setTitle(navDrawerItem.getTitle());
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void onRefresh() {
		FragmentManager fragmentManager = getFragmentManager();
		BaseFragment fragment = (BaseFragment) fragmentManager
				.findFragmentByTag(FRAGMENT_TAG);
		if (fragment != null)
			fragment.onRefresh();
	}

	@Override
	public void onSuccess(GClient client, JSONObject jsonObject) {
		if (client instanceof UserService && mUserService.parseJson(jsonObject)) {
			
		}
		super.onSuccess(client, jsonObject);
	}
}
