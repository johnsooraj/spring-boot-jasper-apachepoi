package com.tks.repository;

import com.tks.models.Invoices;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface InvoiceRepository extends JpaRepository<Invoices, Long> {

    @Modifying(clearAutomatically = true)
    @Query("update Invoices inv set inv.invoiceFileURL=:invoiceFileURL where inv.id=:id")
    void updateInvoiceFileURL(Long id, String invoiceFileURL);

    Page<Invoices> findAll(Pageable pageable);
}
