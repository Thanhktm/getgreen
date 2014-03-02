package vn.getgreen;

import org.json.JSONObject;

import vn.getgreen.common.BaseActivity;
import vn.getgreen.enties.Forum;
import vn.getgreen.enties.Post;
import vn.getgreen.enties.Thread;
import vn.getgreen.network.GClient;
import vn.getgreen.network.ThreadService;
import vn.getgreen.view.GEditText;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;

public class NewTopicActivity extends BaseActivity {
	private ThreadService mThreadService;
	private Thread mThread;
	private GEditText mEditTitle;
	private GEditText mEditBody;
	private CheckBox mCkSignature;

	public NewTopicActivity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newtopic);
		// View init
		mEditTitle = (GEditText) findViewById(R.id.editsubject);
		mEditBody = (GEditText) findViewById(R.id.editcontent);
		mCkSignature = (CheckBox) findViewById(R.id.signature_tag);

		mCkSignature.setText(signature);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		Intent intent = getIntent();
		if (intent.getSerializableExtra(Forum.class.getName()) != null) {
			Forum forum = (Forum) intent.getSerializableExtra(Forum.class
					.getName());
			mThread = new Thread();
			mThread.setForum_id(forum.getForum_id());
		}
		mThreadService = new ThreadService(this, this);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.findItem(R.id.action_send).setVisible(true);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		switch (menuItem.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			return true;
		case R.id.action_send:
			Post post = new Post();
			post.setPost_body(mEditBody.getText().toString()
					+ (mCkSignature.isChecked() ? "\n\n" + signature : ""));
			mThread.setFirst_post(post);
			mThread.setThread_title(mEditTitle.getText().toString());
			onRefresh();
			return true;
		default:
			break;
		}
		return true;
	}

	@Override
	public void onSuccess(GClient client, JSONObject jsonObject) {
		if (client instanceof ThreadService && mThreadService.parseJson(jsonObject)) {
			setResult(RESULT_OK);
			finish();
		}
		super.onSuccess(client, jsonObject);
	}
	@Override
	public void onRefresh() {
		mThreadService.create(mThread);
	}

}
