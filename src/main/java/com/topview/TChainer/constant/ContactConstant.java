package com.topview.TChainer.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 刘家辉
 * @date 2023/09/22
 */
public class ContactConstant {
    public static final String UINT = "1";
    public static final String UNKNOWN = "unknown";
    public static final String ID = "id";
    public static final Map<String, String> TYPE_MAPPING;

    static {
        TYPE_MAPPING = new HashMap<>();
        TYPE_MAPPING.put("boolean", "bool");
        TYPE_MAPPING.put("byte", "int8");
        TYPE_MAPPING.put("short", "int16");
        TYPE_MAPPING.put("int", "int32");
        TYPE_MAPPING.put("long", "int64");
        // 注意这需要使用Solidity的定点数库或自行实现
        TYPE_MAPPING.put("float", "fixed");
        // 注意这需要使用Solidity的定点数库或自行实现
        TYPE_MAPPING.put("double", "fixed");
        TYPE_MAPPING.put("char", "string");
        TYPE_MAPPING.put("String", "string");
        TYPE_MAPPING.put("Date", "uint256");
        TYPE_MAPPING.put(UINT, "uint");
        // 这是Unix时间戳
    }
}
