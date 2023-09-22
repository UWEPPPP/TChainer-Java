package com.topview.TChainer;

import com.topview.TChainer.codeUtil.ContractGenerator;
import lombok.extern.slf4j.Slf4j;
import org.fisco.solc.compiler.CompilationResult;
import org.fisco.solc.compiler.SolidityCompiler;

import java.io.File;
import java.io.IOException;

import static org.fisco.solc.compiler.SolidityCompiler.Options.*;
@Slf4j
public class Generate {
    public static void main(String[] args) throws IOException {
        ContractGenerator.generateContract(TestData.class);

    }

}
