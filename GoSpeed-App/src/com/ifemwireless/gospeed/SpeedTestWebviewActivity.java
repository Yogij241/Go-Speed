package com.ifemwireless.gospeed;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.http.SslError;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ifemwireless.gospeed.setting.ConnectionDetector;
import com.ifemwireless.gospeed.setting.LocalSettings;

/* * © Copyright 2015, iFem Wireless, Inc. All rights reserved.* */
public class SpeedTestWebviewActivity extends Activity
{
	WebView webview;
	Button retest, website_btn, twitterkbtn, facebookbtn, aboutUs_btn;
	TextView welcome_name;
	RelativeLayout toplayout, testresult, newone;
	RelativeLayout web_viewLayout;
	String photoname = "";
	static boolean flag = false;
	HashMap<String, String> headers = new HashMap<String, String>();
	private ProgressDialog Dialog = null;
	private GPSTracker gpsTracker;
	private String log, lat;
	private String devicename;
	private String imei;
	private boolean wififlag = false;
	private String networktype, ispname;
	private Context context;
	private AppLocationService appLocationService;

	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview_speedtest);
		context = SpeedTestWebviewActivity.this;
		Dialog = new ProgressDialog(SpeedTestWebviewActivity.this);
		TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		imei = telephonyManager.getDeviceId();
		System.out.println("imei==" + imei);

		gpsTracker = new GPSTracker(context, SpeedTestWebviewActivity.this);
		if (gpsTracker.canGetLocation)
		{
			lat = gpsTracker.getLatitude() + "";
			log = gpsTracker.getLongitude() + "";
			System.out.println("getLatitude==" + gpsTracker.getLatitude() + "" + "getLongitude==" + gpsTracker.getLongitude());

		}
		else
		{
			lat = "0.0";
			log = "0.0";
			// gpsTracker.showSettingsAlert(SpeedTestWebviewActivity.this);
			// appLocationService = new
			// AppLocationService(SpeedTestWebviewActivity.this);
			// Location nwLocation =
			// appLocationService.getLocation(LocationManager.NETWORK_PROVIDER);
			//
			// if (nwLocation != null)
			// {
			// double latitude = nwLocation.getLatitude();
			// double longitude = nwLocation.getLongitude();
			// lat = nwLocation.getLatitude() + "";
			// log = nwLocation.getLongitude() + "";
			// Toast.makeText(getApplicationContext(),
			// "Mobile Location (NW): \nLatitude: " + latitude + "\nLongitude: "
			// + longitude, Toast.LENGTH_LONG).show();
			// }
		}

		devicename = android.os.Build.DEVICE + android.os.Build.MODEL;
		devicename = devicename.replace(" ", "%20");// %20
		devicename = devicename.trim();
		System.out.println("Devide name==" + devicename);
		new CheckType().execute();
		// ----Old code
		// back = (ImageView) findViewById(R.id.back);
		// forward = (ImageView) findViewById(R.id.forward);
		// stop = (ImageView) findViewById(R.id.stop);
		// progressBar = (ProgressBar) findViewById(R.id.PBAR_View);
		webview = (WebView) findViewById(R.id.web_view);
		// int k = getResources().getIdentifier("pause", "drawable",
		// getPackageName());
		// stop.setBackgroundResource(k);
		// stop.invalidate();

		// Mapping the view ----
		web_viewLayout = (RelativeLayout) findViewById(R.id.web_viewLayout);
		newone = (RelativeLayout) findViewById(R.id.newone);
		facebookbtn = (Button) findViewById(R.id.facebookbtn);
		twitterkbtn = (Button) findViewById(R.id.twitterkbtn);
		website_btn = (Button) findViewById(R.id.website_btn);
		aboutUs_btn = (Button) findViewById(R.id.aboutUs_btn);
		// Setting the properties to webview
		WebSettings settings = webview.getSettings();
		settings.setJavaScriptEnabled(true);
		webview.setVerticalScrollBarEnabled(false);
		webview.setHorizontalScrollBarEnabled(false);

		webview.setWebViewClient(new WebViewClient()
		{

			public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm)
			{
				super.onReceivedHttpAuthRequest(view, handler, host, realm);
				// handler.proceed(mUserName, mPassC);
			}

			public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error)
			{
				// super.onReceivedSslError(view, handler, error);
				handler.proceed();
			}

			// String up = mUserName +":" +mPassC;
			// String authEncoded = Base64.encodeToString(up.getBytes(), 0);
			// String authHeader = "Basic " +authEncoded;
			//

			// headers.put("Authorization", authHeader);

			// load url
			public boolean shouldOverrideUrlLoading(WebView view, String url)
			{
				view.loadUrl(url);
				return true;
			}
		});

		webview.setWebChromeClient(new WebChromeClient()
		{
			public void onProgressChanged(WebView view, int progress)
			{
				// if (progress < 100&& progressBar.getVisibility() ==
				// ProgressBar.GONE) {
				/*
				 * // rl.setVisibility(RelativeLayout.VISIBLE); //
				 * ll.setVisibility(LinearLayout.VISIBLE); flag = true; int k =
				 * getResources().getIdentifier("pause", "drawable",
				 * getPackageName()); //stop.setBackgroundResource(k);
				 * //stop.invalidate();
				 * //progressBar.setVisibility(ProgressBar.VISIBLE); } //
				 * progressBar.setProgress(progress); if (progress == 100) {
				 * flag = false; int j = getResources().getIdentifier("refresh",
				 * "drawable", getPackageName());
				 * //stop.setBackgroundResource(j); //stop.invalidate();
				 * //progressBar.setVisibility(ProgressBar.GONE); }
				 */
			}
		});

		webview.setOnTouchListener(new View.OnTouchListener()
		{
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				return (event.getAction() == MotionEvent.ACTION_MOVE);
			}
		});

		facebookbtn.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				AlertDialogforConvertImage("Facebook", "" + "");
			}
		});

		twitterkbtn.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{

				// web_viewLayout.setDrawingCacheEnabled(true);
				// getScreenForFBTWEMAIL(web_viewLayout);
				AlertDialogforConvertImage("Twitter", "" + "");
			}
		});

		website_btn.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{

				// web_viewLayout.setDrawingCacheEnabled(true);
				// getScreenForFBTWEMAIL(web_viewLayout);
				// AlertDialogforConvertImage("Twitter", "" +"");
				Intent in = new Intent(SpeedTestWebviewActivity.this, ifemWebsiteActivity.class);
				startActivity(in);
			}
		});
		aboutUs_btn.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{

				Intent in = new Intent(SpeedTestWebviewActivity.this, AboutUsActivity.class);
				startActivity(in);
			}
		});
	}

	public void showAlert()
	{

		SpeedTestWebviewActivity.this.runOnUiThread(new Runnable()
		{
			public void run()
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(SpeedTestWebviewActivity.this);
				builder.setTitle("Internet Error!");
				builder.setIcon(R.drawable.error);
				builder.setMessage("Check Internet Connection.").setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int id)
					{
						finish();
					}
				});
				AlertDialog alert = builder.create();
				alert.show();
			}
		});
	}

	@SuppressWarnings("deprecation")
	private void getScreenForFBTWEMAIL(View web_viewLayout, String appName)
	{

		String root = Environment.getExternalStorageDirectory().toString();
		Random r = new Random();
		int imgNo = r.nextInt(200);

		File myDir = new File(root + "/GoSpeed");
		if (!myDir.exists())
		{
			myDir.mkdirs();
		}
		Picture picture = webview.capturePicture();
		Bitmap b = Bitmap.createBitmap(picture.getWidth(), picture.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(b);
		picture.draw(c);

		FileOutputStream fos = null;
		try
		{
			photoname = myDir + "/gospeed" + imgNo + ".png";
			fos = new FileOutputStream(photoname);
			// File file = new File(myDir + "/gospeed"+new
			// Date().getTime()+".png");
			// File file = new File(root + "/gospeed.png");

			System.out.println("Image name is new  " + photoname);
			if (fos != null)
			{
				b.compress(Bitmap.CompressFormat.JPEG, 90, fos);
				fos.close();
			}

			if (appName.equals("Gmail"))
			{

				if (!initShareIntent("com.android.email"))
					AlertDialogForApp("com.google.android.gm&hl=en", "Gmail");

			}
			else if (appName.equals("Facebook"))
			{
				if (!initShareIntent("com.facebook.katana"))
					AlertDialogForApp("com.facebook.katana&hl=en", "Facebook");
			}
			else if (appName.equals("Twitter"))
			{
				if (!initShareIntent("com.twitter.android"))
					AlertDialogForApp("com.twitter.android&hl=en", "Twitter");
			}
			Dialog.dismiss();
		}
		catch (Exception e)
		{
			System.out.println("-----error--" + e);
			Toast.makeText(getApplicationContext(), "Image is not created! Please try again.", Toast.LENGTH_LONG).show();

		}

	}

	private void openMarketLink(String appPackageName)
	{
		try
		{
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
		}
		catch (android.content.ActivityNotFoundException anfe)
		{
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
		}
	}

	private void AlertDialogforConvertImage(final String appName, final String urlWebview)
	{
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		alertDialogBuilder.setMessage("Do you want to share on " + appName + "?");

		alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface arg0, int arg1)
			{
				Dialog.setMessage("Please wait...");
				Dialog.setCancelable(false);
				Dialog.show();
				web_viewLayout.setDrawingCacheEnabled(true);
				// facebookbtn.setVisibility(View.GONE);
				// twitterkbtn.setVisibility(View.GONE);
				getScreenForFBTWEMAIL(web_viewLayout, appName);

			}

		});

		alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				// facebookbtn.setVisibility(View.VISIBLE);
				// twitterkbtn.setVisibility(View.VISIBLE);
				// dialog1.dismiss();

			}
		});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
		alertDialog.setCancelable(false);

	}// end of alert dialog method

	private void AlertDialogForApp(final String packagename, String appName)
	{
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		alertDialogBuilder.setMessage("Sorry, you need to install " + appName + " application for sharing Go Speed test result.");

		alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface arg0, int arg1)
			{
				openMarketLink(packagename);
			}
		});

		alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{

			}
		});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
		alertDialog.setCancelable(false);

	}// end of alert dialog method

	private boolean initShareIntent(String type)
	{
		boolean found = false;
		Intent share = new Intent(android.content.Intent.ACTION_SEND);
		share.setType("image/*");// image/jpeg

		// gets the list of intents that can be loaded.
		List<ResolveInfo> resInfo = getPackageManager().queryIntentActivities(share, 0);
		if (!resInfo.isEmpty())
		{
			for (ResolveInfo info : resInfo)
			{

				Log.i("Packages ", "" + info.activityInfo.packageName.toLowerCase());

				if (info.activityInfo.packageName.toLowerCase().contains(type) || info.activityInfo.name.toLowerCase().contains(type))
				{
					share.putExtra(Intent.EXTRA_SUBJECT, "Go Speed");
					share.putExtra(Intent.EXTRA_TEXT, "http://www.ifemwireless.com");
					share.putExtra(Intent.EXTRA_TEMPLATE, "http://www.ifemwireless.com");

					@SuppressWarnings("unused")
					String path = Environment.getExternalStorageDirectory().toString();

					share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(photoname)));
					// Optional, just if you wanna share an image.

					// share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new
					// File("/sdcard", "/aaaa.png"))); // Optional, just if you
					// wanna share an image.

					// share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new
					// File(myPath)) ); // Optional, just if you wanna share an
					// image.
					share.setPackage(info.activityInfo.packageName);
					found = true;
					break;
				}
			}
			if (!found)
			{
				return false;
			}
			startActivity(Intent.createChooser(share, "Select"));
		}
		return true;
	}// end of init share intent

	public class CheckType extends AsyncTask<Void, Void, Void>
	{
		private TelephonyManager tm;

		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();

			tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

			ConnectivityManager connectivity = (ConnectivityManager) SpeedTestWebviewActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);

			NetworkInfo[] netInfo = connectivity.getAllNetworkInfo();

			WifiManager myWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
			WifiInfo myWifiInfo = myWifiManager.getConnectionInfo();

			System.out.println("Print the netInfo :- " + netInfo);

			for (NetworkInfo ni : netInfo)
			{
				if (ni.getTypeName().equalsIgnoreCase("WIFI"))
				{
					if (ni.isConnected())
					{
						System.out.println("Print the carrierName :- " + ni.getTypeName());
						wififlag = true;
					}
				}
			}
		}

		@Override
		protected Void doInBackground(Void... params)
		{
			if (wififlag)
			{
				System.out.println("Type:- WIFI");
				networktype = "WIFI";
				ispname = "0";
			}
			else
			{
				System.out.println("Print the Sim :- " + tm.getSimOperatorName());
				System.out.println("Print the Network :- " + tm.getNetworkOperatorName());

				ispname = tm.getNetworkOperatorName();

				if ((tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_CDMA))
				{
					System.out.println("Type:- CDMA");

					networktype = "CDMA";
				}
				else if ((tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_EDGE))
				{
					System.out.println("Type:- EDGE");
					System.out.println("Type:- 2G");
					networktype = "2G";

				}
				else if ((tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_EVDO_0))
				{
					System.out.println("Type:- EVD");
					networktype = "EVD";

				}
				else if ((tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_GPRS))
				{
					System.out.println("Type:- GPRS");
					networktype = "GPRS";

				}
				else if ((tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_HSDPA))
				{
					System.out.println("Type:- HSDPA");
					System.out.println("Type:- 3G");
					networktype = "3G";

				}
				else if ((tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_HSPAP))
				{
					System.out.println("Type:- HSPAP");
					System.out.println("Type:- 3G");
					networktype = "3G";

				}
				else if ((tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_HSPA))
				{
					System.out.println("Type:- HSPA");
					networktype = "HSPA";

				}
				else if ((tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_IDEN))
				{
					System.out.println("Type:- IDEN");
					networktype = "IDEN";

				}
				else if ((tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_LTE))
				{
					System.out.println("Type:- LTE");
					networktype = "LTE";

				}
				else if ((tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS))
				{
					System.out.println("Type:- UMTS");
					networktype = "UMTS";

				}
				else if ((tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_UNKNOWN))
				{
					System.out.println("Type:- UNKNOWN");
					networktype = "UNKNOWN";

				}
				else
				{
					System.out.println("Type:- Undefined Network");
					networktype = "Undefined Network";
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result)
		{
			super.onPostExecute(result);

			String url = "";
			ConnectionDetector cd = new ConnectionDetector(SpeedTestWebviewActivity.this);
			if (cd.isConnectingToInternet())
			{
				// http://exceptionaire.co.in/gospeed/webservices/curl.php?name=Otis&devicename=ANDROID&userid=255
				// http://www.ifemwireless.com/gospeed/webservices/curl.php?name=Simon&devicename=APPLE&userid=419
				// &devicetype=iPhone4&lat=18.568029&long=73.775382&imei=BCC048D3-43BD-4D00-9E8E-E24976D92E45
				// String Dv_Name = null;
				// String url = "http://stackoverflow.com/search?q=" + query;
				String name = LocalSettings.username;
				name = name.replace(" ", "%20");// %20
				name = name.trim();
				System.out.println(" name==" + name);
				// url =
				// "http://www.ifemwireless.com/gospeed/webservices/curl.php?name="
				// + name + "&devicename=" + devicename
				// + "&userid=" + LocalSettings.loginUser_id + "&latitude=" +
				// lat + "&long=" + log + "&imei=" + imei + "&device=" +
				// networktype;
				// webview.loadUrl("http://www.ifemwireless.com/gospeed/webservices/curl.php?name="
				// + name + "&devicename=" + devicename + "&userid=" +
				// LocalSettings.loginUser_id + "&latitude=" + lat + "&long=" +
				// log + "&imei=" + imei + "&device=" + networktype);
				// System.out.println("url===" + url);

				// New Url As per Skype
				url = "http://www.ifemwireless.com/gospeed/webservices/curl.php?name=" + name + "&devicename=" + devicename + "&userid=" + LocalSettings.loginUser_id + "&imei=" + imei + "&device=" + networktype;
				webview.loadUrl(url);
				System.out.println("url===" + url);
			}
			else
			{
				showAlert();
			}
			System.out.println("Demo url" + url);
			overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if ((keyCode == KeyEvent.KEYCODE_BACK))
		{

			AlertDialog.Builder alertbox = new AlertDialog.Builder(SpeedTestWebviewActivity.this);
			// alertbox.setIcon(R.drawable.info_icon);
			alertbox.setTitle("Exit the application?");
			alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface arg0, int arg1)
				{
					/*
					 * Session session = Session.getActiveSession(); if
					 * (session.isOpened()) {
					 * session.closeAndClearTokenInformation();
					 * 
					 * }
					 */

					finish();
				}
			});

			alertbox.setNegativeButton("No", new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface arg0, int arg1)
				{

				}
			});

			alertbox.show();

		}
		return super.onKeyDown(keyCode, event);
	}
}
