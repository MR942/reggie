package com.mxl.reggie.common;

/**
 * 基于threadLocal工具类，用于保存和获取当前用户的id信息
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }
    public static Long getCurrentId(){
        return threadLocal.get();
    }

}
