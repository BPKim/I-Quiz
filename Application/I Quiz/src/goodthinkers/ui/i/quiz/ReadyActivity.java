package goodthinkers.ui.i.quiz;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ReadyActivity extends Activity {
	Handler mHandler = new Handler();
	/** Called when the activity is first created. */

	ImageView readyy, goo;
	static SoundManager sManager;
	int n, m;

	static MediaPlayer clock;

	@Override
	public void onCreate(Bundle savedBundle) {
		super.onCreate(savedBundle);
		// setVolumeControlStream(AudioManager.STREAM_MUSIC);
		
		setContentView(R.layout.activity_ready);
		   if (SetActivity.j==0){
			   MainActivity.sManager.play(9);
		   }
		
		readyy = (ImageView) findViewById(R.id.ready);
		goo = (ImageView) findViewById(R.id.go);

		readyy.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View arg0, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_DOWN) {

				}
				return true;
			}
		});

		goo.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View arg0, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_DOWN) {

				}
				return true;
			}
		});

		goo.setVisibility(-1);
		readyy.setBackgroundResource(R.drawable.ready2);
		readyy.setVisibility(1);
		
		mHandler.postDelayed(new Runnable() {
			public void run() {
				
										readyy.setVisibility(-1);
										goo.setBackgroundResource(R.drawable.go1);
										goo.setVisibility(1);
										MainActivity.sManager.stopSound(9);
										ReadyActivity.this.finish();
										Intent intent = new Intent(	ReadyActivity.this,
																	QuizActivity.class);
																startActivity(intent);
																
																// 2초 지나고 없어짐
															}
							}, 1800);
												// 2초 지나고 없어짐
									

					

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {



		return false;

	}


}
