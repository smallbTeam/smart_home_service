//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.atat.core.db.intercept;

import com.atat.core.db.dialect.IDialect;
import com.atat.core.db.dialect.impl.*;
import com.atat.core.db.utils.ReflectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Intercepts({@Signature(
        type = Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
)})
public class OffsetLimitInterceptor implements Interceptor {
    static int MAPPED_STATEMENT_INDEX = 0;
    static int PARAMETER_INDEX = 1;
    static int ROWBOUNDS_INDEX = 2;
    static int RESULT_HANDLER_INDEX = 3;
    private final Logger logger = Logger.getLogger(this.getClass());
    private final IDialect dialect;

    public OffsetLimitInterceptor(String dialectStr) {
        this.dialect = this.chooseDialect(dialectStr);
    }

    public Object intercept(Invocation invocation) throws Throwable {
        this.processIntercept(invocation.getArgs());
        return invocation.proceed();
    }

    void processIntercept(Object[] queryArgs) {
        MappedStatement ms = (MappedStatement)queryArgs[MAPPED_STATEMENT_INDEX];
        Object parameter = queryArgs[PARAMETER_INDEX];
        RowBounds rowBounds = (RowBounds)queryArgs[ROWBOUNDS_INDEX];
        int offset = rowBounds.getOffset();
        int limit = rowBounds.getLimit();
        List lrm = null;
        if(offset != 0 || limit != 2147483647) {
            BoundSql boundSql = ms.getBoundSql(parameter);
            String sql = boundSql.getSql().trim();
            if(offset != -1000 && limit != -1000) {
                sql = this.dialect.getPaginationString(sql, offset, limit);
                lrm = ms.getResultMaps();
            } else {
                sql = this.dialect.getCountString(sql);
                lrm = this.getCountResultMaps(ms);
            }

            offset = 0;
            limit = 2147483647;
            queryArgs[ROWBOUNDS_INDEX] = new RowBounds(offset, limit);
            ReflectionUtils.setFieldValue(boundSql, "sql", sql);
            MappedStatement newMs = this.copyFromMappedStatement(ms, new OffsetLimitInterceptor.BoundSqlSqlSource(boundSql), lrm);
            queryArgs[MAPPED_STATEMENT_INDEX] = newMs;
        }

    }

    private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource, List<ResultMap> lrm) {
        Builder builder = new Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        builder.keyProperty(StringUtils.join(ms.getKeyProperties(), ","));
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(lrm);
        builder.cache(ms.getCache());
        MappedStatement newMs = builder.build();
        return newMs;
    }

    private List<ResultMap> getCountResultMaps(MappedStatement ms) {
        List lrm = new ArrayList();
        org.apache.ibatis.mapping.ResultMap.Builder builder = new org.apache.ibatis.mapping.ResultMap.Builder(ms.getConfiguration(), ms.getId(), Map.class, new ArrayList());
        ResultMap rm = builder.build();
        lrm.add(rm);
        return lrm;
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties arg0) {
    }

    private IDialect chooseDialect(String dialectStr) {
        IDialect dialect = new SQLServer2005Dialect();
        if("DB2".equalsIgnoreCase(dialectStr)) {
            dialect = new DB2Dialect();
        } else if("DERBY".equalsIgnoreCase(dialectStr)) {
            dialect = new DerbyDialect();
        } else if("MYSQL".equalsIgnoreCase(dialectStr)) {
            dialect = new MySQLDialect();
        } else if("ORACLE".equalsIgnoreCase(dialectStr)) {
            dialect = new OracleDialect();
        } else if("SQLSERVER".equalsIgnoreCase(dialectStr)) {
            dialect = new SQLServer2005Dialect();
        }

        return (IDialect)dialect;
    }

    public static class BoundSqlSqlSource implements SqlSource {
        BoundSql boundSql;

        public BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        public BoundSql getBoundSql(Object parameterObject) {
            return this.boundSql;
        }
    }
}
