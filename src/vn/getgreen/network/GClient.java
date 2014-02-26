package vn.getgreen.network;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vn.getgreen.common.Constants;
import vn.getgreen.enties.User;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public abstract class GClient {
	
	public static final String API_TAG = "GG_NETWORK";
	public static final int TIME_OUT = 60 * 1000; // 60s timeout

	public JSONArray errorMessage = null;

	private AsyncHttpClient asyncHttpClient;
	private ResponseListener responseListener;
	private String host;
	protected Context context;
	private String url;

	public GClient(Context context, ResponseListener responseListener) {
		this.responseListener = responseListener;
		asyncHttpClient = new AsyncHttpClient();
		asyncHttpClient.setTimeout(TIME_OUT);
		this.context = context;

		if (Constants.USE_HOST_PRODUCTION) {
			host = Constants.HOST_PRODUCTION;
		}

	}

	/**
	 * Update oauth_token if user has login
	 * @param requestParams
	 */
	private void updateOauthToken(RequestParams requestParams)
	{
		if(User.isLogin(context))
		{
			if (requestParams == null) {
				requestParams = new RequestParams();
			}
			requestParams.put("oauth_token", User.get(context).getAccess_token());
		}
	}
	
	public void get(RequestParams requestParams, String requestPath) {
		url = host + requestPath;
		updateOauthToken(requestParams);
		asyncHttpClient.get(context, this.url, requestParams,
				new ResponseHandler());
	}

	public void post(RequestParams requestParams, String requestPath) {
		url = host + requestPath;
		updateOauthToken(requestParams);
		asyncHttpClient.post(context, this.url,requestParams,
				new ResponseHandler());
	}

	public void put(RequestParams requestParams, String requestPath) {
		url = host + requestPath;
		updateOauthToken(requestParams);
		asyncHttpClient.put(context, this.url, requestParams,
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
			errorMessage = jsonObject.getJSONArray("errors");
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

		@SuppressWarnings("rawtypes")
		@Override
		public void onFailure(int statusCode, Throwable e,
				JSONObject errorResponse) {
			try {
				if(e != null)
				{
					Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
				}
				if(responseListener != null) responseListener.onFailure(GClient.this, errorResponse);
				if(Constants.DEBUG) Log.d(API_TAG, errorResponse.toString());
				if(!errorResponse.isNull("errors"))
				{
					String errorsString = "";
					JSONObject menu = errorResponse.getJSONObject("errors");
					Iterator iter = menu.keys();
				    while(iter.hasNext()){
				        String key = (String)iter.next();
				        String value = menu.getString(key);
				        errorsString += key + " : " + value + "\n";
				    }
				    Toast.makeText(context, errorsString, Toast.LENGTH_LONG).show();
				}
				if(!errorResponse.isNull("error"))
				{
					String errorsString = "";
					JSONObject menu = errorResponse.getJSONObject("error");
					Iterator iter = menu.keys();
				    while(iter.hasNext()){
				        String key = (String)iter.next();
				        String value = menu.getString(key);
				        errorsString += key + " : " + value + "\n";
				    }
				    Toast.makeText(context, errorsString, Toast.LENGTH_LONG).show();
				}
				
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
			super.onFailure(statusCode, e, errorResponse);
		}

		@Override
		public void onFinish() {
			super.onFinish();
			Log.d(API_TAG, "Finish request-url " + url);
			if(responseListener != null) responseListener.onFinish(GClient.this);
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
				Log.d(API_TAG, jsonObjectRoot.toString());
			}

			if(!jsonObjectRoot.isNull("errors"))
			{
				onFailure(statusCode, null,
						jsonObjectRoot);
				return;
			}
			if (responseListener != null) {
				responseListener.onSuccess(GClient.this, jsonObjectRoot);
			}

		}
	}

}
