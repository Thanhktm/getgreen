package vn.getgreen.adapter;

import vn.getgreen.R;
import vn.getgreen.enties.Links;
import vn.getgreen.enties.User;
import vn.getgreen.imagecache.ImageFetcher;
import vn.getgreen.view.GImageView;
import vn.getgreen.view.GTextView;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ProfileAdapter extends BaseAdapter {
	User user;
	Context context;
	ImageFetcher imageFetcher;
	private ProfileAdapterListener profileListener;

	public abstract interface ProfileAdapterListener {
		public abstract void onImageProfile(User user);
	}

	public ProfileAdapter(Context context, User user,
			ProfileAdapterListener profileListener, ImageFetcher imageFetcher) {
		this.user = user;
		this.context = context;
		this.imageFetcher = imageFetcher;
		this.profileListener = profileListener;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (position == 0) {
			if (true) {
				LayoutInflater mInflater = (LayoutInflater) context
						.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
				convertView = mInflater.inflate(R.layout.userinfo_icon, null);
			}

			GImageView iconLay = (GImageView) convertView
					.findViewById(R.id.iconLay);
			GImageView photo = (GImageView) convertView
					.findViewById(R.id.photo);

			GTextView name = (GTextView) convertView.findViewById(R.id.name);
			GImageView onlineStatus = (GImageView) convertView
					.findViewById(R.id.onlineStatus);
			GTextView description = (GTextView) convertView
					.findViewById(R.id.description);
			if (User.get(context) != null && User.get(context).getUser_id() == user.getUser_id()) {
				photo.setVisibility(View.VISIBLE);
				photo.setOnClickListener(photoClickListener);
				iconLay.setOnClickListener(photoClickListener);
			} else {
				photo.setOnClickListener(null);
				iconLay.setOnClickListener(null);
				photo.setVisibility(View.GONE);
			}
			Links links = user.getLinks();
			if (links != null)
				imageFetcher.loadImage(links.getAvatar(), iconLay);
			name.setText(user.getUsername());
			description.setText(user.getUser_title());

			return convertView;
		} else {
			if (true) {
				LayoutInflater mInflater = (LayoutInflater) context
						.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
				convertView = mInflater.inflate(R.layout.userinfo_item, null);
			}

			GTextView name = (GTextView) convertView
					.findViewById(R.id.userinfo_item_name);
			GTextView value = (GTextView) convertView
					.findViewById(R.id.userinfo_item_value);
			if (position == 1) {
				name.setText(context.getResources().getString(
						R.string.user_create_time));
				value.setSince(user.getUser_register_date());
			}
			if (position == 2) {
				name.setText(context.getResources().getString(
						R.string.user_date_birth));
				if (user.getUser_dob_year() != 0) {
					value.setBirtDay(user.getUser_dob_year(),

					user.getUser_dob_month(), user.getUser_dob_day(), context
							.getResources().getString(R.string.years_old));
				} else {
					value.setText(context
							.getResources().getString(R.string.hiden));
				}
			}
			if (position == 3) {
				name.setText(context.getResources().getString(
						R.string.user_like_count));
				value.setText(user.getUser_like_count() + "");
			}

			return convertView;
		}
	}

	OnClickListener photoClickListener = new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			if (profileListener != null)
				profileListener.onImageProfile(user);
		}
	};

}
