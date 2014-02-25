package vn.getgreen.network;

import java.lang.reflect.Type;
import java.util.List;

import org.json.JSONObject;

import vn.getgreen.enties.Category;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class CategoriesService extends GClient {
	public List<Category> categories;
	
	public CategoriesService(Context context, ResponseListener responseListener) {
		super(context, responseListener);
		// TODO Auto-generated constructor stub
	}

	public void list()
	{
		get(null, "categories");
	}
	
	@Override
	public boolean parseJson(JSONObject json) {
		try {
			Gson gson = new Gson();
			if(!json.isNull("categories"))
			{
				Type listType = new TypeToken<List<Category>>() {
				}.getType();
				this.categories = gson.fromJson(json.getJSONArray("categories").toString(), listType);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return super.parseJson(json);
	}
}
