package com.tks.controller;

import com.tks.models.Invoices;
import com.tks.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

    @PostMapping("/{id}")
    public ResponseEntity<Invoices> getInvoiceById(@PathVariable Long id) {
        return new ResponseEntity<>(invoiceService.findInvoicesById(id), HttpStatus.OK);
    }
}
