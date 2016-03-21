package com.ifemwireless.gospeed;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.widget.TextView;

public class AboutUsActivity extends Activity
{
	TextView aboutUs_txt, visit_site_txt, name_txt;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aboutus_layout);
		aboutUs_txt = (TextView) findViewById(R.id.aboutUs_txt);
		visit_site_txt = (TextView) findViewById(R.id.visit_site_txt);
		aboutUs_txt.setText(Html.fromHtml("<div align=\"left\">" + "GoSpeed is owned and operated by iFem Wireless, Inc. GoSpeed measures the quality of your data connection." + " It lets you know your current data network performance. You can run the test on Wi-Fi or cellular data.<br /><br /> " + "Please note that by running the test, you are helping us contribute to our database." + "This will show precisely how your internet service provider is doing in any particular area worldwide.</div>"));

		String htmlString = "For Terms of Use, visit  <a href=\"\"> www.ifemwireless.com <br /> </a></b>";
		visit_site_txt.setText(Html.fromHtml(htmlString.trim()));
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if ((keyCode == KeyEvent.KEYCODE_BACK))
		{
			finish();
		}
		return super.onKeyDown(keyCode, event);

	}
}
