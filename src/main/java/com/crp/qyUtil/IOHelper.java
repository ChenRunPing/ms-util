
package com.crp.qyUtil;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.commons.lang.StringUtils;

public abstract class IOHelper {

	/**
	 * 以非阻塞模式从 InputStream 中读取 Length 个字节
	 * 
	 * @param timeoutMillis
	 *            超时值（0代表永不超时）
	 * @param throwIOException
	 *            如果超时，抛出IOException 还是 返回null
	 * @return 读取的字节，或者null
	 * @throws IOException
	 */
	public static byte[] readBytes(InputStream is, int length, int timeoutMillis, boolean throwIOException) throws IOException {
		long ts = System.currentTimeMillis();
		while (true) {
			int n = is.available();
			if (n >= length) {
				byte[] buffer = new byte[length];
				is.read(buffer);
				return buffer;
			} else {
				if (timeoutMillis > 0 && System.currentTimeMillis() - ts >= timeoutMillis)
					if (throwIOException)
						throw new IOException("timeout: " + timeoutMillis + "ms");
					else
						return null;

				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	/**
	 * 读取当前非阻塞模式下可以 Available 的数据
	 * 
	 * @return 读取的字节，或者null
	 * @throws IOException
	 * @since 2007-12-04
	 */
	public static byte[] readAvailableBytes(InputStream is) throws IOException {
		int n = is.available();
		if (n > 0) {
			byte[] buffer = new byte[n];
			is.read(buffer);
			return buffer;
		}
		return null;
	}

	/**
	 * 以非阻塞模式从 InputStream 中读取内容，知道遇到 ch 字符为止
	 * 
	 * @param timeoutMillis
	 *            超时值（0代表永不超时）
	 * @param throwIOException
	 *            如果超时，抛出IOException 还是 返回null
	 * @return 读取的字节(包括ch字符)，或者null
	 * @throws IOException
	 * @since 2007-12-04
	 */
	public static byte[] readBytes(InputStream is, char ch, int timeoutMillis, boolean throwIOException) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		long ts = System.currentTimeMillis();
		while (true) {
			int n = is.available();
			if (n > 0) {
				for (int i = 0; i < n; i++) {
					int b = is.read();
					os.write(b);
					if (b == ch) {
						return os.toByteArray();
					}
				}
			} else {
				if (timeoutMillis > 0 && System.currentTimeMillis() - ts >= timeoutMillis)
					if (throwIOException)
						throw new IOException("timeout: " + timeoutMillis + "ms");
					else
						return null;

				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	/**
	 * writeBinaryString(os, "FF 01 00");
	 * @throws IOException 
	 * @since 2007-12-07
	 */
	public static void writeBinaryString(OutputStream os, String str) throws IOException {
		String[] ss = StringUtils.split(str, " ");
		for (int i = 0; i < ss.length; i++) {
			os.write((byte) Integer.parseInt(ss[i], 16));
		}
	}

	public static void safeClose(InputStream is) {
		try {
			if (is != null)
				is.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void safeClose(PrintWriter is) {

		if (is != null)
			is.close();

	}

	public static void safeClose(BufferedReader is) {
		try {
			if (is != null)
				is.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void safeClose(OutputStream os) {
		try {
			if (os != null)
				os.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void safeClose(Socket socket) {
		try {
			if (socket != null)
				socket.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void safeClose(ServerSocket socket) {
		try {
			if (socket != null)
				socket.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void safeClose(DatagramSocket udp) {
		try {
			if (udp != null)
				udp.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}