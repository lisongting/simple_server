//package pkg;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FakeAndroidClient {
	
	private static final String HOST = "127.0.0.1";
	private static final int PORT = 9999;
	public static void main(String[] args) {
		ArrayList<String> list = new ArrayList<>();
		ExecutorService threadPool ;
		threadPool = Executors.newFixedThreadPool(20);
		list.add("/home/lee/Bye Bye Bye - Lovestoned.mp3");
		list.add("/home/lee/Chris August - The Upside of Down.mp3");
		list.add("/home/lee/COP - 世末歌者（inst.）.mp3");
		list.add("/home/lee/DEPAPEPE - 禁じられた恋.mp3");
		
		for(String s:list) {
			Socket client;
			try {
				client = new Socket(HOST,PORT);
				UpLoadTask task = new UpLoadTask(client,s);
				threadPool.execute(task);
			}catch(ConnectException e) {
				log("Connection failed. Please check server or network");
				return;
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
//		log("所有文件上传完毕");
	}
	
	private static void log(String s) {
		System.out.println(s);
	}
}

