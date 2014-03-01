package vn.getgreen.adapter;

import java.util.List;

import vn.getgreen.R;
import vn.getgreen.enties.Category;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CategoriesAdapter extends BaseAdapter {
	List<Category> categories;
	Context context;
	public CategoriesAdapter(Context context, List<Category> categories) {
		this.categories = categories;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return categories.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return categories.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.categoryitem, null);
        }
		
		ImageView imgIcon = (ImageView) convertView.findViewById(R.id.directory_img);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.directorytext);
        Category category = categories.get(position);
        txtTitle.setText(category.getCategory_title());
        imgIcon.setBackgroundResource(R.drawable.forum_icon);
		return convertView;
	}

}
