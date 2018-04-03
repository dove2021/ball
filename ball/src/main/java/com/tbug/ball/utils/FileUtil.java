package com.tbug.ball.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

public class FileUtil {

	public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
		File targetFile = new File(filePath);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		FileOutputStream out = new FileOutputStream(filePath + fileName);
		out.write(file);
		out.flush();
		out.close();
	}

	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public static String renameToUUID(String fileName) {
		return UUID.randomUUID() + "." + fileName.substring(fileName.lastIndexOf(".") + 1);
	}
	
	public static boolean isPic(String fileName){
		if (fileName == null || fileName.isEmpty()){
			return false;
		}
		String suffix = fileName.split("\\.")[1];
		if ("jpg".equalsIgnoreCase(suffix)
				|| "JPEG".equalsIgnoreCase(suffix)
				|| "PNG".equalsIgnoreCase(suffix)
				|| "GIF".equalsIgnoreCase(suffix)
				|| "BMP".equalsIgnoreCase(suffix)){
			return true;
		}
		
		return false;
	}
}
