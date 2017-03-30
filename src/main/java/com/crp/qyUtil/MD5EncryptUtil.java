package com.crp.qyUtil;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;


public class MD5EncryptUtil {
	private static Logger logger = Logger.getLogger(MD5EncryptUtil.class);

	public static String createMD5Map(Map<String, String> Md5Map, String key) {
		try {
			if (Md5Map == null) {
				logger.info("Invalid map\n");
				return null;
			}

			if (key == null) {
				logger.info("Invalid key\n");
				return null;
			}

			if (key.length() < 7 || key.length() > 32) {
				logger.info("out of key len range\n");
				return null;
			}


			StringBuffer md5String = new StringBuffer();
			Set<String> set = Md5Map.keySet();

			int i = 0;
			String[] array = new String[set.size()];
			for (String string : set) {
				array[i] = string + "=" + Md5Map.get(string);
				i++;
			}
			Arrays.sort(array);

			for (int j = 0; j < array.length; j++) {
				md5String.append("|" + array[j]);
			}
         

			md5String.append(key);

			md5String.deleteCharAt(0);
			MessageDigest md;
			md = MessageDigest.getInstance("MD5");
			md.update(md5String.toString().getBytes());
			byte[] byteDigest = md.digest();
			return String.valueOf(byte2Hex(byteDigest));
		} catch (Exception e) {
			System.out.println(e);
			// TODO: handle exception
			// logger.Error(e);
			return null;
		}
	}

	public static String createMD5String(String md5String, String key) {
		try {
			if (md5String == null) {
				logger.info("Invalid xmldata\n");
				return null;
			}

			if (key == null) {
				logger.info("Invalid key\n");
				return null;
			}

			if (key.length() < 7 || key.length() > 32) {
				logger.info("out of key len range\n");
				return null;
			}
			md5String += key;

			MessageDigest md;
			md = MessageDigest.getInstance("MD5");
			md.update(md5String.getBytes());
			byte[] byteDigest = md.digest();
			return String.valueOf(byte2Hex(byteDigest));
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e);
			return null;
		}
	}

	public static String createMD5String(String md5String) {
		try {
			if (md5String == null) {
				logger.info("Invalid xmldata\n");
				return null;
			}

			MessageDigest md;
			md = MessageDigest.getInstance("MD5");
			md.update(md5String.getBytes());
			byte[] byteDigest = md.digest();
			return String.valueOf(byte2Hex(byteDigest));
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e);
			return null;
		}
	}

	public static String byte2Hex(byte[] buf) {

		StringBuffer strbuf = new StringBuffer();

		for (byte b : buf) {
			if (b == 0) {
				strbuf.append("00");
			} else if (b == -1) {
				strbuf.append("ff");
			} else {
				String str = Integer.toHexString(b).toLowerCase();
				if (str.length() == 8) {
					str = str.substring(6, 8);
				} else if (str.length() < 2) {
					str = "0" + str;
				}
				strbuf.append(str);
			}

		}

		return strbuf.toString();
	}
}
