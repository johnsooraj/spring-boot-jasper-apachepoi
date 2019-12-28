package com.tks.service;

import com.tks.models.Invoices;

import java.util.List;

public interface InvoiceService {

    List<Invoices> addDummyInvloiceOnStartup();

    Invoices findInvoicesById(Long id);

    Invoices makePurchaseAndGenerateInvoice(Invoices invoices);
}
