package com.atat.soft.freshair.service.impl;

import com.atat.core.db.model.PageTurn;
import com.atat.core.db.service.impl.BaseSupportServiceImpl;
import com.atat.soft.freshair.action.FreshAirDataFormate;
import com.atat.soft.freshair.bean.DataFreshairWeek;
import com.atat.soft.freshair.dao.DataFreshairWeekDao;
import com.atat.soft.freshair.service.DataFreshairWeekService;
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
public class DataFreshairWeekServiceImpl extends BaseSupportServiceImpl implements DataFreshairWeekService {

    @Autowired
    private DataFreshairWeekDao dataFreshairWeekDao;

    private String daoNamespace = DataFreshairWeekDao.class.getName();

    @Override
    public void  addDataFreshairWeek(DataFreshairWeek dataFreshairWeek) {
        dataFreshairWeekDao.addDataFreshairWeek(dataFreshairWeek);
    }

    @Override
    public void  updateDataFreshairWeekById(DataFreshairWeek dataFreshairWeek) {
        dataFreshairWeekDao.updateDataFreshairWeekById(dataFreshairWeek);
    }

    @Override
    public List<Map<String, Object>> selectDataFreshairWeekList(Map<String, Object> param) {
        return dataFreshairWeekDao.selectDataFreshairWeekList(param);
    }

    @Override
    public PageTurn<Map<String, Object>> getDataFreshairWeekPageTurn(Map<String, Object> param, int pageNo, int pageSize) {
        return this.getDao().pagedQuery(daoNamespace + ".selectDataFreshairWeekList", pageNo, pageSize, param);
    }

    @Override
    public void delDataFreshairWeekById(Long dataFreshairWeekId) {

     //DataFreshairWeek dataFreshairWeek = new DataFreshairWeek();
     //dataFreshairWeek.setIsDeleted(1);
     //dataFreshairWeek.setDataFreshairWeekId(dataFreshairWeekId);
     //dataFreshairWeekDao.updateDataFreshairWeekById(dataFreshairWeek);

        dataFreshairWeekDao.delDataFreshairWeekById(dataFreshairWeekId);
    }

    @Override public List<Map<String, Object>> getOneYearDeviceData(Long tabDeviceFreshairId) {
        List<Map<String, Object>> categoryParameter = new ArrayList<Map<String, Object>>();
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("tabDeviceFreshairId", tabDeviceFreshairId);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.YEAR, -1);
        param.put("recordTimeStart", cal.getTime().getTime());
        List<Map<String, Object>> deviceOneMonthData = dataFreshairWeekDao.selectDataFreshairWeekList(param);
        categoryParameter = FreshAirDataFormate.formateDataForEchar(deviceOneMonthData);
        return categoryParameter;
    }
}
