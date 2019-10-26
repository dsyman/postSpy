package it.poste.postspy.utility;

import it.poste.postspy.constant.MessageConstant;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipUtility {
	
	public ZipUtility(){
		
	}
	
	
	public boolean compress(String basefolder, String destfolder, String zipname){
		boolean res=false;
		try{
		FileOutputStream dest = new FileOutputStream(destfolder+File.separator+zipname);
        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
        File f = new File(basefolder);
        
        addToArchive(out,f, basefolder);
        if(out!=null)
        	out.close();
        if(dest!=null)
        	dest.close();
        res=true;
			}
        catch(FileNotFoundException e){
        	LogUtility.logError(MessageConstant.GENERIC_ERROR, e);
        	res=false;
			}
		catch (IOException e) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, e);
			res=false;
		}
		return res;
	}
	
	
	private void addToArchive(ZipOutputStream outStream, File srcfile, String basefolder) {
		FileInputStream fi=null;
		try{
		File files[] = srcfile.listFiles();
		for (File file: files){
            if(file.isDirectory()){
            String entryName=file.getPath().substring(basefolder.length()+1, file.getPath().length())+File.separator;
        	ZipEntry entry = new ZipEntry(entryName);
        	outStream.putNextEntry(entry);
         	addToArchive(outStream,file,basefolder);
         	outStream.closeEntry();
            }
        else{  
        	byte data[] = new byte[2048];
           	fi = new FileInputStream(file);
        	String entryName=file.getPath().substring(basefolder.length()+1, file.getPath().length());
        	ZipEntry entry = new ZipEntry(entryName);
        	outStream.putNextEntry(entry);
        	int length;
            while ((length = fi.read(data)) > 0) {
                outStream.write(data, 0, length);
            }
            outStream.closeEntry();
            }
            if(fi!=null)
            fi.close();
           
		}
	 }catch(IOException e){
		 LogUtility.logError(MessageConstant.GENERIC_ERROR, e);
		 }
}
	
	public String getZipFolder(String path){
		String pathRes=null;
		try {
			String regex="(C:\\\\Security Reviewer\\\\Client\\\\Results\\\\[a-zA-Z0-9._-]+\\\\[a-zA-Z0-9._-]+)\\\\";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(path);
			if (matcher.find())
			{
				pathRes=matcher.group(1);
			}			
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
		return pathRes;
	}
	
	public String getDestFolder(String path){
		String pathRes=null;
		try {
			String regex="(C:\\\\Security Reviewer\\\\Client\\\\Results\\\\[a-zA-Z0-9._-]+)\\\\";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(path);
			if (matcher.find())
			{
				pathRes=matcher.group(1);
			}			
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
		return pathRes;
	}
	
	public String getZipName(String path){
		String res=null;
		try {
			String zipname="@results.";
			String regex="(C:\\\\Security Reviewer\\\\Client\\\\Results\\\\[a-zA-Z0-9._-]+\\\\[a-zA-Z0-9._-]+)\\\\";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(path);
			if (matcher.find()){
				res=matcher.group(1);
				String ver= new File(res).getName();
				String project= new File(res).getParentFile().getName();
				res=zipname+project+"@"+ver+".zip";
			}
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
		return res;
	}
	
	public static File unzip(String destinationFolder, String zipFile) throws Exception {
		try {
			File directory = new File(destinationFolder);
			if(!directory.exists()) {
				directory.mkdirs();
			}
			ZipFile zipFile2 =  new ZipFile(zipFile);
			Enumeration<?> enu = zipFile2.entries();
			ZipEntry zipEntry;
			String name;
			Path path;
			String fileName = null;
			InputStream is;
			FileOutputStream fos;
			byte[] bytes = new byte[1];
			while (enu.hasMoreElements()) {
				zipEntry = (ZipEntry) enu.nextElement();
				name = zipEntry.getName();
				path = Paths.get(name);
				fileName = path.getName(0).toString();
				File file = new File(destinationFolder + File.separator + name);
				if (name.endsWith("/")) {
					file.mkdirs();
					continue;
				} else {
					file.getParentFile().mkdirs();
					file.createNewFile();
				}
				is = zipFile2.getInputStream(zipEntry);
				fos = new FileOutputStream(file);
				int length;
				while ((length = is.read(bytes)) >= 0) {
					fos.write(bytes, 0, length);
				}
				is.close();
				fos.close();
			}
			zipFile2.close();
			return new File(destinationFolder + File.separator + fileName);
		} catch(Exception e) {
			throw e;
		}
	}

}
