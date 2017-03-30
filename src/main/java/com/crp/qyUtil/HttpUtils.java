package com.crp.qyUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

public class HttpUtils {

	private static Logger logger = Logger.getLogger(HttpUtils.class);

	public static final String Response_Code = "Response-Code";

	public static final String Response_Message = "Response-Message";

	public static final String ENCODING = "Encoding";

	private static final int BUFFER_SIZE = 16 * 1024;

	/**
	 * 获得指定URL的数据
	 * 
	 * @param url
	 * @param headers
	 *            包含HTTP请求的头，所以Map中必须是<String,String>
	 * @param connectTimeout
	 *            设置连接超时时间，单位是ms
	 * @param readTimeout
	 *            设置读超时时间，单位是ms
	 * @return 内容，同时在headers中，包含HTTP响应的头， 其中： <code>Response-Code</code>对应
	 *         Response Code <code>Response-Message</code>对应 Response Message
	 *         <code>Encoding</code>对应响应内容体的Encoding
	 * @throws IOException
	 */
	public static byte[] webGet(URL url, Map headers,
			int connectTimeout, int readTimeout) throws IOException {
		if (headers == null)
			headers = new HashMap();
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) url.openConnection();

			String jdkV = System.getProperty("java.vm.version");
			if (jdkV != null && jdkV.indexOf("1.5") != -1) {
				Class clazz = conn.getClass();
				try {
					java.lang.reflect.Method method = clazz.getMethod(
							"setConnectTimeout", new Class[] { int.class });
					method.invoke(conn, new Object[] { new Integer(
							connectTimeout) });
					method = clazz.getMethod("setReadTimeout",
							new Class[] { int.class });
					method.invoke(conn,
							new Object[] { new Integer(readTimeout) });
				} catch (Exception ec) {
					ec.printStackTrace();
				}
			} else {
				System.setProperty("sun.net.client.defaultConnectTimeout", ""
						+ connectTimeout);
				System.setProperty("sun.net.client.defaultReadTimeout", ""
						+ readTimeout);
			}

