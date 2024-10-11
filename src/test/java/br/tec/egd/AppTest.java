package br.tec.egd;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfCanvasProcessor;
import com.itextpdf.kernel.pdf.canvas.parser.listener.SimpleTextExtractionStrategy;

public class AppTest {
    String inputFilePath = "src/test/java/br/tec/egd/test.docx";

    @Test
    public void PDFGerado() throws IOException {

        FileInputStream fis = new FileInputStream(new File(inputFilePath));
        byte[] docxBytes = fis.readAllBytes();
        fis.close();

        byte[] pdfBytes = App.convertDocxToPdf(docxBytes);

        try (PdfDocument pdfDocument = new PdfDocument(new PdfReader(new ByteArrayInputStream(pdfBytes)))) {
            SimpleTextExtractionStrategy strategy = new SimpleTextExtractionStrategy();
            PdfCanvasProcessor processor = new PdfCanvasProcessor(strategy);
            for (int i = 1; i <= pdfDocument.getNumberOfPages(); i++) {
                processor.processPageContent(pdfDocument.getPage(i));
            }
            String pdfText = strategy.getResultantText();

            Assert.assertTrue(pdfText.contains("Olá. Teste"));
        }
    }
}
