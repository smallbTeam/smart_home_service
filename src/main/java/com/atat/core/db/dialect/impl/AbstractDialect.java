//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.atat.core.db.dialect.impl;

import com.atat.core.db.dialect.IDialect;
import com.atat.core.db.utils.SqlUtils;

public abstract class AbstractDialect implements IDialect {
    protected static final String SQL_END_DELIMITER = ";";

    public AbstractDialect() {
    }

    protected String trim(String s) {
        s = s.trim();
        if(s.endsWith(";")) {
            s = s.substring(0, s.length() - 1 - ";".length());
        }

        return s;
    }

    public String getCountString(String sql) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT COUNT(HIKESOFT_COUNT.X) COUNTNUM FROM ( SELECT 'X' X ");
        sb.append(SqlUtils.removeOrders(SqlUtils.removeSelect(sql)));
        sb.append(" ) HIKESOFT_COUNT ");
        return sb.toString();
    }
}
