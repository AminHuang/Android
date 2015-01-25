package com.example.ex07;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.widget.Toast;

public class FileUtils {
	public void saveContent(Context context, String fileName, String fileText){
		try {
			FileOutputStream fos = context.openFileOutput(fileName, context.MODE_PRIVATE);
			fos.write(fileText.getBytes());
			fos.close();
			
			Toast.makeText(context, "保存成功", Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			Toast.makeText(context, "保存失败", Toast.LENGTH_LONG).show();
		}
		
	}
	
	public String readContent(Context context, String fileName) throws IOException {
		try {
			FileInputStream inputStream = context.openFileInput(fileName);
			byte[] b = new byte[inputStream.available()];
			inputStream.read(b);
			Toast.makeText(context, "读取成功", Toast.LENGTH_LONG).show();
			return new String(b);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
		
	}
	
	public void deleteFile (Context context, String fileName) {
		context.deleteFile(fileName);
	}

}
