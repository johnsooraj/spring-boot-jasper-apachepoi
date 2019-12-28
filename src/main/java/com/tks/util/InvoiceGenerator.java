package com.tks.util;

import com.tks.models.Address;
import com.tks.models.Invoices;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * InvoiceGenerator is for parent of Invoices generator based on the request (can generate by Jasper Report or Apache POI library).
 * JasperReport {@link JasperUtility}
 * Apache POI {@link OfficeDocxUtility}
 *
 * @author johnsooraj
 * @since 1.0
 */
public abstract class InvoiceGenerator {

    public abstract String generateInvoice(Invoices invoices, String template, FileExtenstion fileExtenstion);

    public final Map<String, Object> generateParamFieldsForInvoice(Invoices invoices) throws FileNotFoundException {
        Map<String, Object> map = new HashMap<>();
        map.put("iconContext", ResourceUtils.getFile("classpath:static/images/download.png").getAbsolutePath());
        map.put("invoiceId", invoices.getId());
        map.put("customerName", invoices.getUser().getCustomerName());
        map.put("mobile", invoices.getUser().getMobile());
        if (invoices.getUser() != null && invoices.getUser().getAddresses() != null) {
            for (Address address : invoices.getUser().getAddresses()) {
                map.put("addressLine1", address.getAddressLine1());
                map.put("addressLine2", address.getAddressLine2());
                map.put("pincode", address.getPincode());
            }
        }
        map.put("billAmount", invoices.getBillAmount());
        map.put("purchaseTime", invoices.getPurchaseTime());
        return map;
    }

    public final String fileFullPathGenerator(Long invoiceId, String fileLocation, FileExtenstion fileExtenstion) {
        return fileLocation + fileNameGenerator(invoiceId, fileExtenstion);
    }

    public final String fileNameGenerator(Long invoiceId, FileExtenstion fileExtenstion) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(invoiceId);
        stringBuffer.append("_");
        stringBuffer.append(LocalDateTime.now());
        stringBuffer.append(fileExtenstion.extenstion);
        return stringBuffer.toString();
    }
}
