package com.ampla.api.mis.interceptor;

import org.hibernate.EmptyInterceptor;

public class Interceptor extends EmptyInterceptor {
    @Override
    public String onPrepareStatement(String sql) {
//        if(StringUtils.hasLength(sql) && sql.toLowerCase().startsWith("select")){
//            if(sql.contains("where")){
//                sql +=" and id_entreprise = 1";
//            }else {
//                sql += " where id_entreprise = 1";
//            }
//            System.out.println("ok ok");
//        }
        return super.onPrepareStatement(sql);
    }
}
