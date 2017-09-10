package com.atat.soft.deviceGroup.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atat.core.db.model.PageTurn;
import com.atat.core.db.service.impl.BaseSupportServiceImpl;
import com.atat.soft.common.bootitem.MinaFreshAirBean;
import com.atat.soft.common.bootitem.MinaServerHandler;
import com.atat.soft.common.prop.WxPlatformProperty;
import com.atat.soft.customer.service.TabCustomerService;
import com.atat.soft.deviceGroup.bean.RelCustomerDeviceGroup;
import com.atat.soft.deviceGroup.dao.RelCustomerDeviceGroupDao;
import com.atat.soft.deviceGroup.dao.TabDeviceGroupDao;
import com.atat.soft.deviceGroup.service.RelCustomerDeviceGroupService;
import com.atat.soft.freshair.service.DataFreshairHourService;
import com.atat.soft.freshair.service.DataFreshairNowService;
import com.atat.soft.freshair.service.TabDeviceFreshairService;
import com.atat.soft.message.action.WeixinAction;
import com.atat.soft.record.bean.TabCustomerAlarmRecord;
import com.atat.soft.record.service.TabCustomerAlarmRecordService;
import com.atat.soft.record.service.TabCustomerAlarmRecordService;
import com.atat.soft.util.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author whaosoft
 */
@Service
@Transactional
public class RelCustomerDeviceGroupServiceImpl extends BaseSupportServiceImpl implements RelCustomerDeviceGroupService {

    @Autowired
    private RelCustomerDeviceGroupDao relCustomerDeviceGroupDao;

    @Autowired
    private TabDeviceGroupDao tabDeviceGroupDao;

    @Autowired
    private TabDeviceFreshairService tabDeviceFreshairService;

    @Autowired
    private TabCustomerAlarmRecordService tabCustomerAlarmRecordService;

    @Autowired
    private WeixinAction weixinAction;

    @Autowired
    private TabCustomerService tabCustomerService;

    @Autowired
    private WxPlatformProperty wxPlatformProperty;

    @Autowired
    private DataFreshairHourService dataFreshairHourService;

    @Autowired
    private DataFreshairNowService dataFreshairNowService;

    private String daoNamespace = RelCustomerDeviceGroupDao.class.getName();

    @Override
    public void addRelCustomerDeviceGroup(RelCustomerDeviceGroup relCustomerDeviceGroup) {
        relCustomerDeviceGroupDao.addRelCustomerDeviceGroup(relCustomerDeviceGroup);
    }

    @Override
    public void updateRelCustomerDeviceGroupById(RelCustomerDeviceGroup relCustomerDeviceGroup) {
        relCustomerDeviceGroupDao.updateRelCustomerDeviceGroupById(relCustomerDeviceGroup);
    }

    @Override
    public List<Map<String, Object>> selectRelCustomerDeviceGroupList(Map<String, Object> param) {
        return relCustomerDeviceGroupDao.selectRelCustomerDeviceGroupList(param);
    }

    @Override
    public PageTurn<Map<String, Object>> getRelCustomerDeviceGroupPageTurn(Map<String, Object> param, int pageNo,
            int pageSize) {
        return this.getDao().pagedQuery(daoNamespace + ".selectRelCustomerDeviceGroupList", pageNo, pageSize, param);
    }

    @Override
    public Map<String, Object> getRelCustomerDeviceGroupById(Long relCustomerDeviceGroupId) {
        Map<String, Object> relCustomerDeviceGroupinfo = new HashMap<String, Object>();
        Map<String, Object> rs = new HashMap<String, Object>();
        rs.put("relCustomerDeviceGroupId", relCustomerDeviceGroupId);
        List<Map<String, Object>> relCustomerDeviceGroupList = relCustomerDeviceGroupDao.selectRelCustomerDeviceGroupList(rs);
        if ((null != relCustomerDeviceGroupList) && (relCustomerDeviceGroupList.size() > 0)) {
            relCustomerDeviceGroupinfo = relCustomerDeviceGroupList.get(0);
        }
        return relCustomerDeviceGroupinfo;
    }

    @Override
    public void delRelCustomerDeviceGroupById(Long relCustomerDeviceGroupId) {
        RelCustomerDeviceGroup relCustomerDeviceGroup = new RelCustomerDeviceGroup();
        relCustomerDeviceGroup.setIsDeleted(1);
        relCustomerDeviceGroup.setModifiedDate(new Date());
        relCustomerDeviceGroup.setRelCustomerDeviceGroupId(relCustomerDeviceGroupId);
        relCustomerDeviceGroupDao.updateRelCustomerDeviceGroupById(relCustomerDeviceGroup);
    }

