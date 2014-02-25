package vn.getgreen.network;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import vn.getgreen.enties.Category;
import vn.getgreen.enties.Forum;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ForumService extends GClient {
	public List<Forum> forums = new ArrayList<Forum>();
	
	public ForumService(Context context, ResponseListener responseListener) {
		super(context, responseListener);
		// TODO Auto-generated constructor stub
	}

	/**
	 * List all forums
	 */
	public void list()
	{
		get(null, "forums");
	}
	
	/**
	 * List forums by category
	 * @param category
	 */
	public void listByCategory(Category category)
	{
		get(null, "forums/&parent_category_id="+category.getCategory_id());
	}
	
	/**
	 * List sub-forums by parent forum
	 * @param forum
	 */
	public void listByForum(Forum forum)
	{
		get(null, "forums/&parent_forum_id="+forum.getForum_id());
	}
	
	@Override
	public boolean parseJson(JSONObject json) {
		try {
			Gson gson = new Gson();
			if(!json.isNull("forums"))
			{
				Type listType = new TypeToken<List<Forum>>() {
				}.getType();
				this.forums = gson.fromJson(json.getJSONArray("forums").toString(), listType);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return super.parseJson(json);
	}
}
