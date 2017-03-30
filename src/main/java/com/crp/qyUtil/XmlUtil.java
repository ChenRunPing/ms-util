package com.crp.qyUtil;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class XmlUtil {

	public static Map<String, Object> xmlToMap(String xmlStr) {
		Document document;
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		;
		try {
			document = DocumentHelper.parseText(xmlStr);
			map = domToMap(document);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return map;
	}

	public static Map<String, Object> domToMap(Document doc) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		if (doc == null)
			return map;
		Element root = doc.getRootElement();
		for (Iterator iterator = root.elementIterator(); iterator.hasNext();) {
			Element e = (Element) iterator.next();
			List list = e.elements();
			if (list.size() > 0) {
				map.put(e.getName(), domToMap(e));
			} else
				map.put(e.getName(), e.getText());
		}
		return map;
	}

	public static Map domToMap(Element e) {
		Map map = new LinkedHashMap();
		List list = e.elements();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Element iter = (Element) list.get(i);
				List mapList = new ArrayList();

				if (iter.elements().size() > 0) {
					Map m = domToMap(iter);
					if (map.get(iter.getName()) != null) {
						Object obj = map.get(iter.getName());
						if (!obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = new ArrayList();
							mapList.add(obj);
							mapList.add(m);
						}
						if (obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = (List) obj;
							mapList.add(m);
						}
						map.put(iter.getName(), mapList);
					} else
						map.put(iter.getName(), m);
				} else {
					if (map.get(iter.getName()) != null) {
						Object obj = map.get(iter.getName());
						if (!obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = new ArrayList();
							mapList.add(obj);
							mapList.add(iter.getText());
						}
						if (obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = (List) obj;
							mapList.add(iter.getText());
						}
						map.put(iter.getName(), mapList);
					} else
						map.put(iter.getName(), iter.getText());
				}
			}
		} else
			map.put(e.getName(), e.getText());
		return map;
	}

	public static String mapToXml(Map map, String node) {
		Document document = DocumentHelper.createDocument();
		Element nodeElement = document.addElement(node);
		for (Object obj : map.keySet()) {
			if (map.get(obj) instanceof String) {
				Element keyElement = nodeElement
						.addElement(String.valueOf(obj));
				keyElement.setText(String.valueOf(map.get(obj)));
			}
			if (map.get(obj) instanceof Map) {
				Element keyElement = nodeElement
						.addElement(String.valueOf(obj));
				mapToXml((Map) map.get(obj), keyElement);
			}
		}
		return doc2String(document);
	}

	public static void mapToXml(Map map, Element e) {
		for (Object obj : map.keySet()) {
			Element keyElement = e.addElement(String.valueOf(obj));
			keyElement.setText(String.valueOf(map.get(obj)));
		}
	}

	public static String doc2String(Document document) {
		String s = "";
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();

			OutputFormat format = new OutputFormat(" ", true, "UTF-8");
			XMLWriter writer = new XMLWriter(out, format);
			writer.write(document);
			s = out.toString("UTF-8");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return s;
	}

	public static void main(String[] args) throws DocumentException {
		String strxml = "<?xml version=\"1.0\" encoding=\"gb2312\"?>"
				+ "<Response><TradeCode>2155"
				+ "</TradeCode><HospitalID>03001</HospitalID>"
				+ "<HISDate>20160519015340</HISDate><HISTradeNo>3488515</HISTradeNo><ResultCode>0000</ResultCode><ResultContent>交易成功</ResultContent><CycleNum>5</CycleNum><IsLastPage>T</IsLastPage><Records>"
				+ "<Record><BankTradeNo>QY55200031461575471279</BankTradeNo><HISTradeNo>3154945</HISTradeNo><PatientID>0007859066</PatientID><PayCardNo>203876492</PayCardNo><PayType>QY</PayType><Money>1</Money><OperationType>1</OperationType><PatientName>王盼盼</PatientName><IoFlag>0</IoFlag><OperationDate>20160425171040</OperationDate></Record>"
				+ "<Record><BankTradeNo>QY55200031461575125631</BankTradeNo><HISTradeNo>3154931</HISTradeNo><PatientID>0007859066</PatientID><PayCardNo>203876492</PayCardNo><PayType>QY</PayType><Money>10</Money><OperationType>1</OperationType><PatientName>王盼盼</PatientName><IoFlag>0</IoFlag><OperationDate>20160425170504</OperationDate></Record>"
				+ "<Record><BankTradeNo>QY55200031461573647199</BankTradeNo><HISTradeNo>3154808</HISTradeNo><PatientID>0007859066</PatientID><PayCardNo>203876492</PayCardNo><PayType>QY</PayType><Money>0.10</Money><OperationType>1</OperationType><PatientName>王盼盼</PatientName><IoFlag>0</IoFlag><OperationDate>20160425164037</OperationDate></Record>"
				+ "<Record><BankTradeNo>QY55200031461547876189</BankTradeNo><HISTradeNo>3152001</HISTradeNo><PatientID>0007834781</PatientID><PayCardNo>203688904</PayCardNo><PayType>QY</PayType><Money>0.10</Money><OperationType>1</OperationType><PatientName>李红斌</PatientName><IoFlag>0</IoFlag><OperationDate>20160425093035</OperationDate></Record>"
				+ "<Record><BankTradeNo>QY55200031461546672962</BankTradeNo><HISTradeNo>3151705</HISTradeNo><PatientID>0007834781</PatientID><PayCardNo>203688904</PayCardNo><PayType>QY</PayType><Money>0.10</Money><OperationType>1</OperationType><PatientName>李红斌</PatientName><IoFlag>0</IoFlag><OperationDate>20160425091034</OperationDate></Record>"
				+ "</Records></Response>";
		System.out.println(xmlToMap(strxml));
	}
}
