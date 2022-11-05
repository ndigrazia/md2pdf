package com.example.utils;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);

    public static final String getAbsolutePathWithNewExtension(
        String absolutePath, FileType type) {
        int i = absolutePath.indexOf(".", 0);
        
        StringBuilder pathName = new StringBuilder();

        pathName.append(absolutePath.substring(0, i));
        pathName.append(type.getExtWithDot());
        
        return pathName.toString();
    }

    public static String getPathWithoutRootPath(
     String absolutePath, String rootPath) {
        if(absolutePath != null && rootPath != null) {
            int absolutePathLength = absolutePath.length();
            int rootPathLength = rootPath.length();
            return absolutePath.substring(rootPathLength, absolutePathLength);
        }
        
        return null;
    }

    public static boolean exists(File file) {
        if (file != null && file.exists()) {
         return true;
        }
        LOGGER.error("File {} NO FOUND. ", file.getAbsolutePath());
        return false;
    }

    public static boolean mkdir(String dir) {
        boolean success = exists(new File(dir)) ? true : 
            new File(dir).mkdir();
        return success;
    }
      
    public static boolean mkdirs(String dir) {
        boolean success = exists(new File(dir)) ? true : 
            new File(dir).mkdirs();
        return success;
    }
    
    public static String getPathWithoutFilename(File file) {
        if (file != null) {
            String filepath = file.getAbsolutePath();
            if (file.isDirectory()) {
               return filepath;
            } else {
                String filename = file.getName();
                // Construct path without file name.
                String pathwithoutname = filepath.substring(0,
                        filepath.length() - filename.length());
                if (pathwithoutname.endsWith("/")) {
                    pathwithoutname = pathwithoutname.substring(0, 
                        pathwithoutname.length() - 1);
                }

                return pathwithoutname;
            }
        }
        return null;
    }
 
    public static boolean copyFile(File source, File target) {
        try {
            org.apache.commons.io.FileUtils.copyFile(source, target);
            return true;
        } catch (IOException e) {
            LOGGER.error("ERROR Copying file {}.", source.getName(), e);
            return false;
        }
    }

    public static void copyFileToTmpWithoutRootDirectory(String tmpDirName, 
     String source, String root) {
       String target = tmpDirName + getPathWithoutRootPath(source, root);
       
       LOGGER.info("Copying file to {} ...", target);
       Boolean r = FileUtils.copyFile(new File(source), new File(target));
       LOGGER.info("File COPIED [{}].", r);
   }

    public enum FileType { 
        HTML("html"), 
        PDF("pdf"), 
        MD("md"), 
        TMP("change"),
        CVR("convert");
        
        private String ext;

        FileType(String ext) {
            this.ext = ext ;
        }

        public String getExt () {
            return ext;
        }

        public String getExtWithDot() {
            return "." + ext;
        }        
    };

}
