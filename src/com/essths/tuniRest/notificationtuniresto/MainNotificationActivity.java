package com.essths.tuniRest.notificationtuniresto;

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
import android.widget.ListView;

import com.carouseldemo.main.R;
import com.essths.tuniRest.CustomAdapter.Restaurant;
import com.essths.tuniRest.Restaurant.Constant;
import com.essths.tuniRest.carouseldemo.main.MainActivityCarousel;
import com.essths.tuniRest.profil.Profil;

public class MainNotificationActivity extends Activity {

	String result;
	String obj;
	JSONObject restaurantsJsonObject;
	JSONArray data;
	ArrayList<Notification> alluser = new ArrayList<Notification>();
	ProgressDialog pDialog;
	ListView list;
	Restaurant restaurant = Constant.r;

	int count = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_notification);
		list = (ListView) findViewById(R.id.listView1);
		View header = (View) getLayoutInflater().inflate(
				R.layout.header_notification, null);
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
			pDialog = new ProgressDialog(MainNotificationActivity.this);
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
					"http://10.0.2.2:8080/restProject/resources/entity.notification/Recherche");

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

					Notification List = new Notification();
					JSONObject JObject = data.getJSONObject(t);
					List.setContenu(JObject.getString("contenu"));
					List.setDate(JObject.getString("dateNotif"));
//					List.setIdrest(JObject.getString("idRest"));
//					List.setNomRest(JObject.getString("nomRest"));

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
			list.setAdapter(new CustomListAdapter(getApplicationContext(),
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
