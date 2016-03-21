package com.ifemwireless.gospeed.dto;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class SignUpDTO implements Serializable
{
	/*
	 * { "account": [ { "status": "1", "userid": "11", "user_name": "ashwini",
	 * "website_url": "http://www.ifemwireless.com", "questions": [ { "id": "3",
	 * "question": "    2 test question  new update " }, { "id": "4",
	 * "question": "Test question" }, { "id": "5", "question":
	 * "Your current city name?" } ] } ] }
	 */

	public String status;
	public String userid;
	public String user_name;
	public String website_url;
	public ArrayList<String> questionsList = new ArrayList<String>();
	public ArrayList<String> questionsListIDS = new ArrayList<String>();

}
