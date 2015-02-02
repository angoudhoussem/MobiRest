package com.essths.tuniRest.Restaurant.VisiteVirtuelle;

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
import android.widget.Gallery;
import android.widget.ImageView;

import com.carouseldemo.main.R;
import com.essths.tuniRest.CustomAdapter.Restaurant;
import com.essths.tuniRest.Restaurant.AfficheRestaurants;
import com.essths.tuniRest.Restaurant.Constant;

public class MainVisiteVirtuelle extends Activity {

	String result;
	String obj;
	JSONObject restaurantsJsonObject;
	JSONArray data;
	ArrayList<VisiteVirtuelle> alluser = new ArrayList<VisiteVirtuelle>();
	ProgressDialog pDialog;
	Gallery g;
	ImageView img;

	Restaurant restaurant = Constant.r;

	int count = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_visite_virtuelle);

		g = (Gallery) findViewById(R.id.Gallery);
		img = (ImageView) findViewById(R.id.GalleryView);

	}

	class Loade extends AsyncTask<String, String, String> {
		String result;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(MainVisiteVirtuelle.this);
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
					"http://10.0.2.2:8080/restProject/resources/entity.visitevirtuelle/Recherche"
							+ "/" + restaurant.getIdrest());

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

					VisiteVirtuelle galery = new VisiteVirtuelle();
					JSONObject JObject = data.getJSONObject(t);
					galery.setImage(JObject.getString("image"));
					// List.setDate(JObject.getString("dateNotif"));

					alluser.add(galery);

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
			g.setAdapter(new VisiteVirtuelleAdapter(getApplicationContext(),
					alluser));
			System.out.println("liste" + g);

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
