package goodthinkers.ui.i.quiz;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

public class actViewFlipper extends Activity { 
	private ViewFlipper m_viewFlipper;
	
	private int m_nPreTouchPosX = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		

	}
	

}
