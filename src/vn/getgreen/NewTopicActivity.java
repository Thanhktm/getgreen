package vn.getgreen;

import vn.getgreen.common.BaseActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class NewTopicActivity extends BaseActivity {

	
	public NewTopicActivity() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newtopic);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem)
	{       
		switch (menuItem.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			return true;
		default:
			break;
		}
	    return true;
	}
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub

	}

}
