package vn.getgreen;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import vn.getgreen.adapter.ConversationAdapter;
import vn.getgreen.common.BaseFragment;
import vn.getgreen.enties.Conversation;
import vn.getgreen.network.ConversationService;
import vn.getgreen.network.GClient;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class ConversationsFragment extends BaseFragment {
	
	public ConversationsFragment(){}
	
	
	private ConversationService mConversationService;
	private ListView mListConversations;
	private ConversationAdapter mConversationAdapter;
	private List<Conversation> conversations = new ArrayList<Conversation>();
	private RelativeLayout ll;
	private ProgressBar loading;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_conversations, container, false);
		
        mConversationService = new ConversationService(getActivity(), this);
        onRefresh();
        mConversationAdapter = new ConversationAdapter(getActivity(), conversations, ((MainActivity)getActivity()).mImageFetcher);
		ll = (RelativeLayout) rootView.findViewById(R.id.ll);
		loading = (ProgressBar) rootView.findViewById(R.id.loading);
		
		mListConversations = (ListView) rootView.findViewById(R.id.list);
		mListConversations.setAdapter(mConversationAdapter);
		mListConversations.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View convertView, int position,
					long itemId) {
				Conversation conversation = conversations.get(position);
				Intent intent = new Intent(getActivity(), MessagesActivity.class);
				intent.putExtra(Conversation.class.getName(), conversation);
				startActivity(intent);
			}
		});
        return rootView;
    }
	
	@Override
	public void onStart(GClient client) {
		if(client instanceof ConversationService && conversations.size() == 0)
		{
			loading.setVisibility(View.VISIBLE);
		}
		super.onStart(client);
	}

	@Override
	public void onFinish(GClient client) {
		if(client instanceof ConversationService)
		{
			loading.setVisibility(View.GONE);
		}
		super.onFinish(client);
	}
	
	@Override
	public void onSuccess(GClient client, JSONObject jsonObject) {
		if (client instanceof ConversationService
				&& mConversationService.parseJson(jsonObject)) {
			conversations.clear();
			conversations.addAll(mConversationService.conversations);
			mConversationAdapter.notifyDataSetChanged();
			if(conversations.size() == 0){
				ll.setVisibility(View.VISIBLE);
			} else
			{
				ll.setVisibility(View.GONE);
			}
		}
		super.onSuccess(client, jsonObject);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == MainActivity.CODE_NEW_CONVERSTATION && resultCode == Activity.RESULT_OK)
		{
			onRefresh();
		}
		
	}


	@Override
	public void onRefresh() {
		mConversationService.list();
	}
}
