package com.shudailaoshi.utils;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * 
 * @author Heguoyong
 *
 */
public class PublicUtil {

	private PublicUtil() {
	}
	/**
	 * 获取预约编号
	 * @return
	 */ 
	public static String getBespeakNo() {
		return UUID.randomUUID().toString().replaceAll("-", "").substring(0,20).toUpperCase();
	}
	/**
	 * Xml字符串转Map
	 * @return
	 */ 
	public static Map<String, String> xmlStrToMap(String protocolXML) {  
		
		Map<String, String> map = new WeakHashMap<String, String>();
		try {  
			protocolXML = protocolXML.replaceAll("\n", "");
            DocumentBuilderFactory factory = DocumentBuilderFactory  
                    .newInstance();  
            DocumentBuilder builder = factory.newDocumentBuilder();  
            Document doc = builder  
                    .parse(new InputSource(new StringReader(protocolXML)));  

            Element root = doc.getDocumentElement();  
            NodeList books = root.getChildNodes();  
            if (books != null) {  
               for (int i = 0; i < books.getLength(); i++) {  
                    Node book = books.item(i);  
                    map.put(book.getNodeName(), book.getFirstChild() == null ? "" : book.getFirstChild().getNodeValue());
                    /*System.out.println("节点=" + book.getNodeName() + "\ttext=" + book.getFirstChild().getNodeValue());*/
                }  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return map;
    }  
	
	/**
	 * 基于余弦定理求两经纬度之间的距离，单位（KM）
	 * 
	 * @param lat1
	 *            第一个纬度
	 * @param lng1
	 *            第一个经度
	 * @param lat2
	 *            第二个纬度
	 * @param lng2
	 *            第二个经度
	 * @return
	 */
	public static double getDistance(BigDecimal lat1, BigDecimal lng1, BigDecimal lat2, BigDecimal lng2) {
		
		double radLat1 = rad(lat1.doubleValue());
		double radLat2 = rad(lat2.doubleValue());
		double a = radLat1 - radLat2;
		double b = rad(lng1.doubleValue()) - rad(lng2.doubleValue());
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = NumberUtil.div(Math.round(s * 1000), 1000);// 返回千米数
		return s;
	}
	
	private static double EARTH_RADIUS = 6378.137;// 地球半径

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}
	public static void main(String[] args) {
		System.out.println( getDistance(BigDecimal.valueOf(31.24916171),BigDecimal.valueOf(121.48789949),BigDecimal.valueOf(31.22085),BigDecimal.valueOf(121.440544)));
	}
}
