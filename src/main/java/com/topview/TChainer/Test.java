package com.topview.TChainer;

import com.topview.TChainer.codeUtil.ContractGenerator;

import java.io.FileWriter;
import java.io.IOException;

public class Test {
    public static void main(String[] args) {
        ContractGenerator.generateContract(TestData.class);

    }

}