    @Override
    public Map<String, Object> findDeviceByIp(String ip) {
        // 依次检索各种类型设别下相同Ip设备 并检索分组
        Map<String, Object> param = new HashMap<String, Object>();
        // 相同Ip下设备分组列表
        List<Long> deviceGroupIdList = new ArrayList<Long>();
        List<Map<String, Object>> deviceGroupList = new ArrayList<Map<String, Object>>();
        param.put("ip", ip);
        // 相同Ip下设备信息列表
        List<Map<String, Object>> deviceList = new ArrayList<Map<String, Object>>();
        // 取出空气检测系统下相同Ip设备
        List<String> freshairSeriaNumberList = MinaServerHandler.GetNowDataForIp(ip);
        List<Map<String, Object>> freshairList = new ArrayList<Map<String, Object>>();
        // tabDeviceFreshairService.selectTabDeviceFreshairList(param);
        for (String freshairSeriaNumber : freshairSeriaNumberList) {
            Map<String, Object> tabDeviceFreshairinfo = new HashMap<String, Object>();
            tabDeviceFreshairinfo = tabDeviceFreshairService.getTabDeviceFreshairByDeviceSeriaNumber(freshairSeriaNumber);
            freshairList.add(tabDeviceFreshairinfo);
        }
        if (CollectionUtil.isNotEmpty(freshairList)) {
            deviceList.addAll(freshairList);
            // 取出设别分组 已存在则不必添加
            for (Map<String, Object> freshair : freshairList) {
                if (null != freshair.get("tabDeviceGroupId")) {
                    Long tabDeviceGroupId = Long.parseLong(freshair.get("tabDeviceGroupId").toString());
                    if (!deviceGroupIdList.contains(tabDeviceGroupId)) {
                        deviceGroupIdList.add(tabDeviceGroupId);
                        // 查询当前分组信息并添加
                        Map<String, Object> tabDeviceGroupParam = new HashMap<String, Object>();
                        tabDeviceGroupParam.put("tabDeviceGroupId", tabDeviceGroupId);
                        List<Map<String, Object>> tabDeviceGroupList = tabDeviceGroupDao.selectTabDeviceGroupList(tabDeviceGroupParam);
                        if ((null != tabDeviceGroupList) && (tabDeviceGroupList.size() > 0)) {
                            deviceGroupList.add(tabDeviceGroupList.get(0));
                        }
                    }
                }
                else {
                    freshair.put("tabDeviceGroupId", null);
                }
            }
        }
        // 取出**类设备下设备
        // 封装结果集
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("deviceList", deviceList);
        resultMap.put("deviceGroupList", deviceGroupList);
        resultMap.put("ip", ip);
        return resultMap;
    }

    @Override
    public Integer deviceSendAlarmToCustomer(String deviceSeriaNumber, String wxWarnModel, JSONObject putData) {
        return 0;
    }

