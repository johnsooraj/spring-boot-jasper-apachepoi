package com.tks.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(
        uniqueConstraints =
        @UniqueConstraint(columnNames = {"item", "batch"})
)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String item;
    private String batch;

    @Column(nullable = false)
    private Double price;

    @UpdateTimestamp
    private Timestamp timestamp;

    @CreationTimestamp
    private Timestamp createTime;
    private byte status = 1;

    public Product(String item, String batch, Double price) {
        this.item = item;
        this.batch = batch;
        this.price = price;
    }
}
