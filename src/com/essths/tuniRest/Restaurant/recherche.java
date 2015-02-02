
package com.essths.tuniRest.Restaurant;

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
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.carouseldemo.main.R;
import com.essths.tuniRest.CustomAdapter.CustomAdapter;
import com.essths.tuniRest.CustomAdapter.Restaurant;
import com.essths.tuniRest.carouseldemo.main.MainActivityCarousel;

public class recherche extends Activity {
	String result;
	String obj;
	JSONObject restaurantsJsonObject;
	JSONArray data;
	ArrayList<Restaurant> alluser = new ArrayList<Restaurant>();
	ProgressDialog pDialog;
	ListView list;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recherche);
		list = (ListView) findViewById(R.id.listView1);
		View header = (View) getLayoutInflater().inflate(
				R.layout.listview_header_resto, null);
		list.addHeaderView(header);
		list = (ListView) findViewById(R.id.listView1);
		new Loade().execute();
		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Restaurant t = (Restaurant) list.getItemAtPosition(position);
				Constant.r = t;
				Intent intent = new Intent(getApplicationContext(),
						AfficheRestaurants.class);
				intent.putExtra("valeur", "bonjour" + position);

				startActivity(intent);
			}
		});

	}

	class Loade extends AsyncTask<String, String, String> {
		String result;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(recherche.this);
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
					"http://10.0.2.2:8080/restProject/resources/entity.restaurant/Recherche");

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

					Restaurant List = new Restaurant();
					JSONObject JObject = data.getJSONObject(t);
					List.setNom_rest(JObject.getString("nomRest"));
					List.setAdresse(JObject.getString("adresse"));
					List.setImage(JObject.getString("image"));
					List.setIdrest(JObject.getString("idRest"));
					List.setInfo(JObject.getString("info"));
					List.setTypecuisine(JObject.getString("typeCuisine"));

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
			list.setAdapter(new CustomAdapter(getApplicationContext(), alluser));
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
