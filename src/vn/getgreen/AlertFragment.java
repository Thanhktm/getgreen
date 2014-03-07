package vn.getgreen;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import vn.getgreen.adapter.AlertAdapter;
import vn.getgreen.common.BaseFragment;
import vn.getgreen.enties.Notification;
import vn.getgreen.network.GClient;
import vn.getgreen.network.NotificationService;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class AlertFragment extends BaseFragment {
	
	public AlertFragment(){}
	
	private RelativeLayout ll;
	private AlertAdapter mAlertAdapter;
	private List<Notification> notifications = new ArrayList<Notification>();
	private NotificationService mNotificationService;
	private ListView mListView;
	private ProgressBar loading;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_notification, container, false);
        ll = (RelativeLayout) rootView.findViewById(R.id.ll);
        loading = (ProgressBar) rootView.findViewById(R.id.loading);
        
        mNotificationService = new NotificationService(getActivity(), this);
        mListView = (ListView) rootView.findViewById(R.id.list);
        mAlertAdapter = new AlertAdapter(getActivity(), notifications, ((MainActivity)getActivity()).mImageFetcher);
        mListView.setAdapter(mAlertAdapter);
        onRefresh();
        return rootView;
    }
	
	@Override
	public void onStart(GClient client) {
		if(client instanceof NotificationService && notifications.size() == 0)
		{
			loading.setVisibility(View.VISIBLE);
		}
		super.onStart(client);
	}

	@Override
	public void onFinish(GClient client) {
		if(client instanceof NotificationService)
		{
			loading.setVisibility(View.GONE);
		}
		super.onFinish(client);
	}
	
	@Override
	public void onSuccess(GClient client, JSONObject jsonObject) {
		if(client instanceof NotificationService && mNotificationService.parseJson(jsonObject))
		{
			notifications.clear();
			notifications.addAll(mNotificationService.notifications);
			mAlertAdapter.notifyDataSetChanged();
		}
		if(notifications.size() == 0)
		{
			ll.setVisibility(View.VISIBLE);
		}else
		{
			ll.setVisibility(View.GONE);
		}
		super.onSuccess(client, jsonObject);
	}
	
	@Override
	public void onRefresh() {
		mNotificationService.list();
	}
}
