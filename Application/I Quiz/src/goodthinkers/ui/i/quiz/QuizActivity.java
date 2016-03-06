package goodthinkers.ui.i.quiz;






import android.app.Activity;
import android.app.AlertDialog;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;

import android.graphics.*;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import android.widget.TextView;



public class QuizActivity extends Activity implements OnClickListener{
	
	
	Handler mHandler = new Handler();
	
	Handler thandler = new Handler() {			//timer �޼����� 0�� �Ǹ� ���ο� ���� ����
		public void handleMessage(Message msg) {

			if (msg.what == 1) {
				// 0.5�ʽ� 1�� ���� �ϰ� 1�ʴ� 2���� �����ε� ���⼭ ũ�� �۾����� ������ 
				if(msg.arg1 ==0 |msg.arg1==2 | msg.arg1==4 | msg.arg1==6 | msg.arg1==8){
					timer.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
					timer.setBackgroundResource(R.drawable.timer2); // ���� �ð�
					
					if (SetActivity.j==0){
						MainActivity.sManager.play(3);
					   }

					if (GlobalVar.life==1){
						scared.setVisibility(-1);
						life1.setVisibility(1);
					}
				}
				else if(msg.arg1==1 | msg.arg1==3 | msg.arg1==5 | msg.arg1==7|msg.arg1==9){
					
					timer.setBackgroundResource(R.drawable.timer); // ū�ð�
					timer.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
					timer.setText(Integer.toString(timercount)); //�� �����ֱ� 
					if (GlobalVar.life==1){
						scared.setVisibility(1);
						life1.setVisibility(-1);
					}
					timercount--;  // �ʰ� �Ųٷ� ����
					
				}
				
				if (msg.arg1 == 0 & check==0) {
					
					
					xx.setVisibility(1);
					if(SetActivity.j==0){
					int f = (int)(Math.random()*3+5);
					MainActivity.sManager.play(f);}
					mHandler.postDelayed(new Runnable()
					{
						public void run()
						{
							xx.setVisibility(-1);
						}
					},2000);
					
					GlobalVar.life=(GlobalVar.life)-1;
					GlobalVar.combo = 0;
					if(GlobalVar.life==2){
						life3.setVisibility(View.GONE);
					}
					else if(GlobalVar.life==1)
					{
						life2.setVisibility(View.GONE);
					}
					else{
						life1.setVisibility(View.GONE);
						t.pauseNResume(true);
					
						MainActivity.button_click=0;
						Intent intent = new Intent(QuizActivity.this, RankJoin.class);
						//�ð�
						startActivity(intent);
							QuizActivity.this.finish();
					
					}
					///////////////
					if (GlobalVar.life!=0){
						MainActivity.button_click=0;
						
						Intent intent = new Intent(QuizActivity.this,
								QuizActivity.class);
						startActivity(intent);
						QuizActivity.this.finish();
						
						
					}
				}
			}
		}
	};
	////
	/////

	/** Called when the activity is first created. */
	Button oBtn,xBtn;
	Button logoBtn;
	TextView board, score, timer, combo;
	ImageView life1,scared, life2, life3, pass1, pass2, pass;
	int timercount=4;
	TimerThread t = new TimerThread(thandler);
	ImageView oo, xx;
	private ViewFlipper m_viewFlipper;
	private int m_nPreTouchPosX = 0;
	static int backmain;
	int n, m,noTouch;
	int check=0;
	int check2=1;
	float xDown;
	float xUp;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setVolumeControlStream(AudioManager.STREAM_MUSIC);

		setContentView(R.layout.activity_start);

		m_viewFlipper = (ViewFlipper)findViewById(R.id.viewFlipper);
		m_viewFlipper.setOnTouchListener(MyTouchListener);

		
		oBtn= (Button) findViewById(R.id.button1);
		xBtn= (Button) findViewById(R.id.button2);
		board= (TextView) findViewById(R.id.quiz_txt);
		score= (TextView) findViewById(R.id.score);
		timer= (TextView) findViewById(R.id.timer); 
		timer.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
		combo= (TextView) findViewById(R.id.combo); 

