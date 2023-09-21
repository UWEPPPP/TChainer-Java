package com.topview.TChainer.codeUtil;

/**
 * @author 刘家辉
 * @date 2023/09/21
 */
public class ContractComposition {

    public static StringBuilder getSb(){
        return new StringBuilder();
    }
    //版本控制
    public static StringBuilder versionControl(){
        StringBuilder sb = getSb();
        sb.append("// SPDX-License-Identifier: MIT\n");
        sb.append("pragma solidity >=0.6.10 <=0.8.11;\n\n");
        sb.append("import \"src/main/resources/contract/DataContract.sol\";\n\n");
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


    //函数
    public  static  StringBuilder setFunction(StringBuilder decodeParams, StringBuilder decodeReceiveParams, StringBuilder inputParams, String event){
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

    public static StringBuilder getEventsBlockFunction(){
        StringBuilder sb = getSb();
        sb.append("\n    function getEventsBlock(uint256 id) public override view returns (uint256) {\n");
        sb.append("        return blockHeightMap[id];\n");
        sb.append("    }\n");
        return sb;
    }

    public static StringBuilder getFunction(){
        StringBuilder sb = getSb();
        sb.append("\n    function get(uint256 id) public override view returns (bytes memory) {\n");
        sb.append("            return abi.encode(dataMap[id]);\n");
        sb.append("    }\n");
        return sb;
    }

    public static StringBuilder addFunction(StringBuilder decodeParams, StringBuilder decodeReceiveParams, StringBuilder inputParams, String event){
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
