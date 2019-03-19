package com.hsy.study.baselibrary.utils;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;


/**
 * 字符串处理类
 * @author haosiyuan
 * @date 2019/1/27 7:56 PM
 */
public class CharacterHandlerUtil {

    private static final String LEFT_BRACE = "{";
    private static final String LEFT_BRACKET = "[";

    /**
     * json 格式化
     * @param json
     * @return
     */
    public static String jsonFormat(String json){
        if (TextUtils.isEmpty(json)) {
            return "Empty/Null json content";
        }
        String message = null;
        try {
            json = json.trim();
            if (json.startsWith(LEFT_BRACE)){
                JSONObject jsonObject = null;
                jsonObject = new JSONObject(json);
                message = jsonObject.toString(4);
            }else if (json.startsWith(LEFT_BRACKET)){
                JSONArray jsonArray = new JSONArray(json);
                message = jsonArray.toString(4);
            }
        } catch (JSONException e) {
            message = json;
        }
        return message;
    }

    /**
     * xml 格式化
     * @param xml
     * @return
     */
    public static String xmlFormat(String xml){
        if (TextUtils.isEmpty(xml)) {
            return "Empty/Null xml content";
        }
        String message;
        try {
            Source xmlInput = new StreamSource(new StringReader(xml));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(xmlInput, xmlOutput);
            message = xmlOutput.getWriter().toString().replaceFirst(">", ">\n");
        } catch (TransformerException e) {
            message = xml;
        }
        return message;
    }
}
