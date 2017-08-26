package com.atat.soft.freshair.controller;

import com.atat.soft.common.bean.JsonResult;
import com.atat.soft.util.StringUtil;
import com.atat.soft.common.bean.ResultCode;
import com.atat.soft.freshair.bean.DataFreshairDay;
import com.atat.soft.freshair.service.DataFreshairDayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wuhaosoft
 * @version $Id DataFreshairDayController.java, 2017-08-11 17:34:10 wuhaosoft
 *          Exp
 */
@Api("空气监测设备每日数据表接口")
@RestController
@RequestMapping("/freshair")
public class DataFreshairDayController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DataFreshairDayService dataFreshairDayService;

    @ApiOperation("查询 所有 空气监测设备每日数据表 分页")
    @RequestMapping(value = "/dataFreshairDays", method = RequestMethod.POST)
    public Object getDataFreshairDayPageTurn(
            @ApiParam(value = "设备Id (非必传参数)") @RequestParam(required = false) Long tabDeviceFreshairId,
            @ApiParam(value = "温度 (非必传参数)") @RequestParam(required = false) Double wendu,
            @ApiParam(value = "湿度 (非必传参数)") @RequestParam(required = false) Double shidu,
            @ApiParam(value = "pm2.5 (非必传参数)") @RequestParam(required = false) Double pm,
            @ApiParam(value = "voc (非必传参数)") @RequestParam(required = false) Double voc,
            @ApiParam(value = "CO2 (非必传参数)") @RequestParam(required = false) Double co2,
            @ApiParam(value = "时间范围查询-开始(非必传参数日期格式:yyyy-MM-dd HH)") @RequestParam(required = false) @DateTimeFormat(
                    pattern = "yyyy-MM-dd HH") Date recordTimeStart,
            @ApiParam(value = "时间范围查询-截至(非必传参数日期格式:yyyy-MM-dd HH)") @RequestParam(required = false) @DateTimeFormat(
                    pattern = "yyyy-MM-dd HH") Date recordTimeEnd,
            @ApiParam(value = "页码(必传)") @RequestParam Integer pageNo,
            @ApiParam(value = "每页显示多少数据(必传)") @RequestParam Integer pageSize) throws Exception {
        Map<String, Object> rs = new HashMap<String, Object>();
        if (null != tabDeviceFreshairId) {
            rs.put("tabDeviceFreshairId", tabDeviceFreshairId);
        }
        if (null != wendu) {
            rs.put("wendu", wendu);
        }
        if (null != shidu) {
            rs.put("shidu", shidu);
        }
        if (null != pm) {
            rs.put("pm", pm);
        }
        if (null != voc) {
            rs.put("voc", voc);
        }
        if (null != co2) {
            rs.put("co2", co2);
        }
        if (null != recordTimeStart) {
            rs.put("recordTimeStart", recordTimeStart.getTime());
        }
        if (null != recordTimeEnd) {
            rs.put("recordTimeEnd", recordTimeEnd.getTime());
        }
        return dataFreshairDayService.getDataFreshairDayPageTurn(rs, pageNo, pageSize);
    }

    @ApiOperation("查询 所有 空气监测设备每日数据表")
    @RequestMapping(value = "/allDataFreshairDays", method = RequestMethod.POST)
    public JsonResult<Object> selectDataFreshairDayList(
            @ApiParam(value = "设备Id (非必传参数)") @RequestParam(required = false) Long tabDeviceFreshairId,
            @ApiParam(value = "温度 (非必传参数)") @RequestParam(required = false) Double wendu,
            @ApiParam(value = "湿度 (非必传参数)") @RequestParam(required = false) Double shidu,
            @ApiParam(value = "pm2.5 (非必传参数)") @RequestParam(required = false) Double pm,
            @ApiParam(value = "voc (非必传参数)") @RequestParam(required = false) Double voc,
            @ApiParam(value = "CO2 (非必传参数)") @RequestParam(required = false) Double co2,
            @ApiParam(value = "时间范围查询-开始(非必传参数日期格式:yyyy-MM-dd HH)") @RequestParam(required = false) @DateTimeFormat(
                    pattern = "yyyy-MM-dd HH") Date recordTimeStart,
            @ApiParam(value = "时间范围查询-截至(非必传参数日期格式:yyyy-MM-dd HH)") @RequestParam(required = false) @DateTimeFormat(
                    pattern = "yyyy-MM-dd HH") Date recordTimeEnd)
            throws Exception {
        JsonResult<Object> result = new JsonResult<Object>();
        Map<String, Object> rs = new HashMap<String, Object>();
        if (null != tabDeviceFreshairId) {
            rs.put("tabDeviceFreshairId", tabDeviceFreshairId);
        }
        if (null != wendu) {
            rs.put("wendu", wendu);
        }
        if (null != shidu) {
            rs.put("shidu", shidu);
        }
        if (null != pm) {
            rs.put("pm", pm);
        }
        if (null != voc) {
            rs.put("voc", voc);
        }
        if (null != co2) {
            rs.put("co2", co2);
        }
        if (null != recordTimeStart) {
            rs.put("recordTimeStart", recordTimeStart.getTime());
        }
        if (null != recordTimeEnd) {
            rs.put("recordTimeEnd", recordTimeEnd.getTime());
        }
        result.setObj(dataFreshairDayService.selectDataFreshairDayList(rs));
        result.setCode(ResultCode.SUCCESS.getCode());
        return result;
    }

    @ApiOperation("新增 空气监测设备每日数据表")
    @RequestMapping(value = "/dataFreshairDay", method = RequestMethod.POST)
    public JsonResult<Object> addDataFreshairDay(
            @ApiParam(value = "设备Id (非必传参数)") @RequestParam(required = false) Long tabDeviceFreshairId,
            @ApiParam(value = "温度 (非必传参数)") @RequestParam(required = false) Double wendu,
            @ApiParam(value = "湿度 (非必传参数)") @RequestParam(required = false) Double shidu,
            @ApiParam(value = "pm2.5 (非必传参数)") @RequestParam(required = false) Double pm,
            @ApiParam(value = "voc (非必传参数)") @RequestParam(required = false) Double voc,
            @ApiParam(value = "CO2 (非必传参数)") @RequestParam(required = false) Double co2) throws Exception {
        JsonResult<Object> result = new JsonResult<Object>();
        DataFreshairDay dataFreshairDay = new DataFreshairDay();
        if (null != tabDeviceFreshairId) {
            dataFreshairDay.setTabDeviceFreshairId(tabDeviceFreshairId);
        }
        if (null != wendu) {
            dataFreshairDay.setWendu(wendu);
        }
        if (null != shidu) {
            dataFreshairDay.setShidu(shidu);
        }
        if (null != pm) {
            dataFreshairDay.setPm(pm);
        }
        if (null != voc) {
            dataFreshairDay.setVoc(voc);
        }
        if (null != co2) {
            dataFreshairDay.setCo2(co2);
        }
        dataFreshairDay.setTabDeviceFreshairId(new Date().getTime());
        dataFreshairDayService.addDataFreshairDay(dataFreshairDay);
        result.setCode(ResultCode.SUCCESS.getCode());
        return result;
    }

    @ApiOperation("更新 空气监测设备每日数据表")
    @RequestMapping(value = "/dataFreshairDay/{dataFreshairDayId}", method = RequestMethod.PUT)
    public JsonResult<Object> updateDataFreshairDayById(
            @ApiParam(value = "采集时间 (必传参数)") @PathVariable Long dataFreshairDayId,
            @ApiParam(value = "设备Id (非必传参数)") @RequestParam(required = false) Long tabDeviceFreshairId,
            @ApiParam(value = "温度 (非必传参数)") @RequestParam(required = false) Double wendu,
            @ApiParam(value = "湿度 (非必传参数)") @RequestParam(required = false) Double shidu,
            @ApiParam(value = "pm2.5 (非必传参数)") @RequestParam(required = false) Double pm,
            @ApiParam(value = "voc (非必传参数)") @RequestParam(required = false) Double voc,
            @ApiParam(value = "CO2 (非必传参数)") @RequestParam(required = false) Double co2) throws Exception {
        JsonResult<Object> result = new JsonResult<Object>();
        DataFreshairDay dataFreshairDay = new DataFreshairDay();
        dataFreshairDay.setDataFreshairDayId(dataFreshairDayId);
        if (null != tabDeviceFreshairId) {
            dataFreshairDay.setTabDeviceFreshairId(tabDeviceFreshairId);
        }
        if (null != wendu) {
            dataFreshairDay.setWendu(wendu);
        }
        if (null != shidu) {
            dataFreshairDay.setShidu(shidu);
        }
        if (null != pm) {
            dataFreshairDay.setPm(pm);
        }
        if (null != voc) {
            dataFreshairDay.setVoc(voc);
        }
        if (null != co2) {
            dataFreshairDay.setCo2(co2);
        }
        // dataFreshairDay.setUpdateDate(new Date());
        dataFreshairDayService.updateDataFreshairDayById(dataFreshairDay);
        result.setCode(ResultCode.SUCCESS.getCode());
        return result;
    }

    @ApiOperation("删除 空气监测设备每日数据表")
    @RequestMapping(value = "/dataFreshairDay/{dataFreshairDayId}", method = RequestMethod.DELETE)
    public JsonResult<Object> delDataFreshairDayById(
            @ApiParam(value = "采集时间 (必传参数)") @PathVariable Long dataFreshairDayId) throws Exception {
        JsonResult<Object> result = new JsonResult<Object>();
        dataFreshairDayService.delDataFreshairDayById(dataFreshairDayId);
        result.setCode(ResultCode.SUCCESS.getCode());
        return result;
    }

    @ApiOperation("查询 空气监测设备 一月内数据")
    @RequestMapping(value = "/oneMounthFreshairData/{tabDeviceFreshairId}", method = RequestMethod.GET)
    public JsonResult<Object> delTabDeviceFreshairById(
            @ApiParam(value = "设备ID (必传参数)") @PathVariable Long  tabDeviceFreshairId
    ) throws Exception {
        JsonResult<Object> result = new JsonResult<Object>();
        dataFreshairDayService.getOneMounthDeviceData(tabDeviceFreshairId);
        result.setCode(ResultCode.SUCCESS.getCode());
        return result;
    }
}
