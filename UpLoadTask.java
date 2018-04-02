//package pkg;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLEncoder;
import java.util.Arrays;

public class UpLoadTask implements Runnable{

	private Socket serverSocket;
	private String file;
	private OutputStream outputStream;
	public UpLoadTask(Socket socket,String file) {
		serverSocket = socket;
		this.file = file;
	}
	
	@Override
	public void run() {
		File fileResource = new File(file);
		String fileName = null;
		FileInputStream fis = null;
		
		if(!fileResource.exists()) {
			log("file is not exists");
			return;
		}
		try {
			outputStream = serverSocket.getOutputStream();
			fileName = URLEncoder.encode(fileResource.getName(), "utf-8");
			String fileNameLenBinary = Integer.toBinaryString(fileName.length());
			StringBuilder sb = new StringBuilder();
			int numZero = 16 - fileNameLenBinary.length();
			for(int i=0;i<numZero;i++) {
				sb.append('0');
			}
			sb.append(fileNameLenBinary);
			byte[] fileNameLenBytes = sb.toString().getBytes("utf-8");
			outputStream.write(fileNameLenBytes);
//			log("fileNameLengthBinary bytes :"+Arrays.toString(fileNameLenBytes));
			byte[] fileNameBytes = fileName.getBytes("utf-8");
			outputStream.write(fileNameBytes);
//			log("fileNameBytes:"+Arrays.toString(fileNameBytes));
			
			if(!fileResource.canRead()) {
				log("read file failed");
				return;
			}
			
			fis = new FileInputStream(file);
			byte[] bytes = new byte[1024];
			long total = file.length();
			int len;
			long current = 0;
			while((len=fis.read(bytes))!=-1) {
				outputStream.write(bytes,0,len);
				current +=len;
//				log(fileName+" : "+current*100/total +" %");
			}
			
			Utils.close(serverSocket,outputStream,fis);
			log("upload: "+fileResource.getName()+" -- success !");
		} catch (Exception e1) {
			Utils.close(serverSocket,outputStream,fis);
			e1.printStackTrace();
		}
		
		
	}
	
	private static void log(String s) {
		System.out.println(s);
	}
}
