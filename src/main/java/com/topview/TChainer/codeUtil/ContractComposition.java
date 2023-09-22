package com.topview.TChainer.codeUtil;

import com.topview.TChainer.constant.ContactConstant;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static com.topview.TChainer.constant.ContactConstant.TYPE_MAPPING;
import static com.topview.TChainer.constant.ContactConstant.UNKNOWN;

/**
 * @author 刘家辉
 * @date 2023/09/21
 */
public class ContractComposition {

    public static StringBuilder getSb() {
        return new StringBuilder();
    }

    //版本控制
    public static StringBuilder versionControl() {
        StringBuilder sb = getSb();
        sb.append("// SPDX-License-Identifier: MIT\n");
        sb.append("pragma solidity >=0.6.10 <=0.8.11;\n\n");
        sb.append("import \"./DataContract.sol\";\n\n");
        return sb;
    }

    //映射
    public static StringBuilder mapping() {
        StringBuilder sb = getSb();
        sb.append("    mapping(uint256 => Data) private dataMap;\n");
        sb.append("    mapping(uint256 => uint256) private blockHeightMap;\n");
        sb.append("    mapping(uint256 => uint256) private dataVersionsMap;\n\n ");
        return sb;
    }

    //结构体
    public static Map<Integer, StringBuilder> struct(Class<?> beanClass) {
        Map<Integer ,StringBuilder> map = new HashMap<>(4);
        StringBuilder sb = getSb();
        StringBuilder decodeParams = new StringBuilder();
        StringBuilder decodeReceiveParams = new StringBuilder();
        StringBuilder inputParams = new StringBuilder();
        int length = beanClass.getDeclaredFields().length;
        //根据beanClass生成对应数据模板的结构体
        for (Field field : beanClass.getDeclaredFields()) {
            length--;
            String javaType = field.getType().getSimpleName();
            String solidityType = UNKNOWN;
            if (field.getAnnotations().length != 0) {
                for (Annotation annotation : field.getAnnotations()) {
                    if (annotation instanceof Uint) {
                        int value = ((Uint) annotation).value();
                        solidityType = "uint" + value;
                        break;
                    }
                }
            } else {
                solidityType = TYPE_MAPPING.getOrDefault(javaType, UNKNOWN);
            }
            if (!field.getName().equals(ContactConstant.ID)) {
                if (length > 0) {
                    decodeReceiveParams.append(solidityType).append(" ").append(field.getName()).append(", ");
                    decodeParams.append(solidityType).append(", ");
                } else {
                    decodeReceiveParams.append(solidityType).append(" ").append(field.getName());
                    decodeParams.append(solidityType);
                }

                inputParams.append("        inputData.").append(field.getName()).append("=").append(field.getName()).append(";\n");
            }
            String fieldName = field.getName();
            sb.append("        ").append(solidityType).append(" ").append(fieldName).append(";\n");
        }
        map.put(0,sb);
        map.put(1,decodeParams);
        map.put(2,decodeReceiveParams);
        map.put(3,inputParams);
        return  map;
    }


    //函数
    public static StringBuilder setFunction(StringBuilder decodeParams, StringBuilder decodeReceiveParams, StringBuilder inputParams, String event) {
        StringBuilder sb = getSb();
        sb.append("\n    function set(uint256 id, bytes memory data) public override returns (bool) {\n");
        sb.append("        Data memory inputData;\n");
        sb.append("        (").append(decodeReceiveParams).append(") = abi.decode(data, (").append(decodeParams).append("));\n");
        sb.append("        inputData.id = latestDataIndex;\n");
        sb.append(inputParams);
        sb.append("        dataMap[id] = inputData;\n");
        sb.append("        dataVersionsMap[latestDataIndex]++;\n");
        sb.append("        emit  ").append(event).append("(block.number,dataVersionsMap[id] ,inputData);\n");
        sb.append("        blockHeightMap[id] = block.number;\n");
        sb.append("        dataVersionsMap[id]++;\n");
        sb.append("        return true;\n");
        sb.append("    }\n");
        return sb;
    }

    public static StringBuilder getEventsBlockFunction() {
        StringBuilder sb = getSb();
        sb.append("\n    function getEventsBlock(uint256 id) public override view returns (uint256) {\n");
        sb.append("        return blockHeightMap[id];\n");
        sb.append("    }\n");
        return sb;
    }

    public static StringBuilder getFunction() {
        StringBuilder sb = getSb();
        sb.append("\n    function get(uint256 id) public override view returns (bytes memory) {\n");
        sb.append("            return abi.encode(dataMap[id]);\n");
        sb.append("    }\n");
        return sb;
    }

    public static StringBuilder addFunction(StringBuilder decodeParams, StringBuilder decodeReceiveParams, StringBuilder inputParams, String event) {
        StringBuilder sb = getSb();
        sb.append("\n    function add(bytes memory data) public override returns (bool) {\n");
        sb.append("        Data memory inputData;\n");
        sb.append("        (").append(decodeReceiveParams).append(") = abi.decode(data, (").append(decodeParams).append("));\n");
        sb.append("        inputData.id = latestDataIndex;\n");
        sb.append(inputParams);
        sb.append("        latestDataIndex++;\n");
        sb.append("        dataMap[latestDataIndex] = inputData;\n");
        sb.append("        dataVersionsMap[latestDataIndex]++;\n");
        sb.append("        emit  ").append(event).append("(block.number,dataVersionsMap[latestDataIndex] ,inputData);\n");
        sb.append("        blockHeightMap[latestDataIndex] = block.number;\n");
        sb.append("        dataVersionsMap[latestDataIndex]++;\n");
        sb.append("        return true;\n");
        sb.append("    }\n");
        return sb;
    }
}
