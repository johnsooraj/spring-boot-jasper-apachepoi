package com.tks.service;

import com.tks.models.Invoices;

import java.util.List;

public interface InvoiceService {

    Invoices findInvoicesById(Long id);

    Invoices makePurchaseAndGenerateInvoice(Invoices invoices);

    List<Invoices> fetchAllInvoicesByPage(Integer page, Integer count);
}
