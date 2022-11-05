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

    private static final String CHARSET_NAME = "UTF-8";

    private static final String NO_CONTENT = "No Content.";

    private static final Logger LOGGER = LoggerFactory.getLogger(MDConverterPdfStr.class);

    public MDConverterPdfStr(MDConverterHtmlStr c) {
        super(c);
    }

    public String convert(String source) throws IOException {
        String result = super.convert(source);

        String pdf = FileUtils.getAbsolutePathWithNewExtension(
            result, FileType.PDF);
        
        LOGGER.debug("Generating pdf file...");
        generatePdfFromHtml(result, pdf);
        LOGGER.debug("PDF file GENERATED.");  
    
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
                LOGGER.debug("Generating Blank PDF Document...");
                //Create empty pdf.
                PdfWriter writer = new PdfWriter(target);
                PdfDocument pdfDoc = new PdfDocument(writer);
                pdfDoc.addNewPage();
                Document document = new Document(pdfDoc); 
                document.add(new Paragraph(NO_CONTENT));
                document.close();     
                LOGGER.debug("Blank PDF Document COMPLETED.");
            }
            else {
                throw e;
            }
        }
    }

}
