package goodthinkers.ui.i.quiz;

//import org.eclipse.swt.layout.GridLayout;
//import org.eclipse.swt.widgets.Display;
//import org.eclipse.swt.widgets.Shell;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class SetActivity extends Activity {
	RadioButton a;
	RadioButton b;
	RadioButton c;
	RadioButton d;
	TextView backgndtxt, effectgndtxt;
	static SharedPreferences settings;
	static int j;
	 static int sound_onoff;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	   // MainView.canRun = false;
		backgndtxt= (TextView) findViewById(R.id.backgnd);
		effectgndtxt= (TextView) findViewById(R.id.effect);
		settings = getSharedPreferences("set_sound",Activity.MODE_PRIVATE);
	
	    setContentView(R.layout.activity_set);
	    // TODO Auto-generated method stub

	    RadioGroup ColGroup = (RadioGroup)findViewById(R.id.a_onoff);
	    RadioGroup ColGroup2 = (RadioGroup)findViewById(R.id.b_onoff);
        ColGroup.setOnCheckedChangeListener(mRadioCheck);
        ColGroup2.setOnCheckedChangeListener(mRadioCheck);
        a = (RadioButton) findViewById(R.id.a_on);
        b = (RadioButton) findViewById(R.id.a_off);
        c = (RadioButton) findViewById(R.id.b_on);
        d = (RadioButton) findViewById(R.id.b_off);
        int res1=settings.getInt("MainActivity.click_a_on", 1);
	 	  MainActivity.click_a_on=res1;
	 	   int res2=settings.getInt("MainActivity.click_a_off", 0);
	 	  MainActivity.click_a_off=res2;
	 	   int res3= settings.getInt("MainActivity.click_b_on", 1);
	 	  MainActivity.click_b_on=res3;
	 	   int res4=settings.getInt("MainActivity.click_b_off", 0);
	 	  MainActivity.click_b_off=res4;
      if (MainActivity.click_a_on==1){
    	  a.setChecked(true);
      }else if(MainActivity.click_a_off==1){
    	  b.setChecked(true);
      }
      if(MainActivity.click_b_on==1){
    	  c.setChecked(true);
      }else if(MainActivity.click_b_off==1){
    	  d.setChecked(true);
      }
      
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
	
		//	RankView.canRun = false;
			
			
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
		        	  MainActivity.button_click=0;
			SetActivity.this.finish();
			
			Intent intent = new Intent(SetActivity.this, MainActivity.class);
								startActivity(intent);
								return true;
			
		          }
		          return true;
	 }
	    RadioGroup.OnCheckedChangeListener mRadioCheck = new RadioGroup.OnCheckedChangeListener() {
	    	 
            public void onCheckedChanged(RadioGroup group, int checkedId) {
            	SharedPreferences.Editor esettings =settings.edit();
                   // TODO Auto-generated method stub

                   if(group.getId()==R.id.a_onoff){

						switch(checkedId){//checkedId는 어떤 라디오 버튼이 체크되있는지 그 체크된 아이디 

                          case R.id.a_on:
                          	  sound_onoff=0;
                        	  MainActivity.click_a_on=1;
                        	  MainActivity.click_a_off=0;
                          esettings.putInt("sound_onoff", 0);
                          esettings.putInt("MainActivity.click_a_on", 1);
                          esettings.putInt("MainActivity.click_a_off", 0);
                          esettings.commit();

                        	  MainActivity.myPlayer.start();
                                break;
                          case R.id.a_off:   
                        	  if(sound_onoff==0){
                        			
                            	  MainActivity.myPlayer.pause();        
                        	  }
                        	  sound_onoff=1;
                        	  MainActivity.click_a_off=1;
                        	  MainActivity.click_a_on=0;
                          esettings.putInt("sound_onoff", 1);
                          esettings.putInt("MainActivity.click_a_on", 0);
                          esettings.putInt("MainActivity.click_a_off", 1);
                          esettings.commit();
                          		
                                break;
                          }
                   }
					else if(group.getId()==R.id.b_onoff){
						switch(checkedId){//checkedId는 어떤 라디오 버튼이 체크되있는지 그 체크된 아이디 

	                        case R.id.b_on:
	                        	j=0;
	                        	MainActivity.click_b_on=1;
	                        	MainActivity.click_b_off=0;
	                            esettings.putInt("j", 0);
	                            esettings.putInt("MainActivity.click_b_on", 1);
	                            esettings.putInt("MainActivity.click_b_off", 0);
	                            esettings.commit();

	                              break;
	                        case R.id.b_off:
	                           	MainActivity.click_b_on=0;
	                        	MainActivity.click_b_off=1;
	                        	 j=1;
	                            esettings.putInt("j", 1);
	                            esettings.putInt("MainActivity.click_b_on", 0);
	                            esettings.putInt("MainActivity.click_b_off", 1);
	                            esettings.commit();
	  
	                             break;
						}
                   }
            }

		
      };

    	public void onPause(){
    		super.onPause();
    	//	MainView.canRun=false;
    	
    		
    	}
    	public void onRestart(){
    		super.onRestart();
    	//	MainView.canRun=false;
    	
    	}
    	
    	public void onStop(){
    		super.onStop();
    	//	MainView.canRun=false;
    		
      	  if(MainActivity.button_click!=0){
      		 
      //		MainView.canRun=false;
        		moveTaskToBack(true); 
            	finish();         	 
            	android.os.Process.sendSignal(android.os.Process.myPid(), android.os.Process.SIGNAL_KILL);

        	
    	  }
    	
    	}
    	

    

	    
	    
	 /*   setTemp= (View)findViewById(R.id.setting_temp);
	    setTemp.setVisibility(View.VISIBLE);
	    
	    setTemp.postDelayed(new Runnable() {
			
			public void run() {
				 setReal= (View)findViewById(R.id.setting_real);
				 setTemp.setVisibility(View.INVISIBLE);
				 setReal.setVisibility(View.VISIBLE);
			}
		}, 1500);// 3초딜레이후 실행됨 */
	    
	        
	

}
