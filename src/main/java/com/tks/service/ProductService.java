package com.tks.service;

import com.tks.models.Invoices;
import com.tks.models.Product;

import java.util.List;

public interface ProductService {

    /**
     * Do not remove this method, We need some product in DB as we are not writing api for add Product
     *
     * @return
     */
    List<Product> addDummyProductOnStartup();

    List<Product> getAllProduct(Integer page, Integer count);

    List<Product> findProductByIds(Long... ids);
}
