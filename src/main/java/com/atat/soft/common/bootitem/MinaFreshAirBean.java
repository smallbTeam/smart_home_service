package com.atat.soft.common.bootitem;

import org.apache.mina.core.session.IoSession;

import javax.xml.crypto.Data;
import java.util.Date;

/**
 * Created by Administrator on 2017/8/10.
 */
public class MinaFreshAirBean {

    @Override
    public String toString() {
        return "MinaFreshAirBean{" +
                "nowWendu=" + nowWendu +
                ", nowShidu=" + nowShidu +
                ", nowPm=" + nowPm +
                ", nowCo2=" + nowCo2 +
                ", nowVoc=" + nowVoc +
                ", allWendu=" + allWendu +
                ", allShidu=" + allShidu +
                ", allPm=" + allPm +
                ", allCo2=" + allCo2 +
                ", allVoc=" + allVoc +
                ", number=" + number +
                ", date=" + date +
                ", session=" + session +
                ", ip='" + ip + '\'' +
                ", devicenumber='" + devicenumber + '\'' +
                ", state=" + state +
                '}';
    }

    /////当前的空气数据
    private double nowWendu;
    private double nowShidu;
    private double nowPm;
    private double nowCo2;
    private double nowVoc;

    /////保存的总数值
    private double allWendu;
    private double allShidu;
    private double allPm;
    private double allCo2;
    private double allVoc;

    ////保存数值个数
    private int number;
    /////保存时间
    private Date date;
    /////mina连接的session
    private IoSession session;
    ///// ip地址
    private String ip;
    /////设备号
    private String devicenumber;
    //////是否第一次存储
    private int state ;

    public String getDevicenumber() {
        return devicenumber;
    }

    public void setDevicenumber(String devicenumber) {
        this.devicenumber = devicenumber;
    }




    public MinaFreshAirBean(double nowWendu, double nowShidu, double nowPm, double nowCo2, double nowVoc, double allWendu, double allShidu, double allPm, double allCo2, double allVoc, int number, Date date, IoSession session, String ip,String devicenumber) {
        this.nowWendu = nowWendu;
        this.nowShidu = nowShidu;
        this.nowPm = nowPm;
        this.nowCo2 = nowCo2;
        this.nowVoc = nowVoc;
        this.allWendu = allWendu;
        this.allShidu = allShidu;
        this.allPm = allPm;
        this.allCo2 = allCo2;
        this.allVoc = allVoc;
        this.number = number;
        this.date = date;
        this.session = session;
        this.ip = ip;
        this.devicenumber = devicenumber;
    }

    public MinaFreshAirBean() {
    }

    public MinaFreshAirBean(IoSession session, String ip,String devicenumber) {
        this.session = session;
        this.ip = ip;
        this.devicenumber = devicenumber;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public IoSession getSession() {
        return session;
    }

    public void setSession(IoSession session) {
        this.session = session;
    }

    public double getNowWendu() {
        return nowWendu;
    }

    public void setNowWendu(double nowWendu) {
        this.nowWendu = nowWendu;
    }

    public double getNowShidu() {
        return nowShidu;
    }

    public void setNowShidu(double nowShidu) {
        this.nowShidu = nowShidu;
    }

    public double getNowPm() {
        return nowPm;
    }

    public void setNowPm(double nowPm) {
        this.nowPm = nowPm;
    }

    public double getNowCo2() {
        return nowCo2;
    }

    public void setNowCo2(double nowCo2) {
        this.nowCo2 = nowCo2;
    }

    public double getNowVoc() {
        return nowVoc;
    }

    public void setNowVoc(double nowVoc) {
        this.nowVoc = nowVoc;
    }

    public double getAllWendu() {
        return allWendu;
    }

    public void setAllWendu(double allWendu) {
        this.allWendu = allWendu;
    }

    public double getAllShidu() {
        return allShidu;
    }

    public void setAllShidu(double allShidu) {
        this.allShidu = allShidu;
    }

    public double getAllPm() {
        return allPm;
    }

    public void setAllPm(double allPm) {
        this.allPm = allPm;
    }

    public double getAllCo2() {
        return allCo2;
    }

    public void setAllCo2(double allCo2) {
        this.allCo2 = allCo2;
    }

    public double getAllVoc() {
        return allVoc;
    }

    public void setAllVoc(double allVoc) {
        this.allVoc = allVoc;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
