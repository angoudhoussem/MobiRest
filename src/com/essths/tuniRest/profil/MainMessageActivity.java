package com.essths.tuniRest.profil;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.carouseldemo.main.R;

public class MainMessageActivity extends Activity {

	public static final int SENT = 0;
	public static final int DELIVERED = 1;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		OnClickListener listener = new OnClickListener() {

			public void onClick(View v) {
				/** Getting reference to et_number of main.xml */
				EditText etNumber = (EditText) findViewById(R.id.id_numero);

				/** Getting reference to et_message of main.xml */
				EditText etMessage = (EditText) findViewById(R.id.id_textmessage);

				String number = etNumber.getText().toString();
				String message = etMessage.getText().toString();

				/** Creating an intent, corresponding to sent delivery report */
				/** This intent will call the activity SmsStatus */
				Intent sentIntent = new Intent(
						"in.wptrafficanalyzer.activity.status.sent");

				/** Sms is sent to this number */
				sentIntent.putExtra("number", number);

				/** Setting status data on the intent */
				sentIntent.putExtra("status", SENT);

				/**
				 * Creating an intent, corresponding to delivered delivery
				 * report
				 */
				/** This intent will call the activity SmsStatus */
				Intent deliveredIntent = new Intent(
						"in.wptrafficanalyzer.activity.status.delivered");

				/** Sms is sent to this number */
				deliveredIntent.putExtra("number", number);

				/** Setting status data on the intent */
				deliveredIntent.putExtra("status", DELIVERED);

				/**
				 * Creating a pending intent which will be invoked by SmsManager
				 * when an sms message is successfully sent
				 */
				PendingIntent piSent = PendingIntent.getActivity(
						getBaseContext(), 0, sentIntent,
						PendingIntent.FLAG_ONE_SHOT);

				/**
				 * Creating a pending intent which will be invoked by SmsManager
				 * when an sms message is successfully delivered
				 */
				PendingIntent piDelivered = PendingIntent.getActivity(
						getBaseContext(), 0, deliveredIntent,
						PendingIntent.FLAG_ONE_SHOT);

				/**
				 * Getting an instance of SmsManager to sent sms message from
				 * the application
				 */
				SmsManager smsManager = SmsManager.getDefault();

				/** Sending the Sms message to the intended party */
				smsManager.sendTextMessage(number, null, message, piSent,
						piDelivered);

			}
		};

		/** Getting reference to btn_send of main.xml */
		Button btnSend = (Button) findViewById(R.id.id_envoiyer);

		/** Setting a click event listener for the button */
		btnSend.setOnClickListener(listener);

	}
}