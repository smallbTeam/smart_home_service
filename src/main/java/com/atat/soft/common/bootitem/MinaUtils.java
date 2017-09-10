package com.atat.soft.common.bootitem;

import com.alibaba.fastjson.JSONObject;
import com.atat.core.config.SpringBeanFactoryUtils;
import com.atat.soft.common.prop.WxPlatformProperty;
import com.atat.soft.customer.service.TabCustomerService;
import com.atat.soft.device.action.SaveDeviceData;
import com.atat.soft.deviceGroup.service.RelCustomerDeviceGroupService;
import com.atat.soft.freshair.bean.DataFreshairNow;
import com.atat.soft.freshair.bean.TabDeviceFreshair;
import com.atat.soft.util.JsonUtil;
import com.atat.soft.util.StringUtil;
import org.apache.mina.core.session.IoSession;
import org.springframework.aop.framework.autoproxy.AbstractAdvisorAutoProxyCreator;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/8/10.
 */
public class MinaUtils {


    /**
     * 将取回来的string数组 转化为mina对象
     * 设备号 3位       功能位    温度值     湿度     PM2.5     co2     tvoc
     * TJAT00000  |  KQSJ     |  000000  |  0000  |  0000  | 000000 | 000000 |
     *
     * @param msg
     * @param ips
     * @param session
     * @return
     */
    public static MinaFreshAirBean InPutMessageToBean(String[] msg, String ips, IoSession session) {
        MinaFreshAirBean tdf = new MinaFreshAirBean();

        //设备号
        tdf.setDevicenumber(msg[0] + msg[1] + msg[2]);
        //mima连接的session
        tdf.setSession(session);
        //ip
        tdf.setIp(ips);
        tdf.setNowWendu((StringToPoint(msg[4], "wendu"))-1.5);
        tdf.setNowShidu(StringToPoint(msg[5], "shidu"));
        tdf.setNowPm(StringToPoint(msg[6], "pm"));
        double vocnumber = StringToPoint(msg[7], "voc");
        if (vocnumber == 0) {
            vocnumber = (int) (Math.round(Math.random() * 5));
        }
        vocnumber = vocnumber * 44 / 22.4 / 1000;
        DecimalFormat df_voc = new DecimalFormat("#.0000");
        tdf.setNowVoc(Double.parseDouble(df_voc.format(vocnumber)));
        tdf.setNowCo2(StringToPoint(msg[8], "co2"));

        AlarmPush(tdf);
        return tdf;
    }


    /**
     * 将mina中的对象转化为后台 （TabDeviceFreshair） bean 并存储
     *
     * @param bean
     * @return
     */
    private static Integer saveFreshairData(MinaFreshAirBean bean) {
        TabDeviceFreshair tabDevice = new TabDeviceFreshair();
        DataFreshairNow dataFreshairNow = new DataFreshairNow();
        dataFreshairNow.setDataFreshairNowId(new Date().getTime());
        dataFreshairNow.setWendu(bean.getNowWendu());
        dataFreshairNow.setShidu(bean.getNowShidu());
        dataFreshairNow.setPm(bean.getNowPm());
        dataFreshairNow.setCo2(bean.getNowCo2());
        dataFreshairNow.setVoc(bean.getNowVoc());

        tabDevice.setIp(bean.getIp());
        tabDevice.setDeviceSeriaNumber(bean.getDevicenumber());
        tabDevice.setDeviceCategory("freshAir");

        SaveDeviceData saveDeviceData = new SaveDeviceData();
        Integer state = saveDeviceData.saveFreshAirData(tabDevice, dataFreshairNow);
        return state;
    }


    /**
     * 每一个值根据不同类型添加小数点
     *
     * @param s
     * @param type
     * @return
     */
    private static Double StringToPoint(String s, String type) {
        StringBuilder sb = new StringBuilder(s);//构造一个StringBuilder对象
        if (TypeToPoint(type) != 0) {
            sb.insert(sb.length() - TypeToPoint(type), ".");//在指定的位置1，插入指定的字符串
        }
        return Double.parseDouble(sb.toString());
    }


    /**
     * 不同类型所需要添加的小数点
     *
     * @param type
     * @return
     */
    private static int TypeToPoint(String type) {
        switch (type) {
            case "wendu": {
                return 2;
            }
            case "shidu": {
                return 2;
            }
            case "pm": {
                return 0;
            }
        }
        return 0;
    }


