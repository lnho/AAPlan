package com.lnho.aaplan.commons.utils;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

/**
 * XmlDom工具包.
 * 
 * @author qingwu
 * @date 2013-6-26 上午10:00:00
 */
public class XmlUtil {

	public static String XML_HEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	public static String ROOT_BEIGN = "<root>";
	public static String ROOT_END = "</root>";

	/**************************** dom4j begin ********************************/

	/**
	 * 字符串转org.dom4j.Document.
	 * 
	 * @param xml
	 *            xml字符串
	 * @return org.dom4j.Document文档对象
	 * @author qingwu
	 * @date 2013-6-26 上午10:00:00
	 */
	public static org.dom4j.Document strToDom4jDoc(String xml) {
		try {
			return DocumentHelper.parseText(xml);
		} catch (DocumentException e) {
			throw new UnCaughtException(e);
		}
	}

	/**
	 * 输入流转org.dom4j.Document
	 * 
	 * @param is
	 *            输入流
	 * @return org.dom4j.Document文档对象
	 * @author qingwu
	 * @date 2013-6-26 上午10:00:00
	 */
	public static org.dom4j.Document inputStreamToDom4jDoc(InputStream is) {
		try {
			SAXReader reader = new SAXReader();
			return reader.read(is);
		} catch (DocumentException e) {
			throw new UnCaughtException(e);
		}
	}

	/**
	 * org.dom4j.Document转字符串.
	 * 
	 * @param document
	 *            org.dom4j.Document文档对象
	 * @return xml字符串
	 * @author qingwu
	 * @date 2013-6-26 上午10:00:00
	 */
	public static String docToStr(org.dom4j.Document document) {
		return document.asXML();
	}

	/**
	 * 根据文件路径初始化org.dom4j.Document.
	 * 
	 * @param fileName
	 *            文件路径
	 * @return
	 * @author qingwu
	 * @date 2014-2-25 下午5:20:35
	 */
	public static org.dom4j.Document filePathToDom4jDoc(String fileName) {
		InputStream is = XmlUtil.class.getResourceAsStream(fileName);
		return inputStreamToDom4jDoc(is);
	}

	/**************************** dom4j end ********************************/

	/**************************** w3c begin ********************************/

	/**
	 * 字符串转org.w3c.dom.Document.
	 * 
	 * @param xml
	 *            xml字符串
	 * @return org.dom4j.Document文档对象
	 * @author qingwu
	 * @date 2013-6-26 上午10:00:00
	 */
	public static org.w3c.dom.Document strToW3cDoc(String xml) {
		DocumentBuilder builder;
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			return builder.parse(new InputSource(new StringReader(xml)));
		} catch (ParserConfigurationException e) {
			throw new UnCaughtException(e);
		} catch (SAXException e) {
			throw new UnCaughtException(e);
		} catch (IOException e) {
			throw new UnCaughtException(e);
		}
	}

	/**
	 * 输入流转org.w3c.dom.Document.
	 * 
	 * @param is
	 *            输入流
	 * @return org.dom4j.Document文档对象
	 * @author qingwu
	 * @date 2013-6-26 上午10:00:00
	 */
	public static org.w3c.dom.Document inputStreamToW3cDoc(InputStream is) {
		DocumentBuilder builder;
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			return builder.parse(is);
		} catch (ParserConfigurationException e) {
			throw new UnCaughtException(e);
		} catch (SAXException e) {
			throw new UnCaughtException(e);
		} catch (IOException e) {
			throw new UnCaughtException(e);
		}
	}

	/**
	 * org.w3c.dom.Document转字符串.
	 * 
	 * @param document
	 *            org.dom4j.Document文档对象
	 * @return xml字符串
	 * @author qingwu
	 * @date 2013-6-26 上午10:00:00
	 */
	public static String docToStr(org.w3c.dom.Document document) {
		String result = null;

		if (document != null) {
			StringWriter strWtr = new StringWriter();
			StreamResult strResult = new StreamResult(strWtr);
			TransformerFactory tfac = TransformerFactory.newInstance();
			try {
				javax.xml.transform.Transformer t = tfac.newTransformer();
				t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
				t.setOutputProperty(OutputKeys.INDENT, "yes");
				t.setOutputProperty(OutputKeys.METHOD, "xml"); // xml, html,
				// text
				t.setOutputProperty(
						"{http://xml.apache.org/xslt}indent-amount", "4");
				t.transform(new DOMSource(document.getDocumentElement()),
						strResult);
			} catch (Exception e) {
				System.err.println("XML.toString(Document): " + e);
			}
			result = strResult.getWriter().toString();
			try {
				strWtr.close();
			} catch (IOException e) {
				throw new UnCaughtException(e);
			}
		}

		return result;
	}
	
	/**
	 * 获得org.w3c.dom.Node节点下指定nodeName节点.
	 * 
	 * @param node
	 *            org.w3c.dom.Node
	 * @param nodeName
	 *            节点名称
	 * @return
	 * @author qingwu
	 * @date 2013-6-26 上午10:00:00
	 */
	public static org.w3c.dom.Node getChildNode(org.w3c.dom.Node node,
			String nodeName) {
		NodeList list = node.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			if (list.item(i).getNodeName().equals(nodeName)) {
				return list.item(i);
			}
		}
		return null;
	}

	/**************************** w3c end ********************************/

	/**
	 * 格式化XML字符串(utf-8编码).
	 * 
	 * @param xmlMsg
	 * @return
	 * @author qingwu
	 * @date 2013-6-26 上午10:00:00
	 */
	public static String formatXml(String xmlMsg) {
		return formatXml(xmlMsg, null);
	}

	/**
	 * 格式化XML字符串.
	 * 
	 * @param xmlMsg
	 * @param encoding
	 * @return
	 * @author qingwu
	 * @date 2013-6-26 上午10:00:00
	 */
	public static String formatXml(String xmlMsg, String encoding) {
		org.dom4j.Document _document = strToDom4jDoc(xmlMsg);
		String paramXML = "";
		if (encoding == null) {
			encoding = "utf-8";
		}
		try {
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding(encoding);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			XMLWriter writer = new XMLWriter(out, format);
			writer.write(_document);
			writer.flush();
			writer.close();
			paramXML = out.toString(format.getEncoding());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return paramXML;
	}

}
