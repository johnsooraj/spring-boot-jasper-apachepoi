package com.tks.util;

import com.tks.models.Product;

import java.util.List;

public class CommonUtil {

    public static Double calculateInvoiceTotal(List<Product> purchaseItems, Double discount) {
        Double price = 0.0;
        for (Product product : purchaseItems) {
            price = price + product.getPrice();
        }
        return price;
    }
}
