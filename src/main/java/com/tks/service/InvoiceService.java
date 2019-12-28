package com.tks.service;

import com.tks.models.Invoices;

public interface InvoiceService {

    Invoices findInvoicesById(Long id);

    Invoices makePurchaseAndGenerateInvoice(Invoices invoices);
}
