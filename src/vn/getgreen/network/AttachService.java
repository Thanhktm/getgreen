package vn.getgreen.network;

import vn.getgreen.enties.Post;
import vn.getgreen.imagecache.Utils;
import android.content.Context;

import com.loopj.android.http.RequestParams;

public class AttachService extends GClient {

	public AttachService(Context context, ResponseListener responseListener) {
		super(context, responseListener);
		// TODO Auto-generated constructor stub
	}

	public void attachFile(Post post) {
		RequestParams params = new RequestParams();
		params.put("file", Utils.getBytePhoto(post.getPath()));
		params.put("post_id", post.getPost_id() + "");
		post(params, "posts/attachments");
	}
}
