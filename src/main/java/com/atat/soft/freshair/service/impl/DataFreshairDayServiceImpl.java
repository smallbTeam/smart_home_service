package com.atat.soft.freshair.service.impl;

import com.atat.core.db.model.PageTurn;
import com.atat.core.db.service.impl.BaseSupportServiceImpl;
import com.atat.soft.freshair.action.FreshAirDataFormate;
import com.atat.soft.freshair.bean.DataFreshairDay;
import com.atat.soft.freshair.dao.DataFreshairDayDao;
import com.atat.soft.freshair.dao.DataFreshairWeekDao;
import com.atat.soft.freshair.service.DataFreshairDayService;
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
public class DataFreshairDayServiceImpl extends BaseSupportServiceImpl implements DataFreshairDayService {

    @Autowired
    private DataFreshairDayDao dataFreshairDayDao;

    @Autowired
    private DataFreshairWeekDao dataFreshairWeekDao;

    private String daoNamespace = DataFreshairDayDao.class.getName();

    @Override
    public void  addDataFreshairDay(DataFreshairDay dataFreshairDay) {
        dataFreshairDayDao.addDataFreshairDay(dataFreshairDay);
    }

    @Override
    public void  updateDataFreshairDayById(DataFreshairDay dataFreshairDay) {
        dataFreshairDayDao.updateDataFreshairDayById(dataFreshairDay);
    }

    @Override
    public List<Map<String, Object>> selectDataFreshairDayList(Map<String, Object> param) {
        return dataFreshairDayDao.selectDataFreshairDayList(param);
    }

    @Override
    public PageTurn<Map<String, Object>> getDataFreshairDayPageTurn(Map<String, Object> param, int pageNo, int pageSize) {
        return this.getDao().pagedQuery(daoNamespace + ".selectDataFreshairDayList", pageNo, pageSize, param);
    }

    @Override
    public void delDataFreshairDayById(Long dataFreshairDayId) {

     //DataFreshairDay dataFreshairDay = new DataFreshairDay();
     //dataFreshairDay.setIsDeleted(1);
     //dataFreshairDay.setDataFreshairDayId(dataFreshairDayId);
     //dataFreshairDayDao.updateDataFreshairDayById(dataFreshairDay);

        dataFreshairDayDao.delDataFreshairDayById(dataFreshairDayId);

    }

    @Override public void timingFormateForOneWeek() {
        //分别计算前一周前的设备平均值的平均值
        List<Map<String, Object>> deviceDataList = new ArrayList<Map<String, Object>>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -7);
        Long recordTimeEnd = cal.getTime().getTime();
        cal.add(Calendar.DATE, -7);
        Long recordTimeStart = cal.getTime().getTime();
        Map<String, Object> param_day = new HashMap<String, Object>();
        param_day.put("recordTimeStart",recordTimeStart);
        param_day.put("recordTimeEnd",recordTimeEnd);
        deviceDataList.addAll(dataFreshairDayDao.timingDayAverageData(param_day));
        //存入周表
        if (CollectionUtil.isNotEmpty(deviceDataList)){
            dataFreshairWeekDao.addDataWeekList(deviceDataList);
        }
        //移除Day表之前的数据
        cal.add(Calendar.DATE, 7);
        dataFreshairDayDao.delDataDayByEndTime(cal.getTime().getTime());
    }

    @Override public List<Map<String, Object>> getOneMounthDeviceData(Long tabDeviceFreshairId) {
        List<Map<String, Object>> categoryParameter = new ArrayList<Map<String, Object>>();
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("tabDeviceFreshairId", tabDeviceFreshairId);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, -1);
        param.put("recordTimeStart", cal.getTime().getTime());
        List<Map<String, Object>> deviceOneMonthData = dataFreshairDayDao.selectDataFreshairDayList(param);
        categoryParameter = FreshAirDataFormate.formateDataForEchar(deviceOneMonthData);
        return categoryParameter;
    }
}
