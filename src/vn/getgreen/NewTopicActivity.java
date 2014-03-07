package vn.getgreen;

import org.json.JSONObject;

import vn.getgreen.common.BaseActivity;
import vn.getgreen.enties.Forum;
import vn.getgreen.enties.Post;
import vn.getgreen.enties.Thread;
import vn.getgreen.network.GClient;
import vn.getgreen.network.PostService;
import vn.getgreen.network.ThreadService;
import vn.getgreen.view.GEditText;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

public class NewTopicActivity extends BaseActivity {
	private ThreadService mThreadService;
	private Thread mThread;
	private Post mPost;
	private GEditText mEditTitle;
	private GEditText mEditBody;
	private CheckBox mCkSignature;
	private int mode = -1;
	public static String MODE_CODE = "mode_code";

	public static final int MODE_NEW_TOPIC = 0;
	public static final int MODE_NEW_POST = 1;
	public static final int MODE_EDIT_POST = 2;
	public static final int MODE_NEW_POST_QUOTE = 4;
	private PostService mPostNew;
	private PostService mPostEdit;

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

		if (intent.getSerializableExtra(Thread.class.getName()) != null) {
			mThread = (Thread) intent.getSerializableExtra(Thread.class
					.getName());
		}
		if (intent.getSerializableExtra(Post.class.getName()) != null) {
			mPost = (Post) intent.getSerializableExtra(Post.class.getName());
		}

		if (intent.getIntExtra(MODE_CODE, -1) != -1) {
			mode = intent.getIntExtra(MODE_CODE, -1);
		}

		switch (mode) {
		case MODE_NEW_TOPIC:
			mEditTitle.setVisibility(View.VISIBLE);
			break;
		case MODE_EDIT_POST:
			mEditTitle.setVisibility(View.GONE);
			mEditBody.setText(mPost.getPost_body());
			mEditBody.setSelection(mEditBody.getText().length());
			break;
		case MODE_NEW_POST:
			mEditTitle.setVisibility(View.GONE);
			break;
		case MODE_NEW_POST_QUOTE:
			mEditTitle.setVisibility(View.GONE);
			String quote = String.format(
					"[quote=\"%s, post: %d, member: %d\"]%s[/quote]\n\n",
					mPost.getPoster_username(), mPost.getPost_id(),
					mPost.getPoster_user_id(), mPost.getPost_body());
			mEditBody.setText(quote);
			mEditBody.setSelection(mEditBody.getText().length());
			break;
		default:
			break;
		}
		mThreadService = new ThreadService(this, this);
		mPostNew = new PostService(this, this);
		mPostEdit = new PostService(this, this);

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
			switch (mode) {
			case MODE_NEW_TOPIC: {
				Post post = new Post();
				post.setPost_body(mEditBody.getText().toString()
						+ (mCkSignature.isChecked() ? "\n\n" + signature : ""));
				mThread.setFirst_post(post);
				mThread.setThread_title(mEditTitle.getText().toString());
				mThreadService.create(mThread);
			}
				break;
			case MODE_NEW_POST_QUOTE:
			case MODE_NEW_POST: {
				Post post = new Post();
				post.setPost_body(mEditBody.getText().toString()
						+ (mCkSignature.isChecked() ? "\n\n" + signature : ""));
				mPostNew.createPost(mThread, post.getPost_body());
			}
				break;
			case MODE_EDIT_POST: {
				mPost.setPost_body(mEditBody.getText().toString()
						+ (mCkSignature.isChecked() ? "\n\n" + signature : ""));
				mPostNew.editPost(mPost);
			}
				break;
			default:
				break;
			}
			return true;
		default:
			break;
		}
		return true;
	}

	@Override
	public void onSuccess(GClient client, JSONObject jsonObject) {
		if (client instanceof ThreadService
				&& mThreadService.parseJson(jsonObject)) {
			setResult(RESULT_OK);
			finish();
		}
		if(client == mPostEdit || client == mPostNew)
		{
			setResult(RESULT_OK);
			finish();
		}
		super.onSuccess(client, jsonObject);
	}

	@Override
	public void onRefresh() {

	}

}
