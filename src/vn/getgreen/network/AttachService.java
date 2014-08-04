package vn.getgreen.network;

import java.io.File;
import java.io.FileNotFoundException;

import vn.getgreen.enties.Post;
import android.content.Context;

import com.loopj.android.http.RequestParams;

public class AttachService extends GClient {

	public AttachService(Context context, ResponseListener responseListener) {
		super(context, responseListener);
		// TODO Auto-generated constructor stub
	}

	public void attachFile(Post post) {
		RequestParams params = new RequestParams();
		try {
			params.put("file", new File(post.getPath()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		params.put("post_id", post.getPost_id() + "");
		post(params, "posts/attachments");
	}
}
