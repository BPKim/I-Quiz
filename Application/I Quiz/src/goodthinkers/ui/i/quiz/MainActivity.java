
package goodthinkers.ui.i.quiz;

import java.io.IOException;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;



public class MainActivity extends Activity implements OnClickListener {
	Button startButton;
	Button rankButton;
	Button setButton;
	Button helpButton;
	Button madebyButton;
	Paint paint = new Paint();
	static int button_click;
	static int click_a_on;
	static int click_a_off;
	static int click_b_on;
	static int click_b_off;
	SurfaceView sur;
	static int i;
	static MediaPlayer myPlayer ;
	static MediaPlayer quiz_sound ;
	static SoundManager sManager;
	public static int visibleCnt=0;
	View logo,main;
	static int count=1;
	Builder exit;
	//ClientThread ct;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//MainView.canRun = true;
		setContentView(R.layout.activity_main);
		
		MainView.canRun=true;
		
		if(GlobalVar.connectFlag==1 ){
			ClientThread.ConnectExit();
			GlobalVar.connectFlag=0;
		}

		
		Log.i("DEBUG", "Heap maxMemory Size = " + Runtime.getRuntime().maxMemory() / (1024*1024) + " MB"); 
		logo= (View)findViewById(R.id.logoLayout);
		logo.setOnTouchListener(new OnTouchListener(){
			
			public boolean onTouch(View v, MotionEvent event)  {
			    if(event.getAction()==MotionEvent.ACTION_DOWN){
			     
			    }
			    return true;
			   }
			   
			  });
		
		if(visibleCnt==0){
			logo.setVisibility(View.VISIBLE);

			logo.postDelayed(new Runnable() {

				public void run() {
					MainActivity.visibleCnt=1;
					logo.setVisibility(View.INVISIBLE);
					//setReal.setVisibility(View.VISIBLE);
				}
			}, 2500); //3초딜레이후 실행됨 
			
		}

		startButton = (Button) findViewById(R.id.play);
		rankButton = (Button) findViewById(R.id.rank);
		setButton = (Button) findViewById(R.id.set);
		helpButton = (Button) findViewById(R.id.help);
		madebyButton = (Button) findViewById(R.id.madeby);

		startButton.setOnClickListener(this);
		rankButton.setOnClickListener(this);
		setButton.setOnClickListener(this);
		helpButton.setOnClickListener(this);
		madebyButton.setOnClickListener(this);
		GlobalVar.life=3;
		GlobalVar.final_score=0;
		GlobalVar.passlife=2;
		
//		ct = new ClientThread("10.80.75.110");
//		ct.start();

	    quiz_sound = MediaPlayer.create(this, R.raw.quizsound); // 파일 읽기
	    i++;
	  
	   
	    // quiz_sound.setLooping(true);
	      if( i==1){
		    	 SetActivity.settings = getSharedPreferences("set_sound",Activity.MODE_PRIVATE);
		 	    int res= SetActivity.settings.getInt("sound_onoff", 0);
		 	   SetActivity.sound_onoff=res;
		 	   int res0=SetActivity.settings.getInt("j", 0);
		 	  SetActivity.j=res0;
		 	
		 	
		 	    
		    	 myPlayer = MediaPlayer.create(this, R.raw.backsound);
		    	 myPlayer.setLooping(true); // 반복 연주
		    	
		    	
		     }
	     
