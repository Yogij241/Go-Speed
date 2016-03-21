package com.ifemwireless.gospeed;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.ifemwireless.gospeed.dto.SignUpDTO;
import com.ifemwireless.gospeed.setting.ConnectionDetector;
import com.ifemwireless.gospeed.setting.LocalSettings;
import com.ifemwireless.gospeed.webapi.WebApi;

/* * © Copyright 2015, iFem Wireless, Inc. All rights reserved.* */
public class GoSpeedLoginActivity extends Activity
{

	Dialog dialog = null;
	WebApi webApi;
	public static SignUpDTO signUpDTO = new SignUpDTO();
	public static String new_username = "";
	public static String old_username = "";
	private static final String EMAIL_VALID_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static Pattern pattern;
	private static Matcher matcher;
	EditText user_txt, email_txt;
	Button btnFbLogin, login_btn, register_btn, forgotpass_btn, for_send, for_cancle, readmanual_btn;
	ToggleButton remember_btn;
	public static String user_str = "", email_str = "", vendorid_str = "";

	String facebook_email = "";
	ConnectionDetector cd = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);

		Initialize();
		// Getting the device id from the secure android class
		String deviceId = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);
		vendorid_str = deviceId;
		old_username = LocalSettings.username;

		user_txt.setHintTextColor(getResources().getColor(R.color.silver));
		email_txt.setHintTextColor(getResources().getColor(R.color.silver));

		// Setting the listenes for the the view

		user_txt.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
				if (s.length() > 0 && s.subSequence(0, 1).toString().equalsIgnoreCase(" "))
				{
					user_txt.setText("");
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{

			}

			@Override
			public void afterTextChanged(Editable s)
			{

			}
		});
		login_btn.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{

				if (CheckValidation())
				{
					ConnectionDetector cd = new ConnectionDetector(GoSpeedLoginActivity.this);
					if (cd.isConnectingToInternet())
					{
						hideKeyboard(GoSpeedLoginActivity.this);
						user_str = user_txt.getText().toString();
						user_str = user_str.replace(" ", "%20");// %20
						user_str = user_str.trim();
						new LoginTask().execute();
					}
					else
					{
						showAlert();
					}
				}
			}
		});
	}

	public void Initialize()
	{
		user_txt = (EditText) findViewById(R.id.user_txt);
		email_txt = (EditText) findViewById(R.id.email_txt);
		login_btn = (Button) findViewById(R.id.login_btn);

	}

	// Checking the validation for the input fields
	public boolean CheckValidation()
	{

		boolean flag = true;
		user_str = user_txt.getText().toString();
		email_str = email_txt.getText().toString();
		if (user_txt.getText().toString().trim().length() == 0)
		{
			Toast.makeText(GoSpeedLoginActivity.this, "Please enter name.", Toast.LENGTH_LONG).show();
			user_txt.requestFocus(user_txt.getText().length());
			flag = false;
		}

		return flag;
	}

	// show the network alret when net is off
	public void showAlert()
	{

		GoSpeedLoginActivity.this.runOnUiThread(new Runnable()
		{
			public void run()
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(GoSpeedLoginActivity.this);
				builder.setTitle("Internet Error!");
				builder.setIcon(R.drawable.error);
				builder.setMessage("Check Internet Connection.").setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int id)
					{
					}
				});
				AlertDialog alert = builder.create();
				alert.show();
			}
		});
	}

	// Login Class with extends AsyncTask class
	public class LoginTask extends AsyncTask<Void, Void, Void>
	{

		private ProgressDialog Dialog = new ProgressDialog(GoSpeedLoginActivity.this);
		String data = "", rstatus_login = "0";

		int sizeData = 0;
		String Result = "";
		String status = "";

		protected void onPreExecute()
		{
			Dialog.setMessage("Loading...");
			Dialog.setCancelable(false);
			Dialog.show();

		}

		@Override
		protected Void doInBackground(Void... params)
		{

			try
			{
				signUpDTO = WebApi.getSignUp(user_str, vendorid_str);
			}
			catch (Exception e)
			{

			}

			return null;
		}

		@SuppressLint("NewApi")
		protected void onPostExecute(Void unused)
		{
			Dialog.dismiss();
			if (signUpDTO.status.equalsIgnoreCase("1"))
			{
				// user_txt.setText("");
				// password_txt.setText("");
				LocalSettings.loginStatusforQuestionaire = "77";
				LocalSettings.question1 = signUpDTO.questionsList.get(0);
				LocalSettings.question2 = signUpDTO.questionsList.get(1);
				LocalSettings.question3 = signUpDTO.questionsList.get(2);
				LocalSettings.question4 = signUpDTO.questionsList.get(3);
				LocalSettings.question5 = signUpDTO.questionsList.get(4);
				LocalSettings.loginUser_id = signUpDTO.userid;
				signUpDTO.user_name = signUpDTO.user_name.replace("%20", " ");// %20
				signUpDTO.user_name = signUpDTO.user_name.trim();
				LocalSettings.username = signUpDTO.user_name;
				System.out.println("username" + signUpDTO.user_name);
				LocalSettings.Save();
				Intent intent = new Intent(GoSpeedLoginActivity.this, QuestionaireActivity.class);
				startActivity(intent);
				finish();
			}
			else
			{
				Toast.makeText(GoSpeedLoginActivity.this, "Authentication failed! Invalid Username or Password", Toast.LENGTH_SHORT).show();
			}

		}
	}

	// ----Hide Keyboard ----------------
	public void hideKeyboard(Activity activity)
	{
		InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (activity.getCurrentFocus() != null)
		{
			inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	public static boolean validateEmail(final String hex)
	{

		pattern = Pattern.compile(EMAIL_VALID_PATTERN);
		matcher = pattern.matcher(hex);
		return matcher.matches();
	}

	/* key down log out button */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if ((keyCode == KeyEvent.KEYCODE_BACK))
		{

			AlertDialog.Builder alertbox = new AlertDialog.Builder(GoSpeedLoginActivity.this);
			alertbox.setTitle("Exit the application?");
			alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface arg0, int arg1)
				{
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
