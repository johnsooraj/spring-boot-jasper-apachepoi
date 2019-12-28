package com.tks.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String customerName;

    @Column(unique = true, nullable = false)
    private String mobile;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Set<Address> addresses;

    @UpdateTimestamp
    private Timestamp timestamp;

    @CreationTimestamp
    private Timestamp createTime;
    private byte status;

    public Users(String customerName, String mobile, Set<Address> addresses) {
        this.customerName = customerName;
        this.mobile = mobile;
        this.addresses = addresses;
    }
}
