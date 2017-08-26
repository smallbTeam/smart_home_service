/**
 * Company
 * Copyright (C) 2004-2017 All Rights Reserved.
 */
package com.atat.soft.message.action;

import com.atat.core.config.SpringBeanFactoryUtils;
import com.atat.soft.common.prop.SmsPaltformProperty;
import com.atat.soft.util.StringUtil;
import com.atat.soft.util.httpClient.URLUtil;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ligw
 * @version $Id shortMessageServiceImpl.java, v 0.1 2017-07-15 16:00 ligw Exp $$
 */
@Service
public class ShortMessageAction{
    protected static final Logger logger = LoggerFactory.getLogger(ShortMessageAction.class);

    /**
     * 发送短信 返回发送状态
     * @param mobelPhone
     * @param msmContent
     * @return
     */
    public static Integer sendShortMessage(String mobelPhone, String msmContent) {
        SmsPaltformProperty smsPaltformProperty = (SmsPaltformProperty)SpringBeanFactoryUtils.getBean("smsPaltformProperty");
        try {
            msmContent = URLEncoder.encode(msmContent, "utf-8");
            // String msmContent = randomCode;
            Map<String, Object> map = new HashMap<String, Object>();
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("Message", msmContent);
            paramMap.put("Phone", mobelPhone);
            paramMap.put("Timestamp", "0");
            paramMap.put("Psw", smsPaltformProperty.getPwd());
            paramMap.put("Name", smsPaltformProperty.getName());
            paramMap.put("Id", smsPaltformProperty.getId());
            String url = "http://sms.bdt360.com:8180/Service.asmx/SendMessage";
            String resXml = null;
            try {
                resXml = URLUtil.originalGetData(url, paramMap);
                logger.info("短信get请求：" + URLUtil.getDataUrl(url, paramMap));
            }
            catch (Exception e) {
                logger.error("[短信平台发送信息][Http请求异常" + e.getMessage() + "]", e);
            }
            logger.info("短信发送结果：[" + resXml + "]");
            // 读取返回结果
            Map<String, String> resMap = new HashMap<String, String>();
            if (StringUtil.isNotEmpty(resXml)) {
                // 创建一个新的字符串
                StringReader read = new StringReader(resXml);
                // 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
                InputSource source = new InputSource(read);
                // 创建一个新的SAXBuilder
                SAXBuilder sb = new SAXBuilder();
                try {
                    // 通过输入源构造一个Document
                    Document doc = sb.build(source);
                    // 取的根元素
                    Element root = doc.getRootElement();
                    System.out.println(root.getName());// 输出根元素的名称（测试）
                    // 得到根元素所有子元素的集合
                    List jiedian = root.getChildren();
                    // 获得XML中的命名空间（XML中未定义可不写）
                    Namespace ns = root.getNamespace();
                    Element et = null;
                    Object[] mapList = jiedian.toArray();
                    for (int i = 0;i < jiedian.size();i++) {
                        et = (Element) jiedian.get(i);// 循环依次得到子元素
                        String text = (null == et.getText()) ? "" : et.getText();
                        resMap.put(et.getName(), text);
                    }
                }
                catch (JDOMException e) {
                    // TODO 自动生成 catch 块
                    e.printStackTrace();
                }
                catch (IOException e) {
                    // TODO 自动生成 catch 块
                    e.printStackTrace();
                }
            }
            String state = resMap.get("State");
            if ((null != state) && ("1".equals(state))) {
                // 成功
                return 1;
            }
            else {
                return 0;
            }
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
