package co.com.contabilidad.microfocus.file;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FIleCreator {

	public static void createFile(String dir, String msg){
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(dir));
			writer.write(msg);
		     
		    writer.close();
		} catch (IOException e) {
			try {
				if(writer != null) {
				writer.close();}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			if(writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
