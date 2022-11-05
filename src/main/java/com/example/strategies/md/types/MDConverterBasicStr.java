package com.example.strategies.md.types;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.utils.FileUtils;
import com.example.utils.FileUtils.FileType;

public class MDConverterBasicStr implements MDConverterItf {

    private static final Logger LOGGER = LoggerFactory.getLogger(MDConverterBasicStr.class);

    private FileType type;

    public MDConverterBasicStr(FileType type) {
        this.type = type;
    }

    public String convert(String source) throws IOException {
        String result = FileUtils.getAbsolutePathWithNewExtension(source, 
            FileType.TMP);
        
        LOGGER.debug("Changing inner references to {}...", type);
        generateTmpFileWithInnerRefMd2To(source, result, type);  
        LOGGER.debug("Inner references CHANGED.");

        return result;
    }

    private void generateTmpFileWithInnerRefMd2To(String source, 
     String target, FileType type) throws IOException {
        FileReader fr = new FileReader(source);
        BufferedReader br = new BufferedReader(fr);
        
        PrintWriter fw = new PrintWriter(target);
        BufferedWriter bw = new BufferedWriter(fw);
        
        StringBuffer sbf = new StringBuffer();

        String line;
        while((line = br.readLine()) != null){
            sbf.append(line.replaceAll(
                FileType.MD.getExtWithDot(), 
                type.getExtWithDot()));

            sbf.append("\n");
            bw.write(sbf.toString());
            sbf.delete(0, sbf.length());
        }
        
        bw.flush();
        bw.close();
        fw.close();

        br.close();
        fr.close();
    }

}
