package com.crp.qyUtil;

import java.util.Random;

/**
 * 说明：csrf相关实例类
 * @create May 18, 2010 9:08:17 PM
 */
public class CsrfHelper {

	/**
	 * 说明：csrf md5钥匙
	 */
	private static byte[] csrfMd5Str = "csrf_123_!@#$-~#@!".getBytes();
	
	/**
	 * 说明：将字符串处理成md5
	 * @param token
	 * @return
	 */
	public static String getMd5(String token){
		return EncryptHelper.encrypt3DES(token, csrfMd5Str);
	}

	/**
	 * 说明：将md5进行还原
	 * @param md5
	 * @return
	 */
	public static String parseMd5(String md5){
		return EncryptHelper.decrypt3DES(md5, csrfMd5Str);
	}
	
	/**
	 * 说明：token与md5进行匹配，检查是否一致
	 * @param token
	 * @param md5
	 * @return
	 */
	public static final boolean valid(String token,String md5){
		if(StrUtil.isNotEmpty(token)){
			return token.equals(parseMd5(md5));
		}else{
			return true;
		}
	}
	
	/**
	 * 说明：定义的随机参数
	 */
	private static Random random = new Random(9999999L);
	
	/**
	 * 说明：获取token与md5
	 * @return
	 */
	public static final String[] getTokenMd5(){
		String[] str = new String[2];
		str[0] = System.currentTimeMillis()+random.nextInt()+"";
		str[1] = getMd5(str[0]);
		return str;
	}
	
	/**
	 * 说明：测试
	 * @param args
	 */
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		for(int i=0;i<10000;i++){
			String str[] = getTokenMd5();
			valid(str[0], str[1]);
//			System.out.println(str[0] + "--" + str[1]);
//			System.out.println("valid:" + valid(str[0], str[1]));
		}
		
		System.out.println(System.currentTimeMillis()-startTime);
	}
}
