//package pkg;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URLDecoder;

public class DownLoadTask  implements Runnable{
	
	private Socket socket;
	private InputStream inputStream;
	//用来存放下载好的文件
	private String folderPath;
	//Socket流中的前16个字节存储了音频文件名的长度(以二进制表示)
	private final int LENGTH_STRIDE = 16;
	private final String RECEIVE_COMPLETE = "OK";
	public DownLoadTask(String folderPath,Socket socket) {
		this.socket = socket;
		this.folderPath = folderPath;
	}
	
	
	@Override
	public void run() {
		try {
			inputStream = socket.getInputStream();
			byte[] fileLenBytes = new byte[LENGTH_STRIDE];
			inputStream.read(fileLenBytes, 0, LENGTH_STRIDE);
//			log("fileLenBytes:"+Arrays.toString(fileLenBytes));
			
			StringBuilder sb = new StringBuilder();
			for(byte b:fileLenBytes) {
				sb.append((char)b);
			}
			//将文件名长度信息转化为十进制
			int fileNameLen = Integer.parseInt(sb.toString(),2);
//			log("fileNameLen:"+fileNameLen);
			
			byte[] fileNameBytes = new byte[fileNameLen];	
			inputStream.read(fileNameBytes,0,fileNameLen);
			//经过URLEncoder编码过的fileName
			String encodedFileName = new String(fileNameBytes,"utf-8");
			String fileName = URLDecoder.decode(encodedFileName, "utf-8");
//			log("fileNameBytes:"+Arrays.toString(fileNameBytes));
//			log("encodedFileName:"+encodedFileName);
//			log("fileName:"+fileName);
			
			byte[] bytes = new byte[1024];
			int len;
			File file = new File(folderPath,fileName);
			if(!file.exists()) {
				file.createNewFile();
			}
			if(!file.canWrite()) {
				log("write file failed");
				return;
			}
			FileOutputStream os = new FileOutputStream(file);
			while((len = inputStream.read(bytes))!=-1) {
				os.write(bytes, 0, len);
			}
			Utils.close(inputStream,socket,os);
			log("\nReceive : "+fileName+" -- success !"+"\nlocation:"+file.getAbsolutePath());
		} catch (IOException e) {
			Utils.close(inputStream,socket);
			e.printStackTrace();
		}
		
	}
	
	private void log(String s) {
		System.out.println(s);
	}
}
