//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.atat.core.db.dao.impl;

import com.atat.core.db.dao.SqlDao;
import com.atat.core.db.intercept.OffsetLimitInterceptor;
import com.atat.core.db.model.PageTurn;
import com.atat.core.db.utils.BaseUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.SqlSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class BaseSupportDaoImpl implements SqlDao {
    private static final Logger logger = LoggerFactory.getLogger(BaseSupportDaoImpl.class);
    private String dialect;
    private SqlSessionTemplate sqlSessionTemplate;

    public BaseSupportDaoImpl(SqlSessionTemplate sqlSessionTemplate, String dialect) {
        this.dialect = dialect;
        this.sqlSessionTemplate = sqlSessionTemplate;
        this.sqlSessionTemplate.getConfiguration().addInterceptor(new OffsetLimitInterceptor(dialect));
    }

    private int execJDBC(String sql) throws SQLException {
        return this.execJDBC(sql, (String[])null);
    }

    private int execJDBC(String sql, String[] param) throws SQLException {
        SqlSession conn = SqlSessionUtils.getSqlSession(this.sqlSessionTemplate.getSqlSessionFactory(), this.sqlSessionTemplate.getExecutorType(), this.sqlSessionTemplate.getPersistenceExceptionTranslator());

        try {
            PreparedStatement tmt = conn.getConnection().prepareStatement(sql);
            int i;
            if(param != null && param.length > 0) {
                i = 0;

                for(int len = param.length; i < len; ++i) {
                    tmt.setString(i + 1, param[i]);
                }
            }

            i = tmt.executeUpdate();
            tmt.close();
            tmt = null;
            logger.debug("execJDBC_SQL:" + sql);
            return i;
        } catch (SQLException var7) {
            logger.error(var7.getMessage());
            throw new RuntimeException(var7.getMessage());
        }
    }

    public <T> PageTurn<T> pagedQuery(String sqlMappingName, int pageNo, int pageSize, Map<String, Object> value) {
        List<T> result = new ArrayList(0);
        int total = this.selectCountNew(sqlMappingName, new Map[]{value});
        if(total < 1) {
            return new PageTurn();
        } else {
            if(total > 0) {
                result = this.selectPageNew(sqlMappingName, pageNo, pageSize, new Object[]{value});
            }

            return new PageTurn(pageNo, total, pageSize, (List)result);
        }
    }

    public <T> List<T> selectPage(String sqlMappingName, int begin, int end, Object[] value) {
        List list = null;
        if(value != null && value.length > 0) {
            list = this.sqlSessionTemplate.selectList(sqlMappingName, value[0], new RowBounds(begin, end));
        } else {
            list = this.sqlSessionTemplate.selectList(sqlMappingName, (Object)null, new RowBounds(begin, end));
        }

        return list;
    }

    public <T> List<T> selectPageNew(String sqlMappingName, int begin, int end, Object... value) {
        List list = null;
        if(value != null && value.length > 0) {
            list = this.sqlSessionTemplate.selectList(sqlMappingName, value[0], new RowBounds(begin, end));
        } else {
            list = this.sqlSessionTemplate.selectList(sqlMappingName, (Object)null, new RowBounds(begin, end));
        }

        return list;
    }

    public int insert(String table, Map<String, Object> bean) throws RuntimeException {
        try {
            SqlSession conn = SqlSessionUtils.getSqlSession(this.sqlSessionTemplate.getSqlSessionFactory(), this.sqlSessionTemplate.getExecutorType(), this.sqlSessionTemplate.getPersistenceExceptionTranslator());
            if(bean == null) {
                logger.error("bean is null");
                throw new SQLException("bean is null");
            } else {
                String[] column = new String[bean.size()];
                String sql = "INSERT INTO " + table + "($field$) VALUES($value$)";
                String fields = "";
                String values = "";
                Iterator<String> it = bean.keySet().iterator();

                String tmts;
                for(int i = 0; it.hasNext(); ++i) {
                    tmts = (String)it.next();
                    fields = fields + "," + tmts;
                    values = values + ",?";
                    column[i] = tmts;
                }

                if("".equals(fields)) {
                    logger.error("bean is empty");
                    throw new SQLException("bean is empty");
                } else {
                    sql = sql.replace("$field$", fields.substring(1, fields.length()));
                    sql = sql.replace("$value$", values.substring(1, values.length()));
                    PreparedStatement tmt = conn.getConnection().prepareStatement(sql);
                    int j = 0;

                    for(int len = column.length; j < len; ++j) {
                        Object obj = bean.get(column[j]);
                        if(obj instanceof Integer) {
                            Integer value = (Integer)obj;
                            tmt.setInt(j + 1, (value != null?Integer.valueOf(value.intValue()):null).intValue());
                        } else if(obj instanceof String) {
                            String value = (String)obj;
                            tmt.setString(j + 1, value);
                        } else if(obj instanceof Double) {
                            Double value = (Double)obj;
                            tmt.setDouble(j + 1, (value != null?Double.valueOf(value.doubleValue()):null).doubleValue());
                        } else if(obj instanceof Float) {
                            Float value = (Float)obj;
                            tmt.setFloat(j + 1, (value != null?Float.valueOf(value.floatValue()):null).floatValue());
                        } else if(obj instanceof Long) {
                            Long value = (Long)obj;
                            tmt.setLong(j + 1, (value != null?Long.valueOf(value.longValue()):null).longValue());
                        } else if(obj instanceof Boolean) {
                            Boolean value = (Boolean)obj;
                            tmt.setBoolean(j + 1, (value != null?Boolean.valueOf(value.booleanValue()):null).booleanValue());
                        } else if(obj instanceof Date) {
                            Date value = (Date)obj;
                            tmt.setString(j + 1, value != null?BaseUtils.formatDate(value, "yyyy-MM-dd HH:mm:ss"):null);
                        } else {
                            tmt.setString(j + 1, (String)null);
                        }
                    }

                    j = tmt.executeUpdate();
                    tmt.close();
                    tmt = null;
                    logger.debug("SQL:" + sql);
                    return j;
                }
            }
        } catch (Exception var15) {
            logger.error(var15.getMessage());
            throw new RuntimeException(var15.getMessage());
        }
    }

    public int delete(String table, String where) throws RuntimeException {
        try {
            String sql = "DELETE FROM " + table.toLowerCase() + " WHERE ?where?";
            if(BaseUtils.isNotBlank(where)) {
                sql = sql.replace("?where?", where);
            }

            logger.debug("SQL:" + sql);
            return this.execJDBC(sql, (String[])null);
        } catch (SQLException var4) {
            logger.error(var4.getMessage());
            throw new RuntimeException(var4.getMessage());
        }
    }

    public int update(String table, String where, Map<String, Object> record) {
        return this.update(table, where, record, (String)null, (String)null);
    }

    private int update(String table, String where, Map<String, Object> record, String versionField, String versionValue) throws RuntimeException {
        SqlSession conn = SqlSessionUtils.getSqlSession(this.sqlSessionTemplate.getSqlSessionFactory(), this.sqlSessionTemplate.getExecutorType(), this.sqlSessionTemplate.getPersistenceExceptionTranslator());

        try {
            if(record == null) {
                logger.error("bean is null");
                throw new SQLException("bean is null");
            } else {
                boolean locked = false;
                if(versionField != null && !"".equalsIgnoreCase(versionField) && versionValue != null && !"".equalsIgnoreCase(versionValue)) {
                    record.remove(versionField);
                    locked = true;
                }

                String[] column = new String[record.size()];
                String sql = "UPDATE " + table + " SET $values$ WHERE " + where;
                String fields = "";
                String values = "";
                if(locked) {
                    sql = sql + " and " + versionField + "='" + versionValue + "' ";
                }

                Iterator<String> it = record.keySet().iterator();

                String tmts;
                for(int i = 0; it.hasNext(); ++i) {
                    tmts = (String)it.next();
                    column[i] = tmts;
                    if("".equalsIgnoreCase(fields)) {
                        fields = tmts;
                        values = tmts + "=?";
                    } else {
                        fields = fields + "," + tmts;
                        values = values + "," + tmts + "=?";
                    }
                }

                if("".equals(fields)) {
                    logger.error("bean is empty");
                    throw new SQLException("bean is empty");
                } else {
                    if(locked) {
                        fields = fields + "," + versionField + "=" + versionField + "+1";
                    }

                    sql = sql.replace("$values$", values);
                    PreparedStatement tmt = conn.getConnection().prepareStatement(sql);
                    int j = 0;

                    for(int len = column.length; j < len; ++j) {
                        Object obj = record.get(column[j]);
                        if(obj instanceof Integer) {
                            Integer value = (Integer)obj;
                            tmt.setInt(j + 1, (value != null?Integer.valueOf(value.intValue()):null).intValue());
                        } else if(obj instanceof String) {
                            String value = (String)obj;
                            tmt.setString(j + 1, value);
                        } else if(obj instanceof Double) {
                            Double value = (Double)obj;
                            tmt.setDouble(j + 1, (value != null?Double.valueOf(value.doubleValue()):null).doubleValue());
                        } else if(obj instanceof Float) {
                            Float value = (Float)obj;
                            tmt.setFloat(j + 1, (value != null?Float.valueOf(value.floatValue()):null).floatValue());
                        } else if(obj instanceof Long) {
                            Long value = (Long)obj;
                            tmt.setLong(j + 1, (value != null?Long.valueOf(value.longValue()):null).longValue());
                        } else if(obj instanceof Boolean) {
                            Boolean value = (Boolean)obj;
                            tmt.setBoolean(j + 1, (value != null?Boolean.valueOf(value.booleanValue()):null).booleanValue());
                        } else if(obj instanceof Date) {
                            Date value = (Date)obj;
                            tmt.setString(j + 1, value != null?BaseUtils.formatDate(value, "yyyy-MM-dd HH:mm:ss"):null);
                        } else {
                            tmt.setString(j + 1, (String)null);
                        }
                    }

                    j = tmt.executeUpdate();
                    tmt.close();
                    tmt = null;
                    logger.debug("SQL:" + sql);
                    if(locked && j == 0) {
                        throw new SQLException("更新失败，数据已经过期。");
                    } else {
                        return j;
                    }
                }
            }
        } catch (SQLException var19) {
            throw new RuntimeException(var19.getMessage());
        }
    }

    private int selectCount(String sqlMappingName, Object[] value) {
        List list = null;
        if(value != null && value.length > 0) {
            list = this.sqlSessionTemplate.selectList(sqlMappingName, value[0], new RowBounds(-1000, -1000));
        } else {
            list = this.sqlSessionTemplate.selectList(sqlMappingName, (Object)null, new RowBounds(-1000, -1000));
        }

        Object rcount = ((Map)list.get(0)).get("COUNTNUM");
        int intCount = 0;
        if(rcount instanceof String) {
            intCount = Integer.parseInt((String)rcount);
        } else if(rcount instanceof Integer) {
            intCount = ((Integer)rcount).intValue();
        } else if(rcount instanceof Float) {
            intCount = Float.floatToIntBits(((Float)rcount).floatValue());
        } else if(rcount instanceof BigDecimal) {
            intCount = ((BigDecimal)rcount).intValue();
        } else if(rcount instanceof Long) {
            intCount = ((Long)rcount).intValue();
        }

        return intCount;
    }

    private int selectCountNew(String sqlMappingName, Map... value) {
        List list = null;
        if(value != null && value.length > 0) {
            list = this.sqlSessionTemplate.selectList(sqlMappingName, value[0], new RowBounds(-1000, -1000));
        } else {
            list = this.sqlSessionTemplate.selectList(sqlMappingName, (Object)null, new RowBounds(-1000, -1000));
        }

        Object rcount = ((Map)list.get(0)).get("COUNTNUM");
        int intCount = 0;
        if(rcount instanceof String) {
            intCount = Integer.parseInt((String)rcount);
        } else if(rcount instanceof Integer) {
            intCount = ((Integer)rcount).intValue();
        } else if(rcount instanceof Float) {
            intCount = Float.floatToIntBits(((Float)rcount).floatValue());
        } else if(rcount instanceof BigDecimal) {
            intCount = ((BigDecimal)rcount).intValue();
        } else if(rcount instanceof Long) {
            intCount = ((Long)rcount).intValue();
        }

        return intCount;
    }
}
