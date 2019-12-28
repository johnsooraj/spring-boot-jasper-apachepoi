package com.tks.util;

import com.tks.models.Invoices;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;

@Component
public class JasperUtility extends InvoiceGenerator {

    @Value("${resource.file.path}")
    String filepath;

    @Override
    public String generateInvoice(Invoices invoices, String template, FileExtenstion fileExtenstion) {
        String invoiceFilePath = fileFullPathGenerator(invoices.getId(), filepath, fileExtenstion);
        try {
            File file = ResourceUtils.getFile(template);
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(invoices.getPurchaseItems());
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, generateParamFieldsForInvoice(invoices), dataSource);
            switch (fileExtenstion) {
                case PDF: {
                    JasperExportManager.exportReportToPdfFile(jasperPrint, invoiceFilePath);
                    break;
                }
                case DOCX: {
                    break;
                }
                case HTML: {
                    JasperExportManager.exportReportToHtmlFile(jasperPrint, invoiceFilePath);
                    break;
                }
            }
            return invoiceFilePath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
