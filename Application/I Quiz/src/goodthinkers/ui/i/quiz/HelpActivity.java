package goodthinkers.ui.i.quiz;


import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.MotionEvent;

public class HelpActivity extends Activity {
	View hel1, hel2, hel3;
	int ctr=0;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setVolumeControlStream(AudioManager.STREAM_MUSIC);
	    //MainView.canRun = false;
	    

	    setContentView(R.layout.activity_help);
	      // TODO Auto-generated method stub
	    hel1= (View)findViewById(R.id.help11);
	    hel2= (View)findViewById(R.id.help22);
	    hel3= (View)findViewById(R.id.help33);
	    hel1.setOnClickListener(new View.OnClickListener(){
	    	public void onClick(View v){
	        	hel2.setVisibility(1);
	        	hel1.setVisibility(-1);
	        	hel3.setVisibility(-1);
	    	}
	    });
	    hel2.setOnClickListener(new View.OnClickListener(){
	    	public void onClick(View v){
	        	hel2.setVisibility(-1);
	        	hel1.setVisibility(-1);
	        	hel3.setVisibility(1);
	    	}
	    });
	    hel3.setOnClickListener(new View.OnClickListener(){
	    	public void onClick(View v){
	    		MainActivity.button_click=0;
	    		
	        	Intent intent = new Intent(HelpActivity.this, MainActivity.class);
				startActivity(intent);
				HelpActivity.this.finish();
	    	}
	    });

	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		if (keyCode == KeyEvent.KEYCODE_BACK){
			 MainActivity.button_click=0;
		//RankView.canRun = false;
		HelpActivity.this.finish();
		
		Intent intent = new Intent(HelpActivity.this, MainActivity.class);
							startActivity(intent);
		
		}
		return true;
	 }
	public void onPause(){
		super.onPause();
		//MainView.canRun=false;
	
		
	}
	public void onRestart(){
		super.onRestart();
		//MainView.canRun=false;
	
	}
	
	public void onStop(){
		super.onStop();
	//	MainView.canRun=false;
		
  	  if(MainActivity.button_click!=0){
  		 
  		//MainView.canRun=false;
    		moveTaskToBack(true); 
        	finish();         	 
        	android.os.Process.sendSignal(android.os.Process.myPid(), android.os.Process.SIGNAL_KILL);

    	
	  }
	
	}
	



}