			Iterator it = headers.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry me = (Map.Entry) it.next();
				String key = (String) me.getKey();
				String value = (String) me.getValue();
				if (key != null && value != null) {
					conn.setRequestProperty(key, value);
				}
			}

			String contentType = conn.getContentType();
			String encoding = null;
			if (contentType != null
					&& contentType.toLowerCase().indexOf("charset") > 0) {
				int k = contentType.toLowerCase().indexOf("charset");
				if (contentType.length() > k + 7) {
					String sss = contentType.substring(k + 7).trim();
					k = sss.indexOf("=");
					if (k >= 0 && sss.length() > k + 1) {
						encoding = sss.substring(k + 1).trim();
						if (encoding.indexOf(";") > 0) {
							encoding = encoding.substring(0,
									encoding.indexOf(";")).trim();
						}
					}

				}
			}
			headers.clear();

			int k = 0;
			String feildValue = null;
			while ((feildValue = conn.getHeaderField(k)) != null) {
				String key = conn.getHeaderFieldKey(k);
				k++;
				if (key != null) {
					headers.put(key, feildValue);
				}
			}
			headers.put("Response-Code", new Integer(conn.getResponseCode()));
			headers.put("Response-Message", conn.getResponseMessage());

			InputStream bis = conn.getInputStream();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte bytes[] = new byte[1024];
			int n = 0;
			while ((n = bis.read(bytes)) > 0) {
				out.write(bytes, 0, n);
			}
			bis.close();

			bytes = out.toByteArray();
			if (encoding == null) {
				try {
					for (int i = 0; i < 64 && i < bytes.length - 2; i++) {
						if (bytes[i] == '?' && bytes[i + 1] == '>') {
							String s = new String(bytes, 0, i);
							if (s.indexOf("encoding") > 0) {
								s = s.substring(s.indexOf("encoding") + 8);
								if (s.indexOf("=") >= 0) {
									s = s.substring(s.indexOf("=") + 1).trim();
									if (s.charAt(0) == '"') {
										s = s.substring(1);
									}
									if (s.indexOf("\"") > 0) {
										encoding = s.substring(0,
												s.indexOf("\""));

									}
								}
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (encoding == null) {
				encoding = "UTF-8";
			}
			headers.put("Encoding", encoding);

			return bytes;

		} catch (IOException e) {
			logger.error(url.toString() + e.getMessage(), e);
			throw e;
		} finally {
			if (conn != null) {
				try {
					conn.disconnect();
				} catch (Exception e) {
					logger.error(url.toString() + e.getMessage(), e);
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 向指定的URL的发送数据，并获得返回值
	 * 
	 * @param url
	 * @param headers
	 *            包含HTTP请求的头，所以Map中必须是<String,String>
	 * @param content
	 *            内容体
	 * @param connectTimeout
	 *            设置连接超时时间，单位是ms
	 * @param readTimeout
	 *            设置读超时时间，单位是ms
	 * 
	 * @return 内容，同时在headers中，包含HTTP响应的头， 其中： <code>Response-Code</code>对应
	 *         Response Code <code>Response-Message</code>对应 Response Message
	 *         <code>Encoding</code>对应响应内容体的Encoding
	 * @throws IOException
	 */
	public static byte[] webPost(URL url, byte[] content,
			Map headers, int connectTimeout, int readTimeout)
			throws IOException {
		HttpURLConnection urlconnection = null;
		try {
			urlconnection = (HttpURLConnection) url.openConnection();

			// set connectTimeout and readTimeout
			String jdkV = System.getProperty("java.vm.version");
			if (jdkV != null && jdkV.indexOf("1.5") != -1) {
				Class clazz = urlconnection.getClass();
				try {
					java.lang.reflect.Method method = clazz.getMethod(
							"setConnectTimeout", new Class[] { int.class });
					method.invoke(urlconnection, new Object[] { new Integer(
							connectTimeout) });
					method = clazz.getMethod("setReadTimeout",
							new Class[] { int.class });
					method.invoke(urlconnection, new Object[] { new Integer(
							readTimeout) });
				} catch (Exception ec) {
					ec.printStackTrace();
				}
			} else {
				System.setProperty("sun.net.client.defaultConnectTimeout", ""
						+ connectTimeout);
				System.setProperty("sun.net.client.defaultReadTimeout", ""
						+ readTimeout);
			}

			Iterator it = headers.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry me = (Map.Entry) it.next();
				String key = (String) me.getKey();
				String value = (String) me.getValue();
				if (key != null && value != null) {
					urlconnection.setRequestProperty(key, value);
				}
			}
			// urlconnection.setRequestProperty("Request-method", "post");
			urlconnection.setRequestProperty("Content-length",
					Integer.toString(content.length));
			urlconnection.setRequestProperty("Content-type",
					"text/xml; charset=UTF-8");
			urlconnection.setRequestProperty("Accept", "text/html");

			urlconnection.setRequestProperty("Connection", "close"); // http1.1
																		// 默认Connection:
																		// keep-alive

			urlconnection.setDoInput(true);
			urlconnection.setDoOutput(true);

			OutputStream outputstream = urlconnection.getOutputStream();
			outputstream.write(content);
			outputstream.flush();

			String contentType = urlconnection.getContentType();
			String encoding = null;
			if (contentType != null
					&& contentType.toLowerCase().indexOf("charset") > 0) {
				int k = contentType.toLowerCase().indexOf("charset");
				if (contentType.length() > k + 7) {
					String sss = contentType.substring(k + 7).trim();
					k = sss.indexOf("=");
					if (k >= 0 && sss.length() > k + 1) {
						encoding = sss.substring(k + 1).trim();
						if (encoding.indexOf(";") > 0) {
							encoding = encoding.substring(0,
									encoding.indexOf(";")).trim();
						}
					}

				}
			}
			headers.clear();

			int k = 0;
			String feildValue = null;
			while ((feildValue = urlconnection.getHeaderField(k)) != null) {
				String key = urlconnection.getHeaderFieldKey(k);
				k++;
				if (key != null) {
					headers.put(key, feildValue);
				}
			}
			headers.put("Response-Code",
					new Integer(urlconnection.getResponseCode()));
			headers.put("Response-Message", urlconnection.getResponseMessage());

			InputStream bis = urlconnection.getInputStream();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte bytes[] = new byte[1024];
			int n = 0;

			int count = 0;
			while ((n = bis.read(bytes)) > 0) {
				out.write(bytes, 0, n);
			}
			bis.close();
			bytes = out.toByteArray();
			out.close();
			outputstream.close();

			if (encoding == null) {
				encoding = "UTF-8";
			}
			headers.put("Encoding", encoding);

			return bytes;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (urlconnection != null) {
				urlconnection.disconnect();
			}
		}
	}

	private static boolean copyStream(InputStream in, OutputStream out) {
		try {
			try {
				in = new BufferedInputStream(in, BUFFER_SIZE);
				out = new BufferedOutputStream(out, BUFFER_SIZE);
				byte[] buffer = new byte[BUFFER_SIZE];
				int count;
				while ((count = in.read(buffer)) > 0) {
					out.write(buffer, 0, count);
				}
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			} finally {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static String ConvertToString(InputStream inputStream) {
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		StringBuilder result = new StringBuilder();
		String line = null;
		try {
			while ((line = bufferedReader.readLine()) != null) {
				result.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStreamReader.close();
				inputStream.close();
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result.toString();
	}

	public static void main(String[] args) throws Exception {
		URL url;
		url = new URL("http://192.168.20.70:12345/");
		Map headers = new HashMap();
		String str = "requestXml=<?xml version=\"1.0\" encoding=\"GB2312\" standalone=\"yes\" ?><TX><REQUEST_SN>201504211454</REQUEST_SN><CUST_ID>SHP301514697#001</CUST_ID><USER_ID>WLPT01</USER_ID>"
				+ "<PASSWORD>12312345</PASSWORD><TX_CODE>6W0100</TX_CODE><LANGUAGE>CN</LANGUAGE><TX_INFO><ACC_NO>31001600802050024861</ACC_NO></TX_INFO></TX>";
		byte[] content = str.getBytes();
		byte[] ret = null;
		ret = HttpUtils.webPost(url, content, headers, 5000, 5000);

		System.out.println("ret:" + new String(ret, "GBK"));
	}

}
