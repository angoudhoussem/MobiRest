package com.essths.tuniRest.maps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.app.TabActivity;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.carouseldemo.main.R;
import com.essths.tuniRest.CustomAdapter.Restaurant;
import com.essths.tuniRest.Restaurant.Constant;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class Maps extends FragmentActivity {
	private static LatLng position, ESSTHS, ISITCom, latlng;
	String result;
	String obj;
	JSONObject usersJsonObject;
	JSONArray data;
	ArrayList<Restaurant> alluser1 = new ArrayList<Restaurant>();
	ArrayList<Position> alluser = new ArrayList<Position>();
	ProgressDialog pDialog;
	ArrayList<Marker> markerList;
	ArrayList<LatLng> markerPoints;
	Marker marker, myplace;
	MarkerOptions marker1;
	GoogleMap map;
	InputStream is = null;
	int n;
	LatLng tab[];

	// Restaurant restaurant = Constant.r;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		new Loade1().execute();
		new Loade2().execute();
		markerPoints = new ArrayList<LatLng>();
		map = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		map.setTrafficEnabled(true);

		ESSTHS = new LatLng(35.850212, 10.597382);
		myplace = map.addMarker(new MarkerOptions().position(ESSTHS)
				.title("ESSTHS")
				.snippet("Ecole Superieur des Sciences et Technologie")
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.essths)));
		myplace.showInfoWindow();
		markerPoints.add(ESSTHS);

		tab = new LatLng[13];

		// map.setMyLocationEnabled(true);
		// LocationManager locationmanager = (LocationManager)
		// getSystemService(LOCATION_SERVICE);
		// Criteria criteria = new Criteria();
		// String provider = locationmanager.getBestProvider(criteria, true);
		// Location mylocation = locationmanager.getLastKnownLocation(provider);
		// locationmanager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
		// 0, 0, (LocationListener) this);
		// double latitude = mylocation.getLatitude();
		// double longitude = mylocation.getLongitude();
		// final LatLng latlng = new LatLng(latitude, longitude);
		// map.moveCamera(CameraUpdateFactory.newLatLng(latlng));
		// map.animateCamera(CameraUpdateFactory.zoomTo(20));
		// map.addMarker(new MarkerOptions().position(
		// new LatLng(latitude,
		// longitude)).title("you are here!").icon(BitmapDescriptorFactory.fromResource(R.drawable.essths)));
		//

		if (map != null) {

			// Enable MyLocation Button in the Map
			 map.setMyLocationEnabled(true);

			// Setting onclick event listener for the map
			map.setOnMapClickListener(new OnMapClickListener() {

				public void onMapClick(LatLng point) {

					/**
					 * For the start location, the color of marker is GREEN and
					 * for the end location, the color of marker is RED.
					 */

					LatLng origin = ESSTHS;
					for (int i = 0; i < tab.length; i++) {

						LatLng dest = tab[i];

						// Getting URL to the Google Directions API
						String url = getDirectionsUrl(origin, dest);

						System.out.println("url" + url);

						DownloadTask downloadTask = new DownloadTask();

						// Start downloading json data from Google Directions
						// API
						downloadTask.execute(url);
					}

				}
			});
		}

	}

	private String getDirectionsUrl(LatLng origin, LatLng dest) {

		// Origin of route
		String str_origin = "origin=" + origin.latitude + ","
				+ origin.longitude;

		// Destination of route
		String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

		// Sensor enabled
		String sensor = "sensor=false";

		// Building the parameters to the web service
		String parameters = str_origin + "&" + str_dest + "&" + sensor;

		// Output format
		String output = "json";

		// Building the url to the web service
		String url = "https://maps.googleapis.com/maps/api/directions/"
				+ output + "?" + parameters;

		return url;
	}

	/** A method to download json data from url */
	private String downloadUrl(String strUrl) throws IOException {
		String data = "";
		InputStream iStream = null;
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(strUrl);

			// Creating an http connection to communicate with url
			urlConnection = (HttpURLConnection) url.openConnection();

			// Connecting to url
			urlConnection.connect();

			// Reading data from url
			iStream = urlConnection.getInputStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					iStream));

			StringBuffer sb = new StringBuffer();

			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			data = sb.toString();

			br.close();

		} catch (Exception e) {
			Log.d("Exception while downloading url", e.toString());
		} finally {
			iStream.close();
			urlConnection.disconnect();
		}
		return data;
	}

	// Fetches data from url passed
	private class DownloadTask extends AsyncTask<String, Void, String> {

		// Downloading data in non-ui thread
		@Override
		protected String doInBackground(String... url) {

			// For storing data from web service
			String data = "";

			try {
				// Fetching the data from web service
				data = downloadUrl(url[0]);
			} catch (Exception e) {
				Log.d("Background Task", e.toString());
			}
			return data;
		}

		// Executes in UI thread, after the execution of
		// doInBackground()
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			ParserTask parserTask = new ParserTask();

			// Invokes the thread for parsing the JSON data
			parserTask.execute(result);

		}

	}

	/** A class to parse the Google Places in JSON format */
	private class ParserTask extends
			AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

		private ProgressDialog progressDialog;

		// Parsing the data in non-ui thread
		@Override
		protected List<List<HashMap<String, String>>> doInBackground(
				String... jsonData) {

			JSONObject jObject;
			List<List<HashMap<String, String>>> routes = null;

			try {
				jObject = new JSONObject(jsonData[0]);
				DirectionsJSONParser parser = new DirectionsJSONParser();

				// Starts parsing data
				routes = parser.parse(jObject);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return routes;
		}

		// Executes in UI thread, after the parsing process
		@Override
		protected void onPostExecute(List<List<HashMap<String, String>>> result) {
			ArrayList<LatLng> points = null;
			PolylineOptions lineOptions = null;
			MarkerOptions markerOptions = new MarkerOptions();

			// Traversing through all the routes
			for (int i = 0; i < result.size(); i++) {
				points = new ArrayList<LatLng>();
				lineOptions = new PolylineOptions();

				// Fetching i-th route
				List<HashMap<String, String>> path = result.get(i);

				// Fetching all the points in i-th route
				for (int j = 0; j < path.size(); j++) {
					HashMap<String, String> point = path.get(j);

					double lat = Double.parseDouble(point.get("lat"));
					double lng = Double.parseDouble(point.get("lng"));
					LatLng position = new LatLng(lat, lng);

					points.add(position);
				}

				// Adding all the points in the route to LineOptions
				lineOptions.addAll(points);
				lineOptions.width(2);
				lineOptions.color(Color.RED);

			}

			// Drawing polyline in the Google Map for the i-th route
			map.addPolyline(lineOptions);
		}

	}

	@Override
	protected void onPause() {
		finish();
		super.onPause();
	}

	/*********************** Position ********************************/
	class Loade1 extends AsyncTask<String, String, String> {
		String result;

		@Override
		protected String doInBackground(String... arg0) {

			HttpClient httpClient = new DefaultHttpClient();

			final HttpParams httpParams = httpClient.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 100000);
			HttpConnectionParams.setSoTimeout(httpParams, 100000);

			HttpGet httpGet = new HttpGet(
					"http://10.0.2.2:8080/restProject/resources/entity.position/Recherche");

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

				JSONArray a = new JSONArray(result);
				usersJsonObject = new JSONObject();
				usersJsonObject.put("data", a);
				data = usersJsonObject.getJSONArray("data");
				for (int t = 0; t < data.length(); t++) {

					Position List = new Position();

					JSONObject JObject = data.getJSONObject(t);
					List.setLaltitude(JObject.getString("latitude"));
					List.setLongitude(JObject.getString("longitude"));

					alluser.add(List);
				}
				System.out.println("othmen" + alluser);
			}

			catch (Exception e) {

				e.getMessage();

			}
			if (result.equals("yes")) {

				pDialog.cancel();

			}

			return result;
		}

	}

	/**************************** Restaurant **********************************/
	class Loade2 extends AsyncTask<String, String, String> {
		String result;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Maps.this);
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
			// + "/" + restaurant.getIdrest());

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

				JSONArray a = new JSONArray(result);
				usersJsonObject = new JSONObject();
				usersJsonObject.put("data", a);
				data = usersJsonObject.getJSONArray("data");
				for (int t = 0; t < data.length(); t++) {

					Restaurant List = new Restaurant();

					JSONObject JObject1 = data.getJSONObject(t);
					List.setNom_rest(JObject1.getString("nomRest"));
					List.setAdresse(JObject1.getString("adresse"));

					alluser1.add(List);
				}
				System.out.println("naziih" + alluser1);
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
			for (int i = 0; i < tab.length; i++) {
				tab[i] = new LatLng(Double.parseDouble(alluser.get(i)
						.getLaltitude()), Double.parseDouble(alluser.get(i)
						.getLongitude()));
			}
			System.out.println("taille" + tab.length);

			for (int i = 0; i < alluser.size(); i++) {

				position = new LatLng(Double.parseDouble(alluser.get(i)
						.getLaltitude()), Double.parseDouble(alluser.get(i)
						.getLongitude()));

				marker = map.addMarker(new MarkerOptions()
						.position(position)
						.title(alluser1.get(i).getNom_rest())
						.snippet(alluser1.get(i).getAdresse())
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.icon)));
				map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 35));
				map.animateCamera(CameraUpdateFactory.zoomTo(12), 2000, null);
				// markerList.add(marker);

			}

			System.out.println("Result" + result);

			pDialog.cancel();
		}
	}

}
