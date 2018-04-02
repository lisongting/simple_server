//package pkg;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 在Xbot上运行的服务端程序
 * @author lisongting
 */
public class Server {
	
	private static final int PORT = 9999;
	//存放下载文件的路径。
	//可以通过运行程序时设置这个路径：如 java Server  /home/path 
	private static String folderPath = "/home/lee/Downloads/";
	
	public static void main(String[] args) {
		ExecutorService threadPool ;
		ServerSocket serverSocket = null;
		threadPool = Executors.newFixedThreadPool(20);
		
		if(args.length==1) {
			if(folderPath.endsWith("/")) {
				folderPath = args[0];				
			}else {
				folderPath = args[0]+"/";
			}
		}
		File folder = new File(folderPath);
		if(!folder.isDirectory() ||!folder.exists()  ) {
			log("Please set directory correctly.");
			return;
		}
		
		try {
			serverSocket = new ServerSocket(PORT);
			log("Server is running");
			log("---------------------------------------------");
			while(true) {
				Socket socket = serverSocket.accept();
				//为每一个连接请求创建一个DownLoadTask
				DownLoadTask task = new DownLoadTask(folderPath,socket);
				threadPool.execute(task);
			}
		} catch (IOException e) {
			Utils.close(serverSocket);
			e.printStackTrace();
		}
		
	}
	
	private static void log(String s) {
		System.out.println(s);
	}

}
