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
    static GameThread mThread;                             // ������
    private SurfaceHolder mSurfaceHolder;                  // SurfaceHolder 
    private int Width, Height, cx, cy;                          // ȭ���� ��ü ���� �߽���
    private int x1, y1;                                  // Viewport ��ǥ
    private int sx1, sy1;                            // Viewport�� 1ȸ�� �̵��� �Ÿ�
    private Bitmap imgBack1;                    // ��� �̹���
    
    private long counter = 0;                                    // ������ ��ü �ݺ� Ƚ��
    static boolean canRun = true;                            // ������ ����� �÷���

   
    //--------------------------------------
    //         �� �� ��
    //--------------------------------------
    public MainView(Context context) {
    	super(context);
    } // ������ ��
    
    public MainView(Context context, AttributeSet attrs) { 
    	super(context, attrs);       
        
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        
        // ȭ�� �ػ� ���ϱ�
        Display display = ((WindowManager)context.getSystemService(context.WINDOW_SERVICE)).getDefaultDisplay();
        Width  = display.getWidth();
        Height = display.getHeight();
        cx = Width / 2;                   // ȭ���� �߽���
        cy = Height / 2;    

        Resources res = context.getResources();          // ���ҽ� �б�

        // ���ȭ�� �а� ���ȭ���� ũ�⸦ ȭ���� ũ��� ����
        
        imgBack1 = BitmapFactory.decodeResource(res, R.drawable.main);
        imgBack1 = Bitmap.createScaledBitmap(imgBack1, Width, Height, true);
        // ���ּ� �а� ���� ���� ���


        x1 = cx;          // Viewport�� ���� ��ġ�� �̹����� �Ѱ��
        y1 = cy;
        sx1 = -1;         // Viewport�� 1ȸ�� �̵���ų �Ÿ�
        sy1 = -1;

        mThread = new GameThread(context, holder); 
 }


    //--------------------------------------
    //    Surface�� ������ �� ȣ���
    //--------------------------------------
    
    public void surfaceCreated(SurfaceHolder arg0) {
         mThread.start();
    }



    //--------------------------------------
    //    Surface�� �ٲ� �� ȣ���
    //--------------------------------------
   
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
         // TODO Auto-generated method stub
    	
    }



    //--------------------------------------
    //    Surface�� ������ �� ȣ���
    //--------------------------------------
    
    public void surfaceDestroyed(SurfaceHolder arg0) {
         boolean done = true;
         while (done) {
              try {
                   mThread.join();                            // �����尡 ���� step�� ���� �� ���� ���
                   done = false;
              } catch (InterruptedException e) {         // ���ͷ�Ʈ ��ȣ�� ����?   
                   // �� ��ȣ ���� - �ƹ��͵� ����
              } 
         } // while
    } // surfaceDestroyed

//---------------  ���⼭���� ������ �����̾�. �Ѻ��� ��!  -----------------------
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
        	 Rect src = new Rect();                     // Viewport�� ��ǥ
              Rect dst = new Rect();                     // View(ȭ��)�� ��ǥ
              dst.set(0, 0, Width, Height);              // View�� ȭ�� ��ü ũ�� 
              while (canRun) {
                   Canvas canvas = null;
                   canvas = mHolder.lockCanvas();
                   try {
                        synchronized (mHolder) {
                         ScrollImage();                                      // Viewport �̵� 
                         src.set(x1, y1, x1 + cx, y1 + cy);           // �̵��� Viewport ��ǥ
                         canvas.drawBitmap(imgBack1, src, dst, null);
                    }
                   } finally {
                        mHolder.unlockCanvasAndPost(canvas);
                   } // try
              } // while
         } // run ��



         //--------------------------------------
         //    ��� Scroll
         //--------------------------------------
         private void ScrollImage() {
              counter++;
              if (counter % 2 == 0) {                  // ������ 2ȸ�� 1���� ��ũ�� 
                   x1 += sx1;                            // Viewport�� ���� �̵� (sx�� ����(-)��)
                   y1 += sy1;
                   if (x1 < 0) x1 = cx;                 // �̹����� ����� �̹����� �߽����� �̵�
                   if (y1 < 0) y1 = cy;
              }
         } // Scroll ��
 
    } // Thread ��

} // SurfaceView ��

