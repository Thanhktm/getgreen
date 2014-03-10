package vn.getgreen;

import vn.getgreen.common.BaseActivity;
import vn.getgreen.enties.User;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ProfileActivity extends BaseActivity {
	public static final String KEY_USER_ID = "key_user_id";
	public User user = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile_activity);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		Intent intent = getIntent();
		if (intent.getIntExtra(KEY_USER_ID, -1) != -1) {
			ProfileFragment fragment = ProfileFragment.create(intent
					.getIntExtra(KEY_USER_ID, -1));
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager
					.beginTransaction()
					.replace(R.id.frame_container, fragment, "profile_fragment")
					.commit();
		}

	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		if (user != null)
			menu.findItem(R.id.action_newtopic).setVisible(
					user.getPermissions().isCreate_conversation());
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			break;
		case R.id.action_newtopic: {
			Intent intent = new Intent(this, NewConverstationActivity.class);
			intent.putExtra(User.class.getName(), user);
			startActivity(intent);
		}
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub

	}

}
