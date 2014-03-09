package vn.getgreen;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import vn.getgreen.common.BaseActivity;
import vn.getgreen.view.GEditText;

public class NewConverstationActivity extends BaseActivity {
	private GEditText mEditRecipients;
	private GEditText mEditTitle;
	private GEditText mEditBody;
	private CheckBox mCkSignature;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newconversation);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		
		mEditRecipients = (GEditText) findViewById(R.id.conv_msgto);
		mEditTitle = (GEditText) findViewById(R.id.conv_msgsubject);
		mEditBody = (GEditText) findViewById(R.id.conv_msgcontent);
		mCkSignature = (CheckBox) findViewById(R.id.signature_tag);
		
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.findItem(R.id.action_send).setVisible(true);
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			break;
		case R.id.action_send:
			
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
