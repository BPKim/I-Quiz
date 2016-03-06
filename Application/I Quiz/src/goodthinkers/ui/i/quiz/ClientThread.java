package goodthinkers.ui.i.quiz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import android.util.Log;

public class ClientThread extends Thread {

	static Socket Client = null;

	String ipAddress;
	int port = 5000;

	BufferedReader read;

	static InputStream is;
	static ObjectInputStream ois;

	static OutputStream os;
	static ObjectOutputStream oos;

	static String sendData =null;
	String receiveData;

	
	
	public ClientThread(String ip) {
		ipAddress = ip;
	}
	public static void Write(String w){
		
		try {
			oos.writeObject(w);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void ConnectExit(){
		  try {
			Log.d("TAG", "connectexit start");
			os.close(); 
			Log.d("TAG", "connectexit os close");
			oos.close();
			Log.d("TAG", "connectexit oos close");
			Client.close();
			Log.d("TAG", "ClientThread stop end");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.d("TAG", "ClientThread stop error");
			e.printStackTrace();
		}
	}
	public void run() {
		try {
			Log.d("TAG", "ClientThread Start");
			Client = new Socket(ipAddress, port);
			Log.d("TAG", "ClientThread Socket");
			//read = new BufferedReader(new InputStreamReader(Client.getInputStream()));
			Log.d("TAG", "ClientThread BufferReader");

			os = Client.getOutputStream();

			oos = new ObjectOutputStream(os);

				
			// text.setText("connect");
			// System.out.print("insert - >");

			// while((sendData = read.readLine())!=null){

			//while(sendData!=null){
			//	oos.writeObject(sendData);
			//	oos.flush();
			//	Log.d("TAG", "ConnectThread send");
			//	if(sendData.equals("quit"))
			//		break;
			//}	
				//receiveData = (String)ois.readObject();
				
				//System.out.println(Client.getInetAddress()+"로 부터 받은 메시지(에코됨):"+receiveData);
				//System.out.print("입력->");
			

			//}else
			//{
			//	oos.flush();
			//	Log.d("TAG", "ConnectThread flush");
		//	}
			

			// 

			// if(sendData.equals("quit"))
			// break;

			// receiveData = (String)ois.readObject();

			// System.out.println(Client.getInetAddress()+"로 부터 받은 메시지(에코됨):"+receiveData);
			// System.out.print("입력->");
			// }
			// is.close(); ois.close();
			// os.close(); oos.close();
			// Client.close();

		} catch (Exception e) {
			e.printStackTrace();
			Log.d("TAG", "ClientThread Error");
			// System.exit(0);
		}
	}
}
