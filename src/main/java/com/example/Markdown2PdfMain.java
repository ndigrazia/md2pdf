package com.example;

import java.util.GregorianCalendar;
import java.util.Properties;

import com.example.strategies.ConverterItf;
import com.example.utils.FileUtils;
import com.example.utils.PropertiesUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.File;

import com.example.strategies.md.MDConverterStr;

public class Markdown2PdfMain {

    private static final String CONFIG_PROPERTIES = "conf/config.properties";
    
    private static final Logger LOGGER = LoggerFactory.getLogger(Markdown2PdfMain.class);

    public Markdown2PdfMain() {
    }

    public void readAndConvert() throws IOException {
        Properties prop = PropertiesUtils.getProperties(CONFIG_PROPERTIES);

        readAndConvertAllFiles(new File(prop.getProperty(
            PropertiesUtils.ROOT_PATH)), getReadAndConvertStrategy(prop));   
    }
    
    private ConverterItf getReadAndConvertStrategy(Properties prop) {
        return new MDConverterStr(prop);
    }

    private void readAndConvertAllFiles(File file, 
     ConverterItf str) throws IOException {
        if(FileUtils.exists(file)) {
            if(!file.isDirectory() && file.canRead()) {
                str.readFile(file);
            }
            else {
                File contents[] = file.listFiles();

                for(int i=0; i<contents.length; i++) 
                    readAndConvertAllFiles(contents[i], str);
            }
        }
    }

    public static void main(String args[]) { 
        try {
            Markdown2PdfMain md2pdf = new Markdown2PdfMain();
            LOGGER.info("Launch of the conversion process. {}", 
            GregorianCalendar.getInstance().getTime());
            md2pdf.readAndConvert();
            LOGGER.info("Conversion COMPLETED. {} ", 
                GregorianCalendar.getInstance().getTime());
        }catch(Exception e) {
            LOGGER.error("Conversion FAILED. {} ", 
                GregorianCalendar.getInstance().getTime(), e);
        }
    }

}
