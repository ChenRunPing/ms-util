package com.crp.qyUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * 说明：文件处理助手类，包括上传文件的上下文路径信息
 * @create Jan 6, 2009 6:54:11 PM
 */
public class FileUtil {
	
	static final Logger logger = Logger.getLogger(FileUtil.class);
    
    public static final String LOGO_CONTEXT_PATH = "logo";
    
    public static final String IMAGES_CONTEXT_PATH = "b";
    
    public static final String SHOPRADIO_CONTEXT_PATH = "shopradio";
    
    //读取配置文件
	
	private static final FileUtil ic = new FileUtil();
	
	private String fileRoot ;
	
	private FileUtil() {
		try{
			Properties props = new Properties();   
			props.load(FileUtil.class.getClassLoader().getResourceAsStream("resources/upload.properties"));
			//读取相关配置
			fileRoot = props.getProperty("file.root");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 说明：替换路径中的.为系统中的文件路径分隔符号
	 * @param str
	 * @return
	 */
	public static final String replaceDot(String str){
		return str.replace(".",getFileSeparator());
	}
	
	public static final FileUtil getInstance(){
		return ic;
	}
	
	/**
	 * 说明：获取文件存储的root path.
	 * @return
	 */
	public static String getFileRootPath(){
		return ic.fileRoot;
	}
	
	/**
	 * 说明：根据上下文件路径文件名加文件存储root获取存储的文件全路径
	 * @param contextPath
	 * @param fileName
	 * @return
	 */
	public static String getFileFullPath(String fileContextPath){
		fileContextPath = fileContextPath.replace("/", getFileSeparator());
		return getFileRootPath()+fileContextPath;
	}
	
	/**
	 * 说明：获取user logo存储的上下文路径和文件名，获取整个文件存储的上下文路径
	 * @param userId
	 * @return \logo\10\0\11.jpg
	 */
	public static String getUserLogoFileContextPath(Long userId,String fileName){
		return getContextPath(LOGO_CONTEXT_PATH,userId)+fileName;
	}
	
	/**
	 * 说明：获取business的photo的地址
	 * @param businessId
	 * @param fileName
	 * @return
	 */
	public static final String getBusinessPhotoFileContextPath(Long businessId,String fileName){
		return getContextPath(IMAGES_CONTEXT_PATH,businessId)+fileName;
	}
	

	/**
	 * 说明：获取shop radio的上下文路径和文件名，获取整个文件存储的上下文路径
	 * @param userId
	 * @return
	 */
	public static String getShopRadioContextPath(Long userId,String fileName){
		return getContextPath(SHOPRADIO_CONTEXT_PATH,userId)+fileName;
	}
	
	/**
	 * 说明：返回上下文路径
	 * @param contextPath
	 * @param id
	 * @return
	 */
	public static String getContextPath(String contextPath,Long id){
		StringBuilder str = new StringBuilder();
		str.append("/")
		.append(contextPath)
		.append("/")
		.append(FileUtil.idToPath(String.valueOf(id)));
		return str.toString() ;
	}
	
	/**
	 * 说明：获取系统分隔符
	 * @return
	 */
	public static String getFileSeparator(){
		return System.getProperty("file.separator");
	}
	
    public static final String idToPath(String id){
    	if(StrUtil.isEmpty(id) || id.length()<4){
    		return "0/0/";
    	}
    	StringBuffer result = new StringBuffer(Integer.valueOf(id.substring(0,2)).toString());
    	result.append("/")
		.append(Integer.valueOf(id.substring(2,4)))
		.append("/");
    	return result.toString();
    }

    /**
     * 根据File对象创建一个文件。
     * 如果该文件已经存在直接返回true，如果该文件的父路径不存在，则直接创建
     *
     * @param file
     *
     * @return
     */
    public static boolean createFile(File file) {
        if (file == null) {
            throw new RuntimeException("Can not create null file.");
        }
        if (file.exists()) {
            return true;
        }
//        File parentDir = file.getParentFile();
//
//        if ((parentDir != null) && !parentDir.exists()) {
//            if (!parentDir.mkdirs()) {
//                return false;
//            }
//        }

        try {
            return file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

	/**
	 * 删除指定文件
	 * @param file
	 * @throws Exception
	 */
	public static void delFile(File file) throws Exception{
	    if(file.exists() && file.isFile()){
	      try{
	        file.delete();
	        logger.info("del file path:" + file.getAbsolutePath());
	      }
	      catch(Exception e){
	    	  throw new RuntimeException(e);
	      }
	    }
	    file=null;
    }
	
	/**
	 * 
	 * 说明：获得上传路径，并创建
	 * @param request
	 * @return
	 */
	public static String getFileDirectory(HttpServletRequest request){
		Date aDate = new Date(System.currentTimeMillis());
    	Calendar calendar = GregorianCalendar.getInstance();
    	calendar.setTime(aDate); 
    	String sep = System.getProperty("file.separator");
		StringBuilder str = new StringBuilder();
		str.append(sep)
		.append(calendar.get(Calendar.YEAR)).append(sep)
		.append(calendar.get(Calendar.MONTH)+1).append(sep)
		.append(calendar.get(Calendar.DATE));
		
		String uploadDir =  request.getSession().getServletContext().getRealPath("/videofile/")+str.toString();
		File dirPath = new File(uploadDir);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
		return uploadDir ;
	}
	/**
	 * 
	 * 说明：保存的文件名称
	 * @return
	 */
	public static String getFileDirectoryName(String filename){
		Date aDate = new Date(System.currentTimeMillis());
    	Calendar calendar = GregorianCalendar.getInstance();
    	calendar.setTime(aDate); 
    	String sep = System.getProperty("file.separator");
		StringBuilder str = new StringBuilder();
		str
		.append(calendar.get(Calendar.YEAR)).append("/")
		.append(calendar.get(Calendar.MONTH)+1).append("/")
		.append(calendar.get(Calendar.DATE)).append("/")
		.append(filename);
		return str.toString() ;
	}
	/**
	 * 
	 * 说明：获得文件名
	 * @param fileType
	 * @return
	 */
	public static String getFileName(String fileType){
		StringBuffer filepath = new StringBuffer("");
		filepath.append(System.currentTimeMillis()).append("_")
				.append(Math.round(Math.random()*899999+100000)).append(fileType);//uploadFile.getOriginalFilename()
		return filepath.toString();
	}

    public static void main(String[] args) {
    	Date aDate = new Date(System.currentTimeMillis());
    	Calendar calendar = GregorianCalendar.getInstance();
    	calendar.setTime(aDate); 
    	System.out.println("The YEAR is: " + calendar.get(Calendar.YEAR));
    	System.out.println("The MONTH is: " + calendar.get(Calendar.MONTH));
    	System.out.println("The DAY is: " + calendar.get(Calendar.DATE));
    	System.out.println("The HOUR is: " + calendar.get(Calendar.HOUR));
    	System.out.println("The MINUTE is: " + calendar.get(Calendar.MINUTE));
    	System.out.println("The SECOND is: " + calendar.get(Calendar.SECOND));
    	System.out.println("The AM_PM indicator is: " + calendar.get(Calendar.AM_PM)); 
    	
//    	System.out.println(FileUtil.getFileDirectory(".jpg","small_"));
//    	long l = System.currentTimeMillis();
//    	for(long i=1;i<1;i++){
//    		Long userId = i;
//    		String filePath = getFileFullPath(FileUtil.getUserLogoFileContextPath(userId,userId+".jpg"));
//    		System.out.println(filePath);
//    		createFile(new File(filePath));
//    	}
//    	System.out.println((System.currentTimeMillis()-l));
    	
    	try {
    		String sourcePath = "E:\\dd.jpg";
    		String newFile = "E:\\dd2.jpg";
			FileChannel srcChannel = new   FileInputStream(sourcePath).getChannel();
	        FileChannel dstChannel = new   FileOutputStream(newFile).getChannel(); 
	        dstChannel.transferFrom(srcChannel,0,srcChannel.size()); 
	        srcChannel.close(); 
	        dstChannel.close();
	        
	        
		} catch (Exception e) {
			e.printStackTrace(); 
		}
    }
    
    
}
