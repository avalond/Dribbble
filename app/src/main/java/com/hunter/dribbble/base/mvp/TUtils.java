package com.hunter.dribbble.base.mvp;

import java.lang.reflect.ParameterizedType;

/**
 * 泛型工具类，可对象的泛型类型
 */
class TUtils {

    /**
     * @param o 要获取泛型类型的对象
     * @param i 泛型的下标
     */
    static <T> T getT(Object o, int i) {
        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass()
                                                      .getGenericSuperclass())).getActualTypeArguments()[i])
                    .newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Class<?> forName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