    @Override
    public Integer deviceSendAlarm(String deviceSeriaNumber, String partName, String mark, String content) {
        // 报警标记不符合规定
        if ((!"mildly".equals(mark)) && (!"moderate".equals(mark)) && (!"severe".equals(mark))) {
            return 0;
        }
        // 依据设备号查找接收消息的用户列表
        // status 1:轻度 2：中度 3:重度
        Integer cont = 0;
        List<Map<String, Object>> tabCustomerList = getReciveAlramCustomerByDeviceSeriaNumber(deviceSeriaNumber);
        if (CollectionUtil.isNotEmpty(tabCustomerList)) {
            List<String> tousers = new ArrayList<String>();
            for (Map<String, Object> tabCustomer : tabCustomerList) {
                Long tabCustomerId = Long.parseLong(tabCustomer.get("tabCustomerId").toString());
                String wxId = (String) tabCustomer.get("wxId");
                // 依据用户信息和设备号以及报警部位名称 报警方式 查找最近一次发送时间
                Map<String, Object> findCustomerAlarmRecordParam = new HashMap<String, Object>();
                findCustomerAlarmRecordParam.put("tabCustomerId", tabCustomerId);
                findCustomerAlarmRecordParam.put("deviceSeriaNumber", deviceSeriaNumber);
                findCustomerAlarmRecordParam.put("partName", partName);
                findCustomerAlarmRecordParam.put("type", 1);
                List<Map<String, Object>> tabCustomerAlarmRecordList = tabCustomerAlarmRecordService.selectTabCustomerAlarmRecordList(findCustomerAlarmRecordParam);
                TabCustomerAlarmRecord tabCustomerAlarmRecordBean = new TabCustomerAlarmRecord();
                tabCustomerAlarmRecordBean.setContent(content);
                tabCustomerAlarmRecordBean.setTabCustomerId(tabCustomerId);
                tabCustomerAlarmRecordBean.setDeviceSeriaNumber(deviceSeriaNumber);
                tabCustomerAlarmRecordBean.setType(1);
                tabCustomerAlarmRecordBean.setPartName(partName);
                tabCustomerAlarmRecordBean.setMark(mark);
                if (CollectionUtil.isNotEmpty(tabCustomerAlarmRecordList)
                        && CollectionUtil.isNotEmpty(tabCustomerAlarmRecordList.get(0))) {
                    Map<String, Object> tabCustomerAlarmRecord = tabCustomerAlarmRecordList.get(0);
                    Long tabCustomerAlarmRecordId = Long.parseLong(tabCustomerAlarmRecord.get("tabCustomerAlarmRecordId").toString());
                    String markBefore = (String) tabCustomerAlarmRecord.get("mark");
                    Integer count = (Integer) tabCustomerAlarmRecord.get("count");
                    Date recordDate = (Date) tabCustomerAlarmRecord.get("modifiedDate");
                    // 若现在为轻度
                    if ("mildly".equals(mark)) {
                        // 之前也为轻度则报警次数 +1
                        if ("mildly".equals(markBefore)) {
                            count++;
                            tabCustomerAlarmRecordBean.setTabCustomerAlarmRecordId(tabCustomerAlarmRecordId);
                            tabCustomerAlarmRecordBean.setModifiedDate(new Date());
                            tabCustomerAlarmRecordBean.setCount(count);
                            tabCustomerAlarmRecordService.updateTabCustomerAlarmRecordById(tabCustomerAlarmRecordBean);
                        } // 之前为重度 则不处理
                    } else {
                        Integer timeout = "moderate".equals(mark) ? 2160000 : 1080000;
                        if ((new Date().getTime() - recordDate.getTime()) > timeout) {
                            count = 1;
                            cont++;
                            // 发送给时间超过 则添加微信Id 并`更新记录
                            tousers.add(wxId);
                            tabCustomerAlarmRecordBean.setTabCustomerAlarmRecordId(tabCustomerAlarmRecordId);
                            tabCustomerAlarmRecordBean.setModifiedDate(new Date());
                            tabCustomerAlarmRecordBean.setCount(count);
                            tabCustomerAlarmRecordService.updateTabCustomerAlarmRecordById(tabCustomerAlarmRecordBean);
                        }
                    } // 已发送且上次发送时间未超过则不发送
                }
                else {
                    // 没有记录 则添加微信Id 并记录
                    if (!"mildly".equals(mark)) {
                        tousers.add(wxId);
                    }
                    cont++;
                    tabCustomerAlarmRecordBean.setCount(1);
                    tabCustomerAlarmRecordBean.setCreatedDate(new Date());
                    tabCustomerAlarmRecordService.addTabCustomerAlarmRecord(tabCustomerAlarmRecordBean);
                }
            }
            JSONObject putData = new JSONObject();
            Date date = new Date();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // 问候语
            putData.put("first", ContentToJsonObj("用户您好:" + content, "#173177"));
            // 系统名称
            putData.put("system", ContentToJsonObj("ATAT智能家" + partName + "监测", "#173177"));
            // 截止时间
            putData.put("time", ContentToJsonObj(format.format(date), "#173177"));
            // 报警次数
            putData.put("account", ContentToJsonObj("1", "#173177"));
            // 结尾
            putData.put("remark", ContentToJsonObj("详细数据请登录管理页面查看", "#173177"));
            weixinAction.sendWeixinMessage(tousers, "", wxPlatformProperty.getWxWarnModel(), putData);
            return cont;
        }
        return cont;
    }

