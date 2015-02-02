package com.essths.tuniRest.Restaurant;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.carouseldemo.main.R;
import com.essths.tuniRest.Activity.Compte;
import com.essths.tuniRest.CustomAdapter.Restaurant;

public class Resservation extends Activity {

	Spinner spinner1, spinner2, spinner3, spinner4, spinner5;
	private Button reserver;
	static final int DATE_DIALOG_ID = 999;
	String result;
	String obj;
	JSONObject restaurantsJsonObject;
	JSONArray data;
	ProgressDialog pDialog;
	ListView list;
	EditText nbre;
	String Nbre;
	Restaurant restaurant = Constant.r;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reservation);
		reserver = (Button) findViewById(R.id.button1);
		nbre = (EditText) findViewById(R.id.editText1);
		// Nbre=;
		reserver.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				new Loade().execute();

			}

		});

		spinner1 = (Spinner) findViewById(R.id.Spinner1);
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
				this, R.array.jour, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner1.setAdapter(adapter1);

		spinner2 = (Spinner) findViewById(R.id.Spinner2);
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
				this, R.array.annee, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner2.setAdapter(adapter2);

		spinner3 = (Spinner) findViewById(R.id.Spinner3);
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(
				this, R.array.mois, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner3.setAdapter(adapter3);

		spinner4 = (Spinner) findViewById(R.id.spinner4);
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(
				this, R.array.heur, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner4.setAdapter(adapter4);

		spinner5 = (Spinner) findViewById(R.id.spinner5);
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(
				this, R.array.minute, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner5.setAdapter(adapter5);
	}

	class Loade extends AsyncTask<String, String, String> {
		String result;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Resservation.this);
			pDialog.setMessage(Html.fromHtml("<b>Chargement....</b><br/>"));
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... arg0) {

			HttpClient httpClient = new DefaultHttpClient();
			final HttpParams httpParams = httpClient.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 100000);
			HttpConnectionParams.setSoTimeout(httpParams, 100000);

			HttpGet httpGet = new HttpGet(
					"http://10.0.2.2:8080/restProject/resources/entity.reservation/ajout"
							+ "/" + nbre.getText().toString() + "/"
							+ spinner1.getSelectedItem() + "/"
							+ spinner2.getSelectedItem() + "/"
							+ spinner3.getSelectedItem() + "/"
							+ spinner4.getSelectedItem() + "/"
							+ spinner5.getSelectedItem() + "/"
							+ restaurant.getIdrest());
			httpGet.setHeader("Content-Type", "text/plain");
			try {
				HttpResponse response = httpClient.execute(httpGet);
				result = EntityUtils.toString(response.getEntity());
				System.out.println("result" + result);
			} catch (Exception e) {

				System.out.println("erreur" + e.getMessage());

			}
			if (result.equals("yes")) {

				pDialog.cancel();

			}
			return result;
		}

		protected void onPostExecute(String file_url) {

			if (result.equals("yes")) {

				pDialog.cancel();

				Toast.makeText(Resservation.this, "yes", Toast.LENGTH_SHORT)
						.show();
			} else {
				Toast.makeText(Resservation.this, "erreur", Toast.LENGTH_SHORT)
						.show();
			}

		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			startActivity(new Intent(getApplicationContext(),
					AfficheRestaurants.class));

		}

		return super.onKeyDown(keyCode, event);
	}
}
