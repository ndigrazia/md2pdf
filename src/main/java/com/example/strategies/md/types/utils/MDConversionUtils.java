package com.example.strategies.md.types.utils;

import java.util.Properties;

import com.example.strategies.md.types.MDConverterBasicStr;
import com.example.strategies.md.types.MDConverterHtmlStr;
import com.example.strategies.md.types.MDConverterItf;
import com.example.strategies.md.types.MDConverterPdfStr;
import com.example.utils.PropertiesUtils;
import com.example.utils.FileUtils.FileType;

public class MDConversionUtils {

    public static FileType getConversionType(Properties prop) {
        String value = prop.getProperty(PropertiesUtils.CONVERSION_TYPE);

        if(value == null)
                return FileType.PDF;
            
        if(value.equalsIgnoreCase(FileType.HTML.getExt())) 
                return FileType.HTML;

        return FileType.PDF;
    }

    public static MDConverterItf getConversionStr(FileType type) {
        switch (type) {
            case PDF:
                return new MDConverterPdfStr(new MDConverterHtmlStr(
                    new MDConverterBasicStr(type), FileType.CVR));
            case HTML:        
                return new MDConverterHtmlStr(new MDConverterBasicStr(type));
            default:
                throw new RuntimeException("Conversion unknown!!!");
        }
    }
   
}

