//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.atat.core.db.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.lang.reflect.*;

public class ReflectionUtils {
    private static Logger logger = LoggerFactory.getLogger(ReflectionUtils.class);

    public ReflectionUtils() {
    }

    public static Object invokeGetterMethod(Object obj, String propertyName) {
        String getterMethodName = "get" + StringUtils.capitalize(propertyName);
        return invokeMethod(obj, getterMethodName, new Class[0], new Object[0]);
    }

    public static void invokeSetterMethod(Object obj, String propertyName, Object value) {
        invokeSetterMethod(obj, propertyName, value, (Class)null);
    }

    public static void invokeSetterMethod(Object obj, String propertyName, Object value, Class<?> propertyType) {
        Class type = propertyType != null?propertyType:value.getClass();
        String setterMethodName = "set" + StringUtils.capitalize(propertyName);
        invokeMethod(obj, setterMethodName, new Class[]{type}, new Object[]{value});
    }

    public static Object getFieldValue(Object obj, String fieldName) {
        Field field = getAccessibleField(obj, fieldName);
        if(field == null) {
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
        } else {
            Object result = null;

            try {
                result = field.get(obj);
            } catch (IllegalAccessException var5) {
                logger.error("不可能抛出的异常{}", var5.getMessage());
            }

            return result;
        }
    }

    public static void setFieldValue(Object obj, String fieldName, Object value) {
        Field field = getAccessibleField(obj, fieldName);
        if(field == null) {
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
        } else {
            try {
                field.set(obj, value);
            } catch (IllegalAccessException var5) {
                logger.error("不可能抛出的异常:{}", var5.getMessage());
            }

        }
    }

    public static void setFieldValue(Object target, String fname, Class<?> ftype, Object fvalue) {
        if(target != null && fname != null && !"".equals(fname) && (fvalue == null || ftype.isAssignableFrom(fvalue.getClass()))) {
            Class clazz = target.getClass();

            try {
                Method method = clazz.getDeclaredMethod("set" + Character.toUpperCase(fname.charAt(0)) + fname.substring(1), new Class[]{ftype});
                if(!Modifier.isPublic(method.getModifiers())) {
                    method.setAccessible(true);
                }

                method.invoke(target, new Object[]{fvalue});
            } catch (Exception var8) {
                if(logger.isDebugEnabled()) {
                    logger.debug(var8.toString());
                }

                try {
                    Field field = clazz.getDeclaredField(fname);
                    if(!Modifier.isPublic(field.getModifiers())) {
                        field.setAccessible(true);
                    }

                    field.set(target, fvalue);
                } catch (Exception var7) {
                    if(logger.isDebugEnabled()) {
                        logger.debug(var7.toString());
                    }
                }
            }

        }
    }

    public static Field getAccessibleField(Object obj, String fieldName) {
        Assert.notNull(obj, "object不能为空");
        Assert.hasText(fieldName, "fieldName");
        Class superClass = obj.getClass();

        while(superClass != Object.class) {
            try {
                Field field = superClass.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException var4) {
                superClass = superClass.getSuperclass();
            }
        }

        return null;
    }

    public static Object invokeMethod(Object obj, String methodName, Class<?>[] parameterTypes, Object[] args) {
        Method method = getAccessibleMethod(obj, methodName, parameterTypes);
        if(method == null) {
            throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + obj + "]");
        } else {
            try {
                return method.invoke(obj, args);
            } catch (Exception var6) {
                return !(var6 instanceof IllegalAccessException) && !(var6 instanceof IllegalArgumentException) && !(var6 instanceof NoSuchMethodException)?(var6 instanceof InvocationTargetException?new RuntimeException("Reflection Exception.", ((InvocationTargetException)var6).getTargetException()):(var6 instanceof RuntimeException?(RuntimeException)var6:new RuntimeException("Unexpected Checked Exception.", var6))):new IllegalArgumentException("Reflection Exception.", var6);
            }
        }
    }

    public static Method getAccessibleMethod(Object obj, String methodName, Class<?>[] parameterTypes) {
        Assert.notNull(obj, "object不能为空");
        Class superClass = obj.getClass();

        while(superClass != Object.class) {
            try {
                Method method = superClass.getDeclaredMethod(methodName, parameterTypes);
                method.setAccessible(true);
                return method;
            } catch (NoSuchMethodException var5) {
                superClass = superClass.getSuperclass();
            }
        }

        return null;
    }

    public static <T> Class<T> getSuperClassGenricType(Class clazz) {
        return getSuperClassGenricType(clazz, 0);
    }

    public static Class getSuperClassGenricType(Class clazz, int index) {
        Type genType = clazz.getGenericSuperclass();
        if(!(genType instanceof ParameterizedType)) {
            logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
            return Object.class;
        } else {
            Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
            if(index < params.length && index >= 0) {
                if(!(params[index] instanceof Class)) {
                    logger.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
                    return Object.class;
                } else {
                    return (Class)params[index];
                }
            } else {
                logger.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: " + params.length);
                return Object.class;
            }
        }
    }
}
