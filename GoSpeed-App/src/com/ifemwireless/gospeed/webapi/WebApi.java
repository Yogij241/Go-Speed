package com.ifemwireless.gospeed.webapi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.ifemwireless.gospeed.dto.SignUpDTO;
import com.ifemwireless.gospeed.setting.LocalSettings;

/* * © Copyright 2015, iFem Wireless, Inc. All rights reserved.* */
public class WebApi
{

	public static boolean UpdateFlag;
	public static Context _context;
	static WebClient mClient = new WebClient(WebUrl.WebService);
	static WebClient mClient2 = new WebClient(WebUrl.WebService2);

	static
	{
		UpdateFlag = false;
	}

	// Login API
	public static SignUpDTO getSignUp(String name, String vendorId)
	{
		// http://exceptionaire.co.in/gospeed/webservices/index.php/account

		SignUpDTO signUpDTO = new SignUpDTO();
		mClient.ClearParam();
		mClient.AddParam("fullname", name);
		// mClient.AddParam("email", email);
		mClient.AddParam("vendorId", vendorId);
		// mClient.AddParam("password", "123etpl");
		try
		{
			mClient.Execute(1);
			String response = mClient.getResponse();
			System.out.println("sing up==" + response);
			if (!"".equalsIgnoreCase(response) && response != null)
			{
				// Log.v("Signup response----------", response);
				JSONObject localJSONObject = (JSONObject) new JSONObject(response).getJSONArray("account").get(0);
				if (localJSONObject.has("status"))
				{
					signUpDTO.status = localJSONObject.getString("status");
					// System.out.println("Result-----------"+signUpDTO.status);
				}

				if (localJSONObject.has("userid"))
				{
					signUpDTO.userid = localJSONObject.getString("userid");

				}
				if (localJSONObject.has("user_name"))
				{
					signUpDTO.user_name = localJSONObject.getString("user_name");

				}
				if (localJSONObject.has("website_url"))
				{
					signUpDTO.website_url = localJSONObject.getString("website_url");

				}
				JSONArray jArray = localJSONObject.getJSONArray("questions");
				// To get the items from the array
				for (int i1 = 0; i1 < jArray.length(); i1++)
				{
					try
					{
						JSONObject oneObject = jArray.getJSONObject(i1);

						signUpDTO.questionsList.add(oneObject.optString("question"));
						signUpDTO.questionsListIDS.add(oneObject.optString("id"));
						// System.out.println("signUpDTO.questionsList---"+signUpDTO.questionsList.get(i1));
						// System.out.println("signUpDTO.questionsListIDS---"+signUpDTO.questionsListIDS.get(i1));

					}
					catch (JSONException e)
					{
						// Oops
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return signUpDTO;
	}

	// Questionaire API

	public static String getQuestionaire(String questionIds, String questionAnswers)
	{
		/*
		 * URL
		 * :http://exceptionaire.co.in/gospeed/webservices/index.php/submitAnswers
		 * 
		 * Parameters :
		 * 
		 * questionIds 2,3,4 questionAnswers 3,4,5 userid 5
		 */

		String s = "";
		// LoginDto logDto = new LoginDto();
		mClient2.ClearParam();
		// mClient2.AddParam("task", "submitAnswers");
		mClient2.AddParam("userid", LocalSettings.loginUser_id);
		mClient2.AddParam("questionIds", questionIds);
		mClient2.AddParam("questionAnswers", questionAnswers);

		try
		{
			mClient2.Execute(1);
			String response = mClient2.getResponse();
			System.out.println("questionaire==" + response);
			if (!"".equalsIgnoreCase(response) && response != null)
			{
				// Log.v("submitAnswers Response::", response);
				JSONObject localJSONObject = (JSONObject) new JSONObject(response).getJSONArray("submitAnswers").get(0);
				if (localJSONObject.has("status"))
				{
					s = localJSONObject.getString("status");
					System.out.println("Status---" + s);
				}

			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return s;
	}

	// ----------
	// http://yourdomainname.com/foldername/filename.php?
	// task=saveTestResult&userid=2&ping=123 ms&downloadSpeed=2.56 mbps&
	// uploadSpeed=3.86 mbps&deviceName=iPhone 6&currentDateTime=2015/05/20
	// 19.45
	public static String getTestResult(String ping, String downloadSpeed, String uploadSpeed, String deviceName, String currentDateTime)
	{
		// http://yourdomainname.com/foldername/filename.php?
		// task=submitAnswers&questionIds=1,2,3&questionAnswers=3.5,2.5,5&userid=2

		String s = "";

		mClient.ClearParam();
		mClient.AddParam("task", "saveTestResult");
		mClient.AddParam("userid", LocalSettings.loginUser_id);
		mClient.AddParam("ping", ping);
		mClient.AddParam("downloadSpeed", downloadSpeed);
		mClient.AddParam("uploadSpeed", uploadSpeed);
		mClient.AddParam("deviceName", deviceName);
		mClient.AddParam("currentDateTime", currentDateTime);

		try
		{
			mClient.Execute(0);
			String response = mClient.getResponse();

			if (!"".equalsIgnoreCase(response) && response != null)
			{
				// Log.v("Authenticate Response::", response);
				JSONObject localJSONObject = (JSONObject) new JSONObject(response).getJSONArray("saveTestResult").get(0);
				if (localJSONObject.has("status"))
				{
					s = localJSONObject.getString("status");
				}

			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return s;
	}
}
