package com.hunter.cookies.base.mvp;

import java.lang.reflect.ParameterizedType;

class TUtils {

    static <T> T getT(Object o, int i) {
        try {
            ParameterizedType parameter = (ParameterizedType) (o.getClass().getGenericSuperclass());
            return ((Class<T>) parameter.getActualTypeArguments()[i]).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }
}
