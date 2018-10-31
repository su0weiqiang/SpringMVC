package com.swq.WeiXinBasePackage.util;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

/**
 * 原生HTTP请求工具类
 * 
 * @author CX
 */
@SuppressWarnings("restriction")
public class HttpUtil {

	private final static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
	private final static int connectionTimeout = 3000;// 通信连接超时时间
	private final static int readTimeOut = 30000;// 通信读超时时间

	private static String httpPost(String urlString, String content, String encoding) {
		InputStream in = null;
		OutputStream outputStream = null;
		StringBuffer resultBuffer = new StringBuffer(1024);
		try {
			URL url = new URL(null, urlString, new sun.net.www.protocol.https.Handler());
			HttpURLConnection connection = createConnection(url, encoding);
			if (null == connection) {
				logger.error("HttpUtil创建POST联接失败");
				return null;
			}
			byte[] data = content.getBytes(encoding);
			connection.setRequestProperty("Content-Length", String.valueOf(data.length));
			outputStream = connection.getOutputStream();
			outputStream.write(data);// 写入需要发送的参数
			outputStream.flush();
			outputStream.close();
			if (200 == connection.getResponseCode()) {
				in = connection.getInputStream();
				resultBuffer.append(new String(read(in), encoding));
			} else {
				in = connection.getErrorStream();
				resultBuffer.append(new String(read(in), encoding));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		} finally {
			try {
				if (null != in) {
					in.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
				return null;
			}
		}
		return resultBuffer.toString();
	}

	public static String httpPost(String urlString, String content) {
		return httpPost(urlString, content, "UTF-8");
	}

	public static String httpGet(String urlString) {
		return httpGet(urlString, "UTF-8");
	}

	public static byte[] httpGetImg(String urlString) throws IOException {
		URL url = new URL(urlString);
		HttpURLConnection connection = createConnectionGet(url, "UTF-8");
		if (null == connection) {
			logger.error("创建联接失败");
			return null;
		}
		InputStream in = null;
		if (200 == connection.getResponseCode()) {
			in = connection.getInputStream();
		} else {
			in = connection.getErrorStream();
		}
		return IOUtils.toByteArray(in);
	}

	private static String httpGet(String urlString, String encoding) {
		InputStream in = null;
		StringBuffer resultBuffer = new StringBuffer(1024);
		try {
			URL url = new URL(null, urlString, new sun.net.www.protocol.https.Handler());
			HttpURLConnection connection = createConnectionGet(url, encoding);
			if (null == connection) {
				logger.error("HttpUtil创建GET联接失败");
				return null;
			}
			if (200 == connection.getResponseCode()) {
				in = connection.getInputStream();
				resultBuffer.append(new String(read(in), encoding));
			} else {
				in = connection.getErrorStream();
				resultBuffer.append(new String(read(in), encoding));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		} finally {
			try {
				if (null != in) {
					in.close();
				}
			} catch (IOException e) {
				logger.error("[关闭流异常][错误信息：" + e.getMessage() + "]");
				return null;
			}
		}
		return resultBuffer.toString();
	}

	private static byte[] read(InputStream in) throws IOException {
		byte[] buf = new byte[1024];
		int length = 0;
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		while ((length = in.read(buf, 0, buf.length)) > 0) {
			bout.write(buf, 0, length);
		}
		bout.flush();
		return bout.toByteArray();
	}

	/**
	 * 创建连接
	 *
	 * @return
	 * @throws ProtocolException
	 */
	private static HttpURLConnection createConnection(URL url, String encoding) {
		HttpURLConnection httpURLConnection = null;
		try {
			httpURLConnection = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			logger.error("[打开链接异常][错误信息：" + e.getMessage() + "]");
			return null;
		}
		httpURLConnection.setConnectTimeout(connectionTimeout);// 连接超时时间
		httpURLConnection.setReadTimeout(readTimeOut);// 读取结果超时时间
		httpURLConnection.setDoInput(true); // 可读
		httpURLConnection.setDoOutput(true); // 可写
		httpURLConnection.setUseCaches(false);// 取消缓存
		httpURLConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=" + encoding);
		try {
			httpURLConnection.setRequestMethod("POST");// 设置调用POST
		} catch (ProtocolException e) {
			logger.error("[设置请求方法异常][错误信息：" + e.getMessage() + "]");
			return null;
		}
		if ("https".equalsIgnoreCase(url.getProtocol())) {
			HttpsURLConnection husn = (HttpsURLConnection) httpURLConnection;
			husn.setSSLSocketFactory(new BaseHttpSSLSocketFactory());
			husn.setHostnameVerifier(new BaseHttpSSLSocketFactory.TrustAnyHostnameVerifier());// 解决由于服务器证书问题导致HTTPS无法访问的情况
			return husn;
		}
		return httpURLConnection;
	}

	/**
	 * 创建连接
	 *
	 * @return
	 * @throws ProtocolException
	 */
	private static HttpURLConnection createConnectionGet(URL url, String encoding) {
		HttpURLConnection httpURLConnection = null;
		try {
			httpURLConnection = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			logger.error("[打开链接异常][错误信息：" + e.getMessage() + "]");
			return null;
		}
		httpURLConnection.setConnectTimeout(connectionTimeout);// 连接超时时间
		httpURLConnection.setReadTimeout(readTimeOut);// 读取结果超时时间
		httpURLConnection.setUseCaches(false);// 取消缓存
		httpURLConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=" + encoding);
		try {
			httpURLConnection.setRequestMethod("GET");
		} catch (ProtocolException e) {
			logger.error("[设置请求方法异常][错误信息：" + e.getMessage() + "]");
			return null;
		}
		if ("https".equalsIgnoreCase(url.getProtocol())) {
			HttpsURLConnection husn = (HttpsURLConnection) httpURLConnection;
			husn.setSSLSocketFactory(new BaseHttpSSLSocketFactory());
			husn.setHostnameVerifier(new BaseHttpSSLSocketFactory.TrustAnyHostnameVerifier());// 解决由于服务器证书问题导致HTTPS无法访问的情况
			return husn;
		}
		return httpURLConnection;
	}

}