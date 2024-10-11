package br.tec.egd;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

public class App {
    public static byte[] convertDocxToPdf(byte[] docxBytes) throws IOException {
        try (XWPFDocument docxDocument = new XWPFDocument(new ByteArrayInputStream(docxBytes));
                ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream()) {
            PdfWriter pdfWriter = new PdfWriter(pdfOutputStream);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document document = new Document(pdfDocument);
            docxDocument.getParagraphs().forEach(paragraph -> {
                document.add(new Paragraph(paragraph.getText()));
            });
            document.close();
            return pdfOutputStream.toByteArray();
        }
    }
}