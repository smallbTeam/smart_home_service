package com.atat.soft.freshair.service;

import com.atat.core.db.model.PageTurn;
import com.atat.soft.freshair.bean.DataFreshairNow;

import java.util.List;
import java.util.Map;

/**
 *
 * @author whaosoft
 *
 */
public interface DataFreshairNowService {

    /**
     * 添加 空气监测设备实时数据表
     * @param dataFreshairNow
     */
    public void  addDataFreshairNow(DataFreshairNow dataFreshairNow);

    /**
     * 依据主键更新 空气监测设备实时数据表
     * @param dataFreshairNow
     */
    public void  updateDataFreshairNowById(DataFreshairNow dataFreshairNow);

    /**
     * 依据条件查找 空气监测设备实时数据表 列表
     * @param param
     * @return
     */
    public List<Map<String, Object>> selectDataFreshairNowList(Map<String, Object> param);

    /**
     * 依据条件查找分页 空气监测设备实时数据表 列表
     * @param param
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageTurn<Map<String, Object>> getDataFreshairNowPageTurn(Map<String, Object> param, int pageNo, int pageSize);


    /**
     * 依据Id删除 空气监测设备实时数据表 记录
     * @param dataFreshairNowId
     */
    public void delDataFreshairNowById(Long dataFreshairNowId);

    /**
     * 空气检测设备实时表 每3小时向小时表导入1次数据
     */
    public void timingFormateForThreeHour();

    /**
     * 获取空气检测设备近三小时数据
     * @param tabDeviceFreshairId
     * @return
     */
    public List<Map<String, Object>> getThreeHourDeviceData(Long tabDeviceFreshairId);

    /**
     * 计算空气检测设备时间段内平均值
     * @param param
     * @return
     */
    public List<Map<String, Object>> timingNowAverageData(Map param);
}
