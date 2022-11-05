package com.example.strategies.md.types;

import com.example.utils.FileUtils;
import com.example.utils.FileUtils.FileType;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.nio.charset.Charset;

public class MDConverterPdfStr extends MDConverterDecorator {

    public static final String CHARSET_NAME = "UTF-8";

    private static final Logger LOGGER = LoggerFactory.getLogger(MDConverterPdfStr.class);

    public MDConverterPdfStr(MDConverterHtmlStr c) {
        super(c);
    }

    public String convert(String source) throws IOException {
        String result = super.convert(source);

        String pdf = FileUtils.getAbsolutePathWithNewExtension(
            result, FileType.PDF);
        
        LOGGER.info("Generating pdf file...");
        generatePdfFromHtml(result, pdf);
        LOGGER.info("Pdf file generated.");  
    
        return pdf;
    }

    private void generatePdfFromHtml(String source, 
     String target) throws IOException { 
        try {
            ConverterProperties props = new ConverterProperties();
        
            props.setCharset(Charset.forName(CHARSET_NAME).name());
                
            HtmlConverter.convertToPdf(new FileInputStream(source), 
                new FileOutputStream(target), props);
        } catch(PdfException e) {
            if(e.getMessage().equals(PdfException.DocumentHasNoPages)) {
                LOGGER.info("Generating Blank PDF Document...");
                //Create empty pdf.
                PdfWriter writer = new PdfWriter(target);
                PdfDocument pdfDoc = new PdfDocument(writer);
                pdfDoc.addNewPage();
                Document document = new Document(pdfDoc); 
                document.add(new Paragraph("No Content."));
                document.close();     
                LOGGER.info("Blank PDF Document completed.");
            }
            else {
                throw e;
            }
        }
    }

}
