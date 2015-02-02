package com.essths.tuniRest.profil;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.carouseldemo.main.R;

public class MessageView extends Activity {
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.affichamie);
		
		TextView tv = (TextView) findViewById(R.id.id_msgrecue);
		Bundle data = getIntent().getExtras();
		tv.setText(data.getString("number"));		
	}
}
