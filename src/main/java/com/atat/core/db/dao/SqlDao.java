//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.atat.core.db.dao;

import com.atat.core.db.model.PageTurn;

import java.util.List;
import java.util.Map;

public interface SqlDao {
  <T> PageTurn<T> pagedQuery(String var1, int var2, int var3, Map<String, Object> var4);

  <T> List<T> selectPage(String var1, int var2, int var3, Object[] var4);

  int insert(String var1, Map<String, Object> var2);

  int update(String var1, String var2, Map<String, Object> var3);

  int delete(String var1, String var2);
}
