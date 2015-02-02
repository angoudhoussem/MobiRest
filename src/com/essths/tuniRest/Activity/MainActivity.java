package com.essths.tuniRest.Activity;

import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import com.carouseldemo.main.R;
import com.essths.tuniRest.Restaurant.Constant;
//import android.R.string;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	Button B1, B2;
	TextView ins, compt;
	EditText login, password;
	String result = null;
	InputStream is;
	ProgressDialog pDialog;
	String Password, Login;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homee);

		login = (EditText) findViewById(R.id.login);
		password = (EditText) findViewById(R.id.password);
		B1 = (Button) findViewById(R.id.button);
		compt = (TextView) findViewById(R.id.home);
		
		compt.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(getApplicationContext(),
						Compte.class);
				startActivity(intent);

			}
		});

		B1.setOnClickListener(new OnClickListener() {

			// Cursor cursor = null;

			public void onClick(View v) {
				Login = login.getText().toString();
				Password = password.getText().toString();

				if (Login.length() != 0 && Password.length() != 0) {

					Constant.Login = Login;
					Constant.Password = Password;
					new Loade().execute();

				}

				else

				{
					LayoutInflater inflater = getLayoutInflater();
					View layout = inflater.inflate(
							R.layout.toast_costum_layout_drawing,
							(ViewGroup) findViewById(R.id.toast_layout_root));
					TextView text = (TextView) layout
							.findViewById(R.id.id_home);
					text.setText("login et/ou password vides");
					Toast toast = new Toast(getApplicationContext());
					toast.setGravity(Gravity.BOTTOM, 0, 110);
					toast.setDuration(Toast.LENGTH_LONG);
					toast.setView(layout);
					toast.show();

					// Context context = getApplicationContext();
					// CharSequence text = " vide";
					// int duration = Toast.LENGTH_SHORT;
					//
					// Toast toast = Toast.makeText(context, text, duration);
					// toast.show();

				}

			}
		});

	}

	class Loade extends AsyncTask<String, String, String> {
		String result;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(MainActivity.this);
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
					"http://10.0.2.2:8080/restProject/resources/entity.profil/authentification"
							+ "/" + Login + "/" + Password);
			httpGet.setHeader("Content-Type", "text/plain");
			try {
				HttpResponse response = httpClient.execute(httpGet);
				result = EntityUtils.toString(response.getEntity());

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
				LayoutInflater inflater = getLayoutInflater();
				View layout = inflater.inflate(
						R.layout.toast_costum_layout_success,
						(ViewGroup) findViewById(R.id.toast_layout_root));
				TextView text = (TextView) layout.findViewById(R.id.id_home);
				text.setText("Login et password Valides");
				Toast toast = new Toast(getApplicationContext());
				toast.setGravity(Gravity.BOTTOM, 0, 110);
				toast.setDuration(Toast.LENGTH_LONG);
				toast.setView(layout);
				toast.show();
				Intent i = new Intent(getApplicationContext(),
						com.essths.tuniRest.carouseldemo.main.MainActivityCarousel.class);
				startActivity(i);
				pDialog.cancel();

				// Toast.makeText(MainActivity.this, "yes", Toast.LENGTH_SHORT)
				// .show();
			} else {

				LayoutInflater inflater = getLayoutInflater();
				View layout = inflater.inflate(
						R.layout.toast_costum_layout_error,
						(ViewGroup) findViewById(R.id.toast_layout_root));
				TextView text = (TextView) layout.findViewById(R.id.id_home);
				text.setText("Login et password Invalides");
				Toast toast = new Toast(getApplicationContext());
				toast.setGravity(Gravity.BOTTOM, 0, 110);
				toast.setDuration(Toast.LENGTH_LONG);
				toast.setView(layout);
				toast.show();

				pDialog.cancel();
				// Toast.makeText(MainActivity.this, "erreur",
				// Toast.LENGTH_SHORT)
				// .show();
			}

		}
	}

}
