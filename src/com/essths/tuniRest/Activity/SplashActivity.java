package com.essths.tuniRest.Activity;

import com.carouseldemo.main.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SplashActivity extends Activity implements AnimationListener {

	 private static final int STOPSPLASH = 0;
	 Animation animBlink,animrotate;

	    /**
'	     * Durée de Splash Screen en millisecond
	     */
	 private static final long SPLASHTIME = 6000;

	    /**
	     * Handler pour fermer  cette  activité et commencer automatiquement  automatically {MainActivity]
	    
	     */
	 private final transient Handler splashHandler = new Handler()
	    {
	        @Override
	 public void handleMessage(Message msg)
	        {
	 if (msg.what == STOPSPLASH)
	            {
		 /** le intent qui lance le MainAcitviy aprés 6 seconde*/
	 final Intent intent = new Intent(SplashActivity.this, MainActivity.class);
	                startActivity(intent);
	                finish();
	            }

	 super.handleMessage(msg);
	        }
	    };
    /**Appel de l'activite lorsque elle est créer */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** // Pour cacher la barre de statut et donc mettre votre application en plein écran*/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
	     getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /** Commencer l'animation de translate*/
	     StartAnimations();
    }
    /** Dans cette methode on va commencer a construire l'animation de translate avec un transition fading*/
    private void StartAnimations() {
    	super.onStart();
        Context appContext = getApplicationContext();
        /** commencer a lire le ficher audio intitulé  sonsplash qui se trouve dans le dossier drawable de projet */
        MediaPlayer mp = MediaPlayer.create(appContext , R.drawable.sonsplash);
        mp.start();
        animBlink = AnimationUtils.loadAnimation(this, R.anim.alpha);
        animBlink.reset();
        LinearLayout l=(LinearLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(animBlink);
        animBlink = AnimationUtils.loadAnimation(this, R.anim.translate);
        animBlink.reset();
        ImageView iv = (ImageView) findViewById(R.id.logo);
        iv.setVisibility(View.INVISIBLE);
        ImageView rot = (ImageView) findViewById(R.id.rotation);
       //rot.setVisibility(View.INVISIBLE);
        iv.clearAnimation();
        iv.startAnimation(animBlink);
        final Message msg = new Message();
        msg.what = STOPSPLASH;
        splashHandler.sendMessageDelayed(msg, SPLASHTIME);
        animrotate = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.rotate);
        TextView  txtMessage = (TextView) findViewById(R.id.id_home);
		// set animation listener
		animBlink.setAnimationListener(this);
		rot.setVisibility(View.INVISIBLE);
		txtMessage.startAnimation(animBlink);
		// start the animation
		rot.startAnimation(animrotate);
        
    }
    /** Quiiter l'application on clickant sur la touche e retour */
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			super.finish();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
   
    public void onAnimationEnd(Animation animation) {
		// Take any action after completing the animation

		// check for blink animation
		if (animation == animBlink) {
		}

	}

	
	public void onAnimationRepeat(Animation animation) {

	}

	
	public void onAnimationStart(Animation animation) {

	}

}
/**Tous Droits réservés AndroidMag.ma © 2013*/