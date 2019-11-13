package com.ddq.apt_sdk;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Author : ddq
 * Time : 2019/11/12 16:48
 * Description :
 */
public class _Api {
    public static void bind(AppCompatActivity activity) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Class clz = activity.getClass();
        Class<?> generateClz = Class.forName(clz.getName()+"_AutoGenerate");
        Method m = generateClz.getMethod("bind", activity.getClass());
        m.invoke(generateClz.newInstance(), activity);
    }
}
