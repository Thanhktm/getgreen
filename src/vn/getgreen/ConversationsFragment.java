package vn.getgreen;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import vn.getgreen.adapter.ConversationAdapter;
import vn.getgreen.common.BaseFragment;
import vn.getgreen.enties.Conversation;
import vn.getgreen.network.ConversationService;
import vn.getgreen.network.GClient;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class ConversationsFragment extends BaseFragment {
	
	public ConversationsFragment(){}
	
	
	private ConversationService mConversationService;
	private ListView mListForum;
	private ConversationAdapter mConversationAdapter;
	private List<Conversation> conversations = new ArrayList<Conversation>();
	private RelativeLayout ll;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_conversations, container, false);
		
        mConversationService = new ConversationService(getActivity(), this);
        mConversationService.list();
        mConversationAdapter = new ConversationAdapter(getActivity(), conversations );
		ll = (RelativeLayout) rootView.findViewById(R.id.ll);
		mListForum = (ListView) rootView.findViewById(R.id.list);
		mListForum.setAdapter(mConversationAdapter);
        return rootView;
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
}
