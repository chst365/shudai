package com.shudailaoshi.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.shudailaoshi.utils.exceptions.UtilException;
import com.shudailaoshi.utils.exceptions.UtilExceptionEnum;

@SuppressWarnings("deprecation")
public class HttpUtil {

	private static final String UTF8 = "utf-8";

	/**
	 * 
	 * 向指定 URL 发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * 
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * 
	 * @return 所代表远程资源的响应结果
	 * 
	 */
	public static String get(String url, String param) {
		BufferedReader in = null;
		try {
			// 打开和URL之间的连接
			URLConnection connection = new URL(url + "?" + param).openConnection();
			// 设置通用的请求属性
			setURLConnection(connection);
			// 建立实际的连接
			connection.connect();
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), UTF8));
			StringBuilder sb = new StringBuilder();
			for (String line; (line = in.readLine()) != null;) {
				sb.append(line);
			}
			return sb.toString();
		} catch (Exception e) {
			throw new UtilException(UtilExceptionEnum.HTTP_ERROR, e);
		} finally {
			IOUtils.closeQuietly(in);
		}
	}

	/**
	 * 
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * 
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * 
	 * @return 所代表远程资源的响应结果
	 * 
	 */
	public static String post(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		try {
			URLConnection connection = new URL(url).openConnection();
			// 设置通用的请求属性
			setURLConnection(connection);
			// 发送POST请求必须设置如下两行
			connection.setDoOutput(true);
			connection.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(new OutputStreamWriter(connection.getOutputStream(), UTF8));
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), UTF8));
			StringBuilder sb = new StringBuilder();
			for (String line; (line = in.readLine()) != null;) {
				sb.append(line);
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new UtilException(UtilExceptionEnum.HTTP_ERROR, e);
		} finally {
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(in);
		}
	}
	public static String doRefund(String url,String data,String mchId,String apiclientCertFilePath) {   
        /** 
         * 注意PKCS12证书 是从微信商户平台-》账户设置-》 API安全 中下载的 
         */  
		String jsonStr = "";
		FileInputStream instream = null;
		try {
			KeyStore keyStore  = KeyStore.getInstance("PKCS12");  
			instream = new FileInputStream(new File(apiclientCertFilePath));//P12文件目录  
            /** 
             * 此处要改 
             * */  
            keyStore.load(instream, mchId.toCharArray());//这里写密码..默认是你的MCHID  
            instream.close();  
  
            // Trust own CA and all self-signed certs  
	        /** 
	         * 此处要改 
	         * */  
	        SSLContext sslcontext = SSLContexts.custom()  
                .loadKeyMaterial(keyStore, mchId.toCharArray())//这里也是写密码的    
                .build();  
	        // Allow TLSv1 protocol only  
	        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(  
                sslcontext,  
                new String[] { "TLSv1" },  
                null,  
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);  
	        CloseableHttpClient httpclient = HttpClients.custom()  
                .setSSLSocketFactory(sslsf)  
                .build();  
	        try {  
	            HttpPost httpost = new HttpPost(url); // 设置响应头信息  
	            httpost.addHeader("Connection", "keep-alive");  
	            httpost.addHeader("Accept", "*/*");  
	            httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");  
	            httpost.addHeader("Host", "api.mch.weixin.qq.com");  
	            httpost.addHeader("X-Requested-With", "XMLHttpRequest");  
	            httpost.addHeader("Cache-Control", "max-age=0");  
	            httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");  
	            httpost.setEntity(new StringEntity(data, "UTF-8"));  
	            CloseableHttpResponse response = httpclient.execute(httpost);  
	            try {  
	                HttpEntity entity = response.getEntity();  
	                jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");  
	                EntityUtils.consume(entity);  
	            	return jsonStr;  
	            } finally {  
	                response.close();  
	            }  
	        } finally {  
	            httpclient.close();  
	        }  
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				instream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}  
		}
		return jsonStr;
    }  

	private static void setURLConnection(URLConnection connection) {
		connection.setRequestProperty("accept", "*/*");
		connection.setRequestProperty("connection", "Keep-Alive");
		connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	}

}