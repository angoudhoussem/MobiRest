package com.essths.tuniRest.Activity;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import com.carouseldemo.main.R;
import com.essths.tuniRest.Restaurant.Constant;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Compte extends Activity {
	EditText nom, prenom, numtele, email;
	EditText login;
	EditText password;
	String Nom, Prenom, Numtele, Email;
	public String Login;
	public String Password;
	ImageView img;
	TextView retour;
	Button b1;
	String result = null;
	InputStream is;
	ProgressDialog pDialog;
	public static String ACTUAL_USER = null;
	public static String ID_USER;
	public static String ACTUAL_PASSWORD = null;
	public static Uri ACTUAL_IMAGE_PATH = null;
	private static int RESULT_LOAD_IMAGE = 1;
	String picturePath;
	Uri selectedImage = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.compte);
		nom = (EditText) findViewById(R.id.id_nom);
		prenom = (EditText) findViewById(R.id.id_prenom);
		numtele = (EditText) findViewById(R.id.id_tele);
		email = (EditText) findViewById(R.id.id_email);
		login = (EditText) findViewById(R.id.id_login);
		password = (EditText) findViewById(R.id.id_password);
		img = (ImageView) findViewById(R.id.imageView1);

		retour = (TextView) findViewById(R.id.id_home);

		b1 = (Button) findViewById(R.id.id_button);

		retour.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(getApplicationContext(),
						MainActivity.class);

				startActivity(intent);

			}
		});
		img.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				startActivityForResult(i, RESULT_LOAD_IMAGE);
			}
		});
		b1.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {

				Nom = nom.getText().toString();
				Prenom = prenom.getText().toString();
				Numtele = numtele.getText().toString();
				Email = email.getText().toString();
				Login = login.getText().toString();
				Password = password.getText().toString();

				if (Nom.length() != 0 && Prenom.length() != 0
						&& Email.length() != 0 && Numtele.length() != 0
						&& Login.length() != 0 && Password.length() != 0

				) {
					new Loade().execute();
					Context context = getApplicationContext();
					CharSequence text = "vrai";
					int duration = Toast.LENGTH_SHORT;

					Toast toast = Toast.makeText(context, text, duration);
					toast.show();

				}

				else

				{

					Context context = getApplicationContext();
					CharSequence text = " vide";
					int duration = Toast.LENGTH_SHORT;

					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
				}

			}
		});

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// String msg;
		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
				&& null != data) {
			selectedImage = data.getData();

			ACTUAL_IMAGE_PATH = selectedImage;

			try {

				img.setImageBitmap(scaleImage(this, selectedImage));

			} catch (Exception e) {

			}

			System.out.println("hello" + selectedImage);
		}

	}

	public static Bitmap scaleImage(Context context, Uri photoUri)
			throws IOException {
		InputStream is = context.getContentResolver().openInputStream(photoUri);
		BitmapFactory.Options dbo = new BitmapFactory.Options();
		dbo.inJustDecodeBounds = true;
		int MAX_IMAGE_DIMENSION = 140;
		BitmapFactory.decodeStream(is, null, dbo);
		is.close();

		int rotatedWidth, rotatedHeight;
		int orientation = getOrientation(context, photoUri);

		if (orientation == 90 || orientation == 270) {
			rotatedWidth = dbo.outHeight;
			rotatedHeight = dbo.outWidth;
		} else {
			rotatedWidth = dbo.outWidth;
			rotatedHeight = dbo.outHeight;
		}

		Bitmap srcBitmap;
		is = context.getContentResolver().openInputStream(photoUri);
		if (rotatedWidth > MAX_IMAGE_DIMENSION
				|| rotatedHeight > MAX_IMAGE_DIMENSION) {
			float widthRatio = ((float) rotatedWidth)
					/ ((float) MAX_IMAGE_DIMENSION);
			float heightRatio = ((float) rotatedHeight)
					/ ((float) MAX_IMAGE_DIMENSION);
			float maxRatio = Math.max(widthRatio, heightRatio);

			// Create the bitmap from file
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = (int) maxRatio;
			srcBitmap = BitmapFactory.decodeStream(is, null, options);
		} else {
			srcBitmap = BitmapFactory.decodeStream(is);
		}
		is.close();

		if (orientation > 0) {
			Matrix matrix = new Matrix();
			matrix.postRotate(orientation);

			srcBitmap = Bitmap.createBitmap(srcBitmap, 0, 0,
					srcBitmap.getWidth(), srcBitmap.getHeight(), matrix, true);
		}

		String type = context.getContentResolver().getType(photoUri);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		if (type.equals("image/png")) {
			srcBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		} else if (type.equals("image/jpg") || type.equals("image/jpeg")) {
			srcBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		}
		byte[] bMapArray = baos.toByteArray();
		baos.close();
		return BitmapFactory.decodeByteArray(bMapArray, 0, bMapArray.length);
	}

	private String getEncodeData(Bitmap img) {
		String encodedimage1 = null;
		if (img != null) {
			try {

				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				img.compress(Bitmap.CompressFormat.JPEG, 10, baos); // bm is the
																	// bitmap
																	// object
				byte[] b = baos.toByteArray();
				encodedimage1 = Base64.encodeToString(b, Base64.DEFAULT);
			} catch (Exception e) {
				System.out
						.println("Exception: In getEncodeData" + e.toString());
			}
		}
		return encodedimage1;
	}

	public static int getOrientation(Context context, Uri photoUri) {

		Cursor cursor = context.getContentResolver().query(photoUri,
				new String[] { MediaStore.Images.ImageColumns.ORIENTATION },
				null, null, null);

		if (cursor.getCount() != 1) {
			return -1;
		}

		cursor.moveToFirst();
		return cursor.getInt(0);
	}

	class Loade extends AsyncTask<String, String, String> {
		String result;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Compte.this);
			pDialog.setMessage(Html.fromHtml("<b>Chargement....</b><br/>"));
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... arg0) {

			String codeImage = null;
			try {
				codeImage = getEncodeData(scaleImage(getApplicationContext(),
						selectedImage));
				codeImage = URLEncoder.encode(codeImage, "UTF-8");
				System.out.println("img" + codeImage);
			} catch (Exception e) {

			}

			HttpClient httpClient = new DefaultHttpClient();

			final HttpParams httpParams = httpClient.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 100000);
			HttpConnectionParams.setSoTimeout(httpParams, 100000);

			HttpGet httpGet = new HttpGet(
					"http://10.0.2.2:8080/restProject/resources/entity.profil/ajout"
							+ "/" + nom.getText().toString() + "/"
							+ prenom.getText().toString() + "/"
							+ numtele.getText().toString() + "/"
							+ email.getText().toString() + "/"
							+ login.getText().toString() + "/"
							+ password.getText().toString() + "/" + codeImage);

			httpGet.setHeader("Content-Type", "text/plain");
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

				System.out.println("test" + result);
			}

			catch (Exception e) {

				System.out.println("erreur" + e.getMessage());

			}
			if (result.equals("yes")) {

				pDialog.cancel();

			}

			return result;
		}

		protected void onPostExecute(String file_url) {

			final Intent intent = new Intent(Compte.this, MainActivity.class);

			startActivity(intent);
			finish();
			System.out.println("Resulta" + result);

			Toast.makeText(getApplicationContext(),
					"inscription réussi " + result, Toast.LENGTH_LONG).show();

			pDialog.cancel();
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			startActivity(new Intent(getApplicationContext(),
					MainActivity.class));

		}

		return super.onKeyDown(keyCode, event);
	}

}
