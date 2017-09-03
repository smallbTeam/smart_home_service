package com.atat.soft.customer.service;

import com.atat.core.db.model.PageTurn;
import com.atat.soft.customer.bean.TabCustomer;

import java.util.List;
import java.util.Map;

/**
 *
 * @author whaosoft
 *
 */
public interface TabCustomerService {

    /**
     * 添加 用户表
     * @param tabCustomer
     */
    public void  addTabCustomer(TabCustomer tabCustomer);

    /**
     * 依据主键更新 用户表
     * @param tabCustomer
     */
    public void  updateTabCustomerById(TabCustomer tabCustomer);

    /**
     * 依据条件查找 用户表 列表
     * @param param
     * @return
     */
    public List<Map<String, Object>> selectTabCustomerList(Map<String, Object> param);

    /**
     * 依据条件查找分页 用户表 列表
     * @param param
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageTurn<Map<String, Object>> getTabCustomerPageTurn(Map<String, Object> param, int pageNo, int pageSize);

    /**
     * 依据Id查找 用户表 详情
     * @param tabCustomerId
     * @return
     */
    public Map<String, Object> getTabCustomerById(Long tabCustomerId);

    /**
     * 依据Id删除 用户表 记录
     * @param tabCustomerId
     */
    public void delTabCustomerById(Long tabCustomerId);

    /**
     * 依据微信Id获取用户
     * @param wxId
     * @return
     */
    public  Map<String, Object> getCustomerByWxId(String wxId);

    /**
     * 用户登录
     * @param mobelPhone
     * @param password
     * @param wxId
     * @return
     */
    public Integer accountLogin(String mobelPhone, String password, String wxId);

    /**
     * 依据手机号查询账户信息
     * @param mobelPhone
     * @return
     */
    public Map<String, Object> getCustomerByMobelPhone(String mobelPhone);
}