    /**
     * 向内存中存入数据操作
     * type 0-第一次写入  1-非第一次写入
     */
    public static MinaFreshAirBean writeBean(MinaFreshAirBean beans, int type, MinaFreshAirBean allDate) {
        SaveDeviceData saveDeviceData = new SaveDeviceData();
        MinaFreshAirBean minaFreshAirBean = null;


        if (beans.getNowVoc() > 20 || beans.getNowCo2() > 10000 || beans.getNowCo2() < 400) {
            return minaFreshAirBean;
        }

        ////第一次写入存入数据库
        if (type == 0 || (type == 1 && allDate.getState() == 0)) {


            beans.setAllWendu(beans.getNowWendu());
            beans.setAllShidu(beans.getNowShidu());
            beans.setAllPm(beans.getNowPm());
            beans.setAllVoc(beans.getNowVoc());
            beans.setAllCo2(beans.getNowCo2());
            beans.setNumber(1);
            beans.setState(0);
            //对象转换
            //////////////数据库操作
            int state = saveFreshairData(beans);
            if (state != 0) {
                beans.setState(1);
            }
        } else if (type == 1 && allDate.getState() == 1) {
            if (allDate.getNumber() >= 10) {
                //进行计算
                allDate = GetDataForTenMinu(allDate);
                //对象转换 存储设备数据
                saveFreshairData(allDate);
                //将数据重置
                beans.setAllWendu(beans.getNowWendu());
                beans.setAllShidu(beans.getNowShidu());
                beans.setAllPm(beans.getNowPm());
                beans.setAllVoc(beans.getNowVoc());
                beans.setAllCo2(beans.getNowCo2());
                beans.setNumber(1);

            } else {
                beans.setAllWendu(beans.getNowWendu() + allDate.getAllWendu());
                beans.setAllShidu(beans.getNowShidu() + allDate.getAllShidu());
                beans.setAllPm(beans.getNowPm() + allDate.getAllPm());
                beans.setAllVoc(beans.getNowVoc() + allDate.getAllVoc());
                beans.setAllCo2(beans.getNowCo2() + allDate.getAllCo2());
                beans.setNumber(allDate.getNumber() + 1);
            }
            beans.setState(1);
        }
        beans.setDate(new Date());
        return beans;
    }

    /**
     * 计算60次数据
     *
     * @param bean
     * @return
     */
    private static MinaFreshAirBean GetDataForTenMinu(MinaFreshAirBean bean) {
        DecimalFormat df = new DecimalFormat("#.00");
        DecimalFormat df_voc = new DecimalFormat("#.0000");
        int divisor = 10;
        bean.setNowWendu(Double.parseDouble(df.format(bean.getAllWendu() / divisor)));
        bean.setNowShidu(Double.parseDouble(df.format(bean.getAllShidu() / divisor)));

        bean.setNowPm((int) (bean.getAllPm() / divisor));
        bean.setNowCo2((int) (bean.getAllCo2() / divisor));
        bean.setNowVoc(Double.parseDouble(df_voc.format(bean.getAllVoc() / divisor)));
        return bean;
    }

    /**
     * 提醒推送
     */

    private static void AlarmPush(MinaFreshAirBean bean) {

        ///获取时间
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String datas[] = JudgmentResult(bean);

        for (int i = 0; i < 5; i++) {
            System.out.println("datas[i]----   "+datas[i]);
            if (!datas[i].equals("0")) {
                JSONObject putData = new JSONObject();
                putData.put("first", ContentToJsonObj("用户您好:", "#173177"));
                putData.put("system", ContentToJsonObj(datas[i], "#173177"));
                putData.put("time", ContentToJsonObj(format.format(date), "#173177"));
                putData.put("account", ContentToJsonObj("1", "#173177"));
                putData.put("remark", ContentToJsonObj("AttentionPlace", "#173177"));

                //result 发送~~~
                WxPlatformProperty wxPlatformProperty = (WxPlatformProperty)SpringBeanFactoryUtils.getBean("wxPlatformProperty");
                RelCustomerDeviceGroupService relCustomerDeviceGroupService = (RelCustomerDeviceGroupService) SpringBeanFactoryUtils.getBean("relCustomerDeviceGroupService");
                //relCustomerDeviceGroupService.deviceSendAlarmToCustomer(bean.getDevicenumber(),wxPlatformProperty.getWxWarnModel(),putData);
            }
        }
    }


