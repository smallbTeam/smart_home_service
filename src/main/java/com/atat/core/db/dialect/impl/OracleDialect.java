//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.atat.core.db.dialect.impl;

import org.apache.log4j.Logger;

public final class OracleDialect extends AbstractDialect {
    private final Logger logger = Logger.getLogger(this.getClass());

    public OracleDialect() {
    }

    public String getPaginationString(String sql, int begin, int end) {
        begin = begin == 0?1:begin;
        int curIndex = (begin - 1) * end;
        int toIndex = curIndex + end;
        sql = this.trim(sql);
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append("SELECT * FROM (  SELECT WHAOSOFT_SQL_.*, ROWNUM AS ROWNUM_ FROM (");
        stringbuffer.append(sql);
        stringbuffer.append(" ) WHAOSOFT_SQL_ WHERE ROWNUM <= " + toIndex + " ) WHERE ROWNUM_ > " + curIndex);
        this.logger.debug("OracleDialect execute sql:" + stringbuffer.toString());
        return stringbuffer.toString();
    }
}
