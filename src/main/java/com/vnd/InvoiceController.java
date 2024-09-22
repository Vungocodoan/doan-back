package com.vnd;

import com.vnd.services.InvoiceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/download-invoice")
    public String downloadInvoice() {
        try {
            invoiceService.downloadInvoiceZip();
            return "Invoice downloaded successfully!";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to download invoice.";
        }
    }
}
