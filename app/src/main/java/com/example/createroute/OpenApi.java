package com.example.createroute;

import android.os.AsyncTask;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

public class OpenApi extends AsyncTask<Void, Void, String> {
    private String url;
    private String busNum;

    public OpenApi(String url, String busNum) {
        this.busNum = busNum;
        this.url = url;
    }

    @Override
    protected String doInBackground(Void... params) {
        Document document = null;
        ArrayList<String> selectedBus = new ArrayList<String>();
        try {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(url);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (SAXException saxException) {
            saxException.printStackTrace();
        } catch (ParserConfigurationException parserConfigurationException) {
            parserConfigurationException.printStackTrace();
        }

        // xpath 생성
        XPath xpath = XPathFactory.newInstance().newXPath();
        NodeList cols = null;
        try {
            cols = (NodeList)xpath.evaluate("//BusList/row", document, XPathConstants.NODESET);
        } catch (XPathExpressionException xPathExpressionException) {
            xPathExpressionException.printStackTrace();
        }
        //for( int idx=0; idx<cols.getLength(); idx++ ){
        //    System.out.println(cols.item(idx).getTextContent());
        //}

        DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactoty.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document doc = null;
        try {
            doc = dBuilder.parse(url);
        } catch (IOException | SAXException e) {
            e.printStackTrace();
        }

        // root tag
        doc.getDocumentElement().normalize();
        System.out.println("Root element: " + doc.getDocumentElement().getNodeName()); // Root element: result
        NodeList nBusCount = doc.getElementsByTagName("MsgHeader");
        Node nBusC = nBusCount.item(0);
        Element eElement = (Element) nBusC;
        int busCount = Integer.parseInt(getTagValue("rowCount", eElement));

        //String choose = "100"; // user search

        //Log.d("OPEN API","Route Count :" + doc.getElementsByTagName("rowCount").getTagValue );
        for (int temp = 1 ; temp <= busCount ; temp++ ) {
            String rID = null;
            String rNUM = null;
            try {
                rID = (String) xpath.evaluate("//*[@index='" + Integer.toString(temp) + "']/ROUTE_ID", document, XPathConstants.STRING);
                rNUM = (String) xpath.evaluate("//*[@index='" + Integer.toString(temp) + "']/ROUTE_NUM", document, XPathConstants.STRING);
                if(rNUM.matches(busNum)) {
                    Log.d("OPEN API", "버스 노선 ID : " + rID);
                    //Log.d("OPEN API", "버스 노선 타입 : " + rTP;
                    Log.d("OPEN API", "버스 노선 번호 : " + rNUM);
                    //if(rNUM.matches(busNum)) {
                    selectedBus.add(rID);
                    selectedBus.add(rNUM);
                    //}
                }
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            }
        }

        return (selectedBus.get(0)+", " + selectedBus.get(1));
    }

    @Override
    protected void onPostExecute(String str) {
        super.onPostExecute(str);
    }


    private String getTagValue(String tag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        if (nValue == null)
            return null;
        return nValue.getNodeValue();
    }
}