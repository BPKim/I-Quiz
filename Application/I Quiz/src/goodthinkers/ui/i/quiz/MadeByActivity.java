package goodthinkers.ui.i.quiz;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


public class MadeByActivity extends Activity {
	View who;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	   // MainView.canRun = false;
		
	    setContentView(R.layout.activity_madeby);
	    // TODO Auto-generated method stub
	    who= (View)findViewById(R.id.made);
	    
	    who.setOnClickListener(new View.OnClickListener(){
	    	public void onClick(View v){
	    		MainActivity.button_click=0;
	        	Intent intent = new Intent(MadeByActivity.this, MainActivity.class);
				startActivity(intent);
				MadeByActivity.this.finish();
	    	}
	    });

	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		if (keyCode == KeyEvent.KEYCODE_BACK){
			 MainActivity.button_click=0;
		MadeByActivity.this.finish();
		
		Intent intent = new Intent(MadeByActivity.this, MainActivity.class);
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
