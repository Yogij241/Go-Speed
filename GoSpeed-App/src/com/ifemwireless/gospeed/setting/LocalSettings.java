package com.ifemwireless.gospeed.setting;

import android.content.Context;
import android.content.SharedPreferences;

/* * © Copyright 2015, iFem Wireless, Inc. All rights reserved.* */
public class LocalSettings
{
	public static final String PREFS_NAME = "GoSpeedSharedPreFile";
	static SharedPreferences settings;
	public static boolean isStartServiceCalled = true;
	public static String loginUser_id;
	public static String loginStatusforQuestionaire;
	public static String question1;
	public static String question2;
	public static String question3;
	public static String question4;
	public static String question5;
	public static String emailid;
	public static String username;
	public static String kaywordoflist;

	public static void Init(Context paramContext)
	{
		settings = paramContext.getSharedPreferences("GoSpeedSharedPreFile", 0);
	}

	public static void Load()
	{
		isStartServiceCalled = settings.getBoolean("isFirstTimeLogin_FB", true);
		loginUser_id = settings.getString("loginUser_id", "");
		emailid = settings.getString("email_id", "");
		username = settings.getString("username", "");
		kaywordoflist = settings.getString("kaywordoflist", "");
		loginStatusforQuestionaire = settings.getString("loginStatusforQuestionaire", "66");
		question1 = settings.getString("question1", "");
		question2 = settings.getString("question2", "");
		question3 = settings.getString("question3", "");
		question4 = settings.getString("question4", "");
		question5 = settings.getString("question5", "");
	}

	public static void Save()
	{
		SharedPreferences.Editor localEditor = settings.edit();
		localEditor.putBoolean("isStartServiceCalled", isStartServiceCalled);
		localEditor.putString("loginUser_id", loginUser_id);
		localEditor.putString("emailid", emailid);
		localEditor.putString("username", username);
		localEditor.putString("kaywordoflist", kaywordoflist);
		localEditor.putString("loginStatusforQuestionaire", loginStatusforQuestionaire);
		localEditor.putString("question1", question1);
		localEditor.putString("question2", question2);
		localEditor.putString("question3", question3);
		localEditor.putString("question4", question4);
		localEditor.putString("question5", question5);

		localEditor.commit();
	}

}
