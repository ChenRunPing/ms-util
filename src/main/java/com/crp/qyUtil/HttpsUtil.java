package com.crp.qyUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

/**
 * 公众平台通用接口工具类
 * 
 * @author
 * @date 2013-08-09
 */
public class HttpsUtil {

	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static String httpsRequest(String requestUrl, String requestMethod,
			String outputStr) {
		// ======添加以下代码，避免证书名localhost错误的问题 start=========
		System.setProperty("java.protocol.handler.pkgs", "javax.net.ssl");
		HostnameVerifier hv = new HostnameVerifier() {
			public boolean verify(String urlHostName, SSLSession session) {
				return urlHostName.equals(session.getPeerHost());
			}
		};
		HttpsURLConnection.setDefaultHostnameVerifier(hv);
		// ======添加以下代码，避免证书名localhost错误的问题 end=========

		// JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			// SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url
					.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);
			httpUrlConn.setConnectTimeout(10000);
			httpUrlConn.setReadTimeout(10000);
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			return buffer.toString();
			// jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (ConnectException ce) {
			ce.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// return jsonObject;
		return outputStr;
	}

	public static void main(String[] args) {
		// String token =
		// "9xCvsNqn4WHMvriqQNr9_7dnYeF3qXup5f33hez-SCQby0ZHu8RVDvuUsOMsutw0751laSBxHnQX5W19gGVJng5CZZIPabsPOsnEisZrUN8";
		// String requestUrl = "https://localhost:8443/QuyiPay/deal";
		// String requestMethod = "POST";
		// String outputStr =
		// "{\"button\":[{\"name\":\"关于12\",\"sub_button\":[{\"type\":\"click\",\"name\":\"公司介绍\",\"key\":\"submenu1_1\"},{\"type\":\"click\",\"name\":\"主要资质\",\"key\":\"submenu1_2\"},{\"type\":\"click\",\"name\":\"行业解决方案\",\"key\":\"submenu1_3\"},{\"type\":\"click\",\"name\":\"成功案例\",\"key\":\"submenu1_4\"},{\"type\":\"click\",\"name\":\"新闻报道\",\"key\":\"submenu1_5\"}]},{\"name\":\"公告栏\",\"sub_button\":[{\"type\":\"click\",\"name\":\"销售部门\",\"key\":\"submenu2_1\"},{\"type\":\"click\",\"name\":\"交付部门\",\"key\":\"submenu2_2\"},{\"type\":\"click\",\"name\":\"研发部门\",\"key\":\"submenu2_3\"},{\"type\":\"click\",\"name\":\"职能部门\",\"key\":\"submenu2_4\"}]},{\"type\":\"view\",\"name\":\"联系我们\",\"url\":\"http://www.huateng.com/\"}]}";
		// HttpsUtil.httpRequest(requestUrl, requestMethod, outputStr);

		httpsRequest("https://localhost:8443/QuyiPay/deal", "POST", null);
	}
}
