package com.crp.qyUtil;

public class NetServiceException extends Exception { 

	/*** serial id*/
	private static final long serialVersionUID = 4109764123357142460L;

	/**
	 * 
	 * 空构造
	 */
	public NetServiceException(){
		super("ManagerException 异常");
	}
	
	/**
	 * 
	 * 自定义错误日志
	 * @param e
	 */
	public NetServiceException(String e){
		super(e);
	}
	
	/**
	 * 只抛错误信息
	 * @param e
	 */
	public NetServiceException(Throwable e){
		super(e);
	}
	/**
	 * 两者皆抛
	 * @param er
	 * @param e
	 */
	public NetServiceException(String er,Throwable e){
		super(er, e);
	}

}
