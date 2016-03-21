package com.ifemwireless.gospeed;

import java.util.HashSet;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ifemwireless.gospeed.setting.ConnectionDetector;
import com.ifemwireless.gospeed.setting.LocalSettings;
import com.ifemwireless.gospeed.webapi.WebApi;

@SuppressLint(
{ "ClickableViewAccessibility", "ShowToast" })
public class QuestionaireActivity extends Activity
{
	private RatingBar getRatingBar, getRatingBar2, getRatingBar3, getRatingBar4, getRatingBar5;
	private RatingBar setRatingBar;
	private TextView countText, welcome_name;
	private TextView rateing1, rateing2, rateing3, rateing4, rateing5;
	private TextView questionaire1, questionaire2, questionaire3, questionaire4, questionaire5;
	int count;
	float curRate;
	int stars1, stars2, stars3;
	Button submitbtn;
	ConnectionDetector cd = null;
	String question_level = "";
	public static HashSet<String> question_str = new HashSet<String>();
	String rating_value1 = "", rating_value2 = "", rating_value3 = "", rating_value4 = "", rating_value5 = "";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.questionaire_activity);
		// Mapping the views
		findViewsById();
		// Intialise the shared preferance
		LocalSettings.Init(QuestionaireActivity.this);
		LocalSettings.Load();
		// Welcome text----
		welcome_name.setText("Welcome, " + " " + LocalSettings.username);
		// Setting questions here getting from the local preferance-----
		questionaire1.setText(LocalSettings.question1);
		questionaire2.setText(LocalSettings.question2);
		questionaire3.setText(LocalSettings.question3);
		questionaire4.setText(LocalSettings.question4);
		questionaire5.setText(LocalSettings.question5);

		// -----------------------Setting the click listners here-------------

		getRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener()
		{
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser)
			{

				rating_value1 = String.valueOf(rating);
				rating_value1 = rating_value1.replace(".0", "");
				rateing1.setText(String.valueOf(rating_value1) + "/5");

			}
		});

		getRatingBar2.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener()
		{
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser)
			{

				rating_value2 = String.valueOf(rating);
				rating_value2 = rating_value2.replace(".0", "");
				rateing2.setText(String.valueOf(rating_value2) + "/5");

			}
		});

		getRatingBar3.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener()
		{
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser)
			{

				rating_value3 = String.valueOf(rating);
				rating_value3 = rating_value3.replace(".0", "");
				rateing3.setText(String.valueOf(rating_value3) + "/5");

			}
		});
		getRatingBar4.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener()
		{
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser)
			{

				rating_value4 = String.valueOf(rating);
				rating_value4 = rating_value4.replace(".0", "");
				rateing4.setText(String.valueOf(rating_value4) + "/5");

			}
		});

		getRatingBar5.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener()
		{
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser)
			{

				rating_value5 = String.valueOf(rating);
				rating_value5 = rating_value5.replace(".0", "");
				rateing5.setText(String.valueOf(rating_value5) + "/5");

			}
		});

		submitbtn.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{

				cd = new ConnectionDetector(QuestionaireActivity.this);
				if (cd.isConnectingToInternet())
				{
					if (rateing3.getText().toString().equalsIgnoreCase("0/5") || rateing1.getText().toString().equalsIgnoreCase("0/5") || rateing2.getText().toString().equalsIgnoreCase("0/5") || rateing4.getText().toString().equalsIgnoreCase("0/5") || rateing5.getText().toString().equalsIgnoreCase("0/5"))
					{
						Toast.makeText(QuestionaireActivity.this, "Please share your ratings for all questions.", Toast.LENGTH_SHORT).show();
					}
					else if (rateing1.getText().toString().equalsIgnoreCase("0/5"))
					{
						Toast.makeText(QuestionaireActivity.this, "Please share your ratings for all questions.", Toast.LENGTH_SHORT).show();
					}
					else if (rateing2.getText().toString().equalsIgnoreCase("0/5"))
					{
						Toast.makeText(QuestionaireActivity.this, "Please share your ratings for all questions.", Toast.LENGTH_SHORT).show();
					}
					else if (rateing3.getText().toString().equalsIgnoreCase("0/5"))
					{
						Toast.makeText(QuestionaireActivity.this, "Please share your ratings for all questions.", Toast.LENGTH_SHORT).show();
					}
					else if (rateing4.getText().toString().equalsIgnoreCase("0/5"))
					{
						Toast.makeText(QuestionaireActivity.this, "Please share your ratings for all questions.", Toast.LENGTH_SHORT).show();
					}
					else if (rateing5.getText().toString().equalsIgnoreCase("0/5"))
					{
						Toast.makeText(QuestionaireActivity.this, "Please share your ratings for all questions.", Toast.LENGTH_SHORT).show();
					}
					else
					{
						ConnectionDetector cd = new ConnectionDetector(QuestionaireActivity.this);
						if (cd.isConnectingToInternet())
						{
							new QuestionaireTask().execute();

						}
						else
						{
							showAlert();
						}

					}
				}
				else
				{
					showAlert();
				}
			}
		});

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if ((keyCode == KeyEvent.KEYCODE_BACK))
		{

			AlertDialog.Builder alertbox = new AlertDialog.Builder(QuestionaireActivity.this);
			alertbox.setTitle("Exit the application?");
			alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface arg0, int arg1)
				{
					// Setting the flag for login current screen
					LocalSettings.loginStatusforQuestionaire = "77";
					LocalSettings.Save();
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

	private void findViewsById()
	{
		getRatingBar = (RatingBar) findViewById(R.id.getRating);
		getRatingBar2 = (RatingBar) findViewById(R.id.getRating2);
		getRatingBar3 = (RatingBar) findViewById(R.id.getRating3);
		getRatingBar4 = (RatingBar) findViewById(R.id.getRating4);
		getRatingBar5 = (RatingBar) findViewById(R.id.getRating5);

		submitbtn = (Button) findViewById(R.id.submitbtn);
		rateing1 = (TextView) findViewById(R.id.rateing1);
		rateing2 = (TextView) findViewById(R.id.rateing2);
		rateing3 = (TextView) findViewById(R.id.rateing3);
		rateing4 = (TextView) findViewById(R.id.rateing4);
		rateing5 = (TextView) findViewById(R.id.rateing5);
		// setRatingBar = (RatingBar) findViewById(R.id.setRating);
		welcome_name = (TextView) findViewById(R.id.welcome_name);
		questionaire1 = (TextView) findViewById(R.id.questionaire1);
		questionaire2 = (TextView) findViewById(R.id.questionaire2);
		questionaire3 = (TextView) findViewById(R.id.questionaire3);

		questionaire4 = (TextView) findViewById(R.id.questionaire4);
		questionaire5 = (TextView) findViewById(R.id.questionaire5);
	}

	public void showAlert()
	{

		QuestionaireActivity.this.runOnUiThread(new Runnable()
		{
			public void run()
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(QuestionaireActivity.this);
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

	// Questionaire Class with extends AsyncTask class
	public class QuestionaireTask extends AsyncTask<Void, Void, Void>
	{

		private ProgressDialog Dialog = new ProgressDialog(QuestionaireActivity.this);
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

				String question_ID = GoSpeedLoginActivity.signUpDTO.questionsListIDS.get(0) + "," + GoSpeedLoginActivity.signUpDTO.questionsListIDS.get(1) + "," + GoSpeedLoginActivity.signUpDTO.questionsListIDS.get(2) + "," + GoSpeedLoginActivity.signUpDTO.questionsListIDS.get(3) + "," + GoSpeedLoginActivity.signUpDTO.questionsListIDS.get(4);
				Result = WebApi.getQuestionaire(question_ID, rating_value1 + "," + rating_value2 + "," + rating_value3 + "," + rating_value4 + "," + rating_value5);

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
			LocalSettings.loginStatusforQuestionaire = "1";
			LocalSettings.Save();
			Intent intent = new Intent(QuestionaireActivity.this, SpeedTestWebviewActivity.class);
			startActivity(intent);

			finish();
		}
	}

}
