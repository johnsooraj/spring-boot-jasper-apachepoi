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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;

@Slf4j
@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @Autowired
    JasperUtility jasperUtility;

    @Override
    public List<Invoices> addDummyInvloiceOnStartup() {
        // fetching only 3 product from DB
        List<Product> purchaseItems = productService.getAllProduct(0, 3);
        Invoices invoices = new Invoices();
        invoices.setPurchaseItems(new HashSet<>(purchaseItems));
        invoices.setBillAmount(CommonUtil.calculateInvoiceTotal(purchaseItems, 0d));
        Users users = new Users();
        users.setCustomerName("Sangi");
        users.setMobile("98645798246785");
        users.setAddresses(null);
        invoices.setUser(users);
        invoiceRepository.save(invoices);
        return null;
    }

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
            invoices.setUser(users.get());
        }
        invoices.setPurchaseItems(new HashSet<>(productList));
        invoices.setBillAmount(CommonUtil.calculateInvoiceTotal(productList, 0d));
        invoiceRepository.save(invoices);
        generateInvoiceAsyn(invoices);
        return invoices;
    }

    @Async
    public void generateInvoiceAsyn(Invoices invoices) {
        log.info("async invoice generator");
        String filePath = jasperUtility.generateInvoice(invoices, "classpath:templates/jasper-templates/invoice1.jrxml", FileExtenstion.PDF);
        updateInvoiceFilePath(invoices.getId(), filePath);
    }

    private void updateInvoiceFilePath(Long invoiceId, String path) {
        invoiceRepository.updateInvoiceFileURL(invoiceId, path);
    }

}
