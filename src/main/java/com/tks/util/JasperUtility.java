package com.tks.util;

import com.tks.models.Invoices;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

@Slf4j
@Component
public class JasperUtility extends InvoiceGenerator {

    @Value("${resource.file.path}")
    String outputFilePath;

    @Value("${invoice.image.path}")
    String invoiceLogImageURL;

    /**
     * Abstract method from {@link InvoiceGenerator}, Processing the invoice with help of Jasper Report<br>
     *
     * @param invoices       - object of {@link Invoices} to fill the .jsxml template
     * @param template       - File path to take file of .jrxml from resource directory.
     * @param fileExtenstion - enum of {@link FileExtenstion}
     * @return
     */
    @Override
    public String generateInvoice(Invoices invoices, String template, FileExtenstion fileExtenstion) {
        String invoiceFilePath = fileFullPathGenerator(invoices.getId(), outputFilePath, fileExtenstion);
        try {
            File file = ResourceUtils.getFile(template);
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(invoices.getPurchaseItems());
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, generateParamFieldsForInvoice(invoices, invoiceLogImageURL), dataSource);
            log.info("Japser Compilation Successfull and ready to Export in {} format.", fileExtenstion.extenstion);
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            log.error("Resource util failed to fetch file {}", template);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
