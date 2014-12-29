package com.example.androidgps;

////////////////////////////////////////////
/* Android GPS Demo : Gives current location of user using network provider instead of GPS
 * Author: Dhananjay Kulkarni
 * Email ID: dhananjaykulkarni19@gmail.com
 */
///////////////////////////////////////////

import java.util.ArrayList;
import java.util.List;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


public class LocationDetector extends Activity implements LocationListener{
	
	
	@Override
	protected void onResume() {
		super.onResume();
		
		initilizeMap();
	}
	@Override
	protected void onStart() {
		super.onStart();
		
		initilizeMap();
	}

	private GoogleMap googleMap;
    LocationManager mLocationManager;
    Button changeMapType;

		@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
		changeMapType = (Button)findViewById(R.id.button1);
		
		
		changeMapType.setOnClickListener(new OnClickListener() {
			// Add custom spinner to change Map Type
			
			@Override
			public void onClick(View v) {
				
				final Dialog mapType = new Dialog(LocationDetector.this);
				mapType.setContentView(R.layout.map_type);
				mapType.setTitle("Choose Map Type");
				ListView mapTypeListView = (ListView) mapType.findViewById(R.id.listView1);
				List<String> mapList = new ArrayList<String>();
				mapList.add("Normal");
				mapList.add("Satelite");
				mapList.add("Terrain");
				mapList.add("Hybrid");
				mapType.show();
				
				ArrayAdapter<String> mapAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_single_choice,mapList);
				mapTypeListView.setAdapter(mapAdapter);
				
				mapTypeListView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						
						switch (position) {
						case 0:
							googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
							mapType.dismiss();
							break;
						case 1:
							googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
							mapType.dismiss();
							break;
						case 2:
							googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
							mapType.dismiss();
							break;
						case 3:
							googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
							mapType.dismiss();
							break;
							
						default:
							break;
						}
					}
				});
				
				}
		});
}
		private void initilizeMap() {
	        if (googleMap == null) {
	            googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
	        	
	            if (googleMap == null) {
	                Toast.makeText(getApplicationContext(),"Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
	            }
	        }
	    }

		@Override
		public void onLocationChanged(Location location) {
		
			Toast.makeText(getApplicationContext(), "Latitude :"+location.getLatitude()+"Longitude"+location.getLongitude(), Toast.LENGTH_LONG).show();
			
			
	        LatLng markerLock = new LatLng(location.getLatitude(), location.getLongitude());
	        
	        final CameraPosition cameraPosition = new CameraPosition.Builder()
		    .target(markerLock)      
		    .zoom(13)                   
		    .bearing(90)                
		    .tilt(30)                   
		    .build();       
	        
	        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
	      
	        MarkerOptions marker = new MarkerOptions().position(new LatLng(location.getLatitude(),location.getLongitude())).title("Demo Marker");
	        googleMap.addMarker(marker);
		}
		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
}