    /**
     * 将数据以及颜色放入map中
     *
     * @param content
     * @param color
     * @return
     */
    private static JSONObject ContentToJsonObj(String content, String color) {
        JSONObject dd = new JSONObject();
        dd.put("value", content);//消息提示
        dd.put("color", color);
        return dd;
    }

    /**
     * 计算空气监测高中低
     */

    public static Map<String, Object> GetDateForResult(MinaFreshAirBean bean) {
        double wendu, shidu, pm, co, voc;
        Map<String, Object> getmap = new HashMap<String, Object>();
        wendu = bean.getNowWendu();
        shidu = bean.getNowShidu();
        pm = bean.getNowPm();
        co = bean.getNowCo2();
        voc = bean.getNowVoc();

        getmap.put("wendu", wendu);
        getmap.put("shidu", shidu);
        getmap.put("pm", pm);
        getmap.put("co2", co);
        getmap.put("voc", voc);


        if (wendu < 5) {
            getmap.put("wenstate", "1");
        } else if (wendu > 35) {
            getmap.put("wenstate", "2");
        } else {
            getmap.put("wenstate", "0");
        }

        if (shidu > 90) {
            getmap.put("shistate", "2");
        } else if (shidu < 10) {
            getmap.put("shistate", "1");
        } else {
            getmap.put("shistate", "0");
        }

        if (pm >= 50 && pm < 80) {
            getmap.put("pmstate", "3");
        } else if (pm >= 80 && pm < 150) {
            getmap.put("pmstate", "4");
        } else if (pm >= 150 && pm < 300) {
            getmap.put("pmstate", "5");
        } else if (pm >= 300) {
            getmap.put("pmstate", "6");
        } else {
            getmap.put("pmstate", "0");
        }

        if (co >= 1000 && co < 2000) {
            getmap.put("costate", "3");
        } else if (co >= 2000 && co < 5000) {
            getmap.put("costate", "5");
        } else if (co >= 5000) {
            getmap.put("costate", "6");
        } else {
            getmap.put("costate", "0");
        }

        if (voc >= 0.15 && voc < 0.3) {
            getmap.put("vocstate", "3");
        } else if (voc >= 0.3) {
            getmap.put("vocstate", "6");
        } else {
            getmap.put("vocstate", "0");
        }
        return getmap;
    }

    /**
     * 计算空气监测高中低
     */

    public static String[] JudgmentResult(MinaFreshAirBean bean) {
        double wendu, shidu, pm, co, voc;
        String [] str = new String[5];
        wendu = bean.getNowWendu();
        shidu = bean.getNowShidu();
        pm = bean.getNowPm();
        co = bean.getNowCo2();
        voc = bean.getNowVoc();

        if (wendu < 5) {
            str[0] = "您家中温度值过低，请小心感冒。";
        } else if (wendu > 35) {
            str[0] = "您家中温度值过高，请小心中暑。";
        } else {
            str[0] = "0";
        }

        if (shidu > 90) {
            str[1] = "您家中湿度值过高，请进行除湿操作";
        } else if (shidu < 10) {
            str[1] = "您家中湿度值过低，请进行加湿操作";
        } else {
            str[1] = "0";
        }

        if (pm >= 50 && pm < 80) {
            str[2] = "您家中Pm状态为良";
        } else if (pm >= 80 && pm < 150) {
            str[2] = "您家中Pm状态为轻度污染";
        } else if (pm >= 150 && pm < 300) {
            str[2] = "您家中Pm状态为中度污染";
        } else if (pm >= 300) {
            str[2] = "您家中Pm状态为重度污染";
        } else {
            str[2] = "0";
        }

        if (co >= 1000 && co < 2000) {
            str[3] = "您家中co2含量较高，空气浑浊，并开始觉得昏昏欲睡的状态,请及时通风";
        } else if (co >= 2000 && co < 5000) {
            str[3] = "您家中co2含量很高，您会感觉头痛、嗜睡、呆滞、注意力无法集中、心跳加速、轻度恶心,请及时通风";
        } else if (co >= 5000) {
            str[3] = "您家中co2含量非常高，可能导致严重缺氧，造成永久性脑损伤、昏迷、甚至死亡。,请务必及时通风";
        } else {
            str[3] = "0";
        }

        if (voc >= 0.15 && voc < 0.3) {
            str[4] = "您家中voc含量较高,请及时打开新风设备，或者开窗通风";
        } else if (voc >= 0.3) {
            str[4] = "您家中voc含量很高,请务必打开新风设备，或者开窗通风";
        } else {
            str[4] = "0";
        }
        return str;

    }



}
