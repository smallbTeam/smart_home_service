//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.atat.core.db.dialect.impl;

import org.apache.log4j.Logger;

public final class DB2Dialect extends AbstractDialect {
    private final Logger logger = Logger.getLogger(this.getClass());

    public DB2Dialect() {
    }

    public String getPaginationString(String sql, int begin, int end) {
        sql = this.trim(sql);
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append("SELECT * FROM (  SELECT HIKESOFT_SQL_.*, ROWNUMBER() OVER() AS RN FROM (");
        stringbuffer.append(sql);
        stringbuffer.append(" ) HIKESOFT_SQL_ )  WHERE RN <= " + (begin + end) + " AND RN > " + begin);
        this.logger.debug("DB2Dialect execute sql:" + stringbuffer.toString());
        return stringbuffer.toString();
    }
}
