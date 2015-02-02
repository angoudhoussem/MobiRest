package com.essths.tuniRest.carouseldemo.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.carouseldemo.main.R;
import com.essths.tuniRest.Favoris.MainReservation;
import com.essths.tuniRest.Restaurant.recherche;
import com.essths.tuniRest.carouseldemo.controls.Carousel;
import com.essths.tuniRest.carouseldemo.controls.CarouselAdapter;
import com.essths.tuniRest.carouseldemo.controls.CarouselItem;
import com.essths.tuniRest.carouseldemo.controls.CarouselAdapter.OnItemClickListener;
import com.essths.tuniRest.maps.Maps;
import com.essths.tuniRest.notificationtuniresto.MainNotificationActivity;
import com.essths.tuniRest.profil.MainProfil;

public class MainActivityCarousel extends Activity {
	/** Called when the activity is first created. */
	Carousel carousel;
//	TextView deconnect;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		carousel= (Carousel) findViewById(R.id.carousel);
//		deconnect = (TextView) findViewById(R.id.id_deconnexion);
//		deconnect.setOnClickListener(new OnClickListener() {
//			
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent(getApplicationContext(),
//						MainActivity.class);
//				startActivity(intent);
//			}
//		});
		carousel.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(CarouselAdapter<?> parent, View view,
					int position, long id) {

				switch (position) {
				case 0:
					// **************** Profil *******************
					Toast.makeText(
							MainActivityCarousel.this,
							String.format(
									"%s est selectionne",
									((CarouselItem) parent.getChildAt(position))
											.getName()), Toast.LENGTH_SHORT)
							.show();
					Intent intent0 = new Intent(getApplicationContext(),
							MainProfil.class);
					startActivity(intent0);
					break;

				case 1:
					// *************** Notification ***************
					Toast.makeText(
							MainActivityCarousel.this,
							String.format(
									"%s est selectionne",
									((CarouselItem) parent.getChildAt(position))
											.getName()), Toast.LENGTH_SHORT)
							.show();
					Intent intent1 = new Intent(getApplicationContext(),
							MainNotificationActivity.class);
					startActivity(intent1);
					break;
				case 2:
					// ***************** Maps ********************
					Toast.makeText(
							MainActivityCarousel.this,
							String.format(
									"%s est selectionnee",
									((CarouselItem) parent.getChildAt(position))
											.getName()), Toast.LENGTH_SHORT)
							.show();
					Intent intent2 = new Intent(getApplicationContext(),
							Maps.class);
					startActivity(intent2);
					break;
				case 3:
					// ******************* Recherche ****************
					Toast.makeText(
							MainActivityCarousel.this,
							String.format(
									"%s est selectionne",
									((CarouselItem) parent.getChildAt(position))
											.getName()), Toast.LENGTH_SHORT)
							.show();
					Intent intent3 = new Intent(getApplicationContext(),
							recherche.class);
					startActivity(intent3);
					break;

				case 4:
					// ******************* Favoris ****************
					Toast.makeText(
							MainActivityCarousel.this,
							String.format(
									"%s est selectionne",
									((CarouselItem) parent.getChildAt(position))
											.getName()), Toast.LENGTH_SHORT)
							.show();
					Intent intent4 = new Intent(getApplicationContext(),
							MainReservation.class);
					startActivity(intent4);
					break;
				}
			}
		});

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
