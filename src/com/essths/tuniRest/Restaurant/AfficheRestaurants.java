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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.carouseldemo.main.R;
import com.essths.tuniRest.CustomAdapter.ConvertImageFromStringToBitmap;
import com.essths.tuniRest.CustomAdapter.Restaurant;
import com.essths.tuniRest.Restaurant.VisiteVirtuelle.MainVisiteVirtuelle;

public class AfficheRestaurants extends Activity {

	Intent i = getIntent();
	String result;
	String obj;
	JSONObject restaurantsJsonObject;
	JSONArray data;
	ArrayList<Restaurant> alluser = new ArrayList<Restaurant>();
	ProgressDialog pDialog;
	ListView list;
	TextView text1, text2, text3,text, text4;
	ImageView img_rest, img_like, img_comment, img_share;
	Button map, reservation;
	Restaurant restaurant = Constant.r;

	int count = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profil_restaurant);
		text1 = (TextView) findViewById(R.id.id_nom1);
		text2 = (TextView) findViewById(R.id.id_adresse1);
		text3 = (TextView) findViewById(R.id.id_info1);
		text=(TextView) findViewById(R.id.id_typecuisine);
		text4 = (TextView) findViewById(R.id.id_photos);
		img_rest = (ImageView) findViewById(R.id.id_image1);
		img_like = (ImageView) findViewById(R.id.id_img_like1);
		img_comment = (ImageView) findViewById(R.id.id_img_comment1);
		img_share = (ImageView) findViewById(R.id.id_img_share1);
		map = (Button) findViewById(R.id.id_map1);
		reservation = (Button) findViewById(R.id.id_reservation1);

		img_rest.setImageBitmap(ConvertImageFromStringToBitmap
				.convert(restaurant.getImage()));
		text1.setText(restaurant.getNom_rest());
		text2.setText(restaurant.getAdresse());
		text3.setText(restaurant.getInfo());
		text.setText(restaurant.getTypecuisine());
		text4.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),
						MainVisiteVirtuelle.class);
				startActivity(intent);

			}
		});
		reservation.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),
						Resservation.class);

				startActivity(intent);
			}
		});
		map.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
//				Restaurant t=(Restaurant)list.getItemAtPosition(position);
//				Constant.r=t;
				Intent intent = new Intent(getApplicationContext(),
						MapRestaurant.class);
                 //intent.putExtra();
				startActivity(intent);
			}
		});
		img_comment.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(getApplicationContext(),
						ListCommentaire.class);

				startActivity(intent);

			}
		});
		img_like.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				count++;
				new Loade().execute();

			}
		});

	}

	class Loade extends AsyncTask<String, String, String> {
		String result;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(AfficheRestaurants.this);
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
					"http://10.0.2.2:8080/restProject/resources/entity.listejaime/compteur"
							+ "/" + restaurant.getIdrest() + "/" + count);
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

				pDialog.cancel();

				Toast.makeText(AfficheRestaurants.this, "yes",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(AfficheRestaurants.this, "erreur",
						Toast.LENGTH_SHORT).show();
			}

		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			startActivity(new Intent(getApplicationContext(), recherche.class));

		}

		return super.onKeyDown(keyCode, event);
	}
}