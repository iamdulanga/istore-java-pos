package services;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.itextpdf.io.exceptions.IOException;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import javax.swing.*;
import java.nio.file.FileSystems;

/**
 * Service class for generating invoices/receipts.
 * Handles PDF generation with barcode.
 */
public class InvoiceService {

    private static void generateBarcode(String data, String filePath) throws Exception {
        int width = 200; // Adjusted width for a smaller barcode
        int height = 50;  // Adjusted height for a smaller barcode

        BitMatrix bitMatrix = new MultiFormatWriter().encode(data, BarcodeFormat.CODE_128, width, height);
        com.google.zxing.client.j2se.MatrixToImageWriter.writeToPath(bitMatrix, "PNG", FileSystems.getDefault().getPath(filePath));
    }

    public static void generateInvoice(JTable tblInv, JTextField txtTot, JTextField txtPay, JTextField txtBal) throws WriterException, Exception {
        try {
            // make the file path
            String desktopPath = System.getProperty("user.home") + "/Desktop/";
            String pdfFilePath = desktopPath + "Receipt.pdf";
            String barcodeFilePath = desktopPath + "Barcode.png";

            // Create a barcode for the phone number
            String phoneNumber = "+5181561616";
            generateBarcode(phoneNumber, barcodeFilePath);

            PdfWriter writer = new PdfWriter(pdfFilePath);
            try (var pdf = new com.itextpdf.kernel.pdf.PdfDocument(writer)) {

                // Set custom page size (e.g., 200x300 points)
                PageSize customPageSize = new PageSize(300, 700);
                pdf.setDefaultPageSize(customPageSize);

                // the receipt content
                try (var document = new Document(pdf)) {
                    document.setMargins(10, 10, 10, 10); // Set smaller margins

                    // Header
                    document.add(new Paragraph("APPLE COMPUTERS USA").setBold().setFontSize(14).setTextAlignment(TextAlignment.CENTER));
                    document.add(new Paragraph("1, Menlo Park, California").setTextAlignment(TextAlignment.CENTER));
                    document.add(new Paragraph("Apple Cool Line : +1125 689 4178").setTextAlignment(TextAlignment.CENTER));
                    document.add(new Paragraph("\n"));

                    // Table
                    Table table = new Table(5);
                    table.addCell("Item ID");
                    table.addCell("Name");
                    table.addCell("Category");
                    table.addCell("Quantity");
                    table.addCell("Price($)");

                    /* Add data from the table
                    for (int i = 0; i < tblInv.getRowCount(); i++) {
                        for (int j = 0; j < tblInv.getColumnCount(); j++) {
                            table.addCell(tblInv.getValueAt(i, j).toString());
                        }
                    }*/

                    // Add data from the table with custom formatting
                    for (int i = 0; i < tblInv.getRowCount(); i++) {
                        table.addCell(new Paragraph(tblInv.getValueAt(i, 0).toString())
                                .setMargin(0)
                                .setPadding(0)
                                .setTextAlignment(TextAlignment.LEFT)
                                .setFontColor(ColorConstants.BLACK));
                        table.addCell(new Paragraph(tblInv.getValueAt(i, 1).toString())
                                .setMargin(0)
                                .setPadding(0)
                                .setTextAlignment(TextAlignment.CENTER)
                                .setFontColor(ColorConstants.BLACK));
                        table.addCell(new Paragraph(tblInv.getValueAt(i, 2).toString())
                                .setMargin(0)
                                .setPadding(0)
                                .setTextAlignment(TextAlignment.RIGHT)
                                .setFontColor(ColorConstants.BLACK));
                        table.addCell(new Paragraph(tblInv.getValueAt(i, 3).toString())
                                .setMargin(0)
                                .setPadding(0)
                                .setTextAlignment(TextAlignment.RIGHT)
                                .setFontColor(ColorConstants.BLACK));
                        table.addCell(new Paragraph(tblInv.getValueAt(i, 4).toString())
                                .setMargin(0)
                                .setPadding(0)
                                .setTextAlignment(TextAlignment.RIGHT)
                                .setFontColor(ColorConstants.BLACK));
                    }

                    // Set alignment for the entire table
                    table.setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER).setBorder(Border.NO_BORDER);
                    document.add(table);

                    // Total and payment information
                    document.add(new Paragraph("Total Price : " + txtTot.getText()).setTextAlignment(TextAlignment.CENTER));
                    document.add(new Paragraph("Payment : " + txtPay.getText()).setTextAlignment(TextAlignment.CENTER));
                    document.add(new Paragraph("Balance: " + txtBal.getText()).setTextAlignment(TextAlignment.CENTER));
                    document.add(new Paragraph("\n\n"));

                    // Barcode
                    Image barcodeImage = new Image(ImageDataFactory.create(barcodeFilePath));
                    barcodeImage.setWidth(100); // Adjusted width for a smaller barcode
                    barcodeImage.setHeight(25);  // Adjusted height for a smaller barcode
                    document.add(barcodeImage);

                    // Footer
                    document.add(new Paragraph("Wish You A Merry Christmas!\nThank You For Buying From Us And Your Trust!\nCome Again!")
                            .setTextAlignment(TextAlignment.CENTER)
                            .setFontColor(ColorConstants.BLUE));
                }
            }
            JOptionPane.showMessageDialog(null, "Invoice Generated Successfully at:\n" + pdfFilePath, "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error generating invoice:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
