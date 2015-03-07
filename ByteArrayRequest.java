package com.example.utils;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

public class ByteArrayRequest extends Request<byte[]> {
	
	private final Listener<byte[]> mByteListener;
	
	public ByteArrayRequest(String url,Listener<byte[]> listener,Response.ErrorListener errorListener)
	{
		this(Method.GET,url,listener,errorListener);
	}

	public ByteArrayRequest(int method, String url, Listener<byte[]> listener,	Response.ErrorListener errorListener) 
	{
		super(method, url, errorListener);
		mByteListener = listener;
    }	
	
	@Override
    protected void deliverResponse(byte[] response) 
	{
        if(null != mByteListener)
        {
        	mByteListener.onResponse(response);
        }
    }

    @Override
    protected Response<byte[]> parseNetworkResponse(NetworkResponse response) 
    {
        return Response.success(response.data, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    public String getBodyContentType() 
    {
        return "application/octet-stream";
    }
}
