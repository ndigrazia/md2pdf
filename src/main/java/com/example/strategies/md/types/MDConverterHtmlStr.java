package com.example.strategies.md.types;

import com.example.utils.FileUtils;
import com.example.utils.FileUtils.FileType;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

public class MDConverterHtmlStr extends MDConverterDecorator { 

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);

    private FileType fileType;

    public MDConverterHtmlStr(MDConverterBasicStr c) {
        this(c, null);
    }

    public MDConverterHtmlStr(MDConverterBasicStr c, 
     FileType fileType) {
        super(c);

        if(fileType == null)
            fileType = FileType.HTML;

        this.fileType = fileType;
    }

    public String convert(String source) throws IOException {
        String result = super.convert(source);

        String html = FileUtils.getAbsolutePathWithNewExtension(
            result, fileType);
        
        LOGGER.info("Generating html file...");
        generateHmlFromMD(result, html);
        LOGGER.info("Html file generated.");

        return html;
    }

    public void convert(String source, String target) throws IOException {
        LOGGER.info("Generating html file...");
        generateHmlFromMD(source, target);
        LOGGER.info("Html file generated.");
    }

    private void generateHmlFromMD(String source, 
     String target) throws IOException {
       FileReader fr = new FileReader(source);
       BufferedReader br = new BufferedReader(fr);
       
       PrintWriter fw = new PrintWriter(target);
       BufferedWriter bw = new BufferedWriter(fw);

       Parser parser = Parser.builder().build();
       Node document = parser.parseReader(br);
       HtmlRenderer htmlRenderer = HtmlRenderer.builder().build();
       
       htmlRenderer.render(document, bw);
       
       bw.flush();
       bw.close();
       fw.close();

       br.close();
       fr.close();
   }

}
