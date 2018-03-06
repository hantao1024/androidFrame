package frame.base.com.publicutils;

import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 这个工具类用来说明一些文件通用的操作和判断
 * @author liuguoyan
 * @date 2014-8-31
 */
public final class FileUitls {
	
	
	/**
	 * 移动文件
	 * @param srcFile
	 * @param destPath
	 * @return
	 */
	public static boolean Move(File srcFile, String destPath)
	 {
	        // Destination directory
	        File dir = new File(destPath);
	       
	        // Move file to new directory
	        boolean success = srcFile.renameTo(new File(dir, srcFile.getName()));
	       
	        return success;
	 }
	 
	/**
	 * 移动文件
	 * @param srcFile
	 * @param destPath
	 * @return
	 */
	 public static boolean Move(String srcFile, String destPath)
	 {
	        // File (or directory) to be moved
	        File file = new File(srcFile);
	       
	        // Destination directory
	        File dir = new File(destPath);
	       
	        // Move file to new directory
	        boolean success = file.renameTo(new File(dir, file.getName()));
	       
	        return success;
	    }
	
	/**
	 * 判断文件是否存在，有返回TRUE，否则FALSE
	 * 
	 * @return
	 */
	public static boolean exists(String fullName) {
		try {
			if (TextUtils.isEmpty(fullName)) {
				return false;
			}
			return new File(fullName).exists();
		} catch (Exception e) {
			LogUtils.e(e);
			return false;
		}

	}

	public static boolean isReadable(String path) {
		try {
			if (TextUtils.isEmpty(path)) {
				return false;
			}
			File f = new File(path);
			return f.exists() && f.canRead();
		} catch (Exception e) {
			LogUtils.e(e);
			return false;
		}

	}

	public static boolean isWriteable(String path) {
		try {
			if (TextUtils.isEmpty(path)) {
				return false;
			}
			File f = new File(path);
			return f.exists() && f.canWrite();
		} catch (Exception e) {
			LogUtils.e(e);
			return false;
		}
	}

	public static boolean createDir(String dirPath) {
		File file = new File(dirPath);
		if (!file.exists() || !file.isDirectory()) {
			return file.mkdir();
		}
		return true;
	}

	public static boolean createDirs(String dirPath) {
		File file = new File(dirPath);
		if (!file.exists() || !file.isDirectory()) {
			return file.mkdirs();
		}
		return true;
	}

	public static long getSize(String path) {
		if (null == path) {
			return -1l;
		}
		File f = new File(path);
		if (f.isDirectory()) {
			return -1l;
		} else {
			return f.length();
		}
	}

	public static boolean writeFile(InputStream is, String path,
                                    boolean recreate) {
		boolean res = false;
		File f = new File(path);
		FileOutputStream fos = null;
		try {
			if (recreate && f.exists()) {
				f.delete();
			}

			if (!f.exists() && null != is) {
				int count = -1;
				byte[] buffer = new byte[1024];
				fos = new FileOutputStream(f);
				while ((count = is.read(buffer)) != -1) {
					fos.write(buffer, 0, count);
				}
				res = true;
			}
		} catch (FileNotFoundException e) {
			LogUtils.e(e);
		} catch (IOException e) {
			LogUtils.e(e);
		} catch (Exception e) {
			LogUtils.e(e);
		} catch (Throwable e) {
			LogUtils.e(e);
		} finally {
			try {
				if (null != fos) {
					fos.close();
					fos = null;
				}
				if (null != is) {
					is.close();
					is = null;
				}
			} catch (IOException e) {
				LogUtils.e(e);
			}
		}

		return res;
	}

	public static boolean writeFile(String content, String path, boolean append) {
		boolean res = false;
		File f = new File(path);
		RandomAccessFile raf = null;
		try {
			if (f.exists()) {
				if (!append) {
					f.delete();
					f.createNewFile();
				}
			} else {
				f.createNewFile();
			}

			if (f.canWrite()) {
				raf = new RandomAccessFile(f, "rw");
				raf.seek(raf.length());
				raf.write(content.getBytes());
				res = true;
			}
		} catch (FileNotFoundException e) {
			LogUtils.e(e);
		} catch (IOException e) {
			LogUtils.e(e);
		} catch (Exception e) {
			LogUtils.e(e);
		} finally {
			try {
				if (null != raf) {
					raf.close();
					raf = null;
				}
			} catch (IOException e) {
				LogUtils.e(e);
			}
		}

		return res;
	}

