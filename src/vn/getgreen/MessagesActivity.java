package vn.getgreen;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import vn.getgreen.adapter.MessagesAdapter;
import vn.getgreen.adapter.ThreadAdapter.UserListener;
import vn.getgreen.common.BaseActivity;
import vn.getgreen.enties.Conversation;
import vn.getgreen.enties.Message;
import vn.getgreen.network.GClient;
import vn.getgreen.network.MessageService;
import vn.getgreen.view.GEditText;
import vn.getgreen.view.GImageView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class MessagesActivity extends BaseActivity implements UserListener{

	private List<Message> messages = new ArrayList<Message>();
	private ListView mListView;
	private MessagesAdapter mMessagesAdapter;
	private MessageService mMessageService;
	private MessageService mPostMessage;
	private Conversation mConversation;
	private RelativeLayout quickbar;
	private GEditText quickqeply;
	private ProgressBar loading;
	private GImageView showAll;
	private GImageView reply;
	private ProgressBar loadingMain;
	
	public MessagesActivity() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_messages);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		
		mListView = (ListView) findViewById(R.id.list);
		quickbar = (RelativeLayout) findViewById(R.id.quickbar);
		quickqeply = (GEditText) findViewById(R.id.quickqeply);
		showAll = (GImageView) findViewById(R.id.show_all);
		loading = (ProgressBar) findViewById(R.id.loading);
		loadingMain = (ProgressBar) findViewById(R.id.loading1);
		reply = (GImageView) findViewById(R.id.reply);
		
		quickbar.setVisibility(View.VISIBLE);
		mMessageService = new MessageService(this, this);
		mPostMessage = new MessageService(this, this);
		mMessagesAdapter = new MessagesAdapter(this, messages, mImageFetcher, this);
		mListView.setAdapter(mMessagesAdapter);
		
		reply.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				inputMethodManager.hideSoftInputFromWindow(quickqeply.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
				mPostMessage.createMessage(mConversation.getConversation_id(), quickqeply.getText().toString() +"\n\n"+signature);
				quickqeply.getText().clear();
			}
		});

		onRefresh();
	}
	
	@Override
    public void onStart(GClient client) {
    	if(client == mPostMessage)
    	{
    		loading.setVisibility(View.VISIBLE);
    	}
    	if(client == mMessageService)
    	{
    		loadingMain.setVisibility(View.VISIBLE);
    	}
    	super.onStart(client);
    }
    
    @Override
    public void onFinish(GClient client) {
    	if(client == mPostMessage)
    	{
    		loading.setVisibility(View.INVISIBLE);
    	}
    	if(client == mMessageService)
    	{
    		loadingMain.setVisibility(View.GONE);
    	}
    	super.onFinish(client);
    }
    
	@Override
	public void onSuccess(GClient client, JSONObject jsonObject) {
		if(client == mMessageService && mMessageService.parseJson(jsonObject))
		{
			if(page == 1) messages.clear();
			messages.addAll(mMessageService.messages);
			mMessagesAdapter.notifyDataSetChanged();
		}
		if (client == mPostMessage && mPostMessage.parseJson(jsonObject)) {
			messages.add(mPostMessage.message);
			mMessagesAdapter.notifyDataSetChanged();
			mListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
		}
		super.onSuccess(client, jsonObject);
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.findItem(R.id.action_refresh).setVisible(true);
		
		return super.onPrepareOptionsMenu(menu);
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
		Intent intent = getIntent();
		if(intent.getSerializableExtra(Conversation.class.getName()) != null)
		{
			mConversation = (Conversation) intent.getSerializableExtra(Conversation.class.getName());
			setTitle(mConversation.getConversation_title());
			mMessageService.list(mConversation, 1);
		}
	}

	@Override
	public void onUserSelected(int user_id) {
		Intent intent = new Intent(this, ProfileActivity.class);
		intent.putExtra(ProfileActivity.KEY_USER_ID, user_id);
		startActivity(intent);		
	}

}
