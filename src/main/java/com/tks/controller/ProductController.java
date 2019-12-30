package com.tks.controller;

import com.tks.models.Invoices;
import com.tks.service.InvoiceService;
import com.tks.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    InvoiceService invoiceService;

    @GetMapping("/")
    public Object getPurchaiseDatiles(
            @PathParam(value = "page") Integer page,
            @PathParam(value = "count") Integer count) {
        return productService.getAllProduct(page != null ? page : 0, count != null ? count : 10);
    }

    @PostMapping(value = "/purchase")
    public ResponseEntity<Invoices> makePurchase(@RequestBody Invoices invoices) throws Exception {
        if (invoices.getPurchaseItemsIds().length >= 1 &&
                invoices.getUser().getCustomerName() != null &&
                invoices.getUser().getMobile() != null &&
                invoices.getUser().getMobile().length() > 0 &&
                invoices.getUser().getCustomerName().length() > 0) {
            Invoices invoices1 = invoiceService.makePurchaseAndGenerateInvoice(invoices);
            return new ResponseEntity<>(invoices1, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
