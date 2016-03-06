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
	//////////////////스피너 부분.////////////////////
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
		////////////////////view 객체생성///////////////////
		rankJoin_page= findViewById(R.id.rankJoin_layout);
		ScoreView= (TextView)findViewById(R.id.ScoreView);
		ScoreView.setText(Integer.toString(GlobalVar.final_score));
		///////////////////////////////////////////////////
		////스코어 표시
		
		
		//
		/////////////////스피너 객체생성//////////////////
		subjectArr = ArrayAdapter.createFromResource(this, R.array.subject_list, android.R.layout.simple_spinner_item);
		subjectArr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		//subjectArr은 어레이임
		subject_spin= (Spinner)findViewById(R.id.subject_spin);//main.xml의 subject_spin
		subject_spin.setPrompt("학과를 선택하세요");
		subject_spin.setAdapter(subjectArr);
		subject_spin.setOnItemSelectedListener(this);
		name_data= (EditText) findViewById(R.id.name_txt);
		
		/////////////////랭크등록 버튼부////////////////////
		Button rankJoin_btn = (Button) findViewById(R.id.rankJoin_btn);
		//////////////////////////////////////////////

		rankJoin_btn.setOnClickListener(this);
		//ct_on++;
		
		//////////////////////서버접속
		//if(ct_on==1){
		GlobalVar.connectFlag=1;
		
		Log.d("cccccccccccc","cccccccccccccccccccc");
		ct = new ClientThread("10.80.75.110");
		ct.start();
		
		//동환아 이거 살려 
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


				notName.setTitle("학과 혹은  이름을 입력해주세요");
				notName.setMessage("RANK 입력에 꼭 필요합니다");
				//notName.setCancelable(true); //취소가능하게 

				notName.setPositiveButton("확인", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						//다이얼로그의 확인버튼 					
					}
				});				
				notName.show(); //show로하면 두번창이듬


			}else{
				//페이지 넘기기 전에 학과와 이름을 서버로 넘겨야함 
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


	/*게임 도중 뒤로가기 버튼을 눌렀을 때 정말 끝낼 것인지를 묻는 다이얼로그 생성*/
@Override
public boolean onKeyDown(int keyCode, KeyEvent event){
	if(keyCode==KeyEvent.KEYCODE_BACK){
	
		/*다이얼로그 박스 생성*/
		final Builder exit = new AlertDialog.Builder(this);
		exit.setIcon(R.drawable.ic_launcher);
		exit.setTitle(R.string.app_name);
		exit.setMessage("메인화면으로 돌아가시겠습니까?\n 점수는 다 사라집니다.");
		
		/*다이얼로그 왼쪽 버튼에 대한 처리*/
		exit.setPositiveButton("메인으로",new DialogInterface.OnClickListener(){
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
		
		/*다이얼로그 오른쪽 버튼에 대한 처리*/
		exit.setNegativeButton("계속하기",new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int which){
						//t.pauseNResume(false);
					}
		});
				
	exit.show();
	}
    		
	return true;
 }


	public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long arg3) {
		/////////////스피너에 이용

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

