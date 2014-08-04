package vn.getgreen.view;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import vn.getgreen.imagecache.ImageFetcher;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html.ImageGetter;
import android.view.View;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.Target;

public class URLImageParser implements ImageGetter {
	Context c;
    View container;
    ImageFetcher mImageFetcher;
    /***
     * Construct the URLImageParser which will execute AsyncTask and refresh the container
     * @param t
     * @param c
     */
    public URLImageParser(View t, Context c) {
        this.c = c;
        this.container = t;
    }

    public Drawable getDrawable(String source) {
//    	final URLDrawable urlDrawable = new URLDrawable();
//
//    	Picasso.with(c).load(source).into(new Target() {
//			
//			@Override
//			public void onPrepareLoad(Drawable arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void onBitmapLoaded(Bitmap bitmap, LoadedFrom arg1) {
//				Drawable drawable = new BitmapDrawable(c.getResources(),bitmap);
//				urlDrawable.setBounds(0, 0, 0 + drawable.getIntrinsicWidth(), 0 
//		                    + drawable.getIntrinsicHeight()); 
//
//		            // change the reference of the current drawable to the result
//		            // from the HTTP call
//		            urlDrawable.drawable = drawable;
//
//		            // redraw the image by invalidating the container
//		            URLImageParser.this.container.invalidate();
//		            ((TextView)URLImageParser.this.container).setHeight((URLImageParser.this.container.getHeight() 
//		            	    + drawable.getIntrinsicHeight()));
//			}
//			
//			@Override
//			public void onBitmapFailed(Drawable arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
    	URLDrawable urlDrawable = new URLDrawable();
    	
        // get the actual source
        ImageGetterAsyncTask asyncTask = 
            new ImageGetterAsyncTask(urlDrawable);

        asyncTask.execute(source);

        // return reference to URLDrawable where I will change with actual image from
        // the src tag
        return urlDrawable;
    }

    public class ImageGetterAsyncTask extends AsyncTask<String, Void, Drawable>  {
        URLDrawable urlDrawable;

        public ImageGetterAsyncTask(URLDrawable d) {
            this.urlDrawable = d;
        }

        @Override
        protected Drawable doInBackground(String... params) {
            String source = params[0];
            return fetchDrawable(source);
        }

        @Override
        protected void onPostExecute(Drawable result) {
            // set the correct bound according to the result from HTTP call
        	if(urlDrawable == null || result == null) return;
            urlDrawable.setBounds(0, 0, 0 + result.getIntrinsicWidth(), 0 
                    + result.getIntrinsicHeight()); 

            // change the reference of the current drawable to the result
            // from the HTTP call
            urlDrawable.drawable = result;

            // redraw the image by invalidating the container
            URLImageParser.this.container.invalidate();
            
            ((TextView)URLImageParser.this.container).setHeight((URLImageParser.this.container.getHeight() 
            	    + result.getIntrinsicHeight()));
        }

        /***
         * Get the Drawable from URL
         * @param urlString
         * @return
         */
        public Drawable fetchDrawable(String urlString) {
            try {
            	Bitmap bitmap = Picasso.with(c).load(urlString).get();
//                InputStream is = fetch(urlString);
                Drawable drawable = new BitmapDrawable(c.getResources(), bitmap);
                drawable.setBounds(0, 0, 0 + drawable.getIntrinsicWidth(), 0 
                        + drawable.getIntrinsicHeight()); 
                return drawable;
            } catch (Exception e) {
                return null;
            } 
        }

        private InputStream fetch(String urlString) throws MalformedURLException, IOException {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet request = new HttpGet(urlString);
            HttpResponse response = httpClient.execute(request);
            return response.getEntity().getContent();
        }
    }
}
