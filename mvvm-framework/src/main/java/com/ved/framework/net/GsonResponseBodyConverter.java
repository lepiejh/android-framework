package com.ved.framework.net;

import android.text.TextUtils;

import com.google.gson.JsonSyntaxException;
import com.ved.framework.mode.EntityResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.ved.framework.mode.Result;
import com.ved.framework.utils.Configure;
import com.ved.framework.utils.JsonPraise;
import com.ved.framework.utils.KLog;
import com.ved.framework.utils.SPUtils;
import com.ved.framework.utils.StringUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

final class GsonResponseBodyConverter<T> implements Converter<ResponseBody,
        T> {
    private final Gson gson;
    private final Type type;

    GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    /**
     * 针对数据返回成功、错误不同类型字段处理
     */
    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        KLog.i("Interceptor","接口返回的数据："+response);
        Class<?> entityResponse = null;
        try {
            entityResponse = Class.forName("com.ved.framework.mode.EntityResponse");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (entityResponse == null) {
            int code = StringUtils.parseInt(JsonPraise.optCode(response, "resultCode"));
            if (code == Configure.getCode()) {
                JsonReader jsonReader = gson.newJsonReader(value.charStream());
                try {
                    return (T) gson.getAdapter(TypeToken.get(type)).read(jsonReader);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new ResultException("服务器异常", -2);
                }
            } else {
                String pram = SPUtils.getInstance().getString("resultMsg", "");
                String msg;
                if (StringUtils.isSpace(pram)) {
                    msg = JsonPraise.optCode(response, "msg");
                } else {
                    msg = JsonPraise.optCode(response, pram);
                }
                throw new ResultException(msg, code);
            }
        } else {
            Object result = null;
            try {
                result = gson.fromJson(response, entityResponse);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                return gson.fromJson(response, type);
            }
            Method[] allMethods = entityResponse.getDeclaredMethods();
            String methodName = "";
            String methodNameContent = "";
            String methodResultStr = "";
            for (Method method : allMethods) {
                String returnSimpleName = method.getReturnType().getSimpleName();
                switch (returnSimpleName) {
                    case "int":
                        methodName = method.getName();
                        break;
                    case "Object":
                        methodNameContent = method.getName();
                        break;
                    case "String":
                        methodResultStr = method.getName();
                        break;
                }
            }
            Method method = null;
            try {
                method = entityResponse.getDeclaredMethod(methodName);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            int code = 0;
            try {
                if (method != null) {
                    Object o = method.invoke(result);
                    if (o instanceof Integer) {
                        code = (int) o;
                    } else if (o instanceof String) {
                        code = StringUtils.parseInt((String) o);
                    } else {
                        if (o != null) {
                            code = StringUtils.parseInt((String) o.toString());
                        }
                    }
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }

            EntityResponse er = JsonPraise.jsonToObj(response,EntityResponse.class);
            if (er != null){
                Result r = JsonPraise.jsonToObj(er.getMsg(), Result.class);
                if (r != null) {
                    code = r.getResult();
                }
            }
            if (code == Configure.getCode()) {
                return gson.fromJson(response, type);
            } else {
                Object errResponse = gson.fromJson(response, entityResponse);
                if (!TextUtils.isEmpty(methodNameContent)) {
                    Method methodContent = null;
                    try {
                        methodContent = entityResponse.getDeclaredMethod(methodNameContent);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    String errorMsg = null;
                    try {
                        if (methodContent != null) {
                            Object o = methodContent.invoke(errResponse);
                            if (o instanceof String) {
                                errorMsg = (String) o;
                            } else if (o instanceof Integer) {
                                errorMsg = String.valueOf((int) o);
                            } else {
                                if (o != null) {
                                    errorMsg = o.toString();
                                }
                            }
                        }
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    if (!TextUtils.isEmpty(errorMsg)) {
                        throw new ResultException(errorMsg, code);
                    } else if (!TextUtils.isEmpty(methodResultStr)) {
                        Method methodResult2 = null;
                        try {
                            methodResult2 = entityResponse.getDeclaredMethod(methodResultStr);
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                        String errorMsg2 = null;
                        try {
                            if (methodResult2 != null) {
                                Object o = methodResult2.invoke(errResponse);
                                if (o instanceof String) {
                                    errorMsg2 = (String) o;
                                } else if (o instanceof Integer) {
                                    errorMsg2 = String.valueOf((int) o);
                                } else {
                                    if (o != null) {
                                        errorMsg2 = o.toString();
                                    }
                                }
                            }
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        throw new ResultException(errorMsg2, code);
                    } else {
                        throw new ResultException("", code);
                    }
                } else if (!TextUtils.isEmpty(methodResultStr)) {
                    Method methodResult = null;
                    try {
                        methodResult = entityResponse.getDeclaredMethod(methodResultStr);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    String errorMsg1 = null;
                    try {
                        if (methodResult != null) {
                            Object o = methodResult.invoke(errResponse);
                            if (o instanceof String) {
                                errorMsg1 = (String) o;
                            } else if (o instanceof Integer) {
                                errorMsg1 = String.valueOf((int) o);
                            } else {
                                if (o != null) {
                                    errorMsg1 = o.toString();
                                }
                            }
                        }
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    throw new ResultException(errorMsg1, code);
                } else {
                    throw new ResultException("服务器异常", code);
                }
            }
        }
    }
}