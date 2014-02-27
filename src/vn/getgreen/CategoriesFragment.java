package vn.getgreen;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import vn.getgreen.adapter.CategoriesAdapter;
import vn.getgreen.common.BaseFragment;
import vn.getgreen.enties.Category;
import vn.getgreen.network.CategoriesService;
import vn.getgreen.network.GClient;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class CategoriesFragment extends BaseFragment {
	
	public CategoriesFragment(){}
	private CategoriesService mCategoriesService;
	private ListView mListCategories;
	private CategoriesAdapter mCategoriesAdapter;
	private List<Category> categories = new ArrayList<Category>();
	private RelativeLayout ll;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mCategoriesService = new CategoriesService(getActivity(), this);
		mCategoriesAdapter = new CategoriesAdapter(getActivity(), categories);
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_forum, container, false);
		mListCategories = (ListView) rootView.findViewById(R.id.list);
        mListCategories.setAdapter(mCategoriesAdapter);
        onRefresh();
        ll = (RelativeLayout) rootView.findViewById(R.id.ll);
        mListCategories.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> viewGroup, View convertView, int position,
					long arg3) {
				Category category = categories.get(position);
				
				Intent intent = new Intent(getActivity(), ForumActivity.class);
				intent.putExtra(Category.class.getName(), category);
				startActivity(intent);
			}
		});
        return rootView;
    }
	
	
	
	@Override
	public void onSuccess(GClient client, JSONObject jsonObject) {
		if(client instanceof CategoriesService && mCategoriesService.parseJson(jsonObject))
		{
			categories.clear();
			categories.addAll(mCategoriesService.categories);
			mCategoriesAdapter.notifyDataSetChanged();
			if(categories.size() == 0){
				ll.setVisibility(View.VISIBLE);
			} else
			{
				ll.setVisibility(View.GONE);
			}
		}
		super.onSuccess(client, jsonObject);
	}

	@Override
	public void onFinish(GClient client) {
		
		super.onFinish(client);
	}

	@Override
	public void onRefresh() {
		mCategoriesService.list();
	}
}
