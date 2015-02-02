package com.essths.tuniRest.Favoris;

import java.util.ArrayList;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.carouseldemo.main.R;
import com.essths.tuniRest.Restaurant.AfficheRestaurants;
import com.essths.tuniRest.carouseldemo.main.MainActivityCarousel;

public class MainReservation extends Activity {
	String result;
	String obj;
	JSONObject restaurantsJsonObject;
	JSONArray data;
	ArrayList<Reservation> alluser = new ArrayList<Reservation>();
	ProgressDialog pDialog;
	ListView list;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainfavoris);
		list = (ListView) findViewById(R.id.id_listView1);
		View header = (View) getLayoutInflater().inflate(
				R.layout.header_favoris, null);
		list.addHeaderView(header);
		new Loade().execute();
		// list.setOnItemClickListener(new OnItemClickListener() {
		// public void onItemClick(AdapterView<?> parent, View view,
		// int position, long id) {
		// Toast.makeText(getApplicationContext(),
		// "Click ListItem Number " + position, Toast.LENGTH_LONG)
		// .show();
		// Intent intent = new Intent (getApplicationContext(),
		// AfficheRestaurants.class);
		//
		// intent.putExtra("nom", "Bonjour");
		// startActivity(intent);
		// }
		// });

	}

	class Loade extends AsyncTask<String, String, String> {
		String result;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(MainReservation.this);
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
					"http://10.0.2.2:8080/restProject/resources/entity.reservation/Recherche");

			httpGet.setHeader("Content-Type", "application/json");
			try {
				HttpResponse response = httpClient.execute(httpGet);
				result = EntityUtils.toString(response.getEntity());

				System.out.println("test" + result);

				JSONArray a = new JSONArray(result);

				restaurantsJsonObject = new JSONObject();
				restaurantsJsonObject.put("data", a);
				data = restaurantsJsonObject.getJSONArray("data");

				for (int t = 0; t < data.length(); t++) {

					Reservation List = new Reservation();
					JSONObject JObject = data.getJSONObject(t);
					List.setDate(JObject.getString("date"));
					List.setNbreplace(JObject.getString("nbrePlace"));

					alluser.add(List);

				}
				System.out.println("alluer" + alluser);
			}

			catch (Exception e) {

				e.getMessage();

			}

			return result;
		}

		protected void onPostExecute(String file_url) {

			System.out.println("Result" + result);

			pDialog.cancel();

			// pDialog.dismiss();
			list.setAdapter(new CustomlistAdapter(getApplicationContext(),
					alluser));
			System.out.println("liste" + list);

		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			startActivity(new Intent(getApplicationContext(),
					MainActivityCarousel.class));

		}

		return super.onKeyDown(keyCode, event);
	}

}