		life1 = (ImageView) findViewById(R.id.life1); // life ��ä ���� 
		life2 = (ImageView) findViewById(R.id.life2);
		life3 = (ImageView) findViewById(R.id.life3);
		scared = (ImageView) findViewById(R.id.life11);
		pass = (ImageView) findViewById(R.id.pass);
		pass1 = (ImageView) findViewById(R.id.passlife1);
		pass2 = (ImageView) findViewById(R.id.passlife2);
		oo = (ImageView) findViewById(R.id.o1);
		xx = (ImageView) findViewById(R.id.x1);
		
		if (GlobalVar.passlife==1)
			pass2.setVisibility(-1);
		else if (GlobalVar.passlife==0){
			pass1.setVisibility(-1);
			pass2.setVisibility(-1);
		}
		oo.setVisibility(-1); // �ʱ⿡ �Ⱥ��̰� ��
		xx.setVisibility(-1);
		scared.setVisibility(-1);
		combo.setVisibility(-1);
		
		oBtn.setOnClickListener(this);
		xBtn.setOnClickListener(this);
		
		n = (int)(Math.random()*80);	// 0 ~ 66 ������ ���� ���� ����
		
		m = (int)(Math.random()*2+1);	// 1~2 ������ ���� ���� ����
		
		Typeface face1 =Typeface.createFromAsset(getAssets(),"fonts/milk.ttf");
		String text = breakText(board.getPaint(), QuizData.Quiz[n][0], 600); //�ٹٲ��Լ� �ҷ���
		Typeface face =Typeface.createFromAsset(getAssets(),"fonts/dd.ttf");
		combo.setTypeface(face1);
		combo.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
		board.setTypeface(face);
		score.setTypeface(face);
		score.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
		score.setText("SCORE : " + GlobalVar.final_score);	//���ھ� �ǿ� ���� �ֽ�ȭ
		board.setText(text);	//��ĭ ���� �ִ� �ٹٲ� �� �ؽ�Ʈ�� ������

		/* o ��ư�� ������ ���������� ���� x ��ư�� ����*/
		
		oBtn.setTypeface(face1);
		xBtn.setTypeface(face1);
		oBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		xBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		
		if(QuizData.Quiz[n][m] =="o" | QuizData.Quiz[n][m] =="x"){
		oBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 45);
		xBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 45);
		}
		
		oBtn.setText(QuizData.Quiz[n][m]);
		if (m==1)
		{xBtn.setText(QuizData.Quiz[n][2]);}
		else if (m==2)
		{xBtn.setText(QuizData.Quiz[n][1]);}
		
		if(GlobalVar.life==0)
		{
			life3.setVisibility(View.GONE);
			life2.setVisibility(View.GONE);
			life1.setVisibility(View.GONE);
//			t.pauseNResume(true);
		/*	if (SetActivity.sound_onoff==0){
				   MainActivity.quiz_sound.stop();
			   }
			if ( SetActivity.j==0){
			MainActivity.sManager.play(4);}*/
			
			t.pauseNResume(true);
			Intent intent = new Intent(QuizActivity.this, RankJoin.class);
			//������0
			Log.d("sbsbsbsbs", "sbsbsbsbsb");
			QuizActivity.this.finish();
			Log.d("kjsdaklfdsajlkdfsajlk","kdasjfkldsajfdalks");
			
			
			
			startActivity(intent);
			Log.d("k00000000000000000","k0000000000000000");
			//QuizActivity.this.finish();
//			Handler tHandler = new Handler();
//			tHandler.postDelayed(new Runnable(){
//				public void run() {}
//			}, 5000);
			Log.d("k111111111111111","k11111111111111");
			//t.pauseNResume(true);
			//t.resume();
		}
		else if(GlobalVar.life==3)
		{
			t.start();		// �̷��� ���������� ������ �־�߸� �Ѵ�.
		}
		else if(GlobalVar.life==2)
		{
			life3.setVisibility(View.GONE);
			t.start();		// �̷��� ���������� ������ �־�߸� �Ѵ�.
		}
		else if(GlobalVar.life==1)
		{
			life3.setVisibility(View.GONE);
			life2.setVisibility(View.GONE);
			t.start();		// �̷��� ���������� ������ �־�߸� �Ѵ�.
		}
		Log.d("asdfaaaaaaaaaaaa", "asdfaaaaaaaaaaaa");
		//QuizActivity.this.finish();
		//t.start();		// �̷��� ���������� ������ �־�߸� �Ѵ�.
		Log.d("asdfbbbbbbbbbbbb", "asdfbbbbbbbbbbb");
	}
