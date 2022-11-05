package com.example.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtils {

    public static final String EXCLUDE_FILES = "exclude.files";
    
    public static final String ROOT_PATH = "root.path";
    
    public static final String TMP_FILE_NAME = "tmp.name";
    
    public static final String TMP_DIRECTORY = "tmp.dir";
    
    public static final String COPY_FILES = "copy.files";

    public static final String CONVERSION_TYPE = "conversion.type";
    
    private static final String TMP_DIR_DEFAULT = "java.io.tmpdir";

    public static final Properties getProperties(String file)
     throws IOException {
        try {
            Properties prop = new Properties();
        
            prop.load(new FileInputStream(file));

            if(!containsRequiredProperties(prop)) {
                String msg = "Could not load properties!\n";
                msg += msg + "check whether keys exists on the properties file"; 
                msg += msg + " or their values are correct."; 
                throw new IOException(msg);
            }
                        
            return prop;
        } 
        catch (IOException ex) {
            String msg = "Could not load properties from " + file + ".\n";
            msg += msg + "check whether properties file exists on the correct path."; 
            throw new IOException(msg, ex);
        }
    }

    public static String getTempDirectoryPath() {
        return getTempDirectoryPath(null);
    }

    public static String getTempDirectoryPath(Properties prop) {
        String tmpDirName = null;

        if(prop != null)
            tmpDirName = prop.getProperty(TMP_DIRECTORY);

        if(tmpDirName == null) 
            tmpDirName = System.getProperty(TMP_DIR_DEFAULT);

        if (!tmpDirName.endsWith(File.separator)) 
            tmpDirName += File.separator;
        
        return tmpDirName;
    }

    private static boolean containsRequiredProperties(Properties prop) {
        if(
            !prop.containsKey(ROOT_PATH) ||
            !prop.containsKey(EXCLUDE_FILES) ||
            !prop.containsKey(TMP_FILE_NAME) 
        )
            return false;
    
        return true;
    }

    public static boolean isRequireCopyFiles(Properties properties) {
        String value = properties.getProperty(
            PropertiesUtils.COPY_FILES);
        
        if(value != null) {
            return Boolean.valueOf(value);
        }
        else {
            return false;
        }
    }

    

   
}
