//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.atat.core.db.service.impl;

import com.atat.core.db.dao.SqlDao;
import com.atat.core.db.service.BaseService;
import org.springframework.context.MessageSource;

import javax.annotation.Resource;

public class BaseSupportServiceImpl implements BaseService {
    @Resource
    private SqlDao defaultSqlDao;
    @Resource
    private MessageSource messageSource;

    public BaseSupportServiceImpl() {
    }

    public SqlDao getDao() {
        return this.defaultSqlDao;
    }
}