    @Override public Integer freshairAlarmMonitor() {
       Integer cont = 0;
        //遍历空气检测设备 取出三小时内平均值
        Map<String, Object> findFreshairParam = new HashMap<String, Object>();
        List<Map<String, Object>> tabDeviceFreshairList = tabDeviceFreshairService.selectTabDeviceFreshairList(findFreshairParam);
        if (CollectionUtil.isNotEmpty(tabDeviceFreshairList)) {
            for (Map<String, Object> tabDeviceFreshair : tabDeviceFreshairList) {
                List<String> tousers = new ArrayList<String>();
                // 设备ID
                Long tabDeviceFreshairId = Long.parseLong(tabDeviceFreshair.get("tabDeviceFreshair").toString());
                // 设备序列号
                String deviceSeriaNumber = (String) tabDeviceFreshair.get("deviceSeriaNumber");
                // 依据设备序列号获取通知用户
                List<Map<String, Object>> tabCustomerList = getReciveAlramCustomerByDeviceSeriaNumber(deviceSeriaNumber);
                if (CollectionUtil.isNotEmpty(tabCustomerList)) {
                    for (Map<String, Object> tabCustomer : tabCustomerList) {
                        String wxId = (String) tabCustomer.get("wxId");
                        cont++;
                        tousers.add(wxId);
                    }
                }
                // 依据设备ID获取设备数据
                Map<String, Object> getAverageFreshairDataParam = new HashMap<String, Object>();
                getAverageFreshairDataParam.put("recordTimeStart", (new Date().getTime() - (3 * 3600 * 1000)));
                getAverageFreshairDataParam.put("recordTimeEnd", (new Date().getTime()));
                getAverageFreshairDataParam.put("tabDeviceFreshairId", tabDeviceFreshairId);
                // 从实时表获取三小时内数据
                List<Map<String, Object>> freshairtimingHourAverageList = dataFreshairNowService.timingNowAverageData(getAverageFreshairDataParam);
                if (CollectionUtil.isNotEmpty(freshairtimingHourAverageList) && CollectionUtil.isNotEmpty(freshairtimingHourAverageList.get(0))) {
                    Map<String, Object> freshairtimingHourAverage = freshairtimingHourAverageList.get(0);
                    //温度
                    Double wendu = Double.parseDouble(freshairtimingHourAverage.get("wendu").toString());
                    Map<String, Object> wendu_res = GetDateForResult("wendu",wendu);
                    if (!"0".equals(wendu_res.get("state"))){
                        cont += deviceSendAlarm(deviceSeriaNumber,"wendu",(String) wendu_res.get("mark"), (String)wendu_res.get("content"));
                    }
                    //湿度
                    Double shidu = Double.parseDouble(freshairtimingHourAverage.get("shidu").toString());
                    Map<String, Object> shidu_res = GetDateForResult("shidu",shidu);
                    if (!"0".equals(shidu_res.get("state"))){
                        cont += deviceSendAlarm(deviceSeriaNumber,"shidu",(String) shidu_res.get("mark"), (String)shidu_res.get("content"));
                    }
                    //pm
                    Double pm = Double.parseDouble(freshairtimingHourAverage.get("pm").toString());
                    Map<String, Object> pm_res = GetDateForResult("pm",wendu);
                    if (!"0".equals(pm_res.get("state"))){
                        cont += deviceSendAlarm(deviceSeriaNumber,"pm",(String) pm_res.get("mark"), (String)pm_res.get("content"));
                    }
                    //voc
                    Double voc = Double.parseDouble(freshairtimingHourAverage.get("voc").toString());
                    Map<String, Object> voc_res = GetDateForResult("voc",wendu);
                    if (!"0".equals(voc_res.get("state"))){
                        cont += deviceSendAlarm(deviceSeriaNumber,"voc",(String) voc_res.get("mark"), (String)voc_res.get("content"));
                    }
                    //co2
                    Double co2 = Double.parseDouble(freshairtimingHourAverage.get("co2").toString());
                    Map<String, Object> co2_res = GetDateForResult("co2",wendu);
                    if (!"0".equals(co2_res.get("state"))){
                        cont += deviceSendAlarm(deviceSeriaNumber,"co2",(String) co2_res.get("mark"), (String)co2_res.get("content"));
                    }
                }
            }
        }
       return cont;
    }

