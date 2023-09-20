package com.topview.TChainer.codeUtil;

import com.sun.javafx.util.Logging;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
@Slf4j
public class ContractGenerator {
    private static final Map<String, String> typeMapping;

    static {
        typeMapping = new HashMap<>();
        typeMapping.put("boolean", "bool");
        typeMapping.put("byte", "int8");
        typeMapping.put("short", "int16");
        typeMapping.put("int", "int32");
        typeMapping.put("long", "int64");
        // 注意这需要使用Solidity的定点数库或自行实现
        typeMapping.put("float", "fixed");
        // 注意这需要使用Solidity的定点数库或自行实现
        typeMapping.put("double", "fixed");
        typeMapping.put("char", "string");
        typeMapping.put("String", "string");
        typeMapping.put("Date", "uint");
        // 这是Unix时间戳
    }


    public static String generateContract(Class<?> beanClass) {
        StringBuilder sb = new StringBuilder();
        String contractName = beanClass.getSimpleName();
        sb.append("// SPDX-License-Identifier: MIT\n");
        sb.append("pragma solidity >=0.6.10 <0.8.11;\n\n");
        sb.append("import \"./DataContract.sol\";\n\n");
        sb.append("contract ").append(contractName).append(" is DataContract {\n");
        sb.append("    struct Data {\n");
        StringBuilder decodeParams = new StringBuilder();
        StringBuilder decodeReceiveParams = new StringBuilder();
        try {
            int length = beanClass.getDeclaredFields().length;
            for (Field field : beanClass.getDeclaredFields()) {
                length--;
                String javaType = field.getType().getSimpleName();
                String solidityType = typeMapping.getOrDefault(javaType, "unknown");
                if (length>0) {
                    decodeReceiveParams.append(solidityType).append(" ").append(field.getName()).append(", ");
                    decodeParams.append(solidityType).append(", ");
                }else {
                    decodeReceiveParams.append(solidityType).append(" ").append(field.getName());
                    decodeParams.append(solidityType);
                }

                String fieldName = field.getName();

                sb.append("        ").append(solidityType).append(" ").append(fieldName).append(";\n");
            }
            sb.append("    }\n\n");

            // 添加自定义的函数
            sb.append("\n    function add(bytes memory data) public override returns (uint256) {\n");
            sb.append("        Data memory inputData;\n");
            sb.append("        (").append(decodeReceiveParams).append(") = abi.decode(data, (").append(decodeParams).append("));\n");
            sb.append("        self.id++;\n");
            sb.append("        // 解包Data结构体数据并进行处理\n");
            sb.append("        // TODO: 根据需要进行具体的处理\n");
            sb.append("        return self.id;\n");
            sb.append("    }\n");


        } catch (Exception e) {
            log.error("generate contract error", e);
        }

        sb.append("}\n");

        return sb.toString();
    }
}