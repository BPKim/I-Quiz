package goodthinkers.ui.i.quiz;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;



import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.WindowManager;



public class MainView extends SurfaceView implements Callback {
    static GameThread mThread;                             // 스레드
    private SurfaceHolder mSurfaceHolder;                  // SurfaceHolder 
    private int Width, Height, cx, cy;                          // 화면의 전체 폭과 중심점
    private int x1, y1;                                  // Viewport 좌표
    private int sx1, sy1;                            // Viewport가 1회에 이동할 거리
    private Bitmap imgBack1;                    // 배경 이미지
    
    private long counter = 0;                                    // 루프의 전체 반복 횟수
    static boolean canRun = true;                            // 스레드 실행용 플래그

   
    //--------------------------------------
    //         생 성 자
    //--------------------------------------
    public MainView(Context context) {
    	super(context);
    } // 생성자 끝
    
    public MainView(Context context, AttributeSet attrs) { 
    	super(context, attrs);       
        
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        
        // 화면 해상도 구하기
        Display display = ((WindowManager)context.getSystemService(context.WINDOW_SERVICE)).getDefaultDisplay();
        Width  = display.getWidth();
        Height = display.getHeight();
        cx = Width / 2;                   // 화면의 중심점
        cy = Height / 2;    

        Resources res = context.getResources();          // 리소스 읽기

        // 배경화면 읽고 배경화면의 크기를 화면의 크기로 조정
        
        imgBack1 = BitmapFactory.decodeResource(res, R.drawable.main);
        imgBack1 = Bitmap.createScaledBitmap(imgBack1, Width, Height, true);
        // 우주선 읽고 폭과 높이 계산


        x1 = cx;          // Viewport의 시작 위치는 이미지의 한가운데
        y1 = cy;
        sx1 = -1;         // Viewport를 1회에 이동시킬 거리
        sy1 = -1;

        mThread = new GameThread(context, holder); 
 }


    //--------------------------------------
    //    Surface가 생성될 때 호출됨
    //--------------------------------------
    
    public void surfaceCreated(SurfaceHolder arg0) {
         mThread.start();
    }



    //--------------------------------------
    //    Surface가 바뀔 때 호출됨
    //--------------------------------------
   
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
         // TODO Auto-generated method stub
    	
    }



    //--------------------------------------
    //    Surface가 삭제될 때 호출됨
    //--------------------------------------
    
    public void surfaceDestroyed(SurfaceHolder arg0) {
         boolean done = true;
         while (done) {
              try {
                   mThread.join();                            // 스레드가 현재 step을 끝낼 때 까지 대기
                   done = false;
              } catch (InterruptedException e) {         // 인터럽트 신호가 오면?   
                   // 그 신호 무시 - 아무것도 않음
              } 
         } // while
    } // surfaceDestroyed

//---------------  여기서부터 스레드 영역이야. 넘보지 마!  -----------------------
    class GameThread extends Thread {
         SurfaceHolder  mHolder;
 
         //--------------------------------------
         //    Thread Constructor
         //--------------------------------------
         public GameThread(Context context, SurfaceHolder holder) {
              mHolder = holder;
         }
 
         //--------------------------------------
         //    Thread run
         //--------------------------------------
         public void run() {
             super.run(); 
        	 Rect src = new Rect();                     // Viewport의 좌표
              Rect dst = new Rect();                     // View(화면)의 좌표
              dst.set(0, 0, Width, Height);              // View는 화면 전체 크기 
              while (canRun) {
                   Canvas canvas = null;
                   canvas = mHolder.lockCanvas();
                   try {
                        synchronized (mHolder) {
                         ScrollImage();                                      // Viewport 이동 
                         src.set(x1, y1, x1 + cx, y1 + cy);           // 이동한 Viewport 좌표
                         canvas.drawBitmap(imgBack1, src, dst, null);
                    }
                   } finally {
                        mHolder.unlockCanvasAndPost(canvas);
                   } // try
              } // while
         } // run 끝



         //--------------------------------------
         //    배경 Scroll
         //--------------------------------------
         private void ScrollImage() {
              counter++;
              if (counter % 2 == 0) {                  // 루프의 2회에 1번씩 스크롤 
                   x1 += sx1;                            // Viewport를 위로 이동 (sx는 음수(-)임)
                   y1 += sy1;
                   if (x1 < 0) x1 = cx;                 // 이미지를 벗어나면 이미지의 중심으로 이동
                   if (y1 < 0) y1 = cy;
              }
         } // Scroll 끝
 
    } // Thread 끝

} // SurfaceView 끝

