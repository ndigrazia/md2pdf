package com.example.strategies.md;

import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.regex.Pattern;

import com.example.strategies.ConverterItf;
import com.example.strategies.md.types.MDConverterItf;
import com.example.strategies.md.types.utils.MDConversionUtils;
import com.example.utils.FileUtils;
import com.example.utils.PropertiesUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.File;

public class MDConverterStr implements ConverterItf {
 
    private static final Logger LOGGER = LoggerFactory.getLogger(MDConverterStr.class);
    
    private static final String REG_EXP_EXT = "([^\\s]+(\\.(?i)(md))$)";

    private Properties properties;

    private MDConverterItf str;

    private String tmpDirName;

    private String pathRoot;

    public MDConverterStr(Properties prop) {
        properties = prop;

        str = MDConversionUtils.getConversionStr(
            MDConversionUtils.getConversionType(prop));

        tmpDirName = PropertiesUtils.getTempDirectoryPath(prop) +
            properties.getProperty(PropertiesUtils.TMP_FILE_NAME) + 
            "-" +
            GregorianCalendar.getInstance().getTime().getTime();
        
        pathRoot = properties.getProperty(PropertiesUtils.ROOT_PATH);
    }

    public void readFile(File file) throws IOException {
        String source = file.getAbsolutePath();
        String name = file.getName();

        //files to convert   
        if(Pattern.matches(REG_EXP_EXT, name)) { 
             LOGGER.info("Processing file {}", source);
        
             if (!Pattern.matches(properties.getProperty(
                PropertiesUtils.EXCLUDE_FILES), name)) {
                    String result = str.convert(source);
                      
                    if(PropertiesUtils.isRequireCopyFiles(properties)) {
                        FileUtils.copyFileToTmpWithoutRootDirectory(
                            tmpDirName, result, pathRoot);
                    } else {
                        LOGGER.info("The copied files was NOT SELECTED!");
                    }
             }

             LOGGER.info("Conversion COMPLETED!", source);
            
        } else {
            LOGGER.debug("File {} EXCLUDED!!!", source);
        }
    }
 
}