    @Override
    public Integer freshAirDeviceSendOneDayReport() {
        Integer cont = 0;
        // 查询空气检测表 获取设备号 Id
        Map<String, Object> findFreshairParam = new HashMap<String, Object>();
        List<Map<String, Object>> tabDeviceFreshairList = tabDeviceFreshairService.selectTabDeviceFreshairList(findFreshairParam);
        if (CollectionUtil.isNotEmpty(tabDeviceFreshairList)) {
            for (Map<String, Object> tabDeviceFreshair : tabDeviceFreshairList) {
                List<String> tousers = new ArrayList<String>();
                // 设备ID
                Long tabDeviceFreshairId = Long.parseLong(tabDeviceFreshair.get("tabDeviceFreshair").toString());
                // 设备序列号
                String deviceSeriaNumber = (String) tabDeviceFreshair.get("deviceSeriaNumber");
                // 依据设备序列号获取通知用户
                List<Map<String, Object>> tabCustomerList = getReciveAlramCustomerByDeviceSeriaNumber(deviceSeriaNumber);
                if (CollectionUtil.isNotEmpty(tabCustomerList)) {
                    for (Map<String, Object> tabCustomer : tabCustomerList) {
                        String wxId = (String) tabCustomer.get("wxId");
                        cont ++;
                        tousers.add(wxId);
                    }
                }
                // 依据设备ID获取设备数据
                Map<String, Object> getAverageFreshairDataParam = new HashMap<String, Object>();
                getAverageFreshairDataParam.put("recordTimeStart", (new Date().getTime() - (24 * 3600 * 1000)));
                getAverageFreshairDataParam.put("recordTimeEnd", (new Date().getTime()));
                getAverageFreshairDataParam.put("tabDeviceFreshairId", tabDeviceFreshairId);
                // 从小时表获取前一天数据
                List<Map<String, Object>> freshairtimingHourAverageList = dataFreshairHourService.timingHourAverageData(getAverageFreshairDataParam);
                if (CollectionUtil.isNotEmpty(freshairtimingHourAverageList) && CollectionUtil.isNotEmpty(freshairtimingHourAverageList.get(0))) {
                    Map<String, Object> freshairtimingHourAverage = freshairtimingHourAverageList.get(0);
                    //温度
                    String wendu = freshairtimingHourAverage.get("wendu").toString();
                    //湿度
                    String shidu = freshairtimingHourAverage.get("shidu").toString();
                    //pm
                    String pm = freshairtimingHourAverage.get("pm").toString();
                    //voc
                    String voc = freshairtimingHourAverage.get("voc").toString();
                    //co2
                    String co2 = freshairtimingHourAverage.get("co2").toString();

                    String content = "您家中昨日空气监测数据如下[温度:"+wendu+"℃][湿度:"+shidu+"%][PM2.5:"+pm+"μg/m³][VOC:"+voc+"μg/m³][CO2:"+co2+"ppm]";

                    // 组装数据 发送给用户
                    JSONObject putData = new JSONObject();
                    Date date = new Date();
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    // 问候语
                    putData.put("first", ContentToJsonObj("用户您好:" + content, "#173177"));
                    // 系统名称
                    putData.put("system", ContentToJsonObj("ATAT智能家监测", "#173177"));
                    // 截止时间
                    putData.put("time", ContentToJsonObj(format.format(date), "#173177"));
                    // 报警次数
                    putData.put("account", ContentToJsonObj("1", "#173177"));
                    // 结尾
                    putData.put("remark", ContentToJsonObj("详细数据请登录管理页面查看", "#173177"));
                    weixinAction.sendWeixinMessage(tousers, "", wxPlatformProperty.getWxWarnModel(), putData);
                }
            }
        }
        return cont;
    }

