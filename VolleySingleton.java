////////////////////////////////////////////
/* Android Volley : Singleton class for android volley that creates only one instance of connection
 * Author: Dhananjay Kulkarni
 * Email ID: dhananjaykulkarni19@gmail.com
 */
///////////////////////////////////////////


package com.example
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import android.app.Application;


public class VolleySingleton extends Application{

	public static final String TAG = "VolleyTag";
	private RequestQueue  mRequestQueue;
	private static VolleySingleton mRequestInstance;
	
	
	@Override
	public void onCreate() {
	
		super.onCreate();
		mRequestInstance = this;
		
	}
	
	public static synchronized VolleySingleton getInstance(){
		return mRequestInstance;
	}
	
	public RequestQueue getRequestQueue(){
		
		if (mRequestQueue == null){
			mRequestInstance.mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		}
			return mRequestInstance.mRequestQueue;
	}
	
	public <T> void addToRequestQueue(Request<T> req){
		
		req.setTag(TAG);
		getRequestQueue().add(req);
	}
	
	public void cancelPendingRequests(Object tag){
		
		if(mRequestQueue!=null){
			mRequestQueue.cancelAll(tag);
		}
	}	
}
public <T> void addToRequestQueue(Request<T> req, String tag) {
    
    req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);

    VolleyLog.d("Adding request to queue: %s", req.getUrl());

    getRequestQueue().add(req);
}
