package com.essths.tuniRest.profil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.carouseldemo.main.R;
import com.essths.tuniRest.Restaurant.Constant;

public class listamie extends Activity {
	ImageView img_msg, img_ajout;
	TextView text1, text2,text3;
	ImageView img_profil;

	Profil profil = Constant.pr;

	int count = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.affichamie);
		text1 = (TextView) findViewById(R.id.id_nom);
		text2 = (TextView) findViewById(R.id.id_prenom);
		text3 = (TextView) findViewById(R.id.id_mail);
		img_profil = (ImageView) findViewById(R.id.imageView1);
		img_msg = (ImageView) findViewById(R.id.id_message);
		img_ajout = (ImageView) findViewById(R.id.id_ajouter);

		img_profil.setImageBitmap(ConvertImageFromStringToBitmap.convert(profil
				.getImage()));
		text1.setText(profil.getNom());
		text2.setText(profil.getPrenom());
		text3.setText(profil.getEmail());
		img_msg.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				Intent intent = new Intent(getApplicationContext(),
						MainMessageActivity.class);
				startActivity(intent);
			}
		});
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			startActivity(new Intent(getApplicationContext(), MainProfil.class));

		}

		return super.onKeyDown(keyCode, event);
	}

}
