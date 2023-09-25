package com.topview.TChainer.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 刘家辉
 * @date 2023/09/22
 */
public class ContractConstant {

    public static final String UINT = "1";
    public static final String UNKNOWN = "unknown";
    public static final String ID = "id";
    public static final String ABI_FILE_PATH = "src/main/resources/contract/abi";
    public static final String BIN_FILE_PATH = "src/main/resources/contract/bin";
    public static final String PRIVATE_KEY = "src/main/resources/private_key";
    public static final String PUBLIC_KEY = "src/main/resources/public_key";
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
        // 这是Unix时间戳
        TYPE_MAPPING.put("boolean[]", "bool[]");
        TYPE_MAPPING.put("byte[]", "int8[]");
        TYPE_MAPPING.put("short[]", "int16[]");
        TYPE_MAPPING.put("int[]", "int32[]");
        TYPE_MAPPING.put("long[]", "int64[]");
        TYPE_MAPPING.put("float[]", "fixed[]");
        // 注意这需要使用Solidity的定点数库或自行实现
        TYPE_MAPPING.put("double[]", "fixed[]");
        // 注意这需要使用Solidity的定点数库或自行实现
        TYPE_MAPPING.put("char[]", "string[]");
        TYPE_MAPPING.put("String[]", "string[]");
        TYPE_MAPPING.put("Date[]", "uint256[]");
        TYPE_MAPPING.put("List", "type[]");
        // 'type'需要根据实际的泛型参数替换
        TYPE_MAPPING.put("Map", "mapping(type => type)");
        // 'type'需要根据实际的泛型参数替换
    }
}