    @Override
    public Integer SendMildlyAlarm() {
        Integer cont = 0;
        //查询报警记录表make状态为mildly的数据
        // 依据用户信息和设备号以及报警部位名称 报警方式 查找最近一次发送时间
        Map<String, Object> findCustomerAlarmRecordParam = new HashMap<String, Object>();
        findCustomerAlarmRecordParam.put("mark", "mildly");
        findCustomerAlarmRecordParam.put("type", 1);
        List<Map<String, Object>> tabCustomerAlarmRecordList = tabCustomerAlarmRecordService.selectTabCustomerAlarmRecordList(findCustomerAlarmRecordParam);
        if (CollectionUtil.isNotEmpty(tabCustomerAlarmRecordList)){
            for (Map<String, Object> tabCustomerAlarmRecord : tabCustomerAlarmRecordList) {
                Long tabCustomerAlarmRecordId = Long.parseLong(tabCustomerAlarmRecord.get("tabCustomerAlarmRecordId").toString());
                Long tabCustomerId = Long.parseLong(tabCustomerAlarmRecord.get("tabCustomerId").toString());
                String deviceSeriaNumber = (String) tabCustomerAlarmRecord.get("deviceSeriaNumber");
                String partName = (String) tabCustomerAlarmRecord.get("partName");
                String content = (String) tabCustomerAlarmRecord.get("content");
                //获取依据设备号获取用户列表
                List<String> tousers = new ArrayList<String>();
                List<Map<String, Object>> tabCustomerList = getReciveAlramCustomerByDeviceSeriaNumber(deviceSeriaNumber);
                if (CollectionUtil.isNotEmpty(tabCustomerList)) {
                    for (Map<String, Object> tabCustomer : tabCustomerList) {
                        String wxId = (String) tabCustomer.get("wxId");
                        cont ++;
                        tousers.add(wxId);
                    }
                }
                //组装数据
                JSONObject putData = new JSONObject();
                Date date = new Date();
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                // 问候语
                putData.put("first", ContentToJsonObj("用户您好:" + content, "#173177"));
                // 系统名称
                putData.put("system", ContentToJsonObj("ATAT智能家" + partName + "监测", "#173177"));
                // 截止时间
                putData.put("time", ContentToJsonObj(format.format(date), "#173177"));
                // 报警次数
                putData.put("account", ContentToJsonObj("1", "#173177"));
                // 结尾
                putData.put("remark", ContentToJsonObj("详细数据请登录管理页面查看", "#173177"));
                weixinAction.sendWeixinMessage(tousers, "", wxPlatformProperty.getWxWarnModel(), putData);
                //更新记录表
                TabCustomerAlarmRecord tabCustomerAlarmRecordBean = new TabCustomerAlarmRecord();
                tabCustomerAlarmRecordBean.setTabCustomerAlarmRecordId(tabCustomerAlarmRecordId);
                tabCustomerAlarmRecordBean.setModifiedDate(new Date());
                tabCustomerAlarmRecordBean.setCount(1);
                tabCustomerAlarmRecordService.updateTabCustomerAlarmRecordById(tabCustomerAlarmRecordBean);
            }
        }
        return cont;
    }

    // 依据设备号查找接收消息的用户列表
    public List<Map<String, Object>> getReciveAlramCustomerByDeviceSeriaNumber(String deviceSeriaNumber) {
        List<Map<String, Object>> tabCustomerList = new ArrayList<Map<String, Object>>();
        // 依据设备序列号获取所有用户微信ID
        Map<String, Object> tabDeviceFreshairinfo = tabDeviceFreshairService.getTabDeviceFreshairByDeviceSeriaNumber(deviceSeriaNumber);
        if (CollectionUtil.isNotEmpty(tabDeviceFreshairinfo) && null != tabDeviceFreshairinfo.get("tabDeviceGroupId")) {
            // 获取设备分组
            Long tabDeviceFreshairId = Long.parseLong(tabDeviceFreshairinfo.get("tabDeviceFreshairId").toString());
            Long tabDeviceGroupId = Long.parseLong(tabDeviceFreshairinfo.get("tabDeviceGroupId").toString());
            // 依据分组获取用户列表
            Map<String, Object> findDeviceGroupParam = new HashMap<String, Object>();
            findDeviceGroupParam.put("tabDeviceGroupId", tabDeviceGroupId);
            findDeviceGroupParam.put("isSendMsg", 1);
            List<Map<String, Object>> relCustomerDeviceGroupList = relCustomerDeviceGroupDao.selectRelCustomerDeviceGroupList(findDeviceGroupParam);
            if (CollectionUtil.isNotEmpty(relCustomerDeviceGroupList)) {
                List<String> tousers = new ArrayList<String>();
                Integer cont = 0;
                for (Map<String, Object> relCustomerDeviceGroup : relCustomerDeviceGroupList) {
                    Long tabCustomerId = Long.parseLong(relCustomerDeviceGroup.get("tabCustomerId").toString());
                    Map<String, Object> tabCustomer = tabCustomerService.getTabCustomerById(tabCustomerId);
                    if (CollectionUtil.isNotEmpty(tabCustomer)) {
                        tabCustomerList.add(tabCustomer);
                    }
                }
                return tabCustomerList;
            }
            else {
                return null;
            }
        }
        else {
            return null;
        }
    }

