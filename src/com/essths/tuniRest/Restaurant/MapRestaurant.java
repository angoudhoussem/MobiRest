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

import android.R.string;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import com.carouseldemo.main.R;
import com.essths.tuniRest.Activity.Compte;
import com.essths.tuniRest.CustomAdapter.Restaurant;
import com.essths.tuniRest.Restaurant.Constant;
import com.essths.tuniRest.maps.Position;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapRestaurant extends FragmentActivity {
	private static LatLng position, ESSTHS;
	String result;
	JSONObject usersJsonObject;
	ArrayList<Position> alluser = new ArrayList<Position>();
	ProgressDialog pDialog;
	ArrayList<Marker> markerList;
	Marker marker, myplace;
	MarkerOptions marker1;
	GoogleMap map;
	InputStream is = null;
	Restaurant restaurant = Constant.r;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_restaurant);

		map = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		map.setTrafficEnabled(true);

		markerList = new ArrayList<Marker>();

		new Loade().execute();

	}

	class Loade extends AsyncTask<String, String, String> {
		String result;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(MapRestaurant.this);
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
					"http://10.0.2.2:8080/restProject/resources/entity.position/Recher"
							+ "/" + restaurant.getIdrest());

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
				System.out.println("linaaa" + result);
				usersJsonObject = new JSONObject(result);
				Position List = new Position();
				List.setAdresse(usersJsonObject.getString("adresse"));
				List.setLaltitude(usersJsonObject.getString("latitude"));
				List.setLongitude(usersJsonObject.getString("longitude"));
				alluser.add(List);
			}

			catch (Exception e) {

				e.getMessage();

			}
			if (result.equals("yes")) {

				pDialog.cancel();

			}

			return result;
		}

		protected void onPostExecute(String file_url) {

			for (int i = 0; i < alluser.size(); i++) {
				// String snppt = null;
				// String adress = restaurant.getAdresse();
				// String info = restaurant.getInfo().toString();
				// snppt = adress + " \n " + info;

				// System.out.println("snppt" + snppt);

				position = new LatLng(Double.parseDouble(alluser.get(i)
						.getLaltitude()), Double.parseDouble(alluser.get(i)
						.getLongitude()));
				marker = map.addMarker(new MarkerOptions().position(position)
						// .title(alluser.get(i).getAdresse())
						.title(restaurant.getNom_rest())
						.snippet(alluser.get(i).getAdresse())
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.restaurantmap)));

				map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 35));
				map.animateCamera(CameraUpdateFactory.zoomTo(17), 2000, null);
				markerList.add(marker);

			}
			// // latitude and longitude
			// double latitude = 35.850212;
			// double longitude = 10.597382;

			ESSTHS = new LatLng(35.850212, 10.597382);
			myplace = map
					.addMarker(new MarkerOptions()
							.position(ESSTHS)
							.title("ESSTHS")
							.snippet(
									"Ecole Superieur des Sciences et Technologie de Hammam Sousse")
							.icon(BitmapDescriptorFactory
									.fromResource(R.drawable.essths)));
			myplace.showInfoWindow();

			// map.moveCamera(CameraUpdateFactory.newLatLngZoom(ESSTHS, 35));
			// map.animateCamera(CameraUpdateFactory.zoomTo(17), 2000, null);

			System.out.println("Result" + result);

			pDialog.cancel();
		}
	}

}
