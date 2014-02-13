package vn.getgreen.network;

import org.json.JSONException;
import org.json.JSONObject;

import vn.getgreen.common.Constants;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public abstract class GClient {
	public static final String API_TAG = "GG_NETWORK";
	public static final int TIME_OUT = 60 * 1000; // 60s timeout

	public static final int METHOD_GET = 0;
	public static final int METHOD_POST = 1;
	public static final int METHOD_PUT = 2;

	public String errorMessage = "";

	private AsyncHttpClient asyncHttpClient;
	private ResponseListener responseListener;
	private String host;
	protected Context context;
	private String url;
	private RequestParams requestParams;

	public GClient(Context context, ResponseListener responseListener) {
		this.responseListener = responseListener;
		asyncHttpClient = new AsyncHttpClient();
		asyncHttpClient.setTimeout(TIME_OUT);
		this.context = context;

		if (Constants.USE_HOST_PRODUCTION) {
			host = Constants.HOST_PRODUCTION;
		}

	}

	public void get(RequestParams requestParams, String requestPath) {
		url = host + requestPath;
		this.requestParams = requestParams;
		asyncHttpClient.get(context, this.url, this.requestParams,
				new ResponseHandler());
	}

	public void post(RequestParams requestParams, String requestPath) {
		url = host + requestPath;
		this.requestParams = requestParams;
		asyncHttpClient.post(context, this.url, this.requestParams,
				new ResponseHandler());
	}

	public void put(RequestParams requestParams, String requestPath) {
		url = host + requestPath;
		this.requestParams = requestParams;
		asyncHttpClient.put(context, this.url, this.requestParams,
				new ResponseHandler());
	}

	public boolean parseJson(JSONObject jsonObject) {
		return true;
	}

	public boolean parseErrorJson(Object object) {

		return false;

	}

	public boolean parseErrorJson(JSONObject jsonObject) {
		if (jsonObject == null) {
			return false;
		}

		try {
			errorMessage = jsonObject.getString("message");
			return true;
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean isOnline(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

	private class ResponseHandler extends JsonHttpResponseHandler {

		@Override
		public void onFailure(Throwable throwable, String response) {
			Log.d(API_TAG, "Fail request-url " + url + ":" + response);
			throwable.printStackTrace();
		}

		@Override
		public void onFinish() {
			super.onFinish();
			Log.d(API_TAG, "Finish request-url " + url);
		}

		@Override
		public void onStart() {
			super.onStart();
			if (Constants.DEBUG)
				if (responseListener != null) {
					responseListener.onStart(GClient.this);
				}
		}

		@Override
		public void onSuccess(int statusCode, org.apache.http.Header[] headers,
				JSONObject jsonObjectRoot) {
			super.onSuccess(statusCode, headers, jsonObjectRoot);
			if (Constants.DEBUG) {
				Log.d(API_TAG, "Success request-url " + url + " : "
						+ jsonObjectRoot.toString());
			}

			if (responseListener != null) {
				responseListener.onSuccess(GClient.this, jsonObjectRoot);
			}

		}
	}

}
