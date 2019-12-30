package com.tks.service.impl;

import com.tks.models.Invoices;
import com.tks.models.Product;
import com.tks.models.Users;
import com.tks.repository.InvoiceRepository;
import com.tks.service.InvoiceService;
import com.tks.service.ProductService;
import com.tks.service.UserService;
import com.tks.util.CommonUtil;
import com.tks.util.FileExtenstion;
import com.tks.util.JasperUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Value("${resource.jasper.template.path}")
    private String jasperTemplatePath;

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @Autowired
    JasperUtility jasperUtility;

    @Override
    public Invoices findInvoicesById(Long id) {
        return invoiceRepository.findById(id).get();
    }

    /**
     * Fetch all Product using the 'purchaseItemsIds' from request
     * Check the user exist if exist fetch and add, else create new user.
     * Calculate total bill amount
     *
     * @param invoices
     * @return
     */
    @Override
    public Invoices makePurchaseAndGenerateInvoice(Invoices invoices) {
        List<Product> productList = productService.findProductByIds(invoices.getPurchaseItemsIds());
        Optional<Users> users = userService.findUserByNameAndPhone(invoices.getUser().getCustomerName(), invoices.getUser().getMobile());
        if (users.isPresent()) {
            log.info("Processing invoice for Existing user, id {}", users.get().getId());
            invoices.setUser(users.get());
        }
        invoices.setPurchaseItems(new HashSet<>(productList));
        invoices.setBillAmount(CommonUtil.calculateInvoiceTotal(productList, 0d));
        invoiceRepository.save(invoices);
        log.info("Invoice data inserted and Invoice document started to process");
        generateInvoiceAsyn(invoices);
        return invoices;
    }

    @Override
    public List<Invoices> fetchAllInvoicesByPage(Integer page, Integer count) {
        Pageable pageable = PageRequest.of(page, count, Sort.by("purchaseTime").descending());
        List<Invoices> invoicesList = invoiceRepository.findAll(pageable).stream().collect(Collectors.toList());
        return invoicesList;
    }

    @Async
    public void generateInvoiceAsyn(Invoices invoices) {
        String filePath = jasperUtility.generateInvoice(invoices, "classpath:" + jasperTemplatePath, FileExtenstion.PDF);
        updateInvoiceFilePath(invoices.getId(), filePath);
        log.info("Updated Invoice table after generating output file : {}", filePath);
    }

    private void updateInvoiceFilePath(Long invoiceId, String path) {
        invoiceRepository.updateInvoiceFileURL(invoiceId, path);
    }

}
