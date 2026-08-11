package com.mobilemart.backend.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.mobilemart.backend.dto.OrderResponse;
import com.mobilemart.backend.dto.OrderItemResponse;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.awt.Color;

@Service
public class InvoicePdfService {

    public byte[] generateInvoice(OrderResponse order) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Document document = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Fonts
            Font boldTitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22, Color.DARK_GRAY);
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, Color.DARK_GRAY);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Color.BLACK);
            Font smallFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Color.BLACK);
            Font boldSmallFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.BLACK);

            // Header
            Paragraph title = new Paragraph("MOBILEMART", boldTitleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            
            Paragraph subtitle = new Paragraph("Premium Mobile Store", smallFont);
            subtitle.setAlignment(Element.ALIGN_CENTER);
            document.add(subtitle);
            
            document.add(new Paragraph("\nINVOICE\n\n", headerFont));

            // Order & Customer Details
            PdfPTable detailsTable = new PdfPTable(2);
            detailsTable.setWidthPercentage(100);
            detailsTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");
            String dateFormatted = order.getCreatedAt() != null ? order.getCreatedAt().format(formatter) : "N/A";

            detailsTable.addCell(new Phrase("Invoice Number: INV-" + order.getOrderId(), normalFont));
            detailsTable.addCell(new Phrase("Customer Info:", boldSmallFont));
            
            detailsTable.addCell(new Phrase("Order ID: " + order.getOrderId(), normalFont));
            detailsTable.addCell(new Phrase("ID: " + order.getUserId(), normalFont));
            
            detailsTable.addCell(new Phrase("Order Date: " + dateFormatted, normalFont));
            
            if (order.getShippingAddress() != null) {
                String fullAddress = order.getShippingAddress().getStreetAddress() + ", " + order.getShippingAddress().getCity();
                detailsTable.addCell(new Phrase("Address: " + fullAddress, smallFont));
            } else {
                detailsTable.addCell(new Phrase("Address: N/A", smallFont));
            }
            
            document.add(detailsTable);
            document.add(new Paragraph("\n---------------------------------------------------------------------------------------------------------------------\n\n", smallFont));

            // Products Table
            document.add(new Paragraph("PRODUCTS\n\n", headerFont));
            
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{4, 2, 1, 2, 2});

            // Table Header
            String[] headers = {"Product Name", "Category", "Qty", "Unit Price", "Subtotal"};
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h, boldSmallFont));
                cell.setBackgroundColor(Color.LIGHT_GRAY);
                cell.setPadding(5);
                table.addCell(cell);
            }

            // Table Body
            if (order.getItems() != null) {
                for (OrderItemResponse item : order.getItems()) {
                    table.addCell(new Phrase(item.getProductName(), smallFont));
                    table.addCell(new Phrase(item.getBrand() != null ? item.getBrand() : "N/A", smallFont));
                    table.addCell(new Phrase(String.valueOf(item.getQuantity()), smallFont));
                    table.addCell(new Phrase("Rs. " + item.getPricePerUnit(), smallFont));
                    table.addCell(new Phrase("Rs. " + item.getTotalPrice(), smallFont));
                }
            }

            document.add(table);
            document.add(new Paragraph("\n---------------------------------------------------------------------------------------------------------------------\n\n", smallFont));

            // Summary
            document.add(new Paragraph("ORDER SUMMARY\n\n", headerFont));
            
            PdfPTable sumTable = new PdfPTable(2);
            sumTable.setWidthPercentage(50);
            sumTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
            sumTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);

            sumTable.addCell(new Phrase("Subtotal:", normalFont));
            sumTable.addCell(new Phrase("Rs. " + order.getTotalAmount(), normalFont));
            
            sumTable.addCell(new Phrase("Discount:", normalFont));
            sumTable.addCell(new Phrase("Rs. 0.00", normalFont));
            
            sumTable.addCell(new Phrase("Tax:", normalFont));
            sumTable.addCell(new Phrase("Inclusive", normalFont));
            
            sumTable.addCell(new Phrase("Shipping:", normalFont));
            sumTable.addCell(new Phrase("FREE", normalFont));
            
            document.add(sumTable);
            document.add(new Paragraph("\n"));
            
            Paragraph totalP = new Paragraph("TOTAL: Rs. " + order.getTotalAmount(), headerFont);
            totalP.setAlignment(Element.ALIGN_RIGHT);
            document.add(totalP);
            
            Paragraph statusP = new Paragraph("Payment Status: " + order.getStatus(), boldTitleFont);
            statusP.setAlignment(Element.ALIGN_RIGHT);
            document.add(statusP);

            document.close();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return out.toByteArray();
    }
}
