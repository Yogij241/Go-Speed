package com.ifemwireless.gospeed;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.ifemwireless.gospeed.setting.LocalSettings;

/* * © Copyright 2015, iFem Wireless, Inc. All rights reserved.* */
// Google Play login Details:-
// Email:- ifemwireless@gmail.com
// Password :- qszwax123
public class SplashScreenActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		LocalSettings.Init(SplashScreenActivity.this);
		LocalSettings.Load();
		// System.out.println("LocalSettings.loginStatusforQuestionaire===="+LocalSettings.loginStatusforQuestionaire);
		final int img_splashTime = 2000;
		Thread splashTread = new Thread()
		{
			public void run()
			{
				try
				{
					int waited = 0;
					while (waited < img_splashTime)
					{
						sleep(100);
						waited += 100;
					}

				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				finally
				{
					// Decide here which screen required to call------
					if (LocalSettings.loginStatusforQuestionaire.equalsIgnoreCase("66"))
					{
						Intent intent = new Intent(SplashScreenActivity.this, GoSpeedLoginActivity.class);
						startActivity(intent);
						finish();
					}
					else if (LocalSettings.loginStatusforQuestionaire.equalsIgnoreCase("77"))
					{
						Intent intent = new Intent(SplashScreenActivity.this, QuestionaireActivity.class);
						startActivity(intent);
						finish();
					}
					else if (LocalSettings.loginStatusforQuestionaire.equalsIgnoreCase("1"))
					{
						Intent intent = new Intent(SplashScreenActivity.this, SpeedTestWebviewActivity.class);
						startActivity(intent);
						finish();
					}
				}
			}
		};
		splashTread.start();
	}

}
