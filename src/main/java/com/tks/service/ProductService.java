package com.tks.service;

import com.tks.models.Invoices;
import com.tks.models.Product;

import java.util.List;

public interface ProductService {

    List<Product> addDummyProductOnStartup();

    List<Product> getAllProduct(Integer page, Integer count);

    List<Product> findProductByIds(Long... ids);
}
