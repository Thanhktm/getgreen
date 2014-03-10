package vn.getgreen;

import org.json.JSONObject;

import vn.getgreen.common.BaseActivity;
import vn.getgreen.enties.User;
import vn.getgreen.network.ConversationService;
import vn.getgreen.network.GClient;
import vn.getgreen.view.GEditText;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.Toast;

public class NewConverstationActivity extends BaseActivity {
	private GEditText mEditRecipients;
	private GEditText mEditTitle;
	private GEditText mEditBody;
	private CheckBox mCkSignature;
	private ConversationService mConversationService;
	private User user = null;
	
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
		mCkSignature.setText(signature);
		mConversationService = new ConversationService(this, this);
		Intent intent = getIntent();
		if(intent.getSerializableExtra(User.class.getName()) != null)
		{
			user = (User) intent.getSerializableExtra(User.class.getName());
			mEditRecipients.setText(user.getUsername() + ";");
			mEditRecipients.setSelection(mEditBody.getText().length());
		}
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
			mConversationService.create(mEditTitle.getText().toString(),
					mEditBody.getText().toString(), mEditRecipients.getText()
							.toString() + (mCkSignature.isChecked() ? "\n\n" + signature : ""));
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onSuccess(GClient client, JSONObject jsonObject) {
		if(client == mConversationService && mConversationService.parseJson(jsonObject))
		{
			Toast.makeText(this, getResources().getString(R.string.newpmsuccess), Toast.LENGTH_SHORT).show();
			setResult(Activity.RESULT_OK);
			finish();
		}
		super.onSuccess(client, jsonObject);
	}
	
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub

	}

}
