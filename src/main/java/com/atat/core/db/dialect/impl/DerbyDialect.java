/*    */ package com.atat.core.db.dialect.impl;
/*    */ 
/*    */

import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*    */
/*    */

/*    */
/*    */ public final class DerbyDialect extends AbstractDialect
/*    */ {
/*  9 */   private final Logger logger = Logger.getLogger(getClass());
/*    */ 
/*    */   public String getPaginationString(String sql, int begin, int end) {
/* 12 */     sql = trim(sql);
/*    */ 
/* 14 */     StringBuffer stringbuffer = new StringBuffer();
/* 15 */     stringbuffer.append("SELECT HIKESOFT_SQL_B__.* FROM (SELECT HIKESOFT_SQL_A_.*, ROW_NUMBER() OVER(");
/*    */ 
/* 17 */     String s1 = null;
/*    */ 
/* 19 */     Pattern pattern = Pattern.compile("ORDER\\s+by", 2);
/*    */ 
/* 21 */     Matcher matcher = pattern.matcher(sql);
/*    */ 
/* 23 */     int i = 0;
/* 24 */     while (matcher.find()) {
/* 25 */       i = matcher.start();
/*    */     }
/*    */ 
/* 28 */     String sqlBeforeLastOrder = sql.substring(0, i);
/* 29 */     String sqlLastOrder = sql.substring(i);
/*    */ 
/* 31 */     StringBuffer stringBuffer = new StringBuffer();
/* 32 */     stringBuffer.append(sqlBeforeLastOrder);
/*    */ 
/* 34 */     Pattern pattern2 = Pattern.compile("ORDER\\s*by[\\w|\\W|\\s|\\S]*", 2);
/*    */ 
/* 36 */     Matcher matcher2 = pattern2.matcher(sqlLastOrder);
/* 37 */     if (matcher2.find()) {
/* 38 */       s1 = sqlLastOrder;
/* 39 */       sql = sqlBeforeLastOrder;
/*    */     } else {
/* 41 */       s1 = "ORDER BY (SELECT 0)";
/*    */     }
/*    */ 
/* 44 */     stringbuffer.append(s1);
/* 45 */     stringbuffer.append(") AS RN_ FROM ( ");
/* 46 */     stringbuffer.append(sql);
/* 47 */     stringbuffer.append(" ) HIKESOFT_SQL_A_) HIKESOFT_SQL_B__ WHERE HIKESOFT_SQL_B__.RN_ BETWEEN ");
/*    */ 
/* 49 */     stringbuffer.append(begin + 1).append(" AND ").append(end + begin);
/*    */ 
/* 51 */     this.logger.debug("DerbyDialect execute sql:" + stringbuffer.toString());
/*    */ 
/* 53 */     return stringbuffer.toString();
/*    */   }
/*    */ }

/* Location:           C:\Users\Administrator\Desktop\zg_microservice-0.0.1-20170606.094355-8.jar
 * Qualified Name:     com.zgiot.core.db.dialect.impl.DerbyDialect
 * JD-Core Version:    0.6.2
 */
