package com.atat.soft.freshair.service.impl;

import com.atat.core.db.model.PageTurn;
import com.atat.core.db.service.impl.BaseSupportServiceImpl;
import com.atat.soft.freshair.action.FreshAirDataFormate;
import com.atat.soft.freshair.bean.DataFreshairNow;
import com.atat.soft.freshair.dao.DataFreshairHourDao;
import com.atat.soft.freshair.dao.DataFreshairNowDao;
import com.atat.soft.freshair.service.DataFreshairNowService;
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
public class DataFreshairNowServiceImpl extends BaseSupportServiceImpl implements DataFreshairNowService {

    @Autowired
    private DataFreshairNowDao dataFreshairNowDao;

    @Autowired
    private DataFreshairHourDao dataFreshairHourDao;

    private String daoNamespace = DataFreshairNowDao.class.getName();

    @Override
    public void  addDataFreshairNow(DataFreshairNow dataFreshairNow) {
        dataFreshairNowDao.addDataFreshairNow(dataFreshairNow);
    }

    @Override
    public void  updateDataFreshairNowById(DataFreshairNow dataFreshairNow) {
        dataFreshairNowDao.updateDataFreshairNowById(dataFreshairNow);
    }

    @Override
    public List<Map<String, Object>> selectDataFreshairNowList(Map<String, Object> param) {
        return dataFreshairNowDao.selectDataFreshairNowList(param);
    }

    @Override
    public PageTurn<Map<String, Object>> getDataFreshairNowPageTurn(Map<String, Object> param, int pageNo, int pageSize) {
        return this.getDao().pagedQuery(daoNamespace + ".selectDataFreshairNowList", pageNo, pageSize, param);
    }

    @Override
    public void delDataFreshairNowById(Long dataFreshairNowId) {

     //DataFreshairNow dataFreshairNow = new DataFreshairNow();
     //dataFreshairNow.setIsDeleted(1);
     //dataFreshairNow.setDataFreshairNowId(dataFreshairNowId);
     //dataFreshairNowDao.updateDataFreshairNowById(dataFreshairNow);

        dataFreshairNowDao.delDataFreshairNowById(dataFreshairNowId);

    }

    @Override
    public void timingFormateForThreeHour() {
        //分别计算前六小时到前三小时内的平均值
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR, -6);
        List<Map<String, Object>> deviceDataList = new ArrayList<Map<String, Object>>();
        for (int i=0;i<3;i++){
            Long recordTimeStart = cal.getTime().getTime();
            cal.add(Calendar.HOUR, 1);
            Long recordTimeEnd = cal.getTime().getTime();
            Map<String, Object> param_oneHour = new HashMap<String, Object>();
            param_oneHour.put("recordTimeStart",recordTimeStart);
            param_oneHour.put("recordTimeEnd",recordTimeEnd);
            deviceDataList.addAll(dataFreshairNowDao.timingNowAverageData(param_oneHour));
        }
        //存入小时表
        if (CollectionUtil.isNotEmpty(deviceDataList)){
            dataFreshairHourDao.addDataHourList(deviceDataList);
        }
        //移除now表之前的数据
        //cal.add(Calendar.HOUR, 3);
        dataFreshairNowDao.delDataNowByEndTime(cal.getTime().getTime());
    }

    @Override public List<Map<String, Object>> getThreeHourDeviceData(Long tabDeviceFreshairId) {
        List<Map<String, Object>> categoryParameter = new ArrayList<Map<String, Object>>();
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("tabDeviceFreshairId", tabDeviceFreshairId);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR, -3);
        param.put("recordTimeStart", cal.getTime().getTime());
        List<Map<String, Object>> deviceThreeHourData = dataFreshairNowDao.selectDataFreshairNowList(param);
        categoryParameter = FreshAirDataFormate.formateDataForEchar(deviceThreeHourData);
        return categoryParameter;
    }

    @Override public List<Map<String, Object>> timingNowAverageData(Map param) {
        return dataFreshairNowDao.timingNowAverageData(param);
    }
}
