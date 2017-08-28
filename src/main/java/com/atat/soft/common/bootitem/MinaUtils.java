package com.atat.soft.common.bootitem;

import com.atat.core.config.SpringBeanFactoryUtils;
import com.atat.soft.customer.service.TabCustomerService;
import com.atat.soft.device.action.SaveDeviceData;
import com.atat.soft.freshair.bean.DataFreshairNow;
import com.atat.soft.freshair.bean.TabDeviceFreshair;
import com.atat.soft.util.JsonUtil;
import org.apache.mina.core.session.IoSession;
import org.springframework.aop.framework.autoproxy.AbstractAdvisorAutoProxyCreator;

import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/8/10.
 */
public class MinaUtils {


    /**
     * 将取回来的string数组 转化为mina对象
     * 设备号 3位       功能位    温度值     湿度     PM2.5     co2     tvoc
     * TJAT00000  |  KQSJ     |  000000  |  0000  |  0000  | 000000 | 000000 |
     * @param msg
     * @param ips
     * @param session
     * @return
     */
    public static MinaFreshAirBean InPutMessageToBean(String[] msg,String ips,IoSession session) {
        MinaFreshAirBean tdf = new MinaFreshAirBean();

        //设备号
        tdf.setDevicenumber(msg[0]+msg[1]+msg[2]);
        //mima连接的session
        tdf.setSession(session);
        //ip
        tdf.setIp(ips);
        tdf.setNowWendu(StringToPoint(msg[4], "wendu"));
        tdf.setNowShidu(StringToPoint(msg[5], "shidu"));
        tdf.setNowPm(StringToPoint(msg[6], "pm"));
        double vocnumber =StringToPoint(msg[7], "voc");
        if(vocnumber==0){
            vocnumber =(int)(Math.round(Math.random()*5));
        }
        vocnumber = vocnumber*44/22.4/1000;
        tdf.setNowVoc(vocnumber);
        tdf.setNowCo2(StringToPoint(msg[8], "co2"));
        return tdf;
    }


    /**
     * 将mina中的对象转化为后台 （TabDeviceFreshair） bean 并存储
     * @param bean
     * @return
     */
    private static Integer saveFreshairData(MinaFreshAirBean bean){
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
        Integer state =  saveDeviceData.saveFreshAirData(tabDevice,dataFreshairNow);
        return state;
    }


    /**
     * 每一个值根据不同类型添加小数点
     * @param s
     * @param type
     * @return
     */
    private static Double StringToPoint(String s, String type) {
        StringBuilder sb = new StringBuilder(s);//构造一个StringBuilder对象
        if(TypeToPoint(type)!=0){
            sb.insert(sb.length()-TypeToPoint(type), ".");//在指定的位置1，插入指定的字符串
        }
        return Double.parseDouble(sb.toString());
    }


    /**
     * 不同类型所需要添加的小数点
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
    public static MinaFreshAirBean writeBean(MinaFreshAirBean beans, int type, MinaFreshAirBean allDate){
        SaveDeviceData saveDeviceData = new SaveDeviceData();
        MinaFreshAirBean minaFreshAirBean = null;

        if(beans.getNowVoc()>20||beans.getNowCo2()>10000||beans.getNowCo2()<400){
            return minaFreshAirBean;
        }

        ////第一次写入存入数据库
        if(type==0||(type==1&&allDate.getState()==0)){


            beans.setAllWendu(beans.getNowWendu());
            beans.setAllShidu(beans.getNowShidu());
            beans.setAllPm(beans.getNowPm());
            beans.setAllVoc(beans.getNowVoc());
            beans.setAllCo2(beans.getNowCo2());
            beans.setNumber(1);
            beans.setState(0);
            //对象转换
            //////////////数据库操作
            int state =   saveFreshairData(beans);
            if(state!=0){
                beans.setState(1);
            }
        }else if(type==1&&allDate.getState()==1){
            if(allDate.getNumber()>=10){
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

            }else{
                beans.setAllWendu(beans.getNowWendu()+allDate.getAllWendu());
                beans.setAllShidu(beans.getNowShidu()+allDate.getAllShidu());
                beans.setAllPm(beans.getNowPm()+allDate.getAllPm());
                beans.setAllVoc(beans.getNowVoc()+allDate.getAllVoc());
                beans.setAllCo2(beans.getNowCo2()+allDate.getAllCo2());
                beans.setNumber(allDate.getNumber()+1);
                System.out.println("当前voc   "+beans.getNowVoc());
                System.out.println("所有的voc相加   "+beans.getAllVoc());
//                System.out.println(beans.toString());
            }
            beans.setState(1);
        }
        beans.setDate(new Date());
        return beans;
    }

    /**
     * 计算60次数据
     * @param bean
     * @return
     */
    private static MinaFreshAirBean  GetDataForTenMinu(MinaFreshAirBean bean){
        DecimalFormat df = new DecimalFormat("#.00");
        DecimalFormat df_voc = new DecimalFormat("#.0000");
        int divisor = 10;
        System.out.println("计算得voc值------------->"+bean.getAllVoc());
        bean.setNowWendu( Double.parseDouble(df.format(bean.getAllWendu()/divisor)));
        bean.setNowShidu(Double.parseDouble(df.format(bean.getAllShidu()/divisor)));

        bean.setNowPm((int)(bean.getAllPm()/divisor));
        bean.setNowCo2((int)(bean.getAllCo2()/divisor));
        bean.setNowVoc(Double.parseDouble(df_voc.format(bean.getAllVoc()/divisor)));
        return bean;
    }




}
