////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by Fernflower decompiler)
////
//
//package com.atat.core.db.model;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class Pageable {
//    private Integer toPage = Integer.valueOf(1);
//    private Integer pageSize = Integer.valueOf(20);
//
//    public Pageable() {
//    }
//
//    public Integer getToPage() {
//        return this.toPage;
//    }
//
//    public void setToPage(Integer toPage) {
//        if(toPage.intValue() != 0 && toPage != null) {
//            this.toPage = toPage;
//        }
//
//    }
//
//    public Integer getPageSize() {
//        return this.pageSize;
//    }
//
//    public void setPageSize(Integer pageSize) {
//        if(pageSize.intValue() != 0 && pageSize != null) {
//            this.pageSize = pageSize;
//        }
//
//    }
//
//    public Map<String, Object> getConditionMap() {
//        return new HashMap();
//    }
//}
