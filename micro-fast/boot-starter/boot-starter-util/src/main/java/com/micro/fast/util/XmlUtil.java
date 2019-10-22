package com.micro.fast.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.File;
import java.io.IOException;

/**
 * 修改xml某一节点的值
 */
public class XmlUtil {

    /**
     * @param xmlPath
     * @param xPathPattern
     * @param newValue
     */
    public Document modifyXmlText(String xmlPath,String xPathPattern,String newValue){
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;
        Document document = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(xmlPath);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取XPathFactory工厂对象
        XPathFactory xPathFactory = XPathFactory.newInstance();
        //通过XPathFactory工厂对象实例化XPath对象
        XPath xPath = xPathFactory.newXPath();
        XPathExpression compile;
        Object evaluate = null;
        try {
            //通xpath语句compile XPath语句"//book[price>80]"
            compile = xPath.compile(xPathPattern);
            //执行查询语句，输入compile参数和XPathConstants.NODESET返回值的类型
            evaluate = compile.evaluate(document, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        //将查询结果强转为NodeList类型
        NodeList  nodeList= (NodeList)evaluate;
        System.out.println(nodeList.getLength());
        for (int i = 0; i <nodeList.getLength() ; i++) {
            Element element = (Element) nodeList.item(i);
            element.setTextContent(newValue);
        }

        return document;
    }

    public void writeDocument(Document document,String xmlPath){
        TransformerFactory transformerFactory = TransformerFactory.newInstance();

        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT,"yes");
            transformer.transform(new DOMSource(document),
                    new StreamResult(new File(xmlPath)));
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        new XmlUtil().modifyXmlText("/root/dev/workspace/micro-fast/boot-starter/pom.xml","//parent/version","1");
//    }

}
