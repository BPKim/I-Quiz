package goodthinkers.ui.i.quiz;

import java.util.HashMap;




import android.content.Context;

import android.media.AudioManager;

import android.media.SoundPool;




/*

 * ���� �޴��� Ŭ������ ����� ���� �ʿ��� �� ���ϰ� ���� ����

 * ����Ǯ�� ���Ⱑ �����ؼ� �����ΰ� ���� ����� ����Ѵ�.

 * 

 * �������� �Ҹ��� �� �� �ִ� �ε��� ������ �ʿ��ϴ�.

 * 

 */

public class SoundManager {

  //�ʿ��� �ʵ� �����ϱ�

  private static SoundManager sManager;

  private SoundPool soundPool;

  private HashMap<Integer, Integer> map;

  private Context context;

  //�̱��� ����

  private SoundManager(){}

  public static SoundManager getInstance(){

   if(sManager==null){

    sManager = new SoundManager();

   }

   return sManager;

  }

  //�ʱ�ȭ�ϱ�

  public void init(Context context){

   this.context=context;

   //�ʿ��� ��ÿ�� �����ؼ� soundpool ��ü�� �����Ѵ�

   soundPool=new SoundPool(5, AudioManager.STREAM_MUSIC, 0);

   //���� ���ҽ� id ���� ������ hashMap ��ü �����ϱ�

   map = new HashMap<Integer, Integer>();

  

  }

  //������ �߰��ϴ� �޼ҵ�(resourceID=R.raw.����)

  public void addSound(int index, int resId){

   //���ڷ� ���޵� resId ���� �̿��ؼ� ���带 �ε���Ű�� ����� �غ� �Ѵ�.

   int id = soundPool.load(context, resId, 1);

   //����ϰ� ���ϵǴ� ���̵� �ʿ� �����Ѵ�.

   map.put(index, id);

  }

  //������ ����ϴ� �޼ҵ�

  public void play(int index){

   //���ڷ� ���޵� �ε��� ���� �̿��ؼ� �ش� ������ ����ϵ��� �Ѵ�.

   soundPool.play(map.get(index), 1, 1, 1, 0, 1);

  }

  //������ �����ϴ� �޼ҵ�

  public void stopSound(int index){

   //���ڷ� ���޵� �ε��� ���� �̿��ؼ� �ش� ������ ������Ų��.

   soundPool.stop(map.get(index));

  }
  public void ClearSound(int resId){

	   //���ڷ� ���޵� resId ���� �̿��ؼ� ���带 �ε���Ű�� ����� �غ� �Ѵ�.

	   int id = soundPool.load(context, resId, 1);

	   //����ϰ� ���ϵǴ� ���̵� �ʿ� �����Ѵ�.

	   map.clear();
	  }

}



