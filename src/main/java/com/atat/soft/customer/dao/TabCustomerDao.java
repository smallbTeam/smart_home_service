package com.atat.soft.customer.dao;

import com.atat.soft.customer.bean.TabCustomer;

import java.util.List;
import java.util.Map;

/**
 *
 * @author whaosoft
 *
 */
public interface TabCustomerDao {

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
     * 依据主键删除 用户表 记录
     * @param tabCustomerId
     */
    public void  delTabCustomerById(Integer tabCustomerId);

}
