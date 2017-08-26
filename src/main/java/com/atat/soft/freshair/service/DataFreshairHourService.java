package com.atat.soft.freshair.service;

import com.atat.core.db.model.PageTurn;
import com.atat.soft.freshair.bean.DataFreshairHour;

import java.util.List;
import java.util.Map;

/**
 *
 * @author whaosoft
 *
 */
public interface DataFreshairHourService {

    /**
     * 添加 空气监测设备每小时数据表
     * @param dataFreshairHour
     */
    public void  addDataFreshairHour(DataFreshairHour dataFreshairHour);

    /**
     * 依据主键更新 空气监测设备每小时数据表
     * @param dataFreshairHour
     */
    public void  updateDataFreshairHourById(DataFreshairHour dataFreshairHour);

    /**
     * 依据条件查找 空气监测设备每小时数据表 列表
     * @param param
     * @return
     */
    public List<Map<String, Object>> selectDataFreshairHourList(Map<String, Object> param);

    /**
     * 依据条件查找分页 空气监测设备每小时数据表 列表
     * @param param
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageTurn<Map<String, Object>> getDataFreshairHourPageTurn(Map<String, Object> param, int pageNo, int pageSize);

    /**
     * 依据Id删除 空气监测设备每小时数据表 记录
     * @param dataFreshairHourId
     */
    public void delDataFreshairHourById(Long dataFreshairHourId);

    /**
     * 空气检测设备每天 小时表向天表导入一次数据
     */
    public void timingFormateForOneDay();

    /**
     * 获取设备一天内小时节点表内数据
     * @param tabDeviceFreshairId
     * @return
     */
    public List<Map<String, Object>> getOneDayDeviceData(Long tabDeviceFreshairId);
}
