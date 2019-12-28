package com.tks.util;

import com.tks.models.Invoices;
import org.springframework.stereotype.Component;

@Component
public class OfficeDocxUtility extends InvoiceGenerator{
    @Override
    public String generateInvoice(Invoices invoices, String template, FileExtenstion fileExtenstion) {
        return null;
    }
}
