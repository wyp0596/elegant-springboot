package com.ajavac.util;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.*;

/**
 * JSON 相关处理工具类
 * Created by wyp0596 on 20/10/2017.
 */
public final class GsonUtils {

    private static final Gson gson = new Gson();
    private static final Gson pretty = new GsonBuilder().setPrettyPrinting().create();
    private static final JsonParser jsonParser = new JsonParser();
    private static final Map<Version, Gson> gsonMap = new HashMap<>();

    static {
        for (Version version : Version.values()) {
            gsonMap.put(version, new GsonBuilder().setVersion(version.getValue()).create());
        }
    }

    public static final Type TYPE_OF_STRING_SET = new TypeToken<HashSet<String>>() {
    }.getType();
    public static final Type TYPE_OF_LONG_SET = new TypeToken<HashSet<Long>>() {
    }.getType();
    public static final Type TYPE_OF_INTEGER_SET = new TypeToken<HashSet<Integer>>() {
    }.getType();

    private GsonUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * 反序列化JSON字符串为特定类型的对象
     *
     * @param <T>      目标对象类型
     * @param json     待序列化JSON字符串
     * @param classOfT 目标对象类型的类
     * @return 反序列化json字符串生成的T类型的对象
     * @throws JsonSyntaxException 转换异常,json不合法或者目标对象类型不合法
     */
    public static <T> T fromJson(String json, Class<T> classOfT) throws JsonSyntaxException {
        return gson.fromJson(json, classOfT);
    }

    /**
     * 反序列化JSON字符串为特定类型的对象
     * <pre>
     * Type typeOfT = new TypeToken<List<String>>(){}.getType();
     * </pre>
     *
     * @param <T>     目标对象类型
     * @param json    待序列化JSON字符串
     * @param typeOfT 目标对象类型
     * @return 反序列化json字符串生成的T类型的对象
     * @throws JsonSyntaxException 转换异常,json不合法或者目标对象类型不合法
     */
    public static <T> T fromJson(String json, Type typeOfT) throws JsonSyntaxException {
        return gson.fromJson(json, typeOfT);
    }

    /**
     * 反序列化JSON字符串为特定类型的对象(带版本号)
     *
     * @param <T>      目标对象类型
     * @param json     待序列化JSON字符串
     * @param classOfT 目标对象类型的类
     * @param version  版本号
     * @return 反序列化json字符串生成的T类型的对象
     * @throws JsonSyntaxException 转换异常,json不合法或者目标对象类型不合法
     */
    public static <T> T fromVersionJson(String json, Class<T> classOfT, Version version) throws JsonSyntaxException {
        return getGson(version).fromJson(json, classOfT);
    }

    /**
     * 序列化特定类型的对象为JSON字符串
     *
     * @param src 待序列化对象
     * @return JSON字符串
     */
    public static String toJson(Object src) {
        return gson.toJson(src);
    }

    /**
     * 序列化特定类型的对象(带版本号)为JSON字符串
     *
     * @param src     待序列化对象
     * @param version 版本号
     * @return JSON字符串
     */
    public static String toVersionJson(Object src, Version version) {
        return getGson(version).toJson(src);
    }

    /**
     * 序列化特定类型的对象为漂亮紧凑的JSON字符串
     *
     * @param src 待序列化对象
     * @return 漂亮紧凑的JSON字符串
     */
    public static String toPrettyJson(Object src) {
        return pretty.toJson(src);
    }

    /**
     * 解析JSON字符串为JSON对象
     *
     * @param json JSON字符串
     * @return JSON对象
     * @throws JsonSyntaxException 转换异常,json不合法或者目标对象类型不合法
     */
    public static JsonObject toJsonObject(String json) throws JsonSyntaxException {
        return jsonParser.parse(json).getAsJsonObject();
    }

    /**
     * 解析JSON字符串为Integer_Set
     *
     * @param json JSON字符串
     * @return Integer_Set
     * @throws JsonSyntaxException 转换异常,json不合法或者目标对象类型不合法
     */
    public static Set<Integer> toIntegerSet(String json) throws JsonSyntaxException {
        return gson.fromJson(json, TYPE_OF_INTEGER_SET);
    }

    /**
     * 解析JSON字符串为Long_Set
     *
     * @param json JSON字符串
     * @return Long_Set
     * @throws JsonSyntaxException 转换异常,json不合法或者目标对象类型不合法
     */
    public static Set<Long> toLongSet(String json) throws JsonSyntaxException {
        return gson.fromJson(json, TYPE_OF_LONG_SET);
    }

    /**
     * 解析JSON字符串为String_Set
     *
     * @param json JSON字符串
     * @return String_Set
     * @throws JsonSyntaxException 转换异常,json不合法或者目标对象类型不合法
     */
    public static Set<String> toStringSet(String json) throws JsonSyntaxException {
        return gson.fromJson(json, TYPE_OF_STRING_SET);
    }

    /**
     * 解析JSON字符串为JSON对象数组
     *
     * @param json JSON字符串
     * @return JSON对象数组
     * @throws JsonSyntaxException 转换异常,json不合法或者目标对象类型不合法
     */
    public static JsonArray toJsonArray(String json) throws JsonSyntaxException {
        return jsonParser.parse(json).getAsJsonArray();
    }

    /**
     * 获取对应版本的Gson对象(若不存在返回默认对象)
     *
     * @param version 版本
     * @return Gson对象
     */
    private static Gson getGson(Version version) {
        return gsonMap.getOrDefault(version, gson);
    }

    /**
     * 版本号枚举类
     */
    public enum Version {
        V_1_0(1.0),
        V_1_1(1.1);

        /**
         * 版本号的值
         */
        private double value;

        Version(double value) {
            this.value = value;
        }

        public double getValue() {
            return value;
        }
    }

}
