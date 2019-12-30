package com.tks.controller;

import com.tks.models.Invoices;
import com.tks.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

    @PostMapping("/{id}")
    public ResponseEntity<Invoices> getInvoiceById(@PathVariable Long id) {
        return new ResponseEntity<>(invoiceService.findInvoicesById(id), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<Invoices>> fetchAllInvoicesByPage(
            @PathParam(value = "page") Integer page,
            @PathParam(value = "count") Integer count) {
        return new ResponseEntity<>(invoiceService.fetchAllInvoicesByPage(page != null ? page : 0, count != null ? count : 100), HttpStatus.OK);
    }
}