//	public void onResume(){
//		android.os.Process.sendSignal(android.os.Process.myPid(),
//				android.os.Process.SIGNAL_KILL);
//	}
	

	//�ٹٲ� �Լ�
	public String breakText(Paint textPaint, String strText, int breakWidth){
		StringBuilder sb = new StringBuilder();
		int endValue = 0;
		do{
			endValue = textPaint.breakText(strText, true, breakWidth, null);
			if(endValue > 0){
				sb.append(strText.substring(0, endValue)).append("\n");
				strText = strText.substring(endValue);
			}
		}while(endValue > 0);
		return sb.toString().substring(0,sb.length()-1);
	}
	
	
	
	//Ÿ�̸� ������
	class TimerThread extends Thread {
		Handler handler;
		boolean canRun = true;
		boolean isWait=false;	
		TimerThread(Handler handler) {
			this.handler = handler;

		}

		public void stopThread(){
			canRun=false;
			synchronized(this){
				this.notify();	//�� ��ġ ���
			}
		}
		
		public void pauseNResume(boolean value){
			isWait=value;
			synchronized(this){
				this.notify();
			}
		}


		public void run() {
			///������ ��
			
			for (int i =9; i > -1; i--) {
			
				
				try {
					Thread.sleep(500);
									
				} catch (InterruptedException e) {}
				
				synchronized(this){
					if(isWait){
						try{
							this.wait();
							this.stop();
						}catch(InterruptedException e){}
					}
				}
				if (SetActivity.j==0 && ((i==8)||(i==5)||(i==2))){
					
				}	
			Message msg = new Message();
			msg.what = 1;
			msg.arg1 = i;
			handler.sendMessage(msg);

				}
			
		}
	}


	/*���� ���� �ڷΰ��� ��ư�� ������ �� ���� ���� �������� ���� ���̾�α� ����*/
@Override
public boolean onKeyDown(int keyCode, KeyEvent event){

	 AudioManager mAudioManager = 
            (AudioManager)getSystemService(AUDIO_SERVICE);
        switch (keyCode) {
    /*    case KeyEvent.KEYCODE_HOME:
    		
    		t.pauseNResume(true);   //TimerThread �Ͻ�����

        		return true;*/
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
        case KeyEvent.KEYCODE_HOME:
		
		t.pauseNResume(true);   //TimerThread �Ͻ�����

		return true;
	
        case KeyEvent.KEYCODE_BACK:

       	 if (SetActivity.j==0){
				   MainActivity.sManager.play(2);
			   }
		t.pauseNResume(true);   //TimerThread �Ͻ�����
		MainActivity.quiz_sound.pause();
		/*���̾�α� �ڽ� ����*/
		final Builder exit = new AlertDialog.Builder(this);
		exit.setIcon(R.drawable.ic_launcher);
		exit.setTitle(R.string.app_name);
		exit.setMessage("����ȭ������ ���ư��ðڽ��ϱ�?");
		
		/*���̾�α� ���� ��ư�� ���� ó��*/
		exit.setPositiveButton("��������",new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which){
						GlobalVar.final_score=0;
						
						if (SetActivity.j==0){
							 MainActivity.sManager.play(2);
						   }
						//t.pauseNResume(true);
						Log.d("here","eeeeeeeeeeeeeeeeeeee");
						//t.stop();
						Log.d("here","wwwwwwwwwwwwwwwwwwwwww");
						QuizActivity.this.finish();
						Log.d("here","qqqqqqqqqqqqqqqqqqqqqqqq");
						MainActivity.quiz_sound.stop();
						MainActivity.i=0;
						MainActivity.button_click=0;
						backmain=1;
						Log.d("here","ttttttttttttttttttttttttttt");
						Intent intent = new Intent(QuizActivity.this, MainActivity.class);
						Log.d("here","yyyyyyyyyyyyyyyyyyyyyyyyyyyy");
						Log.d("here","iiiiiiiiiiiiiiiiiiiiiiiiii");
						startActivity(intent);
						Log.d("here","uuuuuuuuuuuuuuuuuuuuuuuu");
						check=1;
					
						//t.stop();
						
						//Log.d("here","iiiiiiiiiiiiiiiiiiiiiiiiii");
			}
		});
		
		/*���̾�α� ������ ��ư�� ���� ó��*/
		exit.setNegativeButton("����ϱ�",new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int which){
						t.pauseNResume(false);
						MainActivity.quiz_sound.start();
						if (SetActivity.j==0){
							 MainActivity.sManager.play(2);
						   }
						
					}
		});
		
		/*���̾�α� ���� ���� �� �ڷΰ��� ��ư ������ ���� ó��*/
		exit.setCancelable(true);
		exit.setOnCancelListener(new OnCancelListener(){
			
			public void onCancel(DialogInterface dialog){
				t.pauseNResume(false);
				MainActivity.quiz_sound.start();
			}
		});
				
	exit.show();
	return true;
	}

		return true;
		
 }

   


