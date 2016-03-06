package goodthinkers.ui.i.quiz;



import goodthinkers.ui.i.quiz.ClientThread;
import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;



public class RankJoin extends Activity implements OnClickListener,OnItemSelectedListener{
	static ClientThread ct;
	///////////////////view////////////////////////
	View rankJoin_page,rank_page;
	TextView ScoreView;
	//////////////////���ǳ� �κ�.////////////////////
	Spinner subject_spin;
	EditText name_data;
	
	ArrayAdapter<CharSequence> subjectArr;
	String subjectSellected;
	static int ct_on;
	String name;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_rank_join);
		MainActivity.sManager.play(8);
		//RankView.canRun=true;
		////////////////////view ��ü����///////////////////
		rankJoin_page= findViewById(R.id.rankJoin_layout);
		ScoreView= (TextView)findViewById(R.id.ScoreView);
		ScoreView.setText(Integer.toString(GlobalVar.final_score));
		///////////////////////////////////////////////////
		////���ھ� ǥ��
		
		
		//
		/////////////////���ǳ� ��ü����//////////////////
		subjectArr = ArrayAdapter.createFromResource(this, R.array.subject_list, android.R.layout.simple_spinner_item);
		subjectArr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		//subjectArr�� �����
		subject_spin= (Spinner)findViewById(R.id.subject_spin);//main.xml�� subject_spin
		subject_spin.setPrompt("�а��� �����ϼ���");
		subject_spin.setAdapter(subjectArr);
		subject_spin.setOnItemSelectedListener(this);
		name_data= (EditText) findViewById(R.id.name_txt);
		
		/////////////////��ũ��� ��ư��////////////////////
		Button rankJoin_btn = (Button) findViewById(R.id.rankJoin_btn);
		//////////////////////////////////////////////

		rankJoin_btn.setOnClickListener(this);
		//ct_on++;
		
		//////////////////////��������
		//if(ct_on==1){
		GlobalVar.connectFlag=1;
		
		Log.d("cccccccccccc","cccccccccccccccccccc");
		ct = new ClientThread("10.80.75.110");
		ct.start();
		
		//��ȯ�� �̰� ��� 
		//}
		////////////////////////////////////

	}

	


	//public boolean onTouch(View v, MotionEvent event) {
	public void onClick(View v){
		
		
		switch(v.getId()){
		case R.id.rankJoin_btn:
			//EditText subject_data= (EditText) findViewById(R.id.subject_spin);
			
			
			Log.d("aaaaaaaaa","aaaaaaaaaaaaaaaaaaa");
			//String subject= subject_data.getText().toString();
			name= name_data.getText().toString();
			//String subj=subject_spin.getString();
			Log.d("bbbbbbbbbbb","bbbbbbbbbbbbbbbbbbbb");
			if( name.equals("")){
				//if(subject.equals("") || name.equals("")){
				AlertDialog.Builder notName = new AlertDialog.Builder(RankJoin.this);


				notName.setTitle("�а� Ȥ��  �̸��� �Է����ּ���");
				notName.setMessage("RANK �Է¿� �� �ʿ��մϴ�");
				//notName.setCancelable(true); //��Ұ����ϰ� 

				notName.setPositiveButton("Ȯ��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						//���̾�α��� Ȯ�ι�ư 					
					}
				});				
				notName.show(); //show���ϸ� �ι�â�̵�


			}else{
				//������ �ѱ�� ���� �а��� �̸��� ������ �Ѱܾ��� 
				//RankView.canRun=false;
				
				Log.d("eeeeeeeeeeeeee","eeeeeeeeeeeeeeeee");
			ct.Write(Integer.toString(GlobalVar.final_score));
			Log.d("dddddddddddd","dddddddddddddddd");
			ct.Write(subjectSellected);
			ct.Write(name);
			Log.d("ffffffffffffffff","fffffffffffffffffffff");
			//ct.ConnectExit();
			Log.d("gggggggggggggg","gggggggggggggg");
			//ct.ConnectExit();	
				
				if(SetActivity.sound_onoff==0){

				
				//MainActivity.quiz_sound.stop();
				}
				MainActivity.button_click=1;
				MainActivity.i=0;
				RankJoin.this.finish();
				Intent intent = new Intent(RankJoin.this, RankActivity.class);
				
				startActivity(intent);				
				
			}
			break;


		}

	}


	/*���� ���� �ڷΰ��� ��ư�� ������ �� ���� ���� �������� ���� ���̾�α� ����*/
@Override
public boolean onKeyDown(int keyCode, KeyEvent event){
	if(keyCode==KeyEvent.KEYCODE_BACK){
	
		/*���̾�α� �ڽ� ����*/
		final Builder exit = new AlertDialog.Builder(this);
		exit.setIcon(R.drawable.ic_launcher);
		exit.setTitle(R.string.app_name);
		exit.setMessage("����ȭ������ ���ư��ðڽ��ϱ�?\n ������ �� ������ϴ�.");
		
		/*���̾�α� ���� ��ư�� ���� ó��*/
		exit.setPositiveButton("��������",new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which){
			//			RankView.canRun=false;
						GlobalVar.final_score=0;
						MainActivity.visibleCnt=1;
						MainActivity.quiz_sound.stop();
						MainActivity.i=0;
						RankJoin.this.finish();
						Intent intent = new Intent(RankJoin.this, MainActivity.class);
						startActivity(intent);
						//check=1;
			}
			
		});
		
		/*���̾�α� ������ ��ư�� ���� ó��*/
		exit.setNegativeButton("����ϱ�",new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int which){
						//t.pauseNResume(false);
					}
		});
				
	exit.show();
	}
    		
	return true;
 }


	public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long arg3) {
		/////////////���ǳʿ� �̿�

		switch(arg0.getId()){
		case R.id.subject_spin:
			subjectSellected= (String) subjectArr.getItem(position);
			break;
		}

	}

	
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

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
		
  	  if(MainActivity.button_click!=1){
  		 
  		//MainView.canRun=false;
    		moveTaskToBack(true); 
        	finish();         	 
        	android.os.Process.sendSignal(android.os.Process.myPid(), android.os.Process.SIGNAL_KILL);

    	
	  }
	
	}
	


}

