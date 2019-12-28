package com.tks.service.impl;

import com.tks.models.Invoices;
import com.tks.models.Product;
import com.tks.repository.ProductRepository;
import com.tks.service.InvoiceService;
import com.tks.service.ProductService;
import com.tks.util.JasperUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    InvoiceService invoiceService;

    @Autowired
    JasperUtility jasperUtility;

    @Override
    public List<Product> getAllProduct(Integer page, Integer count) {
        Pageable pageable = PageRequest.of(page, count, Sort.by("createTime").descending());
        return productRepository.findAll(pageable).get().collect(Collectors.toList());
    }

    @Override
    public List<Product> findProductByIds(Long... ids) {
        return productRepository.findByIdIn(ids);
    }

    @Override
    public List<Product> addDummyProductOnStartup() {
        return productRepository.saveAll(getDummyProduct());
    }

    private List<Product> getDummyProduct() {
        return Arrays.asList(
                new Product("Soap", "Rex", 12.00),
                new Product("Washing Soap", "Rex2", 52.00),
                new Product("Maggi", "MAG1", 20.00),
                new Product("Rice", "Rex", 80.00),
                new Product("Sugar", "Rex", 120.00),
                new Product("Brash", "Rex", 35.00),
                new Product("Dish Washer", "Rex", 40.00)
        );
    }
}
