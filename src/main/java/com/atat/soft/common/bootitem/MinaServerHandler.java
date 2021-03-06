package com.atat.soft.common.bootitem;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import java.net.InetSocketAddress;
import java.util.*;

import static com.atat.soft.common.bootitem.MinaUtils.InPutMessageToBean;
import static com.atat.soft.common.bootitem.MinaUtils.writeBean;

/**
 * Created by Administrator on 2017/8/8.
 */
public class MinaServerHandler extends IoHandlerAdapter {

    static Map<Object, MinaFreshAirBean> map = new HashMap<Object, MinaFreshAirBean>();

    /**
     * 发送数据方法
     * 
     * @param msg
     * @param number
     */
    public static void sendMessage(String msg, IoSession number) {
        // System.out.println("发送数据:====>"+msg);
        byte[] bar = msg.getBytes();
        IoBuffer buffers = IoBuffer.allocate(bar.length);
        buffers.put(bar, 0, bar.length);
        buffers.flip();
        number.write(buffers);
    }

    /**
     * 创建连接方法
     * @param session
     * @throws
     */
    @Override
    public void sessionCreated(IoSession session) throws Exception {
    }

    /**
     * 连接打开方法
     * 
     * @param session
     * @throws Exception
     */
    @Override
    public void sessionOpened(IoSession session) throws Exception {
    }

    /**
     * 接收数据方法
     * 
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        /////// 获取设备的ip地址
        String ips=GetSessionFromSession(session);
        System.out.println("接收硬件发来数据时候的ip值  " + ips);
        ///// 接收数据后回传数据
        sendMessage("OK\\\r\\\n", session);

          /////处理数据
          IoBuffer ioBuffer = (IoBuffer)message;
          byte[] b = new byte [ioBuffer.limit()];
          ioBuffer.get(b);
          String stringmsg = new String(b);
          /////转化为string数组
          String [] str = stringmsg.split("\\|");
          //////数组转化为对象
          MinaFreshAirBean bean =InPutMessageToBean(str,ips,session);

          /////第一次执行直接进行存库操作
          if(null==map.get(session)){
          bean = writeBean(bean,0,null);
          }else{
          ////否则存60次数据
          bean = writeBean(bean,1,map.get(session));
          }
          map.put(session,bean);

    }

    /**
     * 向客户端发送数据成功方法
     * 
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        String ips=GetSessionFromSession(session);
        System.out.println("给硬件发送数据时候的ip值  " + ips);
    }

    /**
     * 关闭客户端连接方法
     * 
     * @param session
     * @throws Exception
     */
    @Override
    public void sessionClosed(IoSession session) throws Exception {
        System.out.println("关闭session");
        map.remove(session);
        session.close(true);
    }

    /**
     * 读写通道过一定时间无操作会调用方法
     * 
     * @param session
     * @param status
     * @throws Exception
     */
    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        map.remove(session);
        session.close(true);
        System.out.println("长时间未连接" + new Date());
    }

    /**
     * 服务器端异常方法
     * 
     * @param session
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        System.out.println("服务端异常..." + cause);
    }

    /**
     * 服务器向mina获取环境监测实时数值
     * 
     * @param deviceid
     * @return
     */
    public static Map<String, Object> GetNowData(String deviceid) {
        Map<String, Object> getmap = new HashMap<String, Object>();
        MinaFreshAirBean bean = map.get(deviceid);
        getmap.put("wendu", bean.getNowWendu());
        getmap.put("shidu", bean.getNowShidu());
        getmap.put("pm", bean.getNowPm());
        getmap.put("co2", bean.getNowCo2());
        getmap.put("voc", bean.getNowVoc());
        return getmap;
    }

    /**
     * 服务器向mina根据ip获取设备列表
     * 
     * @param ip
     * @return
     */
    public static List<String> GetNowDataForIp(String ip) {
        List<String> list = new ArrayList<String>();
        int x = 0;
        for (Map.Entry<Object, MinaFreshAirBean> entry : map.entrySet()) {
            MinaFreshAirBean beenas = new MinaFreshAirBean();
            beenas = entry.getValue();
            if (beenas.getIp().equals(ip)) {
                ///// 测试
                IoSession session = (IoSession) entry.getKey();
                String ips=GetSessionFromSession(session);
                System.out.println("客户端向mina请求时session中存在的ip  " + ips + "  内存中存储的ip   " + beenas.getIp());
                list.add(beenas.getDevicenumber());
            }
        }
        return list;
    }

    /**
     * @param
     * @return
     */
    public static void SendToDevice() {
        for (Map.Entry<Object, MinaFreshAirBean> entry : map.entrySet()) {
            MinaFreshAirBean beenas = new MinaFreshAirBean();
            beenas = entry.getValue();
            ///// 测试
            IoSession session = (IoSession) entry.getKey();
            String ips=GetSessionFromSession(session);


            System.out.println("客户端向mina请求时session中存在的ip  " + ips);
            sendMessage("aaaa", session);
        }
    }

    /**
     * 根据session获取ip
     * @param session
     * @return
     */
    private static String GetSessionFromSession(IoSession session){
        String getip="";
        String clientIP = ((InetSocketAddress) session.getRemoteAddress()).getAddress().getHostAddress();
        String[] str = clientIP.split("\\.");
        System.out.println(clientIP+"   "+str.length);
        if(str.length>3){

            getip = str[0]+"."+str[1]+"."+str[2]+"."+str[3];
        }
        System.out.println(getip);
        return getip;
    }
}
