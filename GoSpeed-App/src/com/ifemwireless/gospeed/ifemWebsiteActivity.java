package com.ifemwireless.gospeed;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

/* * © Copyright 2015, iFem Wireless, Inc. All rights reserved.* */
public class ifemWebsiteActivity extends Activity
{
	ImageView back, forward, stop;
	ProgressBar progressBar;
	WebView webview;
	ImageView back_btn;
	//
	static boolean flag = false;
	HashMap<String, String> headers = new HashMap<String, String>();

	@SuppressLint("SetJavaScriptEnabled")
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.website_layout);

		back = (ImageView) findViewById(R.id.back);
		forward = (ImageView) findViewById(R.id.forward);
		stop = (ImageView) findViewById(R.id.stop);
		progressBar = (ProgressBar) findViewById(R.id.PBAR_View);
		webview = (WebView) findViewById(R.id.web_view);
		webview.getSettings().setBuiltInZoomControls(true);
		back_btn = (ImageView) findViewById(R.id.back_btn);
		webview.getSettings().setSupportZoom(true);
		webview.getSettings().setUseWideViewPort(true);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		int k = getResources().getIdentifier("pause", "drawable", getPackageName());
		stop.setBackgroundResource(k);
		stop.invalidate();
		WebSettings settings = webview.getSettings();
		settings.setJavaScriptEnabled(true);

		webview.setWebViewClient(new WebViewClient()
		{

			public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm)
			{
				super.onReceivedHttpAuthRequest(view, handler, host, realm);
			}

			public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error)
			{

				handler.proceed();
			}

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
				if (progress < 100 && progressBar.getVisibility() == ProgressBar.GONE)
				{
					flag = true;
					int k = getResources().getIdentifier("pause", "drawable", getPackageName());
					stop.setBackgroundResource(k);
					stop.invalidate();
					progressBar.setVisibility(ProgressBar.VISIBLE);
				}
				progressBar.setProgress(progress);
				if (progress == 100)
				{
					flag = false;
					int j = getResources().getIdentifier("refresh", "drawable", getPackageName());
					stop.setBackgroundResource(j);
					stop.invalidate();
					progressBar.setVisibility(ProgressBar.GONE);
				}
			}
		});

		webview.loadUrl(GoSpeedURLS.website);
		overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);

		back.setOnClickListener(new OnClickListener()
		{

			public void onClick(View v)
			{
				webview.goBack();

			}
		});

		forward.setOnClickListener(new OnClickListener()
		{

			public void onClick(View v)
			{
				webview.goForward();

			}
		});

		stop.setOnClickListener(new OnClickListener()
		{

			public void onClick(View v)
			{
				if (flag)
				{
					webview.stopLoading();
				}
				else
				{
					stop.setBackgroundResource(0);
					int j = getResources().getIdentifier("stop", "drawable", getPackageName());
					stop.setBackgroundResource(j);
					webview.reload();
				}

			}
		});
		back_btn.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				finish();
			}
		});
	}

	public boolean onKeyDown(int keyCode, KeyEvent event)
	{

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
		{
			finish();
			return true;

		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onResume()
	{
		super.onResume();
	}
}