package com.ifemwireless.gospeed;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class GPSTracker extends Service implements LocationListener
{
	private final Context mContext;
	private Activity activity;

	// flag for GPS status
	boolean isGPSEnabled = false;

	// flag for network status
	boolean isNetworkEnabled = false;

	Context context;
	// flag for GPS status
	boolean canGetLocation = false;

	Location location; // location
	double latitude; // latitude
	double longitude; // longitude

	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0; // 00 meters

	private static final long MIN_TIME_BW_UPDATES = 3000; // 3 seconds

	// Declaring a Location Manager
	protected LocationManager locationManager;

	public GPSTracker(Context context, Activity activity)
	{
		this.mContext = context;
		this.activity = activity;
		getLocation();
	}

	public Location getLocation()
	{
		try
		{
			locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

			// getting GPS status
			isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

			// getting network status
			isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

			if (!isGPSEnabled && !isNetworkEnabled)
			{
				// no network provider is enabled
			}
			else
			{
				this.canGetLocation = true;
				if (isNetworkEnabled)
				{
					locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
					Log.d("Network", "Network");
					if (locationManager != null)
					{
						location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
						if (location != null)
						{
							latitude = location.getLatitude();
							longitude = location.getLongitude();
						}
					}
				}
				// if GPS Enabled get lat/long using GPS Services
				if (isGPSEnabled)
				{
					if (location == null)
					{
						locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

						Log.d("GPS Enabled", "GPS Enabled");
						if (locationManager != null)
						{
							location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

							if (location != null)
							{
								latitude = location.getLatitude();
								longitude = location.getLongitude();
							}
						}
					}
				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return location;
	}

	/**
	 * Stop using GPS listener Calling this function will stop using GPS in your
	 * app
	 */
	public void stopUsingGPS()
	{
		if (locationManager != null)
		{
			locationManager.removeUpdates(GPSTracker.this);
		}
	}

	/**
	 * Function to get latitude
	 */
	public double getLatitude()
	{
		if (location != null)
		{
			latitude = location.getLatitude();
		}

		// return latitude
		return latitude;
	}

	/**
	 * Function to get longitude
	 */
	public double getLongitude()
	{
		if (location != null)
		{
			longitude = location.getLongitude();
		}

		// return longitude
		return longitude;
	}

	/**
	 * Function to check GPS/wifi enabled
	 * 
	 * @return boolean
	 */
	public boolean canGetLocation()
	{
		return this.canGetLocation;
	}

	/**
	 * Function to show settings alert dialog On pressing Settings button will
	 * lauch Settings Options
	 * 
	 * @param
	 */
	public void showSettingsAlert(Context context)
	{
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

		// Setting Dialog Title
		alertDialog.setTitle(context.getString(R.string.gps_Setting));

		// Setting Dialog Message
		alertDialog.setMessage(context.getString(R.string.gps_Message));
		alertDialog.setCancelable(false);

		// on pressing cancel button
		alertDialog.setNegativeButton(context.getString(R.string.gps_Cancle_Button), new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.cancel();
				activity.finish();
			}
		});

		// Showing Alert Message
		alertDialog.show();
	}

	public void onLocationChanged(Location location)
	{
	}

	public void onProviderDisabled(String provider)
	{
	}

	public void onProviderEnabled(String provider)
	{
	}

	public void onStatusChanged(String provider, int status, Bundle extras)
	{
	}

	public IBinder onBind(Intent arg0)
	{
		return null;
	}

}