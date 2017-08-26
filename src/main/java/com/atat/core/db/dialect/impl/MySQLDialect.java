//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.atat.core.db.dialect.impl;

import org.apache.log4j.Logger;

public final class MySQLDialect extends AbstractDialect {
    private final Logger logger = Logger.getLogger(this.getClass());

    public MySQLDialect() {
    }

    public String getPaginationString(String sql, int begin, int end) {
        byte begin1;
        begin = begin == 0?(begin1 = 1):begin;
        int curIndex = (begin - 1) * end;
        sql = this.trim(sql);
        StringBuffer stringbuffer = new StringBuffer(sql);
        stringbuffer.append(" LIMIT ").append(curIndex).append(" , ").append(end);
        this.logger.debug("MySQLDialect execute sql:" + stringbuffer.toString());
        return stringbuffer.toString();
    }
}
