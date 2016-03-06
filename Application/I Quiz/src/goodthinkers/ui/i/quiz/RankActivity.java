package goodthinkers.ui.i.quiz;







import android.app.Activity;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class RankActivity extends Activity implements OnClickListener{
	WebView _webview;
	Button btn1;
	int web_check=1;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    //RankView2.canRRun = true;
	    //MainView.canRun = false;
	    //ClientThread.ConnectExit();	//에러발생지점
	    setContentView(R.layout.activity_rank);
	    // TODO Auto-generated method stub
	    MainActivity.button_click=1;
		_webview = (WebView) findViewById(R.id.webview);
		_webview.setWebViewClient(new HelloWebViewClient());
		_webview.getSettings().setDefaultTextEncodingName("EUC-KR");
		_webview.clearCache(true);
		 _webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		 
		_webview.loadUrl("http://10.80.75.110/index.php");
		
		btn1= (Button) findViewById(R.id.webbutton);
		btn1.setOnClickListener(this);
		btn1.setText("과별 랭킹 보기");

	}
	
	public void onClick(View v) {

		if (v == btn1) {
			if(web_check==1){
			_webview.loadUrl("http://10.80.75.110/subject.php");
			btn1.setText("개인 랭킹 보기");
			web_check=2;
		}else{
			_webview.loadUrl("http://10.80.75.110/index.php");
			btn1.setText("과별 랭킹 보기");
			web_check=1;
		}
		}
	}
	
	public boolean shouldOverrideUrlLoading(WebView v, String url){
		v.loadUrl(url);
		return true;
	}
	private class HelloWebViewClient extends WebViewClient {
	    @Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
	        view.loadUrl(url);
	        return true;
	    }
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		//RankView2.canRRun = false;
		if (keyCode == KeyEvent.KEYCODE_BACK){
			MainActivity.button_click=0;
			MainActivity.quiz_sound.stop();
			RankActivity.this.finish();
			Intent intent = new Intent(RankActivity.this, MainActivity.class);
							startActivity(intent);
		
		}
		return true;
	 }
	

  	public void onPause(){
		super.onPause();
		//MainView.canRun=false;
		//RankView2.canRRun=false;
		
	}
	public void onRestart(){
		super.onRestart();
		//MainView.canRun=false;
	//	RankView2.canRRun=true;
	
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