	public static boolean writeFile(int content, String path, boolean append) {
		boolean res = false;
		File f = new File(path);
		RandomAccessFile raf = null;
		try {
			if (f.exists()) {
				if (!append) {
					f.delete();
					f.createNewFile();
				}
			} else {
				f.createNewFile();
			}

			if (f.canWrite()) {
				raf = new RandomAccessFile(f, "rw");
				raf.seek(raf.length());
				raf.writeInt(content);
				res = true;
			}
		} catch (FileNotFoundException e) {
			LogUtils.e(e);
		} catch (IOException e) {
			LogUtils.e(e);
		} catch (Exception e) {
			LogUtils.e(e);
		} finally {
			try {
				if (null != raf) {
					raf.close();
					raf = null;
				}
			} catch (IOException e) {
				LogUtils.e(e);
			}
		}

		return res;
	}

	public static void writeProperties(String filePath, String key,
                                       String value, String comment) {
		writeProperties(new File(filePath), key, value, comment);
	}

	public static void writeProperties(File f, String key, String value,
                                       String comment) {
		if (key == null || value == null) {
			return;
		}
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			if (!f.exists() || !f.isFile()) {
				f.createNewFile();
			}
			fis = new FileInputStream(f);
			Properties p = new Properties();
			p.load(fis);
			p.setProperty(key, value);
			fos = new FileOutputStream(f);
			p.store(fos, comment);
		} catch (IllegalArgumentException e) {
			LogUtils.e(e);
		} catch (IOException e) {
			LogUtils.e(e);
		} finally {
			try {
				if (null != fis) {
					fis.close();
					fis = null;
				}
				if (null != fos) {
					fos.close();
					fos = null;
				}
			} catch (IOException e) {
				LogUtils.e(e);
			}
		}
	}

	public static Integer readInt(String path) {
		Integer res = null;
		File f = new File(path);
		RandomAccessFile raf = null;
		try {
			if (!f.exists()) {
				return null;
			}

			if (f.canWrite()) {
				raf = new RandomAccessFile(f, "r");
				res = raf.readInt();
			}
		} catch (FileNotFoundException e) {
			LogUtils.e(e);
		} catch (IOException e) {
			LogUtils.e(e);
		} catch (Exception e) {
			LogUtils.e(e);
		} finally {
			try {
				if (null != raf) {
					raf.close();
					raf = null;
				}
			} catch (IOException e) {
				LogUtils.e(e);
			}
		}
		return res;
	}
	
	public static boolean copyFile(String srcPath, String destPath) {
		File srcFile = new File(srcPath);
		File destFile = new File(destPath);
		return copyFile(srcFile, destFile);
	}
	
	/**
	 * 文件拷贝
	 */
	public static boolean copyFile(File srcFile, File destFile) {
		if (!srcFile.exists() || !srcFile.isFile()) {
			return false;
		}
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(srcFile);
			out = new FileOutputStream(destFile);
			byte[] buffer = new byte[1024];
			int i = -1;
			while ((i = in.read(buffer)) > 0) {
				out.write(buffer, 0, i);
			}
			out.flush();
			srcFile.delete();
		} catch (Exception e) {
			LogUtils.e(e);
			return false;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					LogUtils.e(e);
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					LogUtils.e(e);
				}
			}

		}

		return true;
	}

	public static boolean deleteFile(String path) {
		return new File(path).delete();
	}
	
	public static boolean deleteFilesInDir(String dir){
		
		File directory = new File(dir) ;
		
		if (!directory.isDirectory()) {
			return false ; 
		}
		
		File[]fiels = directory.listFiles() ;

		try {
			for (int i = 0; i < fiels.length; i++) {
                fiels[i].delete()  ;
            }
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false; 
	}
	
	public static boolean deleteDirs(String dirPath) {
		File dir = new File(dirPath);
		if (!dir.exists()) {
			return true;
		}
		if (dir.isDirectory()) {
			File[] files = dir.listFiles();
			if (files != null) {
				for (File f : files) {
					f.delete();
				}
			}
			return dir.delete();
		}
		return false;
	}
	
	public static boolean deleteAllDirs(String dirPath) {
		File dir = new File(dirPath);
		if (!dir.exists()) {
			return true;
		}
		if (dir.isDirectory()) {
			File[] files = dir.listFiles();
			if (files != null) {
				for (File f : files) {
					if (f.isDirectory()) {
						deleteAllDirs(f.toString());
					}else {
						f.delete();
					}
				}
			}
			return dir.delete();
		}
		return false;
	}

	public static void chmod(String path, String mode) {
		try {
			String command = "chmod " + mode + " " + path;
			Runtime runtime = Runtime.getRuntime();
			runtime.exec(command);
		} catch (IOException e) {
			LogUtils.e(e);
		}
	}

	public static boolean zip(String srcPath, String destPath, String zipEntry) {
		return zip(new File(srcPath), new File(destPath), zipEntry);
	}

	public static boolean zip(File srcFile, File destFile, String zipEntry) {
		boolean res = false;
		if (null == srcFile || !srcFile.exists() || !srcFile.canRead()) {
			return false;
		}
		if (destFile.exists()) {
			destFile.delete();
		}
		ZipOutputStream zos = null;
		FileOutputStream fos = null;
		FileInputStream fis = null;
		try {
			fos = new FileOutputStream(destFile, false);
			zos = new ZipOutputStream(fos);

			ZipEntry entry = new ZipEntry(zipEntry);
			zos.putNextEntry(entry);
			fis = new FileInputStream(srcFile);
			byte[] buffer = new byte[32];
			int cnt = 0;
			while ((cnt = fis.read(buffer)) != -1) {
				zos.write(buffer, 0, cnt);
			}
			zos.flush();
			res = true;
		} catch (FileNotFoundException e) {
			LogUtils.e(e);
		} catch (Exception e) {
			LogUtils.e(e);
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
				if (fos != null) {
					fos.close();
				}
				if (zos != null) {
					zos.closeEntry();
					zos.close();
				}
			} catch (IOException e) {
				// ignored
			}

		}
		return res;
	}
	
	/**
	 * 从文件中读取String字符
	 */
	public static String readString(File file){
		if(!file.exists()){
			return "";
		}
		FileReader reader=null;
		char[] buf=new char[1024];
		StringBuilder builder=new StringBuilder();
		try {
			reader=new FileReader(file);
			while (reader.read(buf)>0) {
				builder.append(String.valueOf(buf));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			if(reader!=null)reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}
	
	/**
	 * 得到SD卡的路径,如:"/sdcard/",(返回最后带右斜杠)
	 * 如果SD不存在,那么返回null
	 * @return
	 */
	public static String getBaseSDPath(){
		if(!existSDCard())return null;
		return Environment.getExternalStorageDirectory()+ File.separator;
	}
	
	/**
	 * SD卡是否存在
	 * 
	 * @return
	 */
	public static boolean existSDCard() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else
			return false;
	}
	
	/**
	 * 文件重命名
	 * @param path 文件目录
	 * @param oldname 原来的文件名
	 * @param newname 新文件名
	 */
	public static void renameFile(String path, String oldname, String newname) {
		if (!oldname.equals(newname)) {// 新的文件名和以前文件名不同时,才有必要进行重命名
			File oldfile = new File(path + "/" + oldname);
			File newfile = new File(path + "/" + newname);
			if (!oldfile.exists()) {
				return;// 重命名文件不存在
			}
			if (newfile.exists())// 若在该目录下已经有一个文件和新文件名相同，则不允许重命名
				System.out.println(newname + "已经存在！");
			else {
				oldfile.renameTo(newfile);
			}
		} else {
			System.out.println("新文件名和旧文件名相同...");
		}
	}
	
	public static void copyDirectiory(String sourceDir, String targetDir) throws IOException {
        
        //新建目标目录
        
        (new File(targetDir)).mkdirs();
        
        //获取源文件夹当下的文件或目录
        File[] file=(new File(sourceDir)).listFiles();
        
        for (int i = 0; i < file.length; i++) {
            if(file[i].isFile()){
                //源文件
                File sourceFile=file[i];
                    //目标文件
                File targetFile=new File(new File(targetDir).getAbsolutePath()+ File.separator+file[i].getName());
                
                copyFile(sourceFile, targetFile);
            
            }
            
            
            if(file[i].isDirectory()){
                //准备复制的源文件夹
                String dir1=sourceDir+file[i].getName();
                //准备复制的目标文件夹
                String dir2=targetDir+"/"+file[i].getName();
                
                copyDirectiory(dir1, dir2);
            }
        }
        
    }
	
	public static boolean  isLocalPath(String path){
		
		if(StringUtil.isBlank(path))
			return false;
		if(path.startsWith("http")){
			return false;
		}else{
			return true;
		}
		
	}
	
}
