package com.essths.tuniRest.profil;

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

import com.carouseldemo.main.R;
import com.essths.tuniRest.Restaurant.Constant;
import com.essths.tuniRest.carouseldemo.main.MainActivityCarousel;

public class MainProfil extends Activity {
	String result;
	String obj;
	JSONObject restaurantsJsonObject;
	JSONArray data;
	ArrayList<Profil> alluser = new ArrayList<Profil>();
	ProgressDialog pDialog;
	ListView list;

	Profil profil = Constant.pr;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainprofil);
		list = (ListView) findViewById(R.id.id_listprofil);
		View header = (View) getLayoutInflater().inflate(
				R.layout.header_utilisateur, null);
		list.addHeaderView(header);
		new Loade().execute();
		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Profil t = (Profil) list.getItemAtPosition(position);
				Constant.pr = t;
				Intent intent = new Intent(getApplicationContext(),
						listamie.class);

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
			pDialog = new ProgressDialog(MainProfil.this);
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
					"http://10.0.2.2:8080/restProject/resources/entity.profil/Recherche");

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

					Profil List = new Profil();
					JSONObject JObject = data.getJSONObject(t);
					List.setNom(JObject.getString("nom"));
					List.setPrenom(JObject.getString("prenom"));
					List.setImage(JObject.getString("image"));
					List.setEmail(JObject.getString("email"));
					List.setIdprofil(JObject.getString("idProfil"));

					alluser.add(List);

				}
				System.out.println("alluer" + alluser.size());
			}

			catch (Exception e) {

				System.out.println("alluer" + e.getMessage());

			}

			return result;
		}

		protected void onPostExecute(String file_url) {

			System.out.println("Result" + result);

			pDialog.cancel();

			list.setAdapter(new CustomProfilAdapter(getApplicationContext(),
					alluser));

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
