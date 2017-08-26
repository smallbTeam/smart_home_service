package com.atat.soft.freshair.service.impl;

import com.atat.core.db.model.PageTurn;
import com.atat.core.db.service.impl.BaseSupportServiceImpl;
import com.atat.soft.freshair.action.FreshAirDataFormate;
import com.atat.soft.freshair.bean.DataFreshairHour;
import com.atat.soft.freshair.dao.DataFreshairDayDao;
import com.atat.soft.freshair.dao.DataFreshairHourDao;
import com.atat.soft.freshair.service.DataFreshairHourService;
import com.atat.soft.util.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 *
 * @author whaosoft
 *
 */
@Service
@Transactional
public class DataFreshairHourServiceImpl extends BaseSupportServiceImpl implements DataFreshairHourService {

    @Autowired
    private DataFreshairHourDao dataFreshairHourDao;

    @Autowired
    private DataFreshairDayDao dataFreshairDayDao;

    private String daoNamespace = DataFreshairHourDao.class.getName();

    @Override
    public void  addDataFreshairHour(DataFreshairHour dataFreshairHour) {
        dataFreshairHourDao.addDataFreshairHour(dataFreshairHour);
    }

    @Override
    public void  updateDataFreshairHourById(DataFreshairHour dataFreshairHour) {
        dataFreshairHourDao.updateDataFreshairHourById(dataFreshairHour);
    }

    @Override
    public List<Map<String, Object>> selectDataFreshairHourList(Map<String, Object> param) {
        return dataFreshairHourDao.selectDataFreshairHourList(param);
    }

    @Override
    public PageTurn<Map<String, Object>> getDataFreshairHourPageTurn(Map<String, Object> param, int pageNo, int pageSize) {
        return this.getDao().pagedQuery(daoNamespace + ".selectDataFreshairHourList", pageNo, pageSize, param);
    }

    @Override
    public void delDataFreshairHourById(Long dataFreshairHourId) {

     //DataFreshairHour dataFreshairHour = new DataFreshairHour();
     //dataFreshairHour.setIsDeleted(1);
     //dataFreshairHour.setDataFreshairHourId(dataFreshairHourId);
     //dataFreshairHourDao.updateDataFreshairHourById(dataFreshairHour);

        dataFreshairHourDao.delDataFreshairHourById(dataFreshairHourId);

    }

    @Override public void timingFormateForOneDay() {
        //分别计算前一天前的设备平均值的平均值
        List<Map<String, Object>> deviceDataList = new ArrayList<Map<String, Object>>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -1);
        Long recordTimeEnd = cal.getTime().getTime();
        cal.add(Calendar.DATE, -1);
        Long recordTimeStart = cal.getTime().getTime();
        Map<String, Object> param_day = new HashMap<String, Object>();
        param_day.put("recordTimeStart",recordTimeStart);
        param_day.put("recordTimeEnd",recordTimeEnd);
        deviceDataList.addAll(dataFreshairHourDao.timingHourAverageData(param_day));
        //存入天表
        if (CollectionUtil.isNotEmpty(deviceDataList)){
            dataFreshairDayDao.addDataDayList(deviceDataList);
        }
        //移除Hour表之前的数据
        cal.add(Calendar.DATE, 1);
        dataFreshairHourDao.delDataHourByEndTime(cal.getTime().getTime());
    }

    @Override public List<Map<String, Object>> getOneDayDeviceData(Long tabDeviceFreshairId) {
        List<Map<String, Object>> categoryParameter = new ArrayList<Map<String, Object>>();
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("tabDeviceFreshairId", tabDeviceFreshairId);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -1);
        param.put("recordTimeStart", cal.getTime().getTime());
        List<Map<String, Object>> deviceOneDayData = dataFreshairHourDao.selectDataFreshairHourList(param);
        categoryParameter = FreshAirDataFormate.formateDataForEchar(deviceOneDayData);
        return categoryParameter;
    }
}