/*�н� ��� ����*/
View.OnTouchListener MyTouchListener = new View.OnTouchListener()
{
	public boolean onTouch(View v, MotionEvent event) 
	{
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			m_nPreTouchPosX = (int)event.getX();

			
		}
		
		if (event.getAction() == MotionEvent.ACTION_UP)
		{
			int nTouchPosX = (int)event.getX();
			
			
			if (nTouchPosX < m_nPreTouchPosX-200)
			{
				

				if (GlobalVar.passlife!=0){
					pass.setVisibility(1);
					t.pauseNResume(true);
					mHandler.postDelayed(new Runnable()
					{
						public void run()
						{
							
							pass.setVisibility(-1);
							
							//2�� ������ ������
						}
					},2000);
					GlobalVar.passlife--;
					if(GlobalVar.passlife==1){
						GlobalVar.combo = GlobalVar.combosav;
						pass2.setVisibility(View.GONE);
						
					}
					else if (GlobalVar.passlife == 0){
						GlobalVar.combo = GlobalVar.combosav;
						pass1.setVisibility(View.GONE);
						
					}
						
				MainActivity.button_click=0;
				QuizActivity.this.finish();
				
				Intent intent = new Intent(QuizActivity.this, QuizActivity.class);
				startActivity(intent);
				}
			}
			
			m_nPreTouchPosX = nTouchPosX;
		}
		
        return true;
    }
};


	/*���� ���� ��ư�� �������� �ƴ����� ó���ϱ� ���� ��*/
	public void onClick(View v) {
		
		check =1;	//���ο� â���� �Ѿ�� �ʰ� �� ��,
			
		/*o ��ư�� ������ �� */
		if(v==oBtn){
			notouch();
			/*o ��ư�� ������ ��� �̷��� ó���� �Ŀ�*/
			if(noTouch!=1){
			if (oBtn.getText()==QuizData.Quiz[n][1]){
				//board.setText("�����Դϴ�.");
				if(SetActivity.j==0){
					MainActivity.sManager.play(0);
					}
				oo.setVisibility(1);
				
				int sum_score=Integer.valueOf(QuizData.Quiz[n][3]);
				if(timercount>=2){
					GlobalVar.combo++;
					GlobalVar.combosav = GlobalVar.combo;
				}
				else
					GlobalVar.combo = 0; 
				
				if(GlobalVar.combo>1){
					// 1�޺����ͽ��� 
					
					combo.setText(Integer.toString(GlobalVar.combo-1)+" Combo!!"); // XCombo!! ǥ�� 
					combo.setVisibility(1); // ���̰��� 
				}
				
				GlobalVar.final_score=GlobalVar.final_score+sum_score+10*(GlobalVar.combo-1);  //score �ֽ�ȭ
																	  // �޺� �����ֱ� 
				score.setText("score = " + GlobalVar.final_score);
				
				mHandler.postDelayed(new Runnable()
				{
					public void run()
					{
						
						oo.setVisibility(-1);
						combo.setVisibility(-1);
						//2�� ������ ������
					}
				},2000);

			}
			
			/*o ��ư�� ������ �ƴ� ��� �̷��� ó���� �Ŀ�*/
			else if(oBtn.getText()==QuizData.Quiz[n][2])
			{
				if(SetActivity.j==0){
					int f = (int)(Math.random()*3+5);
					MainActivity.sManager.play(f);
					}
				GlobalVar.combo=0;
				
				xx.setVisibility(1);
				
				GlobalVar.combo = 0; // combo ���ֱ� 
				mHandler.postDelayed(new Runnable()
				{
					public void run()
					{
						xx.setVisibility(-1);
						
					}
				},2000);
				
//				QuizActivity.this.finish();
				
				// life ����
				GlobalVar.life=(GlobalVar.life)-1;
				
				if(GlobalVar.life==0){
					
					t.pauseNResume(true);
					//MainView.canRun=false;

							Intent intent = new Intent(QuizActivity.this, RankJoin.class);
							//����2
							startActivity(intent);
							QuizActivity.this.finish();

					

				}

			}
			
			Log.d("nononononononononononono", "nnnnnnnnnnnnnnnnnnnn");
			/*���Ӱ� QuizActivity �� ����.*/
			 MainActivity.button_click=0;
			 
			Intent intent = new Intent(QuizActivity.this, QuizActivity.class);
			startActivity(intent);
			/*������ QuizActivity �� �ݴ´�.*/				
			QuizActivity.this.finish();  
			noTouch=0;
			
			}
		}else if(v==xBtn){
			notouch();
			if(noTouch!=1){
			if (xBtn.getText()==QuizData.Quiz[n][1]){
				//board.setText("�����Դϴ�.");
				if(SetActivity.j==0){
					MainActivity.sManager.play(0);
					}
				oo.setVisibility(1);
				
				 
				int sum_score=Integer.valueOf(QuizData.Quiz[n][3]);
				
				
				if(timercount>=2){
					GlobalVar.combo++;
					GlobalVar.combosav= GlobalVar.combo;
				}
				else
					GlobalVar.combo = 0; // �޺� ���� 
				
				if(GlobalVar.combo>1){
					// 1�޺����ͽ��� 
					
					combo.setText(Integer.toString(GlobalVar.combo-1)+" Combo!!"); // XCombo!! ǥ�� 
					combo.setVisibility(1); // ���̰��� 
				}
				
				GlobalVar.final_score=GlobalVar.final_score+sum_score+10*(GlobalVar.combo-1);  //score �ֽ�ȭ
																	  // �޺� �����ֱ� 
				score.setText("score = " + GlobalVar.final_score);
				
				mHandler.postDelayed(new Runnable()
				{
					public void run()
					{
						
						oo.setVisibility(-1);
						combo.setVisibility(-1);
						//2�� ������ ������
					}
				},2000);
				

				
			}

			else if(xBtn.getText()==QuizData.Quiz[n][2]){
				//board.setText("�����Դϴ�.");
				if(SetActivity.j==0){
					int f = (int)(Math.random()*3+5);
					MainActivity.sManager.play(f);
					}
				GlobalVar.combo=0;
				
				xx.setVisibility(1);
				
				GlobalVar.combo = 0; // combo ���ֱ� 
				mHandler.postDelayed(new Runnable()
				{
					public void run()
					{
						xx.setVisibility(-1);
						
					}
				},2000);
				
//				QuizActivity.this.finish();
				
				// life ����
				GlobalVar.life=(GlobalVar.life)-1;
				
				if(GlobalVar.life==0){
					
					t.pauseNResume(true);
					//MainView.canRun=false;

							Intent intent = new Intent(QuizActivity.this, RankJoin.class);
							//����2
							startActivity(intent);
							QuizActivity.this.finish();

					

				}

			}
			 MainActivity.button_click=0;
			 
			Intent intent = new Intent(QuizActivity.this, QuizActivity.class);
			startActivity(intent);
			QuizActivity.this.finish();
		}
		}
	}
	
	public void notouch(){
		oBtn.setOnTouchListener(new OnTouchListener(){

			public boolean onTouch(View v, MotionEvent event)  {
			    if(event.getAction()==MotionEvent.ACTION_DOWN){
			    	noTouch=1;
			    }
			    return true;
			   }
			   
			  });
		xBtn.setOnTouchListener(new OnTouchListener(){

			public boolean onTouch(View v, MotionEvent event)  {
			    if(event.getAction()==MotionEvent.ACTION_DOWN){
			    	noTouch=1;
			    }
			    return true;
			   }
			   
			  });
	}
  	public void onPause(){
		super.onPause();
		
	
		
	}
	public void onRestart(){
		super.onRestart();
		
	
	}
	
	public void onStop(){
		super.onStop();
	//	MainView.canRun=false;		
  	  if(MainActivity.button_click!=0){  		 
  		
    		moveTaskToBack(true); 
        	finish();         	 
        	android.os.Process.sendSignal(android.os.Process.myPid(), android.os.Process.SIGNAL_KILL);    	
	  }	  	
  	  if(backmain!=1){
  	 MainActivity.button_click=1;  		
	}
  	  backmain=0;
	}
}
