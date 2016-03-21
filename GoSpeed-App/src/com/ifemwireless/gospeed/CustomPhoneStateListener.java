package com.ifemwireless.gospeed;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class CustomPhoneStateListener extends PhoneStateListener
{
	Context mContext;
	public static String LOG_TAG = "CustomPhoneStateListener";

	public CustomPhoneStateListener(Context context)
	{
		mContext = context;
	}

	@Override
	public void onDataConnectionStateChanged(int state, int networkType)
	{
		super.onDataConnectionStateChanged(state, networkType);
		switch (state)
		{
		case TelephonyManager.DATA_DISCONNECTED:
			Log.i(LOG_TAG, "onDataConnectionStateChanged: DATA_DISCONNECTED");
			break;
		case TelephonyManager.DATA_CONNECTING:
			Log.i(LOG_TAG, "onDataConnectionStateChanged: DATA_CONNECTING");
			break;
		case TelephonyManager.DATA_CONNECTED:
			Log.i(LOG_TAG, "onDataConnectionStateChanged: DATA_CONNECTED");
			break;
		case TelephonyManager.DATA_SUSPENDED:
			Log.i(LOG_TAG, "onDataConnectionStateChanged: DATA_SUSPENDED");
			break;
		default:
			Log.w(LOG_TAG, "onDataConnectionStateChanged: UNKNOWN " + state);
			break;
		}

		switch (networkType)
		{
		case TelephonyManager.NETWORK_TYPE_CDMA:
			Log.i(LOG_TAG, "onDataConnectionStateChanged: NETWORK_TYPE_CDMA");
			break;
		case TelephonyManager.NETWORK_TYPE_EDGE:
			Log.i(LOG_TAG, "onDataConnectionStateChanged: NETWORK_TYPE_EDGE");
			break;
		case TelephonyManager.NETWORK_TYPE_EVDO_0:
			Log.i(LOG_TAG, "onDataConnectionStateChanged: NETWORK_TYPE_EVDO_0");
			break;
		case TelephonyManager.NETWORK_TYPE_GPRS:
			Log.i(LOG_TAG, "onDataConnectionStateChanged: NETWORK_TYPE_GPRS");
			break;
		case TelephonyManager.NETWORK_TYPE_HSDPA:
			Log.i(LOG_TAG, "onDataConnectionStateChanged: NETWORK_TYPE_HSDPA");
			break;
		case TelephonyManager.NETWORK_TYPE_HSPA:
			Log.i(LOG_TAG, "onDataConnectionStateChanged: NETWORK_TYPE_HSPA");
			break;
		case TelephonyManager.NETWORK_TYPE_IDEN:
			Log.i(LOG_TAG, "onDataConnectionStateChanged: NETWORK_TYPE_IDEN");
			break;
		case TelephonyManager.NETWORK_TYPE_LTE:
			Log.i(LOG_TAG, "onDataConnectionStateChanged: NETWORK_TYPE_LTE");
			break;
		case TelephonyManager.NETWORK_TYPE_UMTS:
			Log.i(LOG_TAG, "onDataConnectionStateChanged: NETWORK_TYPE_UMTS");
			break;
		case TelephonyManager.NETWORK_TYPE_UNKNOWN:
			Log.i(LOG_TAG, "onDataConnectionStateChanged: NETWORK_TYPE_UNKNOWN");
			break;
		default:
			Log.w(LOG_TAG, "onDataConnectionStateChanged: Undefined Network: " + networkType);
			break;
		}
	}
}