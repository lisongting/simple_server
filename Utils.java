//package pkg;

import java.io.Closeable;
import java.io.IOException;

public class Utils {

	public static void close(Closeable c) {
		if(c!=null) {
			try {
				c.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void close(Closeable... closeList) {
		for(Closeable c:closeList) {
			if(c!=null) {
				try {
					c.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}


}
