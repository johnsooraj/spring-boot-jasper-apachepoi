package com.tks.controller;

import com.tks.models.Invoices;
import com.tks.service.InvoiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;

@Slf4j
@Controller
public class WebController {

    @Autowired
    InvoiceService invoiceService;

    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("hello", "Hello John");
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/view-invoice/{id}")
    public Object downloadInvoiceById(@PathVariable Long id) throws IOException {
        Invoices invoices = invoiceService.findInvoicesById(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + System.currentTimeMillis() + ".pdf");
        return ResponseEntity.ok().headers(headers).body(FileCopyUtils.copyToByteArray(new File(invoices.getInvoiceFileURL())));
    }

}
