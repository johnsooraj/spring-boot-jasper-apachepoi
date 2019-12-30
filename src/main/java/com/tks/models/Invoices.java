package com.tks.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@SequenceGenerator(name = "invoiceSeq", allocationSize = 10, initialValue = 1000, sequenceName = "invoice_seq_1")
public class Invoices {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invoiceSeq")
    private Long id;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Users user;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Product> purchaseItems;

    private Double billAmount;

    private String invoiceFileURL;

    @UpdateTimestamp
    private Timestamp timestamp;

    @CreationTimestamp
    private Timestamp purchaseTime;
    private byte status = 1;

    @Transient
    private Long[] purchaseItemsIds;
}
