package com.essths.tuniRest.Restaurant;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
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
import com.carouseldemo.main.R;
import com.essths.tuniRest.Activity.Compte;
import com.essths.tuniRest.CustomAdapter.AffichAdapter;
import com.essths.tuniRest.CustomAdapter.Commentaire;
import com.essths.tuniRest.CustomAdapter.Restaurant;

public class ListCommentaire extends Activity {
	String result;
	String obj;
	JSONObject usersJsonObject;
	JSONArray data;
	ArrayList<Commentaire> alluser = new ArrayList<Commentaire>();
	ProgressDialog pDialog;
	ListView list;
	InputStream is = null;
	EditText comm;
	Restaurant restaurant = Constant.r;
	Compte cmpt = Constant.p;
	String c;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.commentaire);
		list = (ListView) findViewById(R.id.listView1);
		View header = (View) getLayoutInflater().inflate(
				R.layout.listview_header_com, null);
		list.addHeaderView(header);

		comm = (EditText) findViewById(R.id.editText1);
		Button b1 = (Button) findViewById(R.id.button1);
		Button b2 = (Button) findViewById(R.id.button2);
		c = comm.getText().toString();
		String[] mStrings = { c };

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, mStrings);
		list.setAdapter(adapter);
		b1.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				new Loade().execute();

			}
		});

		b2.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				new Loaderecherche().execute();

			}
		});
	}

	class Loade extends AsyncTask<String, String, String> {
		String result;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ListCommentaire.this);
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
					"http://10.0.2.2:8080/restProject/resources/entity.commentaire/ajouter"
							+ "/" + comm.getText().toString() + "/"
							+ restaurant.getIdrest() + "/" + Constant.Login
							+ "/" + Constant.Password);
			System.out.println("idrest" + restaurant.getIdrest());
			System.out.println("login" + Constant.Login);
			System.out.println("password" + Constant.Password);

			httpGet.setHeader("Content-Type", "application/json");
			try {
				HttpResponse response = httpClient.execute(httpGet);
				HttpEntity entity = response.getEntity();
				is = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "UTF-8"));

				StringBuilder sb = new StringBuilder();

				String line = null;

				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				is.close();
				result = sb.toString();
				System.out.println("ajout" + result);

			}

			catch (Exception e) {

				System.out.println("exp" + e.getMessage());

			}

			if (result.equals("yes")) {

				pDialog.cancel();

			}

			return null;
		}

		protected void onPostExecute(String file_url) {

			// System.out.println("Result" + result);

			pDialog.cancel();

			// pDialog.dismiss();
			// list.setAdapter(new CustomListAdapter(getApplicationContext(),
			// alluser));

			// System.out.println(usersJsonObject);
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

	class Loaderecherche extends AsyncTask<String, String, String> {
		String result;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ListCommentaire.this);
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
					"http://10.0.2.2:8080/restProject/resources/entity.commentaire/Recherche"
							+ "/" + restaurant.getIdrest());

			httpGet.setHeader("Content-Type", "application/json");
			try {
				HttpResponse response = httpClient.execute(httpGet);
				HttpEntity entity = response.getEntity();
				is = entity.getContent();
				System.out.println("test" + is);
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "UTF-8"));

				StringBuilder sb = new StringBuilder();

				String line = null;

				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				is.close();
				result = sb.toString();
				System.out.println("recherche" + result);

				JSONArray a = new JSONArray(result);
				usersJsonObject = new JSONObject();
				usersJsonObject.put("data", a);
				data = usersJsonObject.getJSONArray("data");
				for (int t = 0; t < data.length(); t++) {

					Commentaire List = new Commentaire();
					JSONObject JObject = data.getJSONObject(t);
					List.setCom(JObject.getString("commentaire"));

					alluser.add(List);

				}
			}

			catch (Exception e) {

				System.out.println("exp" + e.getMessage());

			}

			if (result.equals("yes")) {

				pDialog.cancel();

			}

			return null;
		}

		protected void onPostExecute(String file_url) {

			// System.out.println("Result" + result);

			pDialog.cancel();

			// pDialog.dismiss();
			list.setAdapter(new AffichAdapter(getApplicationContext(), alluser));

			// System.out.println(usersJsonObject);
		}
	}

}