    /**
     * 将数据及颜色放入JSONObject中
     *
     * @param content
     * @param color
     * @return
     */
    private static JSONObject ContentToJsonObj(String content, String color) {
        JSONObject jo = new JSONObject();
        jo.put("value", content);// 消息提示
        jo.put("color", color);
        return jo;
    }

    public static Map<String, Object> GetDateForResult(String partName, Double value) {
        Map<String, Object> getmap = new HashMap<>();
        switch (partName) {
            case "wendu" : {
                if (value < 5) {
                    getmap.put("state", "1");
                    getmap.put("mark", "moderate");
                    getmap.put("content", "您家中温度值过低，请小心感冒。");
                }
                else if (value > 35) {
                    getmap.put("state", "2");
                    getmap.put("mark", "moderate");
                    getmap.put("content", "您家中温度值过高，请小心中暑。");
                }
                else {
                    getmap.put("state", "0");
                    getmap.put("mark", "normal");
                    getmap.put("content", "温度正常");
                }
                break;
            }
            case "shidu" : {
                if (value > 90) {
                    getmap.put("state", "2");
                    getmap.put("mark", "moderate");
                    getmap.put("content", "您家中湿度值过高，请进行除湿操作");
                }
                else if (value < 10) {
                    getmap.put("state", "1");
                    getmap.put("mark", "moderate");
                    getmap.put("content", "您家中湿度值过低，请进行加湿操作");
                }
                else {
                    getmap.put("state", "0");
                    getmap.put("mark", "normal");
                    getmap.put("content", "湿度正常");
                }
                break;
            }
            case "pm" : {
                if (value >= 50 && value < 80) {
                    getmap.put("state", "3");
                    getmap.put("mark", "mildly");
                    getmap.put("content", "您家中Pm状态为良");
                }
                else if (value >= 80 && value < 150) {
                    getmap.put("state", "4");
                    getmap.put("mark", "mildly");
                    getmap.put("content", "您家中Pm状态为轻度污染");
                }
                else if (value >= 150 && value < 300) {
                    getmap.put("state", "5");
                    getmap.put("mark", "moderate");
                    getmap.put("content", "您家中Pm状态为中度污染");
                }
                else if (value >= 300) {
                    getmap.put("state", "6");
                    getmap.put("mark", "severe");
                    getmap.put("content", "您家中Pm状态为重度污染");
                }
                else {
                    getmap.put("state", "0");
                    getmap.put("mark", "normal");
                    getmap.put("content", "Pm正常");
                }
                break;
            }
            case "co2" : {
                if (value >= 1000 && value < 2000) {
                    getmap.put("state", "3");
                    getmap.put("mark", "mildly");
                    getmap.put("content", "您家中co2含量较高，空气浑浊，并开始觉得昏昏欲睡的状态,请及时通风");
                }
                else if (value >= 2000 && value < 5000) {
                    getmap.put("state", "5");
                    getmap.put("mark", "moderate");
                    getmap.put("content", "您家中co2含量很高，您会感觉头痛、嗜睡、呆滞、注意力无法集中、心跳加速、轻度恶心,请及时通风");
                }
                else if (value >= 5000) {
                    getmap.put("state", "6");
                    getmap.put("mark", "severe");
                    getmap.put("content", "您家中co2含量非常高，可能导致严重缺氧，造成永久性脑损伤、昏迷、甚至死亡。,请务必及时通风");
                }
                else {
                    getmap.put("state", "0");
                    getmap.put("mark", "normal");
                    getmap.put("content", "co2正常");
                }
                break;
            }
            case "voc" : {
                if (value >= 0.15 && value < 0.3) {
                    getmap.put("state", "3");
                    getmap.put("mark", "mildly");
                    getmap.put("content", "您家中voc含量较高,请及时打开新风设备，或者开窗通风");
                }
                else if (value >= 0.3) {
                    getmap.put("state", "6");
                    getmap.put("mark", "severe");
                    getmap.put("content", "您家中voc含量很高,请务必打开新风设备，或者开窗通风");
                }
                else {
                    getmap.put("state", "0");
                    getmap.put("mark", "normal");
                    getmap.put("content", "voc正常");
                }
                break;
            }
            default : {
                getmap.put("state", "0");
                getmap.put("mark", "unknowen");
                getmap.put("content", "");
            }
        }
        return getmap;
    }
}
