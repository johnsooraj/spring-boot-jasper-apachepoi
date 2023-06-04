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

    /**
     * Very important method for adding parameters to XML file, .jrxml file needs non-looping data to show in output file.<br>
     * Example: <b>Invoice Id, Purchase Date</b> etc.<br>
     * <p>
     * Output file using a LOG (image file, .png/.jpg) It is fixed field in the Map Data.<br>
     * <p>
     * For add any new parameter in Invoice file, need to add new tag (<parameter name="iconContext" class="java.lang.String"/>) in .jrxml file
     * and also add same field in this Map (map.put("invoiceId", invoices.getId());).
     *
     * @param invoices
     * @return Map
     * @throws FileNotFoundException
     */
    public final Map<String, Object> generateParamFieldsForInvoice(Invoices invoices, String imagePath) throws FileNotFoundException {
        Map<String, Object> map = new HashMap<>();
        map.put("iconContext", ResourceUtils.getFile("classpath:" + imagePath).getAbsolutePath());
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

    /**
     * Take 'fileLocation' path url from application.properties file.<br>
     * Example : <b>/home/johnsooraj/Workspace/1001_2019-12-28T15:30:36.766.pdf</b><br>
     * Return full path to the location of invoice file need to keep.<br>
     * NB: linux file system is configured, you can change in application.properties file.
     *
     * @param invoiceId      Long invoice id
     * @param fileLocation   String path in properties file
     * @param fileExtenstion Enum value fileExtention
     * @return String - destination path of output
     */
    public final String fileFullPathGenerator(Long invoiceId, String fileLocation, FileExtenstion fileExtenstion) {
        return fileLocation + fileNameGenerator(invoiceId, fileExtenstion);
    }

    public final String fileNameGenerator(Long invoiceId, FileExtenstion fileExtenstion) {
        return invoiceId +
                "_" +
                System.currentTimeMillis() +
                fileExtenstion.extenstion;
    }
}