	     if ( i==1 && SetActivity.sound_onoff==0){
		    	
	  	 myPlayer.start(); // 재생 시작(일시 정지된 부분에서 시작)
	     }
	     if(sManager ==null){
	     	sManager = SoundManager.getInstance();  //필요한 객체를 전달해서 사용할 수 있게 초기화 한다.
			sManager.init(this);  //사운드 등록하기(Thread와 같이 로딩작업을 시작하고 아래 작업으로 넘어간다)
	        
	        sManager.addSound(0, R.raw.ok);
			sManager.addSound(2, R.raw.click);
			sManager.addSound(3, R.raw.clock);
			sManager.addSound(4, R.raw.ending);
	        sManager.addSound(5, R.raw.no);
	        sManager.addSound(6, R.raw.haha);
	        sManager.addSound(7, R.raw.xxx);
	        sManager.addSound(8, R.raw.gameover);
	        sManager.addSound(9, R.raw.readygo);
	        }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void onClick(View v) {

		if (v == startButton) {
		  
			if (  SetActivity.sound_onoff==0){
			    	 myPlayer.stop();
				     quiz_sound.start(); // 재생 시작(일시 정지된 부분에서 시작)
				   }
			   if (SetActivity.j==0){
				   sManager.play(2);
			   }
			   button_click=1;
			//MainView.canRun = false;
			   MainView.canRun=false;
			Intent intent = new Intent(MainActivity.this, ReadyActivity.class);
			startActivity(intent);
		} else if (v == rankButton) {
			if (SetActivity.j==0){
				   sManager.play(2);
			   }
			 button_click=1;
			//MainView.canRun = false;
			//ct.interrupt();
			 MainView.canRun=false;
			 Intent intent = new Intent(MainActivity.this, RankActivity.class);
			startActivity(intent);
		} else if (v == setButton) {
			if (SetActivity.j==0){
				   sManager.play(2);
			 }
			 button_click=1;
			//MainView.canRun = false;
			 MainView.canRun=false;
			 Intent intent = new Intent(MainActivity.this, SetActivity.class);
			startActivity(intent);
		} else if (v == helpButton) {
			if (SetActivity.j==0){
				   sManager.play(2);
			   }
			 button_click=1;
			//MainView.canRun = false;
			 MainView.canRun=false;
			 Intent intent = new Intent(MainActivity.this, HelpActivity.class);
			startActivity(intent);
		} else if (v == madebyButton) {
			if (SetActivity.j==0){
			   sManager.play(2);
			   }
			 button_click=1;
			//MainView.canRun = false;
			 MainView.canRun=false;
			 Intent intent = new Intent(MainActivity.this, MadeByActivity.class);
			
			startActivity(intent);
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		
		AudioManager mAudioManager = 
	              (AudioManager)getSystemService(AUDIO_SERVICE);
	          switch (keyCode) {
	          case KeyEvent.KEYCODE_VOLUME_UP :
	              mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
	                                               AudioManager.ADJUST_RAISE, 
	                                               AudioManager.FLAG_SHOW_UI);
	                  return true;
	          case KeyEvent.KEYCODE_VOLUME_DOWN:
	              mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, 
	                                               AudioManager.ADJUST_LOWER, 
	                                               AudioManager.FLAG_SHOW_UI);
	              return true;
	          case KeyEvent.KEYCODE_BACK: 
	        	  exit = new AlertDialog.Builder(this);
	        	  exit.setIcon(R.drawable.ic_launcher);
	        	  exit.setTitle(R.string.app_name);
	        	  exit.setMessage("IQuiz를 종료하시겠습니까?");
	        	  exit.setPositiveButton("종료", new DialogInterface.OnClickListener() {
				
	        		  public void onClick(DialogInterface dialog, int which) {
	        			  Intent intent = new Intent(MainActivity.this,
	        			  MainActivity.class);
				          startActivity(intent);
					      moveTaskToBack(true);
					    Log.d("TAG", "exit1");
					//ClientThread.ConnectExit();
					//ct.stop();
					Log.d("TAG", "exit2");
					
					finish();

					android.os.Process.sendSignal(android.os.Process.myPid(),
							android.os.Process.SIGNAL_KILL);

				}
			});
			exit.setNegativeButton("취소", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {

				}
			});
			
			exit.setCancelable(true);
			exit.setOnCancelListener(new OnCancelListener(){
				
				public void onCancel(DialogInterface dialog){
					
				}
			});
					
		
			exit.show();
		

		return true;
	          }
	          return true;
	}
	
//	public void onResume(){
	/*	super.onResume();
		MainView.canRun=true;
		MainActivity.myPlayer.start();*/
	//}
	public void onPause(){
		super.onPause();
		//MainView.canRun=false;
	
		
	}
	public void onRestart(){
		super.onRestart();
	//	MainView.canRun=false;
	
	}
	
	public void onStop(){
		super.onStop();
	//	MainView.canRun=false;
		
  	  if(button_click!=1){	 
  	//	MainView.canRun=false;
    	moveTaskToBack(true);  
    	finish(); 	 
    	android.os.Process.sendSignal(android.os.Process.myPid(), android.os.Process.SIGNAL_KILL);


	  }
  

	}

}

